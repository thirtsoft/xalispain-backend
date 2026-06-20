package com.wokite.net.vente.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_lignevente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LigneVente {

    private Long id;

    private Long venteId;

    private Long productId;

    private String productName;

    private Double prixUnitaire;

    private Integer quantite;

}