package com.wokite.net.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_depense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Depense {
    private Long id;
    private Long boulangerieId;
    private Long typeDepenseId;
    private Double montant;
    private LocalDate dateDepense;
    private String justificatif;
    private String commentaire;
}