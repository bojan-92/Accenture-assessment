package com.accenture.service;


import com.accenture.api.dto.UserOwesResponse;
import com.accenture.api.dto.UserPaidResponse;
import java.util.HashSet;

public interface UserService {

  HashSet<UserPaidResponse> getUsersPaidAmount();

  UserPaidResponse getUserPaidAmount(String name);

  HashSet<UserOwesResponse> getUsersOwesAmount();

  UserOwesResponse getUserOwesAmount(String name);

}
