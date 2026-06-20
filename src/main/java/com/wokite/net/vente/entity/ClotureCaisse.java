package com.wokite.net.vente.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "xalispain_cloturecaisse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClotureCaisse {
    private Long id;
    private Long  boulangerieId;
    private LocalDate datecCeation;
    private LocalDate dateCloture;
    private BigDecimal montantTheorique;
    private BigDecimal montantDeclare;
    private BigDecimal montantReel;
    private Double ecart;

    private Long clotureePar;

}