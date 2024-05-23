package ru.study.test5.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AccountOutDto {

  private DataDto data;

  @Data
  @AllArgsConstructor
  public static class DataDto {
    private String accountId;
  }

}
