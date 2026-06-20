package com.wokite.net.statistique.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "xalispain_statistiquejournaliere")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatistiqueJournaliere {

    private Long id;

    private LocalDate date;

    private Integer painsProduits;

    private Integer painsVendus;

    private Integer painInvendus;

    private BigDecimal chiffreAffaires;

    private BigDecimal montantDepenses;

    private BigDecimal beneficeEstime;

    private Double tauxPerte;
}