package com.example.demo.kredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.*;

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
                findKreditByNomorRekening(kredit.getNomorRekening());

        if (kreditOptional.isPresent()) {
            throw new IllegalStateException("Nasabah sudah terdaftar!");
        }
        kreditRepository.save(kredit);
    }

    public void tutupKredit(Long id) {
        boolean exists = kreditRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(
                    "Kredit dengan ID " + id + " tidak terdaftar!");
        }
        kreditRepository.deleteById(id);
    }

    @Transactional
    public void updateTipeKredit(Integer nomorRekening, String tipeKredit) {
        Kredit kredit = kreditRepository.findKreditByNomorRekening(nomorRekening)
                .orElseThrow(() -> new IllegalStateException(
                        "Nasabah dengan nomor rekening " + nomorRekening + " tidak terdaftar!"));
        if (tipeKredit != null) {
            kredit.setTipeKredit(tipeKredit);
            System.out.println(kredit.getTipeKredit());
        }
    }

    public void validasiNomorRekening(Integer nomorRekening) {
        WebClient nasabahClient = WebClient.create("http://10.10.30.34:7001");
        WebClient.ResponseSpec responseSpec = nasabahClient.get()
                .uri("nasabah/validasiNomorRekening/" + nomorRekening)
                .retrieve();

        HashMap<Integer, String> nasabah = responseSpec.bodyToMono(HashMap.class).block();

        System.out.println(nasabah.keySet());
    }

    public String bayarKredit(Integer nomorRekening) {
        Kredit kredit = kreditRepository.findKreditByNomorRekening(nomorRekening)
                .orElseThrow(() -> new IllegalStateException(
                        "Nasabah dengan nomor rekening " + nomorRekening + " tidak terdaftar!"));

        HashMap hsmap = new HashMap();
        hsmap.put("nomorRekening", nomorRekening);
        hsmap.put("jumlah", kredit.getJumlahKredit());

        WebClient tabunganClient = WebClient.create("http://10.10.30.32:7002");
        WebClient.ResponseSpec responseSpec = tabunganClient.put()
                .uri("tabungan/kurangi_saldo")
                .body(Mono.just(hsmap), HashMap.class)
                .retrieve();

        String responseBody = responseSpec.bodyToMono(String.class).block();
        return responseBody;
    }

    public Long tambahKredit(Integer nomorRekening, Long jumlahTambahKredit) {
        List<Kredit> kreditList = getKredit();
        for (int i = 0; i < kreditList.size(); i++) {
            if (kreditList.get(i).getNomorRekening().equals(nomorRekening)) {
                return kreditList.get(i).getJumlahKredit() + jumlahTambahKredit;
            }
        }
        return 0L;
    }

    public String getStatusKredit(Integer nomorRekening) {
        List<Kredit> kreditList = getKredit();
        for (int i = 0; i < kreditList.size(); i++) {
            if (kreditList.get(i).getNomorRekening().equals(nomorRekening)) {
                return kreditList.get(i).getStatusKredit();
            }
        }
        return null;
    }

    public String testApi() {
        HashMap hsmap = new HashMap();
        hsmap.put("nomorNasabah", 456);
        hsmap.put("jenisTransaksi", 2);
        hsmap.put("waktuTransaksi", "2021-09-22T14:13:10");
        hsmap.put("statusTransaksi", 1);
        hsmap.put("logTransaksi", "Log Transaksi - Kredit");

        WebClient tabunganClient = WebClient.create("http://10.10.30.49:7007");
        WebClient.ResponseSpec responseSpec = tabunganClient.post()
                .uri("api/transaksi")
                .body(Mono.just(hsmap), HashMap.class)
                .retrieve();

        String responseBody = responseSpec.bodyToMono(String.class).block();
        return responseBody;
    }
}
