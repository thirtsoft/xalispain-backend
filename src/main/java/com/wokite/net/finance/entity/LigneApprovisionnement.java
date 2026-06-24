package com.wokite.net.finance.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ligneapprovisionnement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LigneApprovisionnement extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "approvisionnement_id", nullable = false)
    private Approvisionnement approvisionnement;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "quantite_approvisionnement")
    private Integer quantiteApprovisionnement;

    @Column(name = "prix_unitaire_achat")
    private Double prixUnitaireAchat;

}