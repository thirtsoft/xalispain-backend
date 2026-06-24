package com.wokite.net.produit.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mouvementstock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStock extends BaseEntity {

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "prix_unitaire_applique")
    private Double prixUnitaireApplique;

    @Enumerated(EnumType.STRING)
    private TypeMouvement type; // ENTREE (achat) ou SORTIE (utilisation en production)

    private Integer quantite;

    @Column(name = "date_mouvement", nullable = false)
    private LocalDateTime dateMouvement;

    @Column(name = "approvisionnement_id")
    private Long approvisionnementId;

    @Column(name = "production_id")
    private Long productionId;

    @Column(name = "consommation_production_id")
    private Long consommationProductionId;

    @Column(name = "vente_id")
    private Long venteId;

    @Enumerated(EnumType.STRING)
    private MotifSortie motif; // VENTE, DISTRIBUTION, RETOUR_INVENDU, PERTE_PEREMPTION

}