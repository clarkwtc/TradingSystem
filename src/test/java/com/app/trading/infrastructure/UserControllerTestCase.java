package com.app.trading.infrastructure;

import com.app.trading.infrastructure.endpoints.UserController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerTestCase {
    @InjectMocks
    private UserController userController;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssuredMockMvc.standaloneSetup(userController);
    }

    @Test
    public void createUser() {
        // Given
        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "clark",
                            "email": "cc@gmail.com",
                            "address": "123"
                        }
                        """);

        // When
        Response response = request.when()
                .post("/api/users");

        // Then
        CreateUserDTO result = response.then().statusCode(201)
                .extract().body().as(CreateUserDTO.class);
        Assertions.assertNotNull(result.id);
    }
}
