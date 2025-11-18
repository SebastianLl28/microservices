package org.app.matriculaservice.infrastructure.adapter.in.web;

/**
 * @author Alonso
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
  
    @GetMapping
    public String healthCheck() {
        return "Matricula Service is up and running!";
    }
  
}
