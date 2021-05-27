package com.accenture;

import static io.restassured.RestAssured.given;

import com.accenture.api.dto.UserOwesResponse;
import com.accenture.api.dto.UserPaidResponse;
import com.accenture.exception.UserNotFoundException;
import io.restassured.RestAssured;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserControllerTest extends Base {

  private static final String USER_BASE_ENDPOINT = "/users";
  private static final String PAID_AMOUNT_ENDPOINT = USER_BASE_ENDPOINT + "/paid";
  private static final String OWES_AMOUNT_ENDPOINT = USER_BASE_ENDPOINT + "/owes";

  @Test
  public void get_paid_amount_for_specific_user_then_status_200() {
    UserPaidResponse response = RestAssured
        .given()
        .when()
        .get(PAID_AMOUNT_ENDPOINT + "/{name}", "francis")
        .prettyPeek()
        .then()
        .statusCode(200)
        .extract()
        .response()
        .body()
        .as(UserPaidResponse.class);

    Assertions.assertEquals(112, response.getPaid());
  }

  @Test
  public void get_paid_amount_for_users_then_status_200() {
    UserPaidResponse[] response = given()
        .when()
        .get(PAID_AMOUNT_ENDPOINT)
        .prettyPeek()
        .then()
        .statusCode(200)
        .extract()
        .response()
        .jsonPath().getObject("$", UserPaidResponse[].class);

    Assertions.assertNotNull(response);
  }

  @Test
  public void get_paid_amount_for_user_then_status_404() {
    UserNotFoundException response = RestAssured
        .given()
        .when()
        .get(PAID_AMOUNT_ENDPOINT + "/{name}", "francisa")
        .prettyPeek()
        .then()
        .statusCode(404)
        .extract()
        .response()
        .body()
        .as(UserNotFoundException.class);

    Assertions.assertNotNull(response.getMessage());
  }

  @Test
  public void get_owes_amount_for_specific_user_then_status_200() {
    UserOwesResponse response = RestAssured
        .given()
        .when()
        .get(OWES_AMOUNT_ENDPOINT + "/{name}", "francis")
        .prettyPeek()
        .then()
        .statusCode(200)
        .extract()
        .response()
        .body()
        .as(UserOwesResponse.class);

    Assertions.assertNotNull(response);
  }

  @Test
  public void get_owes_amount_for_users_then_status_200() {
    UserOwesResponse[] response = given()
        .when()
        .get(OWES_AMOUNT_ENDPOINT)
        .prettyPeek()
        .then()
        .statusCode(200)
        .extract()
        .response()
        .jsonPath().getObject("$", UserOwesResponse[].class);

    Assertions.assertNotNull(response);
  }

  @Test
  public void get_owse_amount_for_user_then_status_404() {
    UserNotFoundException response = RestAssured
        .given()
        .when()
        .get(OWES_AMOUNT_ENDPOINT + "/{name}", "francisa")
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
