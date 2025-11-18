package org.app.matriculaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatriculaServiceApplication {
  
  public static void main(String[] args) {
    System.out.println("Starting Matricula Service Application...");
    SpringApplication.run(MatriculaServiceApplication.class, args);
  }
  
}
