package com.accenture.api.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPaidResponse {

  private String name;
  private long paidAmount;

}
