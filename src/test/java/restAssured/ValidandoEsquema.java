package restAssured;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import jdk.internal.org.xml.sax.SAXParseException;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ValidandoEsquema {

    //Para este test iremos gerar o xsd neste link - https://www.freeformatter.com/xsd-generator.html
    // Criar um arquivo user.xsd e colar o conteúdo no source

    @Test
    public void deveValidarEsquemaXML() {

        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("user.sxd"))   //Deu erro na validação
        ;

    }

    @Test(expected = SAXParseException.class)
    public void naoDeveValidarEsquemaXMLInvalido() {

        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/InvalidUsersXML")
                .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("user.sxd")) //Deu erro na validação
        ;

    }



    // Neste teste criamos um schema json neste link - https://jsonschema.net/app/schemas/294305
    // Criamos um arquivo .json
    @Test
    public void deveValidarEsquemaJson() {

        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"))
//                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("src/test/resource/users.json"))
        ;

    }
}
