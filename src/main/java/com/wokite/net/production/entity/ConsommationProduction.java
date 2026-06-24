package com.wokite.net.production.entity;

import com.wokite.net.produit.entity.MouvementStock;
import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "consommationproduction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsommationProduction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "production_id", nullable = false)
    private Production production; // La production qui consomme

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "quantite_consommee", nullable = false)
    private Double quantiteConsommee;

    @Column(name = "unite_utilisee", length = 150)
    private String uniteUtilisee; // Pour tracer l'unité utilisée (ex: "Kg", "Sachet")

}