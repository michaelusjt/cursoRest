package restAssured;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserJsonTest {

    ////Neste teste estamos validando o primeiro nível do response da API
    @Test
    public void deveVerificarPrimeiroNivel() {

        given()
                .when()
                .get("https://restapi.wcaquino.me/users/1")
                .then()
                .statusCode(200)
                .log().all()
                .body("id", is(1))
                .body("name", containsString("Silva"))
                .body("age", greaterThan(18));
    }


    ////Neste teste estamos validando o primeiro nível do response da API
    @Test
    public void deveVerificarPrimeiroNivelOutrasFormas() {

        Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1");

        //System.out.println((response.path("id")));    //Não sei por que diabos, não consegui imprimir com erro no println


        //Path
        Assert.assertEquals(new Integer(1), response.path("id"));                 ///Validar o id
        Assert.assertEquals(new Integer(1), response.path("%s", "id"));   /// Passando uma string e validando o Id (??? não sei por que)


        //jsonPath
        JsonPath jpath = new JsonPath(response.asString());
        Assert.assertEquals(1, jpath.getInt("id"));

        //from
        int id = JsonPath.from(response.asString()).getInt("id");
        Assert.assertEquals(1, id);
    }


    ////Neste teste estamos validando o segundo nível do response da API
    @Test
    public void deveVerificarSegundoNivel() {

        given()
                .when()
                .get("https://restapi.wcaquino.me/users/2")
                .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"));
    }


    ////Neste teste estamos validando a lista do response da API
    @Test
    public void deveVerificarUmaLista() {

        given()
                .when()
                .get("https://restapi.wcaquino.me/users/3")
                .then()
                .statusCode(200)
                .body("name", containsString("Ana"))
                .body("filhos", hasSize(2))              ///Tamanho da lista
                .body("filhos[0].name", is("Zezinho"))   ///Pegando posição 0 da lista
                .body("filhos[1].name", is("Luizinho"))  ///Pegando posição 1 da lista
                .body("filhos.name", hasItem("Zezinho"))    ///Achando nome na lista
                .body("filhos.name", hasItem("Luizinho"))   ///Achando nome na lista
                .body("filhos.name", hasItems("Luizinho", "Zezinho"));   ///Achando coleção de nomes na lista

    }

    ////Neste teste estamos validando o path com erro de usuário
    @Test
    public void deveRetornarErroUsuarioInexistente() {

        given()
                .when()
                .get("https://restapi.wcaquino.me/users/4")
                .then()
                .statusCode(404)
                .body("error", is("Usuário inexistente"));

    }

    ////Neste teste estamos validando os dados de uma lista
    @Test
    public void deveVerificarListaRaiz() {

        given()
                .when()
                .get("https://restapi.wcaquino.me/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(3))                        ///Aqui estamos validando o tamanho da lista
                .body("name", hasItem("João da Silva"))      /// Aqui estamos validando nome na lsiat
                .body("age[1]", is(25))                      /// Aqui estamos pegando a idade do item 1 da lista
                .body("filhos.name", hasItem(Arrays.asList("Zezinho","Luizinho")))  ///Aqui estamos pegando os nomes dos filhos
                .body("salary", contains(1234.5677f, 2500, null));                  ///Aqui estamos pegando o salário

    }


    ////Neste teste estamos validando uma lista avançada
    @Test
    public void deveVerificarListaAvancada() {

        given()
                .when()
                .get("https://restapi.wcaquino.me/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(3))                        ///Aqui estamos validando o tamanho da lista
                .body("age.findAll{it <= 25}.size()", is(2))
                .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
                .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
                .body("findAll{it.age <= 25 && it.age > 20}[0].name", is("Maria Joaquina"))
                .body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))
                .body("find{it.age <= 25}.name", is("Maria Joaquina"))
                .body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia"))
                .body("findAll{it.name.length() > 10}.name", hasItems("João da Silva", "Maria Joaquina"))
                .body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))  ///Aqui estamos testando nome em Caixa Alta, passando para maiúsculo
                .body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
                ///.body("name.collect{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))
                ///não sei por que não conseguir testar  dando erro no allOf
                .body("age.collect{it * 2}", hasItems(40, 50, 60))
                .body("id.max()", is(3))
                .body("salary.min()", is(1234.5677f))  ///Testes com o F para carregar o dado como float
                .body("salary.findAll{it != null}.sum()", is(closeTo(3734.5677f, 0.001)));    ///Testes formatando o número com 4 casas após o zero
        //.body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000f))));
        ///não sei por que não conseguir testar  dando erro no allOf


    }

    ////Neste teste estamos validando os dados através do JsonPath
    @Test
    public void devoUnirJsonPathComJava() {
        ArrayList<String> names =

                given()
                        .when()
                        .get("https://restapi.wcaquino.me/users")
                        .then()
                        .statusCode(200)
                        .extract().path("name.findAll{it.startsWith('Ana')}")                           //Extraindo os dados do response
                ;
        Assert.assertEquals(1, names.size());                                              //Validando a lista
        Assert.assertTrue(names.get(0).equalsIgnoreCase("Ana Júlia"));
        Assert.assertEquals(names.get(0).toUpperCase(), "ANA JÚLIA".toUpperCase());
        Assert.assertTrue(names.get(0).equalsIgnoreCase("AnA JúliA"));

    }
}
