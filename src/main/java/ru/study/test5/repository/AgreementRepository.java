package ru.study.test5.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import ru.study.test5.model.Agreement;
import ru.study.test5.model.TppProduct;

public interface AgreementRepository extends CrudRepository<Agreement, Integer> {
  Agreement findFirstByNumber(String number);

  List<Agreement> findAllByNumberIn(Set<String> number);
  List<Agreement> findAllByTppProduct(TppProduct product);
}
