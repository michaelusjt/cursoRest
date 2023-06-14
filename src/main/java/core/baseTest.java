package core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.hamcrest.Matchers;
import org.junit.Before;

public class baseTest extends Constantes{

    @Before            //Aqui estamos configurando as informações antes de rodar o teste
    public void setup(){

        RestAssured.baseURI = APP_BASE_URL;
        RestAssured.port = APP_PORT;
        RestAssured.basePath = APP_BASE_PATH;


        // Aqui estamos passando o APP_CONTENT_TYPE
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(APP_CONTENT_TYPE);
        RestAssured.requestSpecification = reqBuilder.build();

        // Aqui estamos passando o MAX_TIMEOUT)
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
        RestAssured.responseSpecification = resBuilder.build();

        //Aqui vamos mostrar o log somente quando houver erro
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }
}
