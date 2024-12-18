package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;

public class PutContactsTests extends TestBase {
    private String id;

    @BeforeMethod
    public void precondition() {

        ContactDto contactDto = ContactDto.builder()
                .name("Anton")
                .lastName("Jonh")
                .email("jonh@gmail.com")
                .phone("5678965432")
                .address("Berlin")
                .description("QA")
                .build();

        String message = given()
                .header(AUTHORIZATION, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .statusCode(200)
                .extract().path("message");

        String[] split = message.split(": ");
        id = split[1].trim();
    }

    @Test
    public void putContactSuccessTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Kate")
                .lastName("Ananas")
                .email("kate_updated@gmail.com")
                .phone("5678965432")
                .address("Berlin")
                .description("QA")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .statusCode(200)
                .assertThat().body("message", equalTo("Contact was updated"));
    }

    @Test
    public void putContactWithWrongToken() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Kate")
                .lastName("Ananas")
                .email("")
                .phone("")
                .address("Berlin")
                .description("QA")
                .build();

        given()
                .header(AUTHORIZATION, 678999)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .statusCode(401)
                .assertThat().body("message", equalTo("JWT strings must contain exactly 2 period characters. Found: 0"));


    }

    @Test
    public void putContactWithWrongID() {

        ContactDto updatedContactDto = ContactDto.builder()
                .id("tzuzui")
                .name("Kate")
                .lastName("Ananas")
                .email("kate_updated@gmail.com")
                .phone("5678965432")
                .address("Berlin")
                .description("QA")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Contact with id: tzuzui not found in your contacts!"));

    }
}
