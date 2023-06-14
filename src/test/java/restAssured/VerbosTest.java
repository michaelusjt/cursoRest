package restAssured;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class VerbosTest {

        // Neste teste estamos validando o método POST
        @Test
        public void devoSalvarUsuario() {

            given()
                    .log().all()
                    .contentType("application/json")
                    .body("{ \"name\": \"Jose\", \"age\" : 30}")
                    .when()
                    .post("https://restapi.wcaquino.me/users")
                    .then()
                    .log().all()
                    .statusCode(201)
                    .body("id", is(notNullValue()))
                    .body("name", is("Jose"))
                    .body("age", is(30))
            ;
        }

        @Test
        public void naoDeveSalvarUsuarioSemNome() {

            given()
                    .log().all()
                    .contentType("application/json")
                    .body("{ \"age\" : 30}")
                    .when()
                    .post("https://restapi.wcaquino.me/users")
                    .then()
                    .log().all()
                    .statusCode(400)
                    .body("id", is(nullValue()))
                    .body("error", is("Name é um atributo obrigatório"))
            ;

        }


        @Test
        public void devoSalvarUsuarioViaXml() {

            given()
                    .log().all()
//		  .contentType("application/json")
                    .contentType(ContentType.XML)
                    .body("<user><name>Joao da Silva</name><age>30</age></user>")
                    .when()
                    .post("https://restapi.wcaquino.me/usersXML")
                    .then()
                    .log().all()
                    .statusCode(201)
                    .body("user.@id", is(notNullValue()))
                    .body("user.name", is("Joao da Silva"))
                    .body("user.age", is("30"))
            ;
        }


        @Test
        public void deveAlterarUsuario() {

            given()
                    .log().all()
                    .contentType("application/json")
                    .body("{\"name\" : \"Usuário alterado\", \"age\" : 150}")
                    .when()
                    .put("https://restapi.wcaquino.me/users/1")
                    .then()
                    .log().all()
//					.body("error", is("Registro inexistente"))
                    .statusCode(200)
                    .body("id", is(1))
                    .body("name", is("Usuário alterado"))
                    .body("salary", is(1234.5678f))
            ;

        }


        @Test
        public void devoCustomizarURL() {

            given()
                    .log().all()
                    .contentType("application/json")
                    .body("{\"name\" : \"Usuário alterado\", \"age\" : 150}")
                    .when()
                    .put("https://restapi.wcaquino.me/{entidade}/{useriId}", "users", "1")
                    .then()
                    .log().all()
//					.body("error", is("Registro inexistente"))
                    .statusCode(200)
                    .body("id", is(1))
                    .body("name", is("Usuário alterado"))
                    .body("salary", is(1234.5678f))
            ;

        }


        @Test
        public void devoCustomizarURLParte2() {

            given()
                    .log().all()
                    .contentType("application/json")
                    .body("{\"name\" : \"Usuário alterado\", \"age\" : 150}")
                    .pathParam("entidade", "users")
                    .pathParam("userId", 1)
                    .when()
                    .put("https://restapi.wcaquino.me/{entidade}/{userId}")
                    .then()
                    .log().all()
//					.body("error", is("Registro inexistente"))
                    .statusCode(200)
                    .body("id", is(1))
                    .body("name", is("Usuário alterado"))
                    .body("salary", is(1234.5678f))
            ;

        }

        @Test
        public void deveRemoverUsuario() {
            given()
                    .log().all()
                    .when()
                    .delete("https://restapi.wcaquino.me/users/1")
                    .then()
                    .log().all()
                    .statusCode(204)

            ;
        }

        @Test
        public void deveRemoverUsuarioInexistente() {
            given()
                    .log().all()
                    .when()
                    .delete("https://restapi.wcaquino.me/users/100")
                    .then()
                    .log().all()
                    .statusCode(400)
                    .body("error", is("Registro inexistente"))

            ;
        }


    }




/// Exemplo de Json que iremos usar
/*{
	"name": "Jose",
	"age" : 30,
}*/


///Exemplo de xml que iremos usar
//<user><name>João da Silva</name><age>30</age></user>

