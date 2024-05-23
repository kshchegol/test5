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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tpp_product_register")
public class TppProductRegister {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("nextval('tpp_product_register_id_seq'")
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "product_id")
  private Long productId;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "type", nullable = false, referencedColumnName = "value")
  private TppRefProductRegisterType type;

  @Column(name = "account")
  private Long account;

  @Size(max = 30)
  @Column(name = "currency_code", length = 30)
  private String currencyCode;

  @Size(max = 50)
  @Column(name = "state", length = 50)
  private EnumStatus state;

  @Size(max = 25)
  @Column(name = "account_number", length = 25)
  private String accountNumber;

}