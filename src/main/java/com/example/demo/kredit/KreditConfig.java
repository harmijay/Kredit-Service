package com.example.demo.kredit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.JANUARY;

@Configuration
public class KreditConfig {

    @Bean
    CommandLineRunner commandLineRunner(KreditRepository repository) {
        return args -> {
            Kredit satu = new Kredit(
                    0,
                    "Gold",
                    100,
                    25L,
                    LocalDate.of(2022, JANUARY, 1),
                    "Belum dibayar."
            );

            Kredit dua = new Kredit(
                    1,
                    "Silver",
                    50,
                    20L,
                    LocalDate.of(2025, Month.DECEMBER, 1),
                    "Belum dibayar."
            );

            repository.saveAll(
                    List.of(satu, dua)
            );
        };
    }
}
