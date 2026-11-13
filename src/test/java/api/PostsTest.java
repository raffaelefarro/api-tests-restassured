package api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class PostsTest extends BaseApiTest {

    @Test
    @DisplayName("GET /posts restituisce 100 post")
    void elencoPost() {
        given().spec(req)
        .when().get("/posts")
        .then().spec(tempoOk)
                .statusCode(200)
                .body("size()", equalTo(100));
    }

    @Test
    @DisplayName("GET /posts/1 ha tutti i campi e il tipo giusto")
    void singoloPost() {
        given().spec(req)
        .when().get("/posts/1")
        .then().spec(tempoOk)
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", not(emptyString()))
                .body("body", not(emptyString()));
    }

    @Test
    @DisplayName("GET /posts/9999 dà 404")
    void postInesistente() {
        given().spec(req)
        .when().get("/posts/9999")
        .then().statusCode(404);
    }

    @Test
    @DisplayName("GET /posts?userId=1 restituisce solo i post di quell'utente")
    void filtroPerUtente() {
        given().spec(req).queryParam("userId", 1)
        .when().get("/posts")
        .then().statusCode(200)
                .body("size()", equalTo(10))
                .body("userId", everyItem(equalTo(1)));
    }

    @Test
    @DisplayName("GET /posts/1/comments restituisce commenti con un'email valida")
    void commentiDiUnPost() {
        given().spec(req)
        .when().get("/posts/1/comments")
        .then().statusCode(200)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(1)))
                .body("email", everyItem(containsString("@")));
    }

    @Test
    @DisplayName("POST /posts crea un post e restituisce 201 con l'id")
    void creaPost() {
        Map<String, Object> nuovo = Map.of(
                "title", "Titolo di prova",
                "body", "Testo di prova",
                "userId", 1);

        given().spec(req).body(nuovo)
        .when().post("/posts")
        .then().spec(tempoOk)
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Titolo di prova"))
                .body("userId", equalTo(1));
    }

    @Test
    @DisplayName("PUT /posts/1 aggiorna il titolo")
    void aggiornaPost() {
        Map<String, Object> modificato = Map.of(
                "id", 1,
                "title", "Titolo cambiato",
                "body", "Testo cambiato",
                "userId", 1);

        given().spec(req).body(modificato)
        .when().put("/posts/1")
        .then().statusCode(200)
                .body("title", equalTo("Titolo cambiato"));
    }

    @Test
    @DisplayName("DELETE /posts/1 risponde 200")
    void cancellaPost() {
        given().spec(req)
        .when().delete("/posts/1")
        .then().statusCode(200);
    }
}
