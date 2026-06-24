package com.wokite.net.finance.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "approvisionnement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Approvisionnement extends BaseEntity {

    @Column(name = "numero_approvisionnement", nullable = false, length = 90)
    private String numeroApprovisionnement;

    @Column(name = "boulangerie_id")
    private Long boulangerieId;

    @Column(name = "fournisseur_id", nullable = false)
    private Long fournisseurId;

    @Column(name = "fournisseur_name", nullable = false, length = 100)
    private Long fournisseurName;

    @Column(name = "montant_total")
    private Double montantTotal;

    @Column(name = "approvisionnement_date", nullable = false)
    private LocalDate dateApprovisionnement;

    @Column(name = "etat_id", nullable = false)
    private Long etatId;

    @OneToMany(mappedBy = "approvisionnement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneApprovisionnement> lignesApprovisionnement;
}