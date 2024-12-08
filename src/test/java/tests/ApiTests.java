package tests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {

    @Test
    void checkUsersApiResponseStatus() {
        // Step 1: Описываем тестовую ситуацию (Given - Дано)
        given()
                .log().all() // Логируем все, чтобы видеть, какие данные отправляются

                // Step 2: Отправляем запрос (When - Когда)
                .when()
                .get("https://reqres.in/api/users?page=2") // Выполняем GET-запрос на указанный URL

                // Step 3: Проверяем ответ (Then - Тогда)
                .then()
                .log().all() // Логируем ответ, чтобы видеть, что вернул сервер
                .statusCode(200); // Проверяем, что статус-код ответа равен 200
    }

    @Test
    void checkUserData() {
        // Step 1: Описываем начальные условия
        given()
                .log().all() // Логируем запрос

                // Step 2: Выполняем GET-запрос на конкретного пользователя
                .when()
                .get("https://reqres.in/api/users/2")

                // Step 3: Проверяем статус-код и данные в ответе
                .then()
                .log().all() // Логируем ответ
                .statusCode(200) // Убедимся, что статус-код — 200
                .body("data.first_name", equalTo("Janet")) // Проверяем имя пользователя
                .body("data.last_name", equalTo("Weaver")); // Проверяем фамилию пользователя
    }

    @Test
    void checkUserEmail() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .body("data.email",equalTo("janet.weaver@reqres.in"));
    }

    @Test
    void createUser() {
        //Описываем тело запроса
        String requestBody = "{\"name\":\"morpheus\",\"job\":\"leader\"}";

        //Выполняем post-запрос
        given()
                .log().all() // логируем весь запрос для проверки
                .header("Content-Type", "application/json") // указываем, что отправляем json
                .body(requestBody) // проверяем тело запроса
                .when()
                .post("https://reqres.in/api/users") // выполняем post запрос
                .then()
                .log().all() // логируем весь ответ для анализа
                .statusCode(201) // Проверяем, что статус-код равен 201 (Created)
                .body("name",equalTo("morpheus")) // Проверяем, что в ответе есть имя "morpheus"
                .body("job", equalTo("leader"));
    }
}