import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;

public class RestAssuredTests {
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


    @Test
    @Tag("RestAssuredTests")
    void getTest() {
        given()
                .spec(basicRQ)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty())
                .log().all();
    }

    @Test
    @Tag("RestAssuredTests")
    void getTesWithRequestSpecification() {
        given()
                .spec(basicRQ)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty())
                .log().all();
    }


    @Test
    @Tag("RestAssuredTests")
    void fullGoodsWorkflowTest() {

        given()
                .spec(postBasicRQ)
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name":"Doll",
                        "price": 0.5
                        }
                        """)
                .auth().basic("admin", "secret123")
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .log().all();


        int idForDelete = given()
                .spec(basicRQ)
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem("Doll"))
                .extract()
                .path("goods.find { it.name == 'Doll' }.id");


        given()
                .spec(postBasicRQ)
                .auth().basic("admin", "secret123")
                .pathParam("id", idForDelete)
                .when()
                .delete("/goods/{id}")
                .then()
                .statusCode(200)
                .log().all();
    }
}



