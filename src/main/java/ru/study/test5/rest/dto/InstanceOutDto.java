package ru.study.test5.rest.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class InstanceOutDto {

  private DataDto data;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DataDto {
    private String instanceId;
    private List<Integer> registerId;
    private List<Integer> supplementaryAgreementId;
  }

}
