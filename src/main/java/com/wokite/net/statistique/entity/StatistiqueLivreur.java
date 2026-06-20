package com.wokite.net.statistique.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "xalispain_statistiquelivreur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatistiqueLivreur {
    private Long id;

    private Long livreurId;

    private Integer quantiteSortie;

    private Integer quantiteVendue;

    private BigDecimal chiffreAffaires;

    private Double tauxRetour;
}