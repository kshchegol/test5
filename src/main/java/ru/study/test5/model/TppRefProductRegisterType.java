package ru.study.test5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tpp_ref_product_register_type")
public class TppRefProductRegisterType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("nextval('tpp_ref_product_register_type_internal_id_seq'")
  @Column(name = "internal_id", nullable = false)
  private Integer id;

  @Size(max = 100)
  @NotNull
  @Column(name = "value", nullable = false, length = 100)
  private String value;

  @Size(max = 100)
  @NotNull
  @Column(name = "register_type_name", nullable = false, length = 100)
  private String registerTypeName;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_class_code", nullable = false, referencedColumnName = "value")
  private TppRefProductClass productClassCode;

  @Column(name = "register_type_start_date")
  private Instant registerTypeStartDate;

  @Column(name = "register_type_end_date")
  private Instant registerTypeEndDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_type", referencedColumnName = "value")
  private TppRefAccountType accountType;

  @OneToMany(mappedBy = "type")
  private Set<TppProductRegister> tppProductRegisters = new LinkedHashSet<>();

}