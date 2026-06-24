package com.wokite.net.livraison.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "retourlivraison")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetourLivraison extends BaseEntity {
    private Long livraisonId;
    private Integer quantiteVendue;
    private Integer quantiteRetournee;
    private Double montantRemis;
}