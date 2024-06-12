package com.app.trading.infrastructure;

import com.app.trading.domain.Currency;
import com.app.trading.domain.Transaction;
import com.app.trading.domain.TransactionType;
import com.app.trading.domain.User;
import com.app.trading.infrastructure.dto.CreateUserDTO;
import com.app.trading.infrastructure.dto.TransactionDTO;
import com.app.trading.infrastructure.dto.TransactionHistoryDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerTestCase {
    @InjectMocks
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

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
        String name = "clark";
        String email = "cc@gmail.com";
        String address = String.valueOf(UUID.randomUUID());
        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "%s",
                            "email": "%s",
                            "address": "%s"
                        }
                        """.formatted(name, email, address));

        // When
        Response response = request.when()
                .post("/api/users");

        // Then
        CreateUserDTO result = response.then().statusCode(201)
                .extract().body().as(CreateUserDTO.class);
        Assertions.assertNotNull(result.id);

        Optional<User> optionalUser = userRepository.findById(UUID.fromString(result.id));
        List<Transaction> transactions = transactionRepository.findByUserId(UUID.fromString(result.id));
        User user = optionalUser.orElseThrow();
        user.setTransactionHistory(transactions);
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(address, user.getAddress());
        Assertions.assertEquals(new BigDecimal("1000.00"), user.getBalances(Currency.USD).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void createDuplicatedUserFail() {
        // Given
        String address = String.valueOf(UUID.randomUUID());
        userRepository.save(new User("clark", "cc@gamil.com", address));

        String name = "clark";
        String email = "cc@gmail.com";

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "%s",
                            "email": "%s",
                            "address": "%s"
                        }
                        """.formatted(name, email, address));

        // When
        Response response = request.when()
                .post("/api/users");

        // Then
        String result = response.then().statusCode(409)
                .extract().body().asString();
        Assertions.assertEquals("Username is exists", result);
    }

    @Test
    public void userTransaction() {
        // Given
        User user = new User("clark", "cc@gmail.com", UUID.randomUUID().toString());
        userRepository.save(user);
        transactionRepository.save(user.getId(), new Transaction(new BigDecimal(1), new BigDecimal("1000.00"), Currency.USD, TransactionType.REWARD));

        RequestSpecification request = given()
                .pathParam("userId", user.getId().toString())
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "amount": 1,
                            "action": "buy"
                        }
                        """);

        // When
        Response response = request.when()
                .post("/api/users/{userId}/transaction");

        // Then
        response.then().statusCode(201);
        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());
        user.setTransactionHistory(transactions);
        Assertions.assertEquals(2, transactions.size());
        Assertions.assertEquals(new BigDecimal("900.00"), user.getBalances(Currency.USD).setScale(2, RoundingMode.HALF_UP));
        Assertions.assertEquals(new BigDecimal("100.00"), user.getBalances(Currency.BTC).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void transactionHistoryWhenIsNotExistUser() {
        // Given
        User user = new User("clark", "cc@gmail.com", UUID.randomUUID().toString());

        RequestSpecification request = given()
                .pathParam("userId", user.getId().toString());

        // When
        Response response = request.when()
                .get("/api/users/{userId}/transactionHistory");

        // Then
        String message = response.then().statusCode(404).extract().body().asString();
        Assertions.assertEquals("User is not exists", message);
    }

    @Test
    public void deleteUser() {
        // Given
        User user = new User("clark", "cc@gmail.com", UUID.randomUUID().toString());
        userRepository.save(user);
        transactionRepository.save(user.getId(), new Transaction(new BigDecimal(1), new BigDecimal(1000), Currency.USD, TransactionType.REWARD));

        RequestSpecification request = given()
                .pathParam("userId", user.getId().toString());

        // When
        Response response = request.when()
                .delete("/api/users/{userId}");

        // Then
        response.then().statusCode(200);
        Optional<User> optionalUser = userRepository.findById(user.getId());
        Assertions.assertTrue(optionalUser.isEmpty());
        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());
        Assertions.assertTrue(transactions.isEmpty());
    }

    @Test
    public void deleteUserWhenIsNotExistUser() {
        // Given
        User user = new User("clark", "cc@gmail.com", UUID.randomUUID().toString());

        RequestSpecification request = given()
                .pathParam("userId", user.getId().toString());

        // When
        Response response = request.when()
                .delete("/api/users/{userId}");

        // Then
        String message = response.then().statusCode(404).extract().body().asString();
        Assertions.assertEquals("User is not exists", message);
    }
}
