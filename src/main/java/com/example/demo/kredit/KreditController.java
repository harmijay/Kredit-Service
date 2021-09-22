package com.example.demo.kredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping(path = "tutupKredit/{idKredit}")
    public void tutupKredit(@PathVariable("idKredit") Long idKredit) {
        kreditService.tutupKredit(idKredit);
    }
}
