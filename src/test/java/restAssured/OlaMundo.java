package restAssured;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class OlaMundo {

    public static void main(String[] args) {

        Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");  /* buscando url */
        System.out.println(response.getBody().asString());                                       /* imprimindo texto */
        System.out.println(response.getStatusCode());                                            /* buscando status */

        Response response1 = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");  /* buscando url */
        System.out.println(response1.getBody().asString().equals("Ola Mundo!"));                  /* validando texto = vardadeiro */
        System.out.println(response1.getStatusCode() == 200);                                     /* validando status = vardadeiro */

        Response response2 = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");  /* buscando url */
        System.out.println(response2.getBody().asString().equals("Ola!"));                        /* validando texto = falso */
        System.out.println(response2.getStatusCode() == 400);                                     /* validando status = falso */
    }
}
