package com.wokite.net.production.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "production")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Production extends BaseEntity {

    @Column(name = "boulangerie_id")
    private Long boulangerieId;

    @Column(name = "employe_id")
    private Long employeId;

    @Column(name = "etat_id", nullable = false)
    private Long etatId; // PLANIFIEE, EN_COURS, TERMINEE, CLOTUREE

    @Column(name = "quantite_production", nullable = false)
    private Integer quantiteProduction;

    @Column(name = "montant_production", nullable = false)
    private Double montantProduction;

    @Column(name = "date_production", nullable = false)
    private LocalDateTime dateProduction;

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsommationProduction> consommations;

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneProduction> ligneProductions;

    private LocalTime heure;

    @Column(name = "commentaire", length = 150)
    private String commentaire;
}