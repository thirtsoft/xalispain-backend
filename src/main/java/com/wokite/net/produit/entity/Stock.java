package com.wokite.net.produit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    private Long id;
    private Long productId;

    private Long boulangerieId;

    private Integer quantiteDisponible;
}