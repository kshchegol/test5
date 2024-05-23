package ru.study.test5.rest;

import jakarta.validation.Valid;
import java.time.Instant;
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
import ru.study.test5.rest.dto.InstanceInDto;
import ru.study.test5.rest.dto.InstanceOutDto;
import ru.study.test5.service.AccountService;
import ru.study.test5.service.InstanceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corporate-settlement-instance")
public class InstanceController {

  private final InstanceService instanceService;

  @PostMapping("/create")
  public InstanceOutDto create(@RequestBody @Valid InstanceInDto dto)
      throws NotEmptyException, DoubleException, NoDataFoundException {

    return instanceService.create(dto);
  }
}
