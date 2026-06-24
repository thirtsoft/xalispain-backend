package com.wokite.net.production.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ligneproduction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LigneProduction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "production_id", nullable = false)
    private Production production;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "prix_unitaire_production", nullable = false, length = 150)
    private Double prixUnitaireProduction;

    @Column(name = "quantite_produite", nullable = false)
    private Integer quantiteProduite;
}