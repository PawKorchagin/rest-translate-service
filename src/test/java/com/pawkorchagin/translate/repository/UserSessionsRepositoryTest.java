package com.pawkorchagin.translate.repository;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserSessionsRepositoryTest {
	@Autowired
	private UserSessionsRepository userSessionsRepository;

	@Test
	public void init() throws IOException, ClassNotFoundException, SQLException {
		String ip = "128.0.0.1";
		userSessionsRepository.addUserSession(ip);

		var ips = userSessionsRepository.selectAll();

		assertFalse(ips.isEmpty());
	}
}