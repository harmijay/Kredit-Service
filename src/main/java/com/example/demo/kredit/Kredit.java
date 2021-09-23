package com.example.demo.kredit;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table
public class Kredit {
    @Id
    @SequenceGenerator(
            name = "kredit_sequence",
            sequenceName = "kredit_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "kredit_sequence"
    )
    private Long id;
    private Integer nomorRekening;
    private String tipeKredit;
    private Integer limitKredit;
    private Long jumlahKredit;
    private LocalDate tenggatKredit;
    private String statusKredit;

    public Kredit(Integer nomorRekening, String tipeKredit, Integer limitKredit, Long jumlahKredit, LocalDate tenggatKredit, String statusKredit) {
        this.nomorRekening = nomorRekening;
        this.tipeKredit = tipeKredit;
        this.limitKredit = limitKredit;
        this.jumlahKredit = jumlahKredit;
        this.tenggatKredit = tenggatKredit;
        this.statusKredit = statusKredit;
    }
}
