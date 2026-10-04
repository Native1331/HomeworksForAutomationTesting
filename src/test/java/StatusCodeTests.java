import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatusCodeTests {
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
    private Object idOfGoods;

    @Test
    @Order(1)
    @Tag("StatusCodeTest")
    void statusCodeGetNewGoodTest() {
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name":"MappetShow",
                        "price": 5
                        }
                        
                        """)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .log().all();
        idOfGoods = given()
                .spec(basicRQ)
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem("MappetShow"))
                .extract()
                .path("goods.find { it.name == 'MappetShow' }.id");
    }

    @Test
    @Order(2)
    @Tag("StatusCodeTest")
    void hasThisProductInGoodsTest() {
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name":"MappetShow",
                        "price": 5
                        }
                        
                        """)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(400)
                .log().all();
        /*"message": "Good with name 'MappetShow' already exists!"*/
    }

    @Test
    @Order(3)
    @Tag("StatusCodeTest")
    void wrongMethodtest() {
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name":"MappetShow",
                        "price": 5
                        }
                        
                        """)
                .when()
                .get("/goods/add")
                .then()
                .statusCode(400)
                .log().all();
    }

    @Test
    @Order(4)
    @Tag("StatusCodeTest")
    void dontHaveBodyTest() {
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("""
                        {}
                        
                        """)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(400)
                .log().all();
        //  "name": "Name cannot be empty"
    }

    @Test
    @Order(5)
    @Tag("StatusCodeTest")
    void wrongContentTypeTest() {
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.TEXT)
                .body("""
                        {
                        "name":"MappetShow",
                        "price": 5
                        }
                        
                        """)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(415)
                .log().all();
        //  "error": "Unsupported Media Type",
    }

    @Test
    @Order(6)
    @Tag("StatusCodeTest")
    void notAuthTest() {
        given()
                .spec(postBasicRQ)
                .contentType(ContentType.TEXT)
                .auth()
                .basic("test", "secret1234")
                .body("""
                        {
                        "name":"MappetShow",
                        "price": 5
                        }
                        
                        """)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(401)
                .log().all();

    }

    @Test
    @Order(7)
    @Tag("StatusCodeTest")
    void wrongAuthTest() {
        given()
                .spec(postBasicRQ)
                .contentType(ContentType.TEXT)
                .body("""
                        {
                        "name":"MappetShow",
                        "price": 5
                        }
                        
                        """)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(401)
                .log().all();

    }

    @Test
    @Order(8)
    @Tag("StatusCodeTest")
    void getTest() {
        given()
                .spec(basicRQ)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    @Tag("StatusCodeTest")
    @Order(9)
    void getWithoutQueryParamTest() {
        given()
                .spec(postBasicRQ)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    @Tag("StatusCodeTest")
    @Order(10)
    void wrongMethodTest() {
        given()
                .spec(basicRQ)
                .when()
                .auth()
                .basic("admin", "secret1234")
                .post("/goods/list")
                .then()
                .statusCode(401)
                .log().all();
    }

    @Test
    @Tag("StatusCodeTest")
    @Order(11)
    void patchTest() {
        Assertions.assertNotNull(idOfGoods, "ID товара пустой. Возможно, первый тест упал.");
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", idOfGoods)
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name":"Python",
                        "price": 5
                        }
                        
                        """)
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    @Tag("StatusCodeTest")
    @Order(12)
    void wrongIdPatchTest() {
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", "0")
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name":"Python",
                        "price": 5
                        }
                        
                        """)
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    @Tag("StatusCodeTest")
    @Order(13)
    void emptyBodyPatchTest() {
        Assertions.assertNotNull(idOfGoods, "ID товара пустой. Возможно, первый тест упал.");
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", idOfGoods)
                .contentType(ContentType.JSON)
                .body("""
                        {
                        
                        "price": 5
                        }
                        
                        """)
                .when()
                .patch("/goods/{id}")
                .then()
                .statusCode(400)
                .log().all();
    }

    @Test
    @Tag("StatusCodeTest")
    @Order(14)
    void getByIdTest(){
        Assertions.assertNotNull(idOfGoods, "ID товара пустой. Возможно, первый тест упал.");
        given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", idOfGoods)
                .when()
                .get("/goods/{id}")
                .then()
                .statusCode(200)
                .log().all();
    }
    @Test
    @Tag("StatusCodeTest")
    @Order(15)
    void getByEmptyIdTest(){
           given()
                .spec(postBasicRQ)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", "0")
                .when()
                .get("/goods/{id}")
                .then()
                .statusCode(404)
                .log().all();
    }



    @Test
    @Tag("StatusCodeTest")
    @Order(16)
    void deleteTest() {
        given()
                .spec(postBasicRQ)
                .auth().basic("admin", "secret123")
                .pathParam("id", idOfGoods)
                .when()
                .delete("/goods/{id}")
                .then()
                .statusCode(200)
                .log().all();

    }
    @Test
    @Tag("StatusCodeTest")
    @Order(17)
    void deleteTwiceTest() {
        given()
                .spec(postBasicRQ)
                .auth().basic("admin", "secret123")
                .pathParam("id", idOfGoods)
                .when()
                .delete("/goods/{id}")
                .then()
                .statusCode(404)
                .log().all();

    }}



