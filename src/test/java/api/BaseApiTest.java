package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.Matchers.lessThan;

public abstract class BaseApiTest {

    protected static RequestSpecification req;
    protected static ResponseSpecification tempoOk;

    @BeforeAll
    static void configura() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        req = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)
                .build();

        // ogni risposta deve arrivare in meno di 3 secondi
        tempoOk = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(3000L))
                .build();
    }
}
