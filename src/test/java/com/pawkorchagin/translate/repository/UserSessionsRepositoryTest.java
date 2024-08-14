package com.pawkorchagin.translate.repository;

import com.pawkorchagin.translate.repository.cfg.RepositoryConfig;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserSessionsRepositoryTest {
	@Autowired
	private UserSessionsRepository userSessionsRepository;

	@MockBean
	private RepositoryConfig repositoryConfig;

	@Test
	public void init() throws IOException, ClassNotFoundException, SQLException {
		String ip = "abc";
		userSessionsRepository.init(ip);
	}
}
