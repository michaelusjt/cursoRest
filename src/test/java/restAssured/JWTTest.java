package restAssured;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class JWTTest {
    //Para os testes criamos uma conta no https://seubarriga.wcaquino.me/logout
    //conta - michael, email - mcavalcantedeoliveiraalves@yahoo.com, senha - 1234
    //conta - michael2, email - michael2@gmail.com, senha - 12345

    @Test
    public void deveFazerAutenticacaoComTokenJWT(){

        //Neste teste estamos validando via API os testes
        //Login na API
        //Obtendo o Token
        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "michael2@gmail.com");
        login.put("senha", "12345");

        String token = given()
                .log().all()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("http://barrigarest.wcaquino.me/signin")
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("token");
        ;

        given()
                .log().all()
                .header("Authorization", "JWT " +token)
                .when()
                .get("http://barrigarest.wcaquino.me/contas")
                .then()
                .log().all()
                .statusCode(200)
                .body("nome", hasItem("michael2"))
        ;
    }

    @Test
    public void deveAcessarAplicacaoWeb() {

        //Neste teste estamos validando via WEB os testes, transitando pelo HTML
        String cookie = given()
                .log().all()
                .formParam("email", "michael2@gmail.com")
                .formParam("senha", "12345")
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post("https://seubarriga.wcaquino.me/logar")
                .then()
                .log().all()
                .statusCode(200)
                .extract().header("set-cookie");

                cookie = cookie.split("=")[1].split(";")[0];
                System.out.println(cookie);
        ;

        String body = given()
                .log().all()
                .cookie("connect.sid", cookie)
                .when()
                .get("https://seubarriga.wcaquino.me/contas")
                .then()
                .log().all()
                .statusCode(200)
                .body("html.body.table.tbody.tr[0].td[0]", is("michael2"))
                .extract().body().asString();                                         //Extraíndo xml como string

        System.out.println("----------------------------------------");
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, body);          //Pegando as informações da String e passando para xml
        System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
        ;
    }

}
