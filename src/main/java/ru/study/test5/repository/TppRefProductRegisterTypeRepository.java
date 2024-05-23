package ru.study.test5.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.study.test5.model.TppRefProductClass;
import ru.study.test5.model.TppRefProductRegisterType;

public interface TppRefProductRegisterTypeRepository extends CrudRepository<TppRefProductRegisterType, Integer> {

  TppRefProductRegisterType findFirstByValue(String value);

  List<TppRefProductRegisterType> findAllByProductClassCodeIn(List<TppRefProductClass> productClassCodes);
}
