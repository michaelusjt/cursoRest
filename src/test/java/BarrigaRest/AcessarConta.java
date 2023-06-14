package BarrigaRest;

import core.baseTest;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AcessarConta extends baseTest {

    @Test
    public void naoDeveAcessarApiSemToken(){

        given()
                .log().all()
                .when()
                .get("/contas")
                .then()
                .log().all()
                .statusCode(401)


        ;
    }
}
