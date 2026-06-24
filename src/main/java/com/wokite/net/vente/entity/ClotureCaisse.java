package com.wokite.net.vente.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cloturecaisse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClotureCaisse extends BaseEntity {

    @Column(name = "boulangerie_id")
    private Long  boulangerieId;

    @Column(name = "date_cloture", nullable = false)
    private LocalDateTime dateCloture;

    @Column(name = "montant_theorique", nullable = false)
    private BigDecimal montantTheorique;

    @Column(name = "montant_declare", nullable = false)
    private BigDecimal montantDeclare;

    @Column(name = "montant_reel", nullable = false)
    private BigDecimal montantReel;

    @Column(name = "ecart", nullable = false)
    private Double ecart;

    @Column(name = "cloturee_par")
    private Long clotureePar;

}