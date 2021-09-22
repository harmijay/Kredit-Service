package com.example.demo.kredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KreditService {
    private final KreditRepository kreditRepository;

    @Autowired
    public KreditService(KreditRepository kreditRepository) {
        this.kreditRepository = kreditRepository;
    }

    public List<Kredit> getKredit() {
        return kreditRepository.findAll();
    }

    public void bukaKredit(Kredit kredit) {
        Optional<Kredit> kreditOptional = kreditRepository.
                findKreditByNomorNasabah(kredit.getNomorNasabah());

        if (kreditOptional.isPresent()) {
            throw new IllegalStateException("No nasabah sudah terdaftar!");
        }
        kreditRepository.save(kredit);
    }

    public void tutupKredit(Long idKredit) {
        boolean exists = kreditRepository.existsById(idKredit);
        if (!exists) {
            throw new IllegalStateException(
                    "Kredit dengan ID" + idKredit + "tidak terdaftar!");
        }
        kreditRepository.deleteById(idKredit);
    }
}
