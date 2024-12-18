package com.phonebook.tests.restassured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZ2ZoemhAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MzUxMTQzODAsImlhdCI6MTczNDUxNDM4MH0.b4k0tnh9NuQQHxlWiEUskR8B8n7g8O3jDIFqZlEkvSA";
    public  static String  AUTHORIZATION = "Authorization";

    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

    }
}
