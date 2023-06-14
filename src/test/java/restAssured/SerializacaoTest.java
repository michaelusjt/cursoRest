package restAssured;

import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SerializacaoTest {

    // Neste teste estamos serializando o MAP do teste criado na VerbosTest
    @Test
    public void devoSalvarUsuarioMap() {

//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("name", "Usuário via map");
//			params.put("age", 25);

        given()
                .log().all()
                .contentType("application/json")
//			  .body(params)
                .when()
                .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Usuário via map"))
                .body("age", is(25))
        ;
    }

    // Neste teste estamos serializando o Objeto do teste criado na VerbosTest
    @Test
    public void devoSalvarUsuarioObject() {

        User user = new User("Usuário via objeto", 25);

        given()
                .log().all()
                .contentType("application/json")
                .body(user)
                .when()
                .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Usuário via objeto"))
                .body("age", is(25))
        ;
    }

    //Neste teste estamos deserializando os dados inserido no teste com Json
    @Test
    public void devoDeseriazarSalvarUsuarioObject() {

        User user = new User("Usuario deserializado 2", 30);

        User usuarioInserido = given()
                .log().all()
                .contentType("application/json")
                .body(user)
                .when()
                .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class)   //Aqui estamos dizendo que vamos extrair nossa Classe.class do java
                ;
        System.out.println(usuarioInserido);
        Assert.assertThat(usuarioInserido.getId(), notNullValue());
        Assert.assertEquals("Usuario deserializado 2", usuarioInserido.getName());
        Assert.assertThat(usuarioInserido.getAge(), is(30));

    }

    //Aqui vamos serializar os dados do teste User com XML
    @Test
    public void devoSalvarUsuarioViaXmlUsandoObjeto() {

        User user = new User("Usuario xml", 40);

        given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
                .when()
                .post("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("Usuario xml"))
                .body("user.age", is("40"))
        ;
    }


    //Aqui vamos serializar os dados do teste User com XML
    @Test
    public void devoDeserializarUsuarioViaXmlUsandoObjeto() {

        User user = new User("Usuario xml", 40);

        User usuarioInserido = given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
                .when()
                .post("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class)
                ;
        System.out.println(usuarioInserido);

        Assert.assertThat(usuarioInserido.getId(), notNullValue());
        Assert.assertThat(usuarioInserido.getName(), is("Usuario xml"));
        Assert.assertThat(usuarioInserido.getAge(), is(40));

    }
}
