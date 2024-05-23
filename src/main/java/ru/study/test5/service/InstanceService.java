package ru.study.test5.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.test5.exception.DoubleException;
import ru.study.test5.exception.NoDataFoundException;
import ru.study.test5.exception.NotEmptyException;
import ru.study.test5.model.Agreement;
import ru.study.test5.model.EnumStatus;
import ru.study.test5.model.TppProduct;
import ru.study.test5.model.TppProductRegister;
import ru.study.test5.model.TppRefProductClass;
import ru.study.test5.model.TppRefProductRegisterType;
import ru.study.test5.repository.AgreementRepository;
import ru.study.test5.repository.TppProductRegisterRepository;
import ru.study.test5.repository.TppProductRepository;
import ru.study.test5.repository.TppRefProductClassRepository;
import ru.study.test5.repository.TppRefProductRegisterTypeRepository;
import ru.study.test5.rest.dto.AccountInDto;
import ru.study.test5.rest.dto.InstanceInDto;
import ru.study.test5.rest.dto.InstanceInDto.InstanceArrangementDto;
import ru.study.test5.rest.dto.InstanceOutDto;
import ru.study.test5.rest.dto.InstanceOutDto.DataDto;

@Service
@RequiredArgsConstructor
public class InstanceService {

  private final TppProductRepository tppProductRepository;
  private final AgreementRepository agreementRepository;
  private final TppRefProductClassRepository tppRefProductClassRepository;
  private final AccountService accountService;
  private final TppProductRegisterRepository tppProductRegisterRepository;
  private final TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;

  @Transactional
  public InstanceOutDto create(InstanceInDto dto)
      throws NotEmptyException, DoubleException, NoDataFoundException {

    validate(dto);

    TppProduct tmpProd;

    if (dto.getInstanceId() == null) {
      TppProduct tppProduct = tppProductRepository.findFirstByNumber(dto.getContractNumber());
      if (tppProduct != null) {
        throw new DoubleException("Параметр ContractNumber № договора "+ dto.getContractNumber() + " уже существует для ЭП с ИД " + tppProduct.getId());
      }

      checkAgreementDoudble(dto);

      //1.3

      List<TppRefProductClass> tppRefProductClasses = tppRefProductClassRepository.findAllByValue(dto.getProductCode());
      List<TppRefProductRegisterType> tppRefProductRegisterTypes = tppRefProductRegisterTypeRepository.findAllByProductClassCodeIn(tppRefProductClasses);

      List<TppRefProductRegisterType> tppRefProductRegisterTypesClient = tppRefProductRegisterTypes.stream().filter(type -> type.getAccountType().getId() == 1).toList();

      if (tppRefProductRegisterTypesClient == null || tppRefProductRegisterTypesClient.size() == 0) {
        throw new NoDataFoundException("Код Продукта " + dto.getProductCode() + " не найдено в Каталоге продуктов tpp_ref_product_class");
      }

      //1.4
      // добавление продукта

      TppProduct newTppProduct = new TppProduct();
       newTppProduct.setProductCodeId(Long.valueOf(tppRefProductClasses.get(0).getId()));

      newTppProduct.setClientId(Long.valueOf(dto.getMdmCode()));
      newTppProduct.setType(dto.getProductType());
      newTppProduct.setNumber(dto.getContractNumber());
      newTppProduct.setPriority(Long.valueOf(dto.getPriority()));
      newTppProduct.setDateOfConclusion(dto.getContractDate());
      newTppProduct.setPenaltyRate(BigDecimal.valueOf(dto.getInterestRatePenalty()));
      newTppProduct.setThresholdAmount(BigDecimal.valueOf(dto.getTechnicalOverdraftLimitAmount()));
      newTppProduct.setInterestRateType(dto.getRateType());
      newTppProduct.setTaxRate(BigDecimal.valueOf(dto.getTaxPercentageRate()));
      newTppProduct.setState(String.valueOf(EnumStatus.OPEN));

      tppProductRepository.save(newTppProduct);

      //1.5
      //добавление ПР

      for (TppRefProductRegisterType type: tppRefProductRegisterTypesClient) {

        AccountInDto accountInDto = new AccountInDto();

        accountInDto.setAccountType(type.getAccountType().getValue());
        accountInDto.setInstanceId(Long.valueOf(newTppProduct.getId()));
        accountInDto.setBranchCode(dto.getBranchCode());
        accountInDto.setMdmCode(dto.getMdmCode());
        accountInDto.setCurrencyCode(dto.getIsoCurrencyCode());
        accountInDto.setRegistryTypeCode(dto.getRegisterType());
        accountInDto.setPriorityCode("00");

        accountService.create(accountInDto);
      }
      tmpProd = tppProductRepository.findFirstById(newTppProduct.getId());

      if (tmpProd == null) {
        throw new NoDataFoundException("Экземпляр продукта с параметром instanceId " + newTppProduct.getId() + " не найден");
      }
      checkAgreementDoudble(dto);

      agreementRepository.saveAll(addAgreement(dto, newTppProduct));

    } else {
      tmpProd = tppProductRepository.findFirstById(dto.getInstanceId());
      if (tmpProd == null) {
        throw new NoDataFoundException("Экземпляр продукта с параметром instanceId " + dto.getInstanceId() + " не найден");
      }
      agreementRepository.saveAll(addAgreement(dto, tmpProd));
    }

    InstanceOutDto outDto = new InstanceOutDto();
    DataDto dataDto =new DataDto();
    dataDto.setInstanceId(String.valueOf(tmpProd.getId()));
    dataDto.setSupplementaryAgreementId(agreementRepository.findAllByTppProduct(tmpProd).stream().map(Agreement::getId).toList());
    dataDto.setRegisterId(tppProductRegisterRepository.findAllByProductId(
        Math.toIntExact(tmpProd.getId())).stream().map(
        TppProductRegister::getId).toList());
    outDto.setData(dataDto);

    return outDto;
  }

  private void validate(InstanceInDto dto) throws NotEmptyException {
    validateString(dto.getProductType(), "productType");
    validateString(dto.getProductCode(), "productCode");
    validateString(dto.getRegisterType(), "registerType");
    validateString(dto.getMdmCode(), "mdmCode");
    validateString(dto.getContractNumber(), "contractNumber");
    validateEmpty(dto.getContractDate(), "contractDate");
    validateEmpty(dto.getPriority(), "priority");
    validateEmpty(dto.getContractId(), "contractId");
    validateString(dto.getBranchCode(), "branchCode");
    validateString(dto.getIsoCurrencyCode(), "isoCurrencyCode");
    validateString(dto.getUrgencyCode(), "urgencyCode");

    if (dto.getInstanceArrangementDtos().size() > 0) {
      for (InstanceArrangementDto instanceArrangementDto : dto.getInstanceArrangementDtos()) {
        validateAgreement(instanceArrangementDto);
      }
    }

  }

  private void validateAgreement(InstanceArrangementDto dto) throws NotEmptyException {
    validateEmpty(dto.getData().getNumber(), "number");
    validateEmpty(dto.getData().getOpeningDate(), "openingDate");
  }

  private void validateString(String value, String fieldname) throws NotEmptyException {
    if (value == null || value.isBlank()) {
      throw new NotEmptyException("Обязательный параметр " + fieldname + " не заполнено");
    }
  }

  private void validateEmpty(Object value, String fieldname) throws NotEmptyException {
    if (value == null) {
      throw new NotEmptyException("Обязательный параметр " + fieldname + " не заполнено");
    }
  }

  private void checkAgreementDoudble(InstanceInDto dto) throws DoubleException {
    List<Agreement> agreements = agreementRepository.findAllByNumberIn(
        dto.getInstanceArrangementDtos().stream()
            .map(instanceArrangementDto -> instanceArrangementDto.getData().getNumber())
            .collect(Collectors.toSet()));

    if (agreements != null && agreements.size() > 0) {
      throw new DoubleException("Параметр № Дополнительного соглашения (сделки) Number " + agreements.stream().map(
          Agreement::getNumber).collect(
          Collectors.joining(" ")) + " уже существует для ЭП с ИД  " + agreements.stream().map(
          Agreement::getId).map(String::valueOf).collect(
          Collectors.joining(" ")));
    }
  }

  private List<Agreement> addAgreement(InstanceInDto dto, TppProduct tppProduct) {
    List<Agreement> agreements = new ArrayList<>();

    for (InstanceArrangementDto agr : dto.getInstanceArrangementDtos()) {
      Agreement tmpAgreement = new Agreement();
      tmpAgreement.setTppProduct(tppProduct);
      tmpAgreement.setGeneralAgreementId(agr.getData().getGeneralAgreementId());
      tmpAgreement.setSupplementaryAgreementId(agr.getData().getSupplementaryAgreementId());
      tmpAgreement.setArrangementType(agr.getData().getArrangementType());
      tmpAgreement.setShedulerJobId(Long.valueOf(agr.getData().getSchedulerJobId()));
      tmpAgreement.setNumber(agr.getData().getNumber());
      tmpAgreement.setOpeningDate(agr.getData().getOpeningDate());
      tmpAgreement.setClosingDate(agr.getData().getClosingDate());
      tmpAgreement.setCancelDate(agr.getData().getCancelDate());
      tmpAgreement.setValidityDuration(Long.valueOf(agr.getData().getValidityDuration()));
      tmpAgreement.setCancellationReason(agr.getData().getCancellationReason());
      tmpAgreement.setStatus(agr.getData().getStatus());
      tmpAgreement.setInterestCalculationDate(agr.getData().getInterestCalculationDate());
      tmpAgreement.setInterestRate(BigDecimal.valueOf(agr.getData().getInterestRate()));
      tmpAgreement.setCoefficient(BigDecimal.valueOf(agr.getData().getCoefficient()));
      tmpAgreement.setCoefficientAction(agr.getData().getCoefficientAction());
      tmpAgreement.setMinimumInterestRate(agr.getData().getMinimumInterestRate());
      tmpAgreement.setMinimumInterestRateCoefficient(
          agr.getData().getMinimumInterestRateCoefficient());
      tmpAgreement.setMinimumInterestRateCoefficientAction(
          agr.getData().getMinimumInterestRateCoefficientAction());
      tmpAgreement.setMaximalInterestRate(agr.getData().getMaximalInterestRate());
      tmpAgreement.setMaximalInterestRateCoefficient(
          agr.getData().getMaximalInterestRateCoefficient());
      tmpAgreement.setMaximalInterestRateCoefficientAction(
          agr.getData().getMaximalInterestRateCoefficientAction());

      agreements.add(tmpAgreement);
    }
    return agreements;
  }
}
