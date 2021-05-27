package com.accenture.service;

import com.accenture.api.dto.UserOwesResponse;
import com.accenture.api.dto.UserPaidResponse;
import com.accenture.exception.UserNotFoundException;
import com.accenture.model.Order;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final String PAYMENTS_FILE_PATH = "src/main/resources/data/payments.json";
  private static final String ORDERS_FILE_PATH = "src/main/resources/data/orders.json";
  private static final String PRODUCTS_FILE_PATH = "src/main/resources/data/products.json";

  @Override
  public HashSet<UserPaidResponse> getUsersPaidAmount() {
    JSONArray payments = readDataFromJsonFile(PAYMENTS_FILE_PATH);
    HashSet<UserPaidResponse> userPaidResponses = new HashSet<>();
    for (Object o : payments) {
      JSONObject paymentJson = (JSONObject) o;
      String name = (String) paymentJson.get("user");
      userPaidResponses.add(getUserPaidAmount(name));
    }
    return userPaidResponses;
  }

  @Override
  public HashSet<UserOwesResponse> getUsersOwesAmount() {
    // I get names from orders, theoretically it's possible to have specific user record in "orders" but not in "payments"
    HashSet<String> userNames = getUserNamesFromOrders();
    HashSet<UserOwesResponse> usersOwes = new HashSet<>();
    for (String name : userNames) {
      usersOwes.add(getUserOwesAmount(name));
    }
    return usersOwes;
  }

  @Override
  public UserPaidResponse getUserPaidAmount(String name) {
    return new UserPaidResponse(name, calculatePaidAmountForUser(name));
  }

  @Override
  public UserOwesResponse getUserOwesAmount(String name) {
    return new UserOwesResponse(name, calculateOwesAmountForUser(name));
  }

  private HashSet<String> getUserNamesFromOrders() {
    JSONArray orders = readDataFromJsonFile(ORDERS_FILE_PATH);
    HashSet<String> userNames = new HashSet<>();
    for (Object o : orders) {
      JSONObject orderJson = (JSONObject) o;
      userNames.add((String) orderJson.get("user"));
    }
    return userNames;
  }

  private long calculatePaidAmountForUser(String name) {
    JSONArray payments = readDataFromJsonFile(PAYMENTS_FILE_PATH);
    long paidAmount = 0;
    // Counter for error check, added in order to avoid multiple iteration through "payments"
    int j = 0;
    for (Object o : payments) {
      JSONObject paymentJson = (JSONObject) o;
      if (paymentJson.get("user").equals(name)) {
        paidAmount += (Long) paymentJson.get("amount");
        j += 1;
      }
    }
    // In this case paidAmount==0 can be used for this checking,
    // but theoretically it's possible to store "amount":0 for existing user in "payments"
    if (j == 0) {
      throw new UserNotFoundException("User with name: " + "'" + name + "'" + " doesn't exist.");
    }
    return paidAmount;
  }

  private double calculateOwesAmountForUser(String name) {
    List<Order> orders = getUserOrders(name);
    double userSpendTotal = getUserSpendTotal(orders);
    return userSpendTotal - calculatePaidAmountForUser(name);
  }

  private List<Order> getUserOrders(String name) {
    JSONArray orders = readDataFromJsonFile(ORDERS_FILE_PATH);
    List<Order> orderList = new ArrayList<>();

    for (Object o : orders) {
      JSONObject orderJson = (JSONObject) o;
      String user = (String) orderJson.get("user");
      String drink = (String) orderJson.get("drink");
      String size = (String) orderJson.get("size");
      if (user.equals(name)) {
        orderList.add(Order.builder().user(user).drink(drink).size(size).build());
      }
    }
    if (orderList.size() == 0) {
      throw new UserNotFoundException("User with name: " + "'" + name + "'" + " doesn't have any order.");
    }
    return orderList;
  }

  private double getUserSpendTotal(List<Order> orders) {
    double userSpend = 0;
    for (Order order : orders) {
      userSpend += getPriceByDrinkNameAndSize(order.getDrink(), order.getSize());
    }
    return userSpend;
  }

  private double getPriceByDrinkNameAndSize(String drink, String size) {
    JSONArray products = readDataFromJsonFile(PRODUCTS_FILE_PATH);
    double price = 0;
    for (Object o : products) {
      JSONObject productJson = (JSONObject) o;
      String drinkName = (String) productJson.get("drink_name");
      if (drinkName.equals(drink)) {
        JSONObject pricesJson = (JSONObject) productJson.get("prices");
        price += (Double) pricesJson.get(size);
      }
    }
    return price;
  }

  private JSONArray readDataFromJsonFile(String filePath) {
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
