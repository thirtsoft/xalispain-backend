package com.wokite.net.boulangerie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_loyer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loyer {
    private Long id;
    private Long boulangerieId;
    private Double montant;
    private LocalDate dateEcheance;
    private String statutPaiement;
}