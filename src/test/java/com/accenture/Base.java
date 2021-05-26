package com.accenture;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Base {

  private static final String BASE_URL = "http://localhost:8080";

  private RequestSpecBuilder builder;

  @BeforeEach
  public void beforeEachCleanup() {
    builder = new RequestSpecBuilder();
    builder.setBaseUri(BASE_URL);
  }

  public RequestSpecification getRequestSpecification() {
    return builder.build().headers(new HashMap<>());
  }

  public RequestSpecification getRequestSpecification(String name) {
    Map<String, String> headers = new HashMap<>();
    headers.put("name", name);
    return getRequestSpecification().headers(headers);
  }

}
