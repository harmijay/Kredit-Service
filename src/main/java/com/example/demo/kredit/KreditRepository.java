package com.example.demo.kredit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KreditRepository
        extends JpaRepository<Kredit, Long> {

    Optional<Kredit> findKreditByNomorRekening(Integer nomorRekening);
}
