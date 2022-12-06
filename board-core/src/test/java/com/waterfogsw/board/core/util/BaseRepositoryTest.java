package com.waterfogsw.board.core.util;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class BaseRepositoryTest {

  @Container
  private static final GenericContainer<?> MY_SQL_CONTAINER = new GenericContainer<>("mysql/mysql-server:8.0");

}
