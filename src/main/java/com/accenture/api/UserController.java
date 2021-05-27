package com.accenture.api;

import com.accenture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping(value = "/paid", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getUsersPaidAmount() {
    return new ResponseEntity<>(userService.getUsersPaidAmount(), HttpStatus.OK);
  }

  @GetMapping(value = "/owes", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getUsersOwesAmount() {
    return new ResponseEntity<>(userService.getUsersOwesAmount(), HttpStatus.OK);
  }

  @GetMapping(value = "/paid/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getUserPaidAmount(@PathVariable("name") String name) {
    return new ResponseEntity<>(userService.getUserPaidAmount(name), HttpStatus.OK);
  }

  @GetMapping(value = "/owes/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getUserOwesAmount(@PathVariable("name") String name) {
    return new ResponseEntity<>(userService.getUserOwesAmount(name), HttpStatus.OK);
  }

}
