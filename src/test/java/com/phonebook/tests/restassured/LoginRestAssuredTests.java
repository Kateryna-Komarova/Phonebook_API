package com.phonebook.tests.restassured;

import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.AuthResponseDto;
import com.phonebook.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LoginRestAssuredTests extends TestBase {

    @Test
    public void loginSuccess() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("gfhzh@gmail.com")
                .password("gfztRujtio1245@23")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post("/user/login/usernamepassword")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponseDto.class);
    }

    @Test
    public void loginWithWrongEmail() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("gfhzhgmail.com")
                .password("gfztRujtio1245@23")
                .build();

        ErrorDto errorDto = given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post("/user/login/usernamepassword")
                .then()
                .statusCode(401) // Проверяем статус 401
                .extract()
                .as(ErrorDto.class);
        System.out.println(errorDto);
        Assert.assertEquals(errorDto.getMessage(), "Login or Password incorrect");
    }
}
