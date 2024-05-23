package ru.study.test5;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.study.test5.repository.AccountPoolRepository;
import ru.study.test5.repository.AccountRepository;
import ru.study.test5.repository.TppRefProductRegisterTypeRepository;

@SpringBootTest
@Testcontainers
class Test5ApplicationTests {

	@Container
	@ServiceConnection
	public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>(
			DockerImageName.parse("postgres:latest"));

	@Autowired
	private AccountPoolRepository accountPoolRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;

	@Test
	@Transactional
	void testMigration() {

		assertThat(accountPoolRepository.count()).isEqualTo(2L);
		assertThat(accountRepository.count()).isEqualTo(6L);
		assertThat(tppRefProductRegisterTypeRepository.count()).isEqualTo(2L);
	}
}
