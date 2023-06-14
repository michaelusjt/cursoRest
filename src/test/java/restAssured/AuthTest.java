package restAssured;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;


public class AuthTest {

    //Neste teste estamos fazendo um get em uma API pública sem token
    @Test
    public void deveAcessarSwapi(){
        given()
                .log().all()
                    .when()
                        .get("https://swapi.dev/api/people/1")
                        .then()
                            .log().all()
                            .statusCode(200)
                            .body("name", is("Luke Skywalker"))


        ;
    }

    @Test
    public void deveObterClima() {

        //Neste teste estamos usando uma API de clima com chave para efetuar o get
        //https://api.openweathermap.org/data/2.5/weather?q=Fortaleza,br&APPID=7651a546cb9fe06c78b62dfb628427db
        given()
                .log().all()
                .queryParam("q", "Fortaleza,BR")
                .queryParam("appId", "7651a546cb9fe06c78b62dfb628427db")
                .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Fortaleza"))
                .body("coord.lon", is(-38.5247F))
                .body("main.temp", greaterThan(25F))
        ;
    }
        @Test
        public void naoDeveLogarSemSenha(){

            given()
                    .log().all()
                    .when()
                    .get("https://restapi.wcaquino.me/basicauth")
                    .then()
                    .log().all()
                    .statusCode(401)
            ;

        }

    @Test
    public void deveLogar(){

        given()
                .log().all()
                .when()
                .get("https://admin:senha@restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))
        ;

    }

    @Test
    public void deveLogarComParan(){

        given()
                .log().all()
                .auth().basic("admin", "senha")
                .when()
                .get("https://admin:senha@restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))
        ;

    }

    @Test
    public void deveLogarComParanChallenge(){

        given()
                .log().all()
                .auth().basic("admin", "senha")
                .when()
                .get("https://admin:senha@restapi.wcaquino.me/basicauth2")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))
        ;

    }


}
