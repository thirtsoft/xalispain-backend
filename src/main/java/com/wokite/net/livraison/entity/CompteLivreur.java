package com.wokite.net.livraison.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "xalispain_comptelivreur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompteLivreur {
    private Long id;
    private Long livreurId;
    private BigDecimal soldeActuel;
}