package com.wokite.net.production.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "xalispain_productionjournaliere")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionJournaliere {
    private Long id;
    private Long boulangerieId;
    private Double montantProduction;
    private LocalDate dateProduction;
    private LocalTime heure;
    private String commentaire;
}