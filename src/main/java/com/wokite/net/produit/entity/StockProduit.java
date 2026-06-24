package com.wokite.net.produit.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stockproduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockProduit extends BaseEntity {

    private Long productId;

    private String productName;

    private Long boulangerieId;

    private Integer quantiteDisponible;

}