package com.wokite.net.produit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;

    private String nom;

    private Long categoryId;
    
    private Double prixVente;

    private String type;
    private String unite;
    
    private int actif;


}