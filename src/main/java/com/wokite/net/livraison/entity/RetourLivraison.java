package com.wokite.net.livraison.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_retourlivraison")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetourLivraison {
    private Long id;
    private Long livraisonId;
    private Integer quantiteVendue;
    private Integer quantiteRetournee;
    private Double montantRemis;
}