package ru.study.test5.repository;

import org.springframework.data.repository.CrudRepository;
import ru.study.test5.model.AccountPool;

public interface AccountPoolRepository extends CrudRepository<AccountPool, Long> {

  AccountPool findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
      String branchCode,
      String currencyCode,
      String mdmCode,
      String priorityCode,
      String registryTypeCode
  );

}
