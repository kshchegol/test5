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
@Table(name = "tpp_ref_account_type")
public class TppRefAccountType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("nextval('tpp_ref_account_type_internal_id_seq'")
  @Column(name = "internal_id", nullable = false)
  private Integer id;

  @Size(max = 100)
  @NotNull
  @Column(name = "value", nullable = false, length = 100)
  private String value;

}