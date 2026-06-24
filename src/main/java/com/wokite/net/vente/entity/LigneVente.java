package com.wokite.net.vente.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "lignevente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LigneVente extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "prix_unitaire_vente", nullable = false)
    private Double prixUnitaire;

    @Column(name = "quantite_vente", nullable = false)
    private Integer quantiteVene;

}