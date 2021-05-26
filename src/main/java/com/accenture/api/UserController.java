package com.accenture.api;

import com.accenture.api.dto.UserPaidResponse;
import com.accenture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping(value = "/paid-amount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserPaidResponse> getUserPaidAmount(@RequestHeader("name") String name) {
    return new ResponseEntity<>(userService.getPaidAmountForUser(name), HttpStatus.OK);
  }

}
