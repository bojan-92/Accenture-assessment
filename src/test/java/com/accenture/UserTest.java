package com.accenture;


import com.accenture.api.dto.UserPaidResponse;
import com.accenture.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

  @Autowired
  private UserService userService;

  @Test
  public void get_paid_amount_for_user_francis() {
    UserPaidResponse result = userService.getPaidAmountForUser("francis");
    Assertions.assertEquals(112.0, result.getPaidAmount());
  }

}
