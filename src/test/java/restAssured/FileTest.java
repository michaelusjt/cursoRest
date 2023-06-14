package restAssured;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class FileTest {

    // Neste teste estamos validando o post sem o arquivo
    @Test
    public void deveObrigarEnvioArquivo() {

        given()
                .log().all()
                .when()
                .post("https://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(404)
                .body("error", is("Arquivo não enviado"))

        ;

    }

    // Neste teste estamos mandando o arquivo
    @Test
    public void deveFazerUploadArquivo() {

        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/1659195284604.jfif"))
                .when()
                .post("https://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("1659195284604.jfif"))

        ;

    }

    // Neste teste estamos validano o erro de tamanho do arquivo e o tempo de resposta
    @Test
    public void NaodeveFazerUploadArquivoGrande() {

        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/beneficios.csv"))
                .when()
                .post("https://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .time(lessThan(10000L))
                .statusCode(413)
//				.body("name", is("1659195284604.jfif"))

        ;

    }


    // Neste teste vamos baixar o arquivo e salvar no file
    @Test
    public void deveFazerBaixarArquivo() throws IOException {

        byte[] image = given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/download")
                .then()
//						.log().all()
                .statusCode(200)
                .extract().asByteArray();                         // Aqui estamos extraindo as info da imagem e guardando no byte[]
        ;

        File imagem = new File("src/main/resources/file.jpg");    // Aqui estamos informando o endereço que vamos salvar a imagem
        //Nesta linha vimos que o File precisa ser tratado como execessão, daí colocamos o throws
        OutputStream out = new FileOutputStream(imagem);          // Médodo java de output
        out.write(image);                                         // Aqui estamos escrevendo a imagem
        out.close();

        System.out.println(imagem.length());
        Assert.assertThat(imagem.length(), lessThan(100000L));  //Pegando o tamanho da imagem e comparando com 10000L
    }
}
