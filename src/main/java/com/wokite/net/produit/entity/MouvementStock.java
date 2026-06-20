package com.wokite.net.produit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_mouvementstock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStock {

    private Long id;
    private Long productId;
    private Integer typeMouvement; // Sortie ou Entré
    private Integer quantite;
    private LocalDate dateMouvement;
}