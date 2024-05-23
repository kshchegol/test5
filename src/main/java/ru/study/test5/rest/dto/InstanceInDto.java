package ru.study.test5.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class InstanceInDto {

  @NotNull
  private Long instanceId;
  private String productType;
  private String productCode;
  private String registerType;
  private String mdmCode;
  private String contractNumber;
  private LocalDate contractDate;
  private Integer priority;
  private Float interestRatePenalty;
  private Float minimalBalance;
  private String accountingDetails;
  private String rateType;
  private Float taxPercentageRate;
  private Float technicalOverdraftLimitAmount;
  private Integer contractId;
  private String BranchCode;
  private String IsoCurrencyCode;
  private String urgencyCode;
  private Integer ReferenceCode;
  private List<AdditionalPropertiesVipDto> additionalPropertiesVipDtos;
  private List<InstanceArrangementDto> instanceArrangementDtos;

  @Data
  static class AdditionalPropertiesVipDto {
    private DataAdditionalPropertiesVip data;
    @Data
    static class DataAdditionalPropertiesVip {
    private String key;
    private String value;
    private String name;
    }
  }

  @Data
  public static class InstanceArrangementDto {

    private DatainstanceArrangement data;

    @Data
    public static class DatainstanceArrangement {
      private String generalAgreementId;
      private String supplementaryAgreementId;
      private String arrangementType;
      private Integer schedulerJobId;
      private String number;
      private LocalDate openingDate;
      private LocalDate closingDate;
      private LocalDate cancelDate;
      private Integer validityDuration;
      private String cancellationReason;
      private String status;
      private LocalDate interestCalculationDate;
      private Float interestRate;
      private Float coefficient;
      private String coefficientAction;
      private Float minimumInterestRate;
      private Float minimumInterestRateCoefficient;
      private String minimumInterestRateCoefficientAction;
      private Float maximalInterestRate;
      private Float maximalInterestRateCoefficient;
      private String maximalInterestRateCoefficientAction;
    }
  }
}
