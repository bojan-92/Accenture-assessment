package com.accenture.service;

import com.accenture.api.dto.UserPaidResponse;
import com.accenture.exception.UserNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final String PAYMENTS_FILE_PATH = "src/main/resources/data/payments.json";

  @Override
  public UserPaidResponse getPaidAmountForUser(String name) {
    JSONParser jsonParser = new JSONParser();
    long paidAmount = 0;

    try (FileReader reader = new FileReader(PAYMENTS_FILE_PATH)) {
      //Read JSON file
      Object obj = jsonParser.parse(reader);
      JSONArray payments = (JSONArray) obj;

      // Counter for error check, added in order to avoid multiple iterations through payments collection
      int j = 0;

      for (Object o : payments) {
        JSONObject paymentJson = (JSONObject) o;
        if (paymentJson.get("user").equals(name)) {
          paidAmount += (Long) paymentJson.get("amount");
          j += 1;
        }
      }
      if (j == 0) {
        throw new UserNotFoundException("User with name: " + "'" + name + "'" + " doesn't exist.");
      }
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
    return UserPaidResponse.builder().paidAmount(paidAmount).build();
  }

}
