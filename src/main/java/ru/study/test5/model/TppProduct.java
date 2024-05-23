package ru.study.test5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tpp_product")
public class TppProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("nextval('tpp_product_id_seq'")
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "product_code_id")
  private Long productCodeId;

  @Column(name = "client_id")
  private Long clientId;

  @Size(max = 50)
  @Column(name = "type", length = 50)
  private String type;

  @Size(max = 50)
  @Column(name = "number", length = 50)
  private String number;

  @Column(name = "priority")
  private Long priority;

  @Column(name = "date_of_conclusion")
  private LocalDate dateOfConclusion;

  @Column(name = "start_date_time")
  private LocalDate startDateTime;

  @Column(name = "end_date_time")
  private LocalDate endDateTime;

  @Column(name = "days")
  private Long days;

  @Column(name = "penalty_rate")
  private BigDecimal penaltyRate;

  @Column(name = "nso")
  private BigDecimal nso;

  @Column(name = "threshold_amount")
  private BigDecimal thresholdAmount;

  @Size(max = 50)
  @Column(name = "requisite_type", length = 50)
  private String requisiteType;

  @Size(max = 50)
  @Column(name = "interest_rate_type", length = 50)
  private String interestRateType;

  @Column(name = "tax_rate")
  private BigDecimal taxRate;

  @Size(max = 100)
  @Column(name = "reasone_close", length = 100)
  private String reasoneClose;

  @Size(max = 50)
  @Column(name = "state", length = 50)
  private String state;

}