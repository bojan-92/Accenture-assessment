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
    return new UserPaidResponse(name, calculatePaidAmountForUser(name));
  }

  private long calculatePaidAmountForUser(String name) {
    JSONArray payments = readDataFromFile(PAYMENTS_FILE_PATH);
    long paidAmount = 0;
    // Counter for error check, added in order to avoid multiple iteration through payments collection
    int j = 0;
    for (Object o : payments) {
      JSONObject paymentJson = (JSONObject) o;
      if (paymentJson.get("user").equals(name)) {
        paidAmount += (Long) paymentJson.get("amount");
        j += 1;
      }
    }
    // In this case paidAmount==0 can be used for this checking,
    // but in general it's possible to store "amount":0 in payments.json
    if (j == 0) {
      throw new UserNotFoundException("User with name: " + "'" + name + "'" + " doesn't exist.");
    }
    return paidAmount;
  }

  private JSONArray readDataFromFile(String filePath) {
    JSONArray retVal = null;
    JSONParser jsonParser = new JSONParser();
    try (FileReader reader = new FileReader(filePath)) {
      //Read JSON file
      Object obj = jsonParser.parse(reader);
      retVal = (JSONArray) obj;
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
    return retVal;
  }

}
