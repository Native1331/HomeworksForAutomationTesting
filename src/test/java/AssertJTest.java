import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.*;
import static io.restassured.RestAssured.given;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssertJTest {

    private int idToDelete;

    private RequestSpecification basicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .addQueryParam("page", 0)
            .addQueryParam("size", 10)
            .log(LogDetail.ALL)
            .build();

    private RequestSpecification postBasicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .log(LogDetail.ALL)
            .build();
    private RequestSpecification deleteBasicRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .addQueryParam("id", idToDelete)
            .log(LogDetail.ALL)
            .build();

    @Test
    @Tag("AssertJTest")
    void fullGoodsWorkflowTest() {

        Response postResponse = given()
                .spec(postBasicRQ)
                .contentType(JSON)
                .auth().basic("admin", "secret123")
                .body("{\"name\":\"Rose\",\"price\":1}")
                .post("/goods/add");

        assertThat(postResponse.statusCode())
                .as("Код ответа при создании должен быть 200")
                .isEqualTo(200);

        assertThat(postResponse.jsonPath().getString("message"))
                .as("В ответе должно быть сообщение об успешном сохранении")
                .isEqualTo("success");

        Response getResponse = given()
                .spec(basicRQ)
                .contentType(JSON)
                .auth().basic("admin", "secret123")
                .get("/goods/list");

        assertThat(getResponse.statusCode())
                .as("Код ответа при получении списка должен быть 200")
                .isEqualTo(200);

        List<String> goodsNames = getResponse.jsonPath().getList("goods.name", String.class);

        assertThat(goodsNames)
                .as("В ответе должно быть имя созданного товара 'Rose'")
                .contains("Rose");

        Integer idToDelete = getResponse.jsonPath().get("goods.find { it.name == 'Rose' }.id");

        assertThat(idToDelete)
                .as("ID созданного товара 'Rose' не должен быть null")
                .isNotNull();
        assertThat(idToDelete)
                .as("ID товара должен быть валидным (больше 0)")
                .isPositive();

        Response deleteResponse = given()
                .spec(postBasicRQ)
                .auth().basic("admin", "secret123")
                .pathParam("id", idToDelete)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().all()
                .extract()
                .response();


        assertThat(deleteResponse.statusCode())
                .as("Код ответа при удалении должен быть 200")
                .isEqualTo(200);
        String responseText = deleteResponse.asString();

        assertThat(responseText)
                .contains("Good with id")
                .contains(String.valueOf(idToDelete))
                .contains("deleted successfully");  }}