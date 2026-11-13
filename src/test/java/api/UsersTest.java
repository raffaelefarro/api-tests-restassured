package api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class UsersTest extends BaseApiTest {

    @Test
    @DisplayName("GET /users restituisce 10 utenti")
    void elencoUtenti() {
        given().spec(req)
        .when().get("/users")
        .then().spec(tempoOk)
                .statusCode(200)
                .body("size()", equalTo(10));
    }

    @Test
    @DisplayName("GET /users/1 ha indirizzo e azienda")
    void dettaglioUtente() {
        given().spec(req)
        .when().get("/users/1")
        .then().statusCode(200)
                .body("id", equalTo(1))
                .body("email", containsString("@"))
                .body("address.city", not(emptyString()))
                .body("address.geo.lat", notNullValue())
                .body("company.name", not(emptyString()));
    }

    @ParameterizedTest(name = "l''utente {0} esiste")
    @ValueSource(ints = {1, 5, 10})
    void utentiEsistenti(int id) {
        given().spec(req)
        .when().get("/users/{id}", id)
        .then().statusCode(200)
                .body("id", equalTo(id));
    }

    @ParameterizedTest(name = "l''utente {0} non esiste")
    @ValueSource(ints = {0, 11, 9999})
    void utentiInesistenti(int id) {
        given().spec(req)
        .when().get("/users/{id}", id)
        .then().statusCode(404);
    }
}
