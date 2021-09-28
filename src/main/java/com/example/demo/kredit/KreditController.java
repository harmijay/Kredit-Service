package com.example.demo.kredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "kredit")
public class KreditController {
    private final KreditService kreditService;

    @Autowired
    public KreditController(KreditService kreditService) {
        this.kreditService = kreditService;
    }

    @GetMapping
    public List<Kredit> getKredit() {
        return kreditService.getKredit();
    }

    @PostMapping(path = "bukaKredit")
    public HashMap<String, Object> bukaKredit(
            @RequestParam HashMap<String, Object> kreditBaru) {
        return kreditService.bukaKredit(kreditBaru);
    }

    @DeleteMapping(path = "tutupKredit")
    public HashMap<String, Object> tutupKredit(
            @RequestParam Integer nomorRekening) {
        return kreditService.tutupKredit(nomorRekening);
    }
    
    @GetMapping(path = "updateTipeKredit")
    public HashMap<String, Object> updateTipeKredit(
            @RequestParam Integer nomorRekening,
            @RequestParam String tipeKredit) {
        return kreditService.updateTipeKredit(nomorRekening, tipeKredit);
    }

    @PostMapping(path = "tambahKredit")
    public HashMap<String, Object> tambahKredit(
            @RequestParam Integer nomorRekening,
            @RequestParam Long jumlahTambahKredit) {
        return kreditService.tambahKredit(nomorRekening, jumlahTambahKredit);
    }

    @GetMapping(path = "statusKredit")
    public HashMap<String, Object> getStatusKredit(
            @RequestParam Integer nomorRekening) {
        return kreditService.getStatusKredit(nomorRekening);
    }

    @RequestMapping(path = "validasiNomorRekening")
    public HashMap<String, Object> validasiNomorRekening(
            @RequestParam Integer nomorRekening) {
        return kreditService.validasiNomorRekening(nomorRekening);
    }

    @RequestMapping(path = "bayarKredit")
    public String bayarKredit(
            @RequestParam Integer nomorRekening) {
        return kreditService.bayarKredit(nomorRekening);
    }
}
