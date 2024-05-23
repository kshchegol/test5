package ru.study.test5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "account_pool")
public class AccountPool {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("nextval('account_pool_id_seq'")
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 50)
  @Column(name = "branch_code", length = 50)
  private String branchCode;

  @Size(max = 30)
  @Column(name = "currency_code", length = 30)
  private String currencyCode;

  @Size(max = 50)
  @Column(name = "mdm_code", length = 50)
  private String mdmCode;

  @Size(max = 30)
  @Column(name = "priority_code", length = 30)
  private String priorityCode;

  @Size(max = 50)
  @Column(name = "registry_type_code", length = 50)
  private String registryTypeCode;

}