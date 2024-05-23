package ru.study.test5.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.study.test5.model.TppProductRegister;
import ru.study.test5.model.TppRefProductRegisterType;

public interface TppProductRegisterRepository extends CrudRepository<TppProductRegister, Integer> {

  boolean existsByProductIdAndType(Long productId, TppRefProductRegisterType type);

  TppProductRegister findFindFirstByProductId(Integer productId);
  List<TppProductRegister> findAllByProductId(Integer productId);
}
