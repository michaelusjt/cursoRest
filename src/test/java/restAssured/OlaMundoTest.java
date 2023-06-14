package restAssured;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class OlaMundoTest {

    @Test
    public void testOlaMundo() {

        /* Tipo de testes utilizando asserttrue e equals */

        Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");  /* buscando url */
        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));                  /* validando texto = vardadeiro */
        Assert.assertTrue(response.getStatusCode() == 200);                                     /* validando assert */
        Assert.assertTrue("O statu code deveria ser 200", response.getStatusCode() == 201);     /* detalhando com mensagem */
        Assert.assertEquals(response.getStatusCode(), 200);                                     /* validando com equals */


        ValidatableResponse validacao = response.then();                                        /* validando o teste*/
        validacao.statusCode(200);                                               /* validando o teste*/

    }


    @Test
    public void devoConhecerOutrasFormasDeRestAssured() {

        /* Tipo de testes utilizando validação */

        Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");  /* buscando url */
        ValidatableResponse validacao = response.then();                                             /* obtendo informação*/
        validacao.statusCode(200);                                                                /* validando status*/


        /*Utilizando o método estático GET */
        RestAssured.get("https://restapi.wcaquino.me/ola").then().statusCode(200);


        /* Aqui fizemos o import do médtodo GET (soucer -> Add import */
        get("https://restapi.wcaquino.me/ola").then().statusCode(200);


        /* Utilizando o Given, when e then */


                given()        /* pré-condições do teste */
                .when()        /*ação do teste */
                    .get("https://restapi.wcaquino.me/ola")
                .then()       /*validção do teste */
                    .statusCode(200);



    }


    @Test

    public void devoConhecerMatchersHamcrest() {

        /* Trabalhando com igualdades */
        Assert.assertThat("Maria", Matchers.is("Maria"));
        Assert.assertThat(128, Matchers.is(128));
        Assert.assertThat(138, Matchers.isA(Integer.class));
        Assert.assertThat(138d, Matchers.isA(Double.class));
        Assert.assertThat(158d, Matchers.greaterThan(138d));
        Assert.assertThat(138d, Matchers.lessThan(158d));


        /* Trabalhando com listas */
        List<Integer> impares = Arrays.asList(1,3,5,7,9);
        assertThat(impares, hasSize(5));
        assertThat(impares, contains(1,3,5,7,9));
        assertThat(impares, containsInAnyOrder(1,3,5,7,9));
        assertThat(impares, hasItem(1));
        assertThat(impares, hasItems(1,3));


        /* Trabalhando com conectores E e OU */
        assertThat("Maria", is(not("João")));
        assertThat("Maria", not("João"));
        assertThat("Maria", anyOf(is("João"), is("Maria")));
        assertThat("Maria", allOf(startsWith("Ma"), endsWith("ia"), containsString("r")));

    }

    @Test

    public void devoValidarBody(){

        given()
                .when()
                .get("https://restapi.wcaquino.me/ola")
                .then()
                .statusCode(200)
                .body(is("Ola Mundo!"))
                .body(containsString("Mundo"))
                .body(is(not(nullValue())));


    }
}
