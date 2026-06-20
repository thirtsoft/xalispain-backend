package com.wokite.net.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_ligneapprovisionnement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LigneApprovisionnement {
    private Long id;
    private Long approvisionnementId;
    private Long productId;
    private String productName;
    private Integer quantiteapprovisionnement;
    private Double prixUnitaire;
}