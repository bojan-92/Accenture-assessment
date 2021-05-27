package com.accenture.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

  private String user;
  private String drink;
  private String size;

}
