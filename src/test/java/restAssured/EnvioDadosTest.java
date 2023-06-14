package restAssured;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class EnvioDadosTest {

    // Neste teste estamos efetuando GET por query
    @Test
    public void deveEnviarValorViaQuery() {

        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/v2/users?format=json")   // Aqui podemos passar xml ou json para validar nos testes
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)                             // Aqui podemos passar xml ou json para validar nos testes
        ;
    }

    // Neste teste estamos efetuando GET por query
    @Test
    public void deveEnviarValorViaQueryParam() {

        given()
                .log().all()
                .queryParam("format", "xml")
                .queryParam("outras", "coisa")
                .when()
                .get("https://restapi.wcaquino.me/v2/users") //Invés de passar na query, mandamos via queryParam
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .contentType(Matchers.containsString("utf-8"))
        ;
    }

    // Neste teste estamos efetuando GET por query
    @Test
    public void deveEnviarValorViaQueryHeader() {

        given()
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .get("https://restapi.wcaquino.me/v2/users")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
        ;
    }
}
