package com.accenture.service;


import com.accenture.api.dto.UserPaidResponse;

public interface UserService {

  UserPaidResponse getPaidAmountForUser(String name);

}
