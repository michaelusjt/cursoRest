package restAssured;

import io.restassured.path.xml.element.Node;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserXMLTest {

    //Teste para a validação das informações em um XML
    @Test
    public void devoTrabalharComXML() {

        ///Aqui vamos testar os campos localizando pelo user
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML/3")
                .then()
                .statusCode(200)
                .body("user.name", is("Ana Julia"))
                .body("user.@id", is("3"))
                .body("user.filhos.name.size()", is(2))
                .body("user.filhos.name[0]", is("Zezinho"))
                .body("user.filhos.name[1]", is("Luizinho"))
                .body("user.filhos.name", hasItem("Luizinho"))
                .body("user.filhos.name", hasItems("Luizinho", "Zezinho"));

    }


    //Teste para a validação das informações em um XML
    @Test
    public void devoTrabalharRootComXML() {

        ///Aqui vamos testar os campos utilizando o root
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML/3")
                .then()
                .statusCode(200)
                .rootPath("user")                          ///Aqui estamos pegando os dados pelo root user
                .body("name", is("Ana Julia"))
                .body("@id", is("3"))

                .rootPath("user.filhos")                   ////Aqui estamos pegando os dados pelo root filhos
                .body("name.size()", is(2))

                .detachRootPath("filhos")                 ///Aqui estamos deletando o root filhos da consulta, obrigando adicionar o root
                .body("filhos.name[0]", is("Zezinho"))
                .body("filhos.name[1]", is("Luizinho"))

                .appendRootPath("filhos")                 ///Aqui estamos adicionando o root filhos da consulta
                .body("name", hasItem("Luizinho"))
                .body("name", hasItems("Luizinho", "Zezinho"));

    }


    //Teste para a validação das informações em um XML
    @Test
    public void devoFazerConsultasAvançadasComXML() {

        ///Aqui vamos testar os campos utilizando o root
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .body("users.user.size()", is(3))
                .body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
                .body("users.user.@id", hasItems("1", "2", "3"))
                .body("users.user.find{it.age == 25}.name", is("Maria Joaquina"))
                .body("users.user.findAll{it.name.toString().contains('n')}.name", hasItems("Maria Joaquina", "Ana Julia"))
                .body("users.user.salary.find{it != null}.toDouble()", is(1234.5678d))
                .body("users.user.age.collect{it.toInteger() * 2}", hasItems(40, 50, 60))
                .body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"));


    }


    //Teste para a validação das informações em um XML unindo com JAVA
    @Test
    public void devoFazerConsultasAvançadasComXMLEJava() {

        ArrayList<Node> nome = given()                  ///Aqui na aula vimos que não devemos buscar os dados por String, e sim com NodeImpl
                //Na aula o professor usou NodeImpl, mas na minha máquina rodou com Node (padrão utilizado no XML
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .extract().path("users.user.name.findAll{it.toString().contains('n')}");

        //System.out.println(path);
        Assert.assertEquals(2, nome.size());
        Assert.assertEquals("Maria Joaquina".toUpperCase(), nome.get(0).toString().toUpperCase());
        Assert.assertTrue("ANA JULIA".equalsIgnoreCase(nome.get(1).toString()));


    }


    //Teste para a validação das informações em um XML unindo com JAVA
    @Test
    public void devoFazerConsultasAvançadasComXPath() {

        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(200)
                .body(hasXPath("count(/users/user)", is("3")))
                .body(hasXPath("/users/user[@id = '1']"))
                .body(hasXPath("//name[text() = 'Luizinho']/../../name", is("Ana Julia")))  ///Aqui achamos o nome Luizinho e o nome Ana Julia percorrendo o XML acima
                .body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
                .body(hasXPath("/users/user/name", is("João da Silva")))
                .body(hasXPath("//name", is("João da Silva")))
                .body(hasXPath("/users/user[2]/name", is("Maria Joaquina")))
                .body(hasXPath("/users/user[last()]/name", is("Ana Julia")))
                .body(hasXPath("count(/users/user/name[contains(., 'n')])", is("2")))
                .body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
                .body(hasXPath("//user[age > 20 and age < 30]/name", is("Maria Joaquina")))
                .body(hasXPath("//user[age > 20][age < 30]/name", is("Maria Joaquina")))
        ;


    }
}
