package core;

import io.restassured.http.ContentType;

public class Constantes {

    // Aqui estamos configurando a url, porta e tempo limite
    String APP_BASE_URL = "https://barrigarest.wcaquino.me";
    Integer APP_PORT = 443; //http: -> 80
    String APP_BASE_PATH = "";

    ContentType APP_CONTENT_TYPE = ContentType.JSON;
    Long MAX_TIMEOUT = 50000L;
}
