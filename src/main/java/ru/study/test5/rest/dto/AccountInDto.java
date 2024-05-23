package ru.study.test5.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountInDto {

  @NotNull
  private Long instanceId;
  private String registryTypeCode;
  private String accountType;
  private String currencyCode;
  private String branchCode;
  private String priorityCode;
  private String mdmCode;
  private String clientCode;
  private String trainRegion;
  private String counter;
  private String salesCode;
}
