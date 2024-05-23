package ru.study.test5.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.study.test5.exception.DoubleException;
import ru.study.test5.exception.NoDataFoundException;
import ru.study.test5.exception.NotEmptyException;
import ru.study.test5.rest.dto.AccountInDto;
import ru.study.test5.rest.dto.AccountOutDto;
import ru.study.test5.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corporate-settlement-account")
public class AccountController {

  private final AccountService accountService;

  @PostMapping("/create")
  public AccountOutDto create(@RequestBody @Valid AccountInDto dto)
      throws NotEmptyException, DoubleException, NoDataFoundException {

    return accountService.create(dto);
  }
}
