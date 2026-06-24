package com.wokite.net.produit.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock extends BaseEntity {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "boulangerie_id")
    private Long boulangerieId;

    @Column(name = "quantite_disponible")
    private Integer quantiteDisponible;
}