package com.example.demo.kredit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Kredit {
    @Id
    @SequenceGenerator(
            name = "credit_sequence",
            sequenceName = "credit_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "credit_sequence"
    )
    private Long id;
    private Integer nomorNasabah;
    private String tipe;
    private LocalDate tenggat;

    public Kredit(Integer nomorNasabah, String tipe, LocalDate tenggat) {
        this.nomorNasabah = nomorNasabah;
        this.tipe = tipe;
        this.tenggat = tenggat;
    }
}
