package ru.study.test5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.study.test5.exception.DoubleException;
import ru.study.test5.exception.NoDataFoundException;
import ru.study.test5.exception.NotEmptyException;
import ru.study.test5.model.Account;
import ru.study.test5.model.AccountPool;
import ru.study.test5.model.TppProductRegister;
import ru.study.test5.model.TppRefProductRegisterType;
import ru.study.test5.repository.AccountPoolRepository;
import ru.study.test5.repository.AccountRepository;
import ru.study.test5.repository.TppProductRegisterRepository;
import ru.study.test5.repository.TppRefProductRegisterTypeRepository;
import ru.study.test5.rest.dto.AccountInDto;
import ru.study.test5.rest.dto.AccountOutDto;

class AccountServiceTest {

  public static final String TEST_TYPE_CODE = "testTypeCode";
  public static final String TEST_BRANCH_CODE = "testBranchCode";
  public static final String TEST_CURRENCY_CODE = "810";
  public static final String TEST_MDM_CODE = "15";
  public static final String TEST_PRIORITY_CODE = "00";
  public static final Long TEST_PRODUCT_ID = 12345L;
  public static final Long TEST_PRODUCT_ID_DOUBLE = 123450L;
  public static final Long TEST_ACCOUNT_ID = 12222L;
  public static final Integer TEST_REGISTER_ID = 13333;
  public static TppRefProductRegisterType TEST_REF_PROD_REGISTER = new TppRefProductRegisterType();
  public static AccountPool TEST_ACCOUNT_POOL = new AccountPool();
  public static Account TEST_ACCOUNT = new Account();
  public static TppProductRegister TEST_PROD_REGISTER = new TppProductRegister();
  private AccountService accountService;
  @Mock
  private AccountRepository accountRepository;
  @Mock
  private TppProductRegisterRepository tppProductRegisterRepository;
  @Mock
  private TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;
  @Mock
  private AccountPoolRepository accountPoolRepository;

  {
    TEST_ACCOUNT.setId(TEST_ACCOUNT_ID);
  }

  {
    TEST_PROD_REGISTER.setProductId(TEST_PRODUCT_ID);
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    accountService = new AccountService(accountRepository, tppProductRegisterRepository,
        tppRefProductRegisterTypeRepository, accountPoolRepository);
    when(tppRefProductRegisterTypeRepository.findFirstByValue(TEST_TYPE_CODE)).thenReturn(
        TEST_REF_PROD_REGISTER);
    when(tppProductRegisterRepository.existsByProductIdAndType(TEST_PRODUCT_ID,
        TEST_REF_PROD_REGISTER)).thenReturn(false);
    when(tppProductRegisterRepository.existsByProductIdAndType(TEST_PRODUCT_ID_DOUBLE,
        TEST_REF_PROD_REGISTER)).thenReturn(true);
    when(
        accountPoolRepository.findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
            TEST_BRANCH_CODE, TEST_CURRENCY_CODE, TEST_MDM_CODE, TEST_PRIORITY_CODE,
            TEST_TYPE_CODE)).thenReturn(TEST_ACCOUNT_POOL);
    when(accountRepository.findFirstByAccountPool(TEST_ACCOUNT_POOL)).thenReturn(TEST_ACCOUNT);
    when(tppProductRegisterRepository.save(Mockito.any(TppProductRegister.class))).thenAnswer(i -> {
      TppProductRegister rez = (TppProductRegister) i.getArguments()[0];
      rez.setId(TEST_REGISTER_ID);
      return rez;
    });
  }

  @Test
  void createSuccess() throws DoubleException, NotEmptyException, NoDataFoundException {
    AccountInDto accountInDto = new AccountInDto();
    accountInDto.setInstanceId(TEST_PRODUCT_ID);
    accountInDto.setRegistryTypeCode(TEST_TYPE_CODE);
    accountInDto.setBranchCode(TEST_BRANCH_CODE);
    accountInDto.setCurrencyCode(TEST_CURRENCY_CODE);
    accountInDto.setMdmCode(TEST_MDM_CODE);
    accountInDto.setPriorityCode(TEST_PRIORITY_CODE);
    AccountOutDto accountOutDto = accountService.create(accountInDto);
    assertNotNull(accountOutDto);
    assertEquals(accountOutDto.getData().getAccountId(), String.valueOf(TEST_REGISTER_ID));
  }

  @Test
  void createFailNotFoundCodProd() throws DoubleException, NotEmptyException, NoDataFoundException {
    AccountInDto accountInDto = new AccountInDto();
    accountInDto.setInstanceId(TEST_PRODUCT_ID);
    accountInDto.setRegistryTypeCode("");
    accountInDto.setBranchCode(TEST_BRANCH_CODE);
    accountInDto.setCurrencyCode(TEST_CURRENCY_CODE);
    accountInDto.setMdmCode(TEST_MDM_CODE);
    accountInDto.setPriorityCode(TEST_PRIORITY_CODE);
    assertThrows(NoDataFoundException.class, () -> accountService.create(accountInDto));
  }

  @Test
  void createFailDouble() throws DoubleException, NotEmptyException, NoDataFoundException {

    AccountInDto accountInDto = new AccountInDto();
    accountInDto.setInstanceId(TEST_PRODUCT_ID_DOUBLE);
    accountInDto.setRegistryTypeCode(TEST_TYPE_CODE);
    accountInDto.setBranchCode(TEST_BRANCH_CODE);
    accountInDto.setCurrencyCode(TEST_CURRENCY_CODE);
    accountInDto.setMdmCode(TEST_MDM_CODE);
    accountInDto.setPriorityCode(TEST_PRIORITY_CODE);
    assertThrows(DoubleException.class, () -> accountService.create(accountInDto));
  }

  @Test
  void createFailNotEmpty() throws DoubleException, NotEmptyException, NoDataFoundException {

    AccountInDto accountInDto = new AccountInDto();
    accountInDto.setInstanceId(null);
    accountInDto.setRegistryTypeCode(TEST_TYPE_CODE);
    accountInDto.setBranchCode(TEST_BRANCH_CODE);
    accountInDto.setCurrencyCode(TEST_CURRENCY_CODE);
    accountInDto.setMdmCode(TEST_MDM_CODE);
    accountInDto.setPriorityCode(TEST_PRIORITY_CODE);
    assertThrows(NotEmptyException.class, () -> accountService.create(accountInDto));
  }

}