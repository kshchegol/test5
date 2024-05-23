package ru.study.test5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "agreement")
public class Agreement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("nextval('agreement_id_seq'")
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private TppProduct tppProduct;

  @Size(max = 50)
  @Column(name = "general_agreement_id", length = 50)
  private String generalAgreementId;

  @Size(max = 50)
  @Column(name = "supplementary_agreement_id", length = 50)
  private String supplementaryAgreementId;

  @Size(max = 50)
  @Column(name = "arrangement_type", length = 50)
  private String arrangementType;

  @Column(name = "sheduler_job_id")
  private Long shedulerJobId;

  @Size(max = 50)
  @Column(name = "number", length = 50)
  private String number;

  @Column(name = "opening_date")
  private LocalDate openingDate;

  @Column(name = "closing_date")
  private LocalDate closingDate;

  @Column(name = "cancel_date")
  private LocalDate cancelDate;

  @Column(name = "validity_duration")
  private Long validityDuration;

  @Size(max = 100)
  @Column(name = "cancellation_reason", length = 100)
  private String cancellationReason;

  @Size(max = 50)
  @Column(name = "status", length = 50)
  private String status;

  @Column(name = "interest_calculation_date")
  private LocalDate interestCalculationDate;

  @Column(name = "interest_rate")
  private BigDecimal interestRate;

  @Column(name = "coefficient")
  private BigDecimal coefficient;

  @Size(max = 50)
  @Column(name = "coefficient_action", length = 50)
  private String coefficientAction;

  @Column(name = "minimum_interest_rate")
  private Float minimumInterestRate;

  @Column(name = "minimum_interest_rate_coefficient")
  private Float  minimumInterestRateCoefficient;

  @Size(max = 50)
  @Column(name = "minimum_interest_rate_coefficient_action", length = 50)
  private String minimumInterestRateCoefficientAction;

  @Column(name = "maximal_interest_rate")
  private Float  maximalInterestRate;

  @Column(name = "maximal_interest_rate_coefficient")
  private Float  maximalInterestRateCoefficient;

  @Size(max = 50)
  @Column(name = "maximal_interest_rate_coefficient_action", length = 50)
  private String maximalInterestRateCoefficientAction;

}