package com.wokite.net.vente.entity;

import com.wokite.net.finance.entity.LigneApprovisionnement;
import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "vente",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"numero_vente"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vente extends BaseEntity {

    @Column(name = "numero_vente", length = 100, unique = true)
    private String numeroVente;

    @Column(name = "boulangerie_id")
    private Long boulangerieId;

    @Column(name = "vendeur_id", nullable = false)
    private Long vendeurId;

    @Column(name = "date_vente")
    private LocalDateTime dateVente;

    @Column(name = "montant_total_vente")
    private Double montantTotalVente;

    @Column(name = "quantite_total_vente")
    private Integer quantiteTotalVente;

    @Column(name = "mode_paiement_id")
    private Long modePaiementId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_vente", length = 90, nullable = false)
    private TypeVente typeVente;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneVente> ligneVentes;

}