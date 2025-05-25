package br.dev.ridanfaust.controleestoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ControleEstoqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleEstoqueApplication.class, args);
    }

}
