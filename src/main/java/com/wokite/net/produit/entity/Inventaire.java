package com.wokite.net.produit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_inventaire")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventaire {

    private Long id;
    private Long boulangerieId;
    private LocalDate dateInventaire;
}