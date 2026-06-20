package com.wokite.net.produit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;

    private String nom;
}