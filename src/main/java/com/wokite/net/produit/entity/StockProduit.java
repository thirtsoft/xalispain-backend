package com.wokite.net.produit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_stockproduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockProduit {

    private Long id;
    private Long productId;

    private String productName;

    private Long boulangerieId;

    private Integer quantiteDisponible;
    
}