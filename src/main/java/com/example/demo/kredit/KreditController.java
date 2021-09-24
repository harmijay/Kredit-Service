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
    public void bukaKredit(
            @RequestBody HashMap<String, Object> kreditBaru) {
        kreditService.bukaKredit(kreditBaru);
    }

    @DeleteMapping(path = "tutupKredit")
    public void tutupKredit(
            @RequestBody Integer nomorRekening) {
        kreditService.tutupKredit(nomorRekening);
    }
    
    @GetMapping(path = "updateTipeKredit")
    public void updateTipeKredit(
            @RequestBody Integer nomorRekening,
            @RequestBody String tipeKredit) {
        kreditService.updateTipeKredit(nomorRekening, tipeKredit);
    }

    @RequestMapping(path = "validasiNomorRekening")
    public void validasiNomorRekening(
            @RequestBody Integer nomorRekening) {
        kreditService.validasiNomorRekening(nomorRekening);
    }

    @RequestMapping(path = "bayarKredit")
    public void bayarKredit(
            @RequestBody Integer nomorRekening) {
        kreditService.bayarKredit(nomorRekening);
    }

    @PostMapping(path = "tambahKredit")
    public void tambahKredit(
            @RequestBody Integer nomorRekening,
            @RequestBody Long jumlahTambahKredit) {
        kreditService.tambahKredit(nomorRekening, jumlahTambahKredit);
    }

    @GetMapping(path = "statusKredit")
    public void getStatusKredit(
            @RequestBody Integer nomorRekening) {
        kreditService.getStatusKredit(nomorRekening);
    }
}
