package com.waterfogsw.board.core.util;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseRepositoryTest {

  @Autowired
  protected EntityManager entityManager;

  static final MySQLContainer<?> MY_SQL_CONTAINER;

  static {
    TestcontainersConfiguration.getInstance().updateUserConfig("testcontainers.reuse.enable", "true");
    MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8").withReuse(true);
  }

}
