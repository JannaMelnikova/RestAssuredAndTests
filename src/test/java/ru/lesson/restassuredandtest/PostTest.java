package ru.lesson.restassuredandtest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class PostTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }

    @Test
    public void getPostByIdTest() {
        given()
                .log().all()
                .pathParam("postId", 1)
                .when()
                .get("/posts/{postId}")
                .then()
                .log().all()
                .statusCode(200)
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
                .body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"))
                .body("userId", equalTo(1));

    }

    @Test
    public void postPostTest() {

        Post post = new Post();
        post.setUserId(10);
        post.setTitle("test at nam consequatur ea labore ea harum");
        post.setBody("test cupiditate quo est modi nesciunt soluta");

        given()
                .contentType("application/json")
                .log().all()
                .body("{\n" +
                        "    \"userId\": 10,\n" +
                        "    \"id\": 101,\n" +
                        "    \"title\": \"test at nam consequatur ea labore ea harum\",\n" +
                        "    \"body\": \"test cupiditate quo est modi nesciunt soluta\"\n" +
                        "}")
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", equalTo(101))
                .body("userId", equalTo(10))
                .body("body",equalTo("test cupiditate quo est modi nesciunt soluta"))
                .body("title", equalTo("test at nam consequatur ea labore ea harum"));
    }

    @Test
    public void getAllPostTest() {
        given()
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(90));
    }
}
