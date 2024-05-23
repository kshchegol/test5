package ru.study.test5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.study.test5.model.TppProduct;
import ru.study.test5.model.TppProductRegister;

public interface TppProductRepository extends CrudRepository<TppProduct, Integer> {

  TppProduct findFirstByNumber(String contractNumber);
  TppProduct findFirstById(Long id);
}
