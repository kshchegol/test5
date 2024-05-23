package ru.study.test5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tpp_ref_product_class")
public class TppRefProductClass {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("nextval('tpp_ref_product_class_internal_id_seq'")
  @Column(name = "internal_id", nullable = false)
  private Integer id;

  @Size(max = 100)
  @NotNull
  @Column(name = "value", nullable = false, length = 100)
  private String value;

  @Size(max = 50)
  @Column(name = "gbi_code", length = 50)
  private String gbiCode;

  @Size(max = 100)
  @Column(name = "gbi_name", length = 100)
  private String gbiName;

  @Size(max = 50)
  @Column(name = "product_row_code", length = 50)
  private String productRowCode;

  @Size(max = 100)
  @Column(name = "product_row_name", length = 100)
  private String productRowName;

  @Size(max = 50)
  @Column(name = "subclass_code", length = 50)
  private String subclassCode;

  @Size(max = 100)
  @Column(name = "subclass_name", length = 100)
  private String subclassName;

}