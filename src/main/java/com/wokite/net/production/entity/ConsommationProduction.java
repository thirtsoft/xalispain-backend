package com.wokite.net.production.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_consommationproduction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsommationProduction {
    private Long id;
    private Long productionId;
    private Long productId;
    private String productName;
    private Long produitApprovisionnementId;
    private Integer quantite;

    // 5 sacs farine, 1 carton levure, 2 sacs charbon
}