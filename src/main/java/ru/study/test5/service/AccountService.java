package ru.study.test5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.test5.exception.DoubleException;
import ru.study.test5.exception.NoDataFoundException;
import ru.study.test5.exception.NotEmptyException;
import ru.study.test5.model.Account;
import ru.study.test5.model.AccountPool;
import ru.study.test5.model.EnumStatus;
import ru.study.test5.model.TppProductRegister;
import ru.study.test5.model.TppRefProductRegisterType;
import ru.study.test5.repository.AccountPoolRepository;
import ru.study.test5.repository.AccountRepository;
import ru.study.test5.repository.TppProductRegisterRepository;
import ru.study.test5.repository.TppRefProductRegisterTypeRepository;
import ru.study.test5.rest.dto.AccountInDto;
import ru.study.test5.rest.dto.AccountOutDto;
import ru.study.test5.rest.dto.AccountOutDto.DataDto;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final TppProductRegisterRepository tppProductRegisterRepository;
  private final TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;
  private final AccountPoolRepository accountPoolRepository;

  @Transactional
  public AccountOutDto create(AccountInDto accountInDto)
      throws NotEmptyException, DoubleException, NoDataFoundException {

    validate(accountInDto);

    TppRefProductRegisterType type = tppRefProductRegisterTypeRepository.findFirstByValue(
        accountInDto.getRegistryTypeCode());

    if (tppRefProductRegisterTypeRepository.findFirstByValue(accountInDto.getRegistryTypeCode())
        == null) {
      throw new NoDataFoundException("Код продукта " + accountInDto.getRegistryTypeCode()
          + " не найден в Каталоге продуктов task5.tpp_ref_product_register_type для данного типа регистра");
    }

    if (tppProductRegisterRepository.existsByProductIdAndType(accountInDto.getInstanceId(), type)) {
      throw new DoubleException(
          "Параметр RegistryTypeCode тип регистра " + accountInDto.getRegistryTypeCode()
              + " уже существует");
    }
    AccountPool accountPool = accountPoolRepository.findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
        accountInDto.getBranchCode(), accountInDto.getCurrencyCode(), accountInDto.getMdmCode(),
        accountInDto.getPriorityCode(), accountInDto.getRegistryTypeCode());

    Account account = accountRepository.findFirstByAccountPool(accountPool);

    TppProductRegister productRegister = new TppProductRegister();
    productRegister.setProductId(accountInDto.getInstanceId());
    productRegister.setType(type);
    productRegister.setAccount(account.getId());
    productRegister.setCurrencyCode(accountInDto.getCurrencyCode());
    productRegister.setAccountNumber(account.getAccountNumber());
    productRegister.setState(EnumStatus.OPEN);

    tppProductRegisterRepository.save(productRegister);

    AccountOutDto accountOut = new AccountOutDto();
    accountOut.setData(new DataDto(String.valueOf(productRegister.getId())));

    return accountOut;
  }

  private void validate(AccountInDto dto) throws NotEmptyException {
    validateString(dto.getInstanceId(), "InstanceId");
  }

  private void validateString(Object value, String fieldname) throws NotEmptyException {
    if (value == null) {
      throw new NotEmptyException("Обязательный параметр " + fieldname + " не заполнено");
    }
  }
}
