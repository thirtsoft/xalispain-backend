package com.wokite.net.production.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_ligneproduction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LigneProduction {

    private Long id;

    private Long productionId;

    private Long productId;

    private String productName;

    private Double prixUnitaire;

    private Integer quantiteProduite;
}