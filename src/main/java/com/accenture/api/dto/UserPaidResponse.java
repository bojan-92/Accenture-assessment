package com.accenture.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPaidResponse {

  private long paidAmount;

}
