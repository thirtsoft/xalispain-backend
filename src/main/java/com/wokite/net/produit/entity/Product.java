package com.wokite.net.produit.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "reference")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "reference", nullable = false, length = 100, unique = true)
    private String reference;

    @Column(name = "libelle", nullable = false, length = 150)
    private String libelle;  // "Baguette", "Pain Mil", "Croissant", "Farine T55", "Levure Fraîche"

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory subCategory;

    @Column(name = "prix_vente")
    private Double prixVente;

    @Column(name = "prix_appro")
    private Double prixAppro;

    @ManyToOne
    @JoinColumn(name = "unite_mesure_id", nullable = false)
    private UniteMesure uniteMesure;

    @Column(name = "duree_conservation")
    private Double dureeConservationJours; // Pour les produits finis (ex: 1 jour pour croissant)

    @Column(name = "est_perissable")
    private Boolean estPerissable; // true pour farine, levure, croissants, etc.

    @Column(name = "seuil_alerte")
    private Integer seuilAlerte; // Pour les matières premières

}