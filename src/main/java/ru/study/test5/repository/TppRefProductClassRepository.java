package ru.study.test5.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.study.test5.model.TppRefProductClass;

public interface TppRefProductClassRepository extends CrudRepository<TppRefProductClass, Integer> {

  List<TppRefProductClass> findAllByValue(String value);
}

