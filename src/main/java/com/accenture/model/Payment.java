package com.accenture.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {

  private String user;
  private long amount;

}
