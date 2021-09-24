package com.example.demo.kredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

import static java.time.Month.JANUARY;

@Service
public class KreditService {
    public static final Integer STATUS_OK = 250;
    public static final Integer STATUS_GAGAL_NOREK = 451;
    public static final Integer STATUS_GAGAL_SALDO = 452;

    private final KreditRepository kreditRepository;

    @Autowired
    public KreditService(KreditRepository kreditRepository) {
        this.kreditRepository = kreditRepository;
    }

    public List<Kredit> getKredit() {
        return kreditRepository.findAll();
    }

    public HashMap<String, Object> bukaKredit(HashMap<String, Object> kreditBaru) {
        Optional<Kredit> kredit = kreditRepository
                .findKreditByNomorRekening((Integer) kreditBaru.get("nomorRekening"));

        HashMap<String, Object> response = new HashMap<>();

        if (kredit.isPresent()) {
            response.put("status", STATUS_GAGAL_NOREK);
            response.put("pesan", "Nomor rekening sudah terdaftar.");
        } else {
            Kredit saveKredit = new Kredit(
                    (Integer) kreditBaru.get("nomorRekening"),
                    (String) kreditBaru.get("tipeKredit"),
                    10,
                    (Long) kreditBaru.get("jumlahKredit"),
                    LocalDate.of(2022, JANUARY, 1),
                    "Belum dibayar"
            );

            kreditRepository.save(saveKredit);
            response.put("status", STATUS_OK);
        }
        return response;
    }

    public HashMap<String, Object> tutupKredit(Integer nomorRekening) {
        Optional<Kredit> kredit = kreditRepository.findKreditByNomorRekening(nomorRekening);

        HashMap<String, Object> response = new HashMap<>();

        if (kredit.isEmpty()) {
            response.put("status", STATUS_GAGAL_NOREK);
            response.put("pesan", "Nomor rekening tidak terdaftar.");
        } else {
            kreditRepository.deleteById(kredit.get().getId());
            response.put("status", STATUS_OK);
        }
        return response;
    }

    @Transactional
    public HashMap<String, Object> updateTipeKredit(Integer nomorRekening, String tipeKredit) {
        Optional<Kredit> kredit = kreditRepository.findKreditByNomorRekening(nomorRekening);

        HashMap<String, Object> response = new HashMap<>();

        if (kredit.isEmpty()) {
            response.put("status", STATUS_GAGAL_NOREK);
            response.put("pesan", "Nomor rekening tidak terdaftar.");
        } else if (tipeKredit != null) {
            kredit.get().setTipeKredit(tipeKredit);
            response.put("status", STATUS_OK);
        }
        return response;
    }

    public HashMap<String, Object> tambahKredit(Integer nomorRekening, Long jumlahTambahKredit) {
        Optional<Kredit> kredit = kreditRepository.findKreditByNomorRekening(nomorRekening);

        HashMap<String, Object> response = new HashMap<>();

        if (kredit.isEmpty()) {
            response.put("status", STATUS_GAGAL_NOREK);
            response.put("pesan", "Nomor rekening tidak terdaftar.");
        } else if (jumlahTambahKredit >= 0) {
            jumlahTambahKredit += kredit.get().getJumlahKredit();
            kredit.get().setJumlahKredit(jumlahTambahKredit);
            response.put("status", STATUS_OK);
        }

        return response;
    }

    public HashMap<String, Object> getStatusKredit(Integer nomorRekening) {
        Optional<Kredit> kredit = kreditRepository.findKreditByNomorRekening(nomorRekening);

        HashMap<String, Object> response = new HashMap<>();

        if (kredit.isEmpty()) {
            response.put("status", STATUS_GAGAL_NOREK);
            response.put("pesan", "Nomor rekening tidak terdaftar.");
        } else {
            response.put("status", STATUS_OK);
            response.put("statusKredit", kredit.get().getStatusKredit());
        }

        return response;
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

    public HashMap<String, Object> postTransaksi(Integer jenisTransaksi,
                                                 Integer statusTransaksi,
                                                 String logTransaksi) {
        WebClient transaksiClient = WebClient.create("http://10.10.30.34:7001");

        HashMap<String, Object> response = new HashMap<>();
        HashMap hsmap = new HashMap();
        hsmap.put("nomorRekening", 1);
        hsmap.put("jenisTransaksi", jenisTransaksi);
        hsmap.put("statusTransaksi", statusTransaksi);
        hsmap.put("logTransaksi", logTransaksi);

        WebClient.ResponseSpec responseSpec = transaksiClient.post()
                .uri("api/transaksi")
                .body(Mono.just(hsmap), HashMap.class)
                .retrieve();
        return response;
    }
}
