package ru.study.test5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.study.test5.model.Account;
import ru.study.test5.model.AccountPool;

public interface AccountRepository extends CrudRepository<Account, Long> {

  Account findFirstByAccountPool(AccountPool accountPool);

}
