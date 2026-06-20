package com.wokite.net.livraison.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_mouvementlivreur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MouvementLivreur {
    private Long id;
    private Long livreurId;
    private String type; // DETTE, VERSEMENT, REGULARISATION
    private Double montant;
    private LocalDate dateMouvementLivreur;
}