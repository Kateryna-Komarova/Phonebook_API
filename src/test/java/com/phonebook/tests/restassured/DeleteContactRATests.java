package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactRATests extends TestBase {
    String id;

    @BeforeMethod

    public void precondition() {
        ContactDto contactDto = ContactDto.builder()
                .name("Kate")
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
                .extract().path("message");
        // System.out.println(message);
        // Contact was added! ID: 3274167e-81ec-401c-966d-3c3ed85eb696

        String[] split = message.split(": ");
        id = split[1];

    }

    @Test
    public void deleteContactSuccessTest() {

        //String message =
                 given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                         .assertThat().body("message",equalTo("Contact was deleted!"));
               // .extract().path("message");
        //  System.out.println(message);
    }

    @Test
    public void DeleteContactByWrongID(){
    // ErrorDto errorDto =
                 given()
                .header(AUTHORIZATION,TOKEN)
                .when()
                .delete("contacts/a45c71-a1f5-4b5c-956f-7e7f1f06a243")
                .then()
                .assertThat().statusCode(400)
             .assertThat().body("message", containsString("not found in your contacts"));


              //  .extract().body().as(ErrorDto.class);
       // System.out.println(errorDto.getMessage());
    }
}
