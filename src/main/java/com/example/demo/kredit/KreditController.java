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

    @PostMapping
    public void bukaKredit(@RequestBody Kredit kredit) {
        kreditService.bukaKredit(kredit);
    }

    @DeleteMapping(path = "tutupKredit/{id}")
    public void tutupKredit(@PathVariable("id") Long id) {
        kreditService.tutupKredit(id);
    }
    
    @GetMapping(path = "updateTipeKredit/{nomorRekening}/{tipeKredit}")
    public void updateTipeKredit(
            @PathVariable("nomorRekening") Integer nomorRekening,
            @PathVariable("tipeKredit") String tipeKredit) {
        kreditService.updateTipeKredit(nomorRekening, tipeKredit);
    }

    @RequestMapping(path = "validasiNomorRekening/{nomorRekening}")
    public void validasiNomorRekening(
            @PathVariable("nomorRekening") Integer nomorRekening) {
        kreditService.validasiNomorRekening(nomorRekening);
    }

    @RequestMapping(path = "bayarKredit/{nomorRekening}")
    public void bayarKredit(
            @PathVariable("nomorRekening") Integer nomorRekening) {
        kreditService.bayarKredit(nomorRekening);
    }

    @PostMapping(path = "tambahKredit")
    public void tambahKredit(
            @RequestBody(required = true) Integer nomorRekening,
            @RequestBody(required = true) Long jumlahTambahKredit) {
        kreditService.tambahKredit(nomorRekening, jumlahTambahKredit);
    }

    @GetMapping(path = "statusKredit")
    public void getStatusKredit(
            @RequestBody(required = true) Integer nomorRekening) {
        kreditService.getStatusKredit(nomorRekening);
    }

    @GetMapping(path = "testApi")
    public void testApi(){
        kreditService.testApi();
    }
}
