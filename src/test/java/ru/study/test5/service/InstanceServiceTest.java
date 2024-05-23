package ru.study.test5.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.study.test5.exception.DoubleException;
import ru.study.test5.model.Agreement;
import ru.study.test5.model.TppProduct;
import ru.study.test5.model.TppRefAccountType;
import ru.study.test5.model.TppRefProductClass;
import ru.study.test5.model.TppRefProductRegisterType;
import ru.study.test5.repository.AgreementRepository;
import ru.study.test5.repository.TppProductRegisterRepository;
import ru.study.test5.repository.TppProductRepository;
import ru.study.test5.repository.TppRefProductClassRepository;
import ru.study.test5.repository.TppRefProductRegisterTypeRepository;
import ru.study.test5.rest.dto.InstanceInDto;
import ru.study.test5.rest.dto.InstanceInDto.InstanceArrangementDto;
import ru.study.test5.rest.dto.InstanceInDto.InstanceArrangementDto.DatainstanceArrangement;

class InstanceServiceTest {

  TppRefProductClass tppRefProductClass = new TppRefProductClass();
  TppRefAccountType tppRefAccountType = new TppRefAccountType();
  TppRefProductRegisterType tppRefProductRegisterType = new TppRefProductRegisterType();
  Agreement agreement = new Agreement();
  TppProduct tppProduct = new TppProduct();
  @Mock
  private TppProductRepository tppProductRepository;
  @Mock
  private AgreementRepository agreementRepository;
  @Mock
  private TppRefProductClassRepository tppRefProductClassRepository;
  @Mock
  private AccountService accountService;
  @Mock
  private TppProductRegisterRepository tppProductRegisterRepository;
  @Mock
  private TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;
  private InstanceService instanceService;

  {
    tppRefProductClass.setId(1);
  }

  {
    tppRefAccountType.setId(1);
    tppRefAccountType.setValue("Клиентский");
  }

  {
    tppRefProductRegisterType.setAccountType(tppRefAccountType);
  }

  {
    tppProduct.setId(1L);
  }

  @BeforeEach
  void setUp() {
    openMocks(this);
    instanceService = new InstanceService(tppProductRepository, agreementRepository,
        tppRefProductClassRepository, accountService, tppProductRegisterRepository,
        tppRefProductRegisterTypeRepository);

    when(tppRefProductClassRepository.findAllByValue("02.001.005")).thenReturn(
        List.of(tppRefProductClass));
    when(tppRefProductRegisterTypeRepository.findAllByProductClassCodeIn(
        List.of(tppRefProductClass))).thenReturn(List.of(tppRefProductRegisterType));
    when(tppProductRepository.save(Mockito.any(TppProduct.class))).thenAnswer(i -> {
      TppProduct rez = (TppProduct) i.getArguments()[0];
      rez.setId(1L);
      return rez;
    });
    when(tppProductRepository.findFirstById(1L)).thenReturn(tppProduct);
    when(tppProductRepository.findFirstByNumber("2")).thenReturn(tppProduct);
    when(agreementRepository.findAllByNumberIn(Set.of("2"))).thenReturn(List.of(agreement));
  }


  @Test
  void createSuccess() {

    InstanceInDto dto = new InstanceInDto();
    dto.setProductType("temp");
    dto.setProductCode("02.001.005");
    dto.setRegisterType("03.012.002_47533_ComSoLd");
    dto.setMdmCode("15");
    dto.setContractNumber("1");
    dto.setProductType("temp");
    dto.setContractDate(LocalDate.now());
    dto.setPriority(0);
    dto.setInterestRatePenalty(10.0f);
    dto.setMinimalBalance(10.0f);
    dto.setAccountingDetails("test");
    dto.setRateType("test");
    dto.setTaxPercentageRate(0.0f);
    dto.setTechnicalOverdraftLimitAmount(0.0f);
    dto.setContractId(0);
    dto.setUrgencyCode("test");
    dto.setBranchCode("0022");
    dto.setIsoCurrencyCode("800");
    dto.setInstanceArrangementDtos(List.of());
    dto.setAdditionalPropertiesVipDtos(List.of());

    assertDoesNotThrow(() -> instanceService.create(dto));
  }

  @Test
  void createSuccessInstanceId() {

    InstanceInDto dto = new InstanceInDto();
    dto.setInstanceId(1l);
    dto.setProductType("temp");
    dto.setProductCode("02.001.005");
    dto.setRegisterType("03.012.002_47533_ComSoLd");
    dto.setMdmCode("15");
    dto.setContractNumber("1");
    dto.setProductType("temp");
    dto.setContractDate(LocalDate.now());
    dto.setPriority(0);
    dto.setInterestRatePenalty(10.0f);
    dto.setMinimalBalance(10.0f);
    dto.setAccountingDetails("test");
    dto.setRateType("test");
    dto.setTaxPercentageRate(0.0f);
    dto.setTechnicalOverdraftLimitAmount(0.0f);
    dto.setContractId(0);
    dto.setUrgencyCode("test");
    dto.setBranchCode("0022");
    dto.setIsoCurrencyCode("800");
    dto.setInstanceArrangementDtos(List.of());
    dto.setAdditionalPropertiesVipDtos(List.of());

    assertDoesNotThrow(() -> instanceService.create(dto));
  }

  @Test
  void createFailDoubleException() {

    InstanceInDto dto = new InstanceInDto();
    dto.setContractNumber("2");
    dto.setProductType("temp");
    dto.setProductCode("02.001.005");
    dto.setRegisterType("03.012.002_47533_ComSoLd");
    dto.setMdmCode("15");
    dto.setProductType("temp");
    dto.setContractDate(LocalDate.now());
    dto.setPriority(0);
    dto.setInterestRatePenalty(10.0f);
    dto.setMinimalBalance(10.0f);
    dto.setAccountingDetails("test");
    dto.setRateType("test");
    dto.setTaxPercentageRate(0.0f);
    dto.setTechnicalOverdraftLimitAmount(0.0f);
    dto.setContractId(0);
    dto.setUrgencyCode("test");
    dto.setBranchCode("0022");
    dto.setIsoCurrencyCode("800");
    dto.setInstanceArrangementDtos(List.of());
    dto.setAdditionalPropertiesVipDtos(List.of());

    assertThrows(DoubleException.class, () -> instanceService.create(dto));
  }

  @Test
  void createFailDoubleArrangement() {

    InstanceInDto dto = new InstanceInDto();
    dto.setContractNumber("2");
    dto.setProductType("temp");
    dto.setProductCode("02.001.005");
    dto.setRegisterType("03.012.002_47533_ComSoLd");
    dto.setMdmCode("15");
    dto.setProductType("temp");
    dto.setContractDate(LocalDate.now());
    dto.setPriority(0);
    dto.setInterestRatePenalty(10.0f);
    dto.setMinimalBalance(10.0f);
    dto.setAccountingDetails("test");
    dto.setRateType("test");
    dto.setTaxPercentageRate(0.0f);
    dto.setTechnicalOverdraftLimitAmount(0.0f);
    dto.setContractId(0);
    dto.setUrgencyCode("test");
    dto.setBranchCode("0022");
    dto.setIsoCurrencyCode("800");
    DatainstanceArrangement datainstanceArrangement = new DatainstanceArrangement();
    datainstanceArrangement.setNumber("2");
    datainstanceArrangement.setOpeningDate(LocalDate.now());
    InstanceArrangementDto instanceArrangementDto = new InstanceArrangementDto();
    instanceArrangementDto.setData(datainstanceArrangement);
    dto.setInstanceArrangementDtos(List.of(instanceArrangementDto));
    dto.setAdditionalPropertiesVipDtos(List.of());

    assertThrows(DoubleException.class, () -> instanceService.create(dto));
  }
}