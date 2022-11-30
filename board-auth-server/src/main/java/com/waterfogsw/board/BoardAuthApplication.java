package com.waterfogsw.board;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(BoardAuthApplication.class, args);
  }

}
