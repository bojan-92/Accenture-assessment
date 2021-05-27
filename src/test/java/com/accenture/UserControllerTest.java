package com.accenture;

import com.accenture.api.dto.UserPaidResponse;
import com.accenture.exception.UserNotFoundException;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserControllerTest extends Base {

  private static final String USER_BASE_ENDPOINT = "/user";
  private static final String PAID_AMOUNT_ENDPOINT = USER_BASE_ENDPOINT + "/paid-amount";

  @Test
  public void get_paid_amount_for_user_then_status_200() {
    UserPaidResponse response = RestAssured
        .given()
        .spec(getRequestSpecification("francis"))
        .when()
        .get(PAID_AMOUNT_ENDPOINT)
        .prettyPeek()
        .then()
        .statusCode(200)
        .extract()
        .response()
        .body()
        .as(UserPaidResponse.class);

    Assertions.assertEquals(112, response.getPaidAmount());
  }

  @Test
  public void get_paid_amount_for_user_then_status_404() {
    UserNotFoundException response = RestAssured
        .given()
        .spec(getRequestSpecification("francisa"))
        .when()
        .get(PAID_AMOUNT_ENDPOINT)
        .prettyPeek()
        .then()
        .statusCode(404)
        .extract()
        .response()
        .body()
        .as(UserNotFoundException.class);

    Assertions.assertNotNull(response.getMessage());
  }

}
