package com.wokite.net.boulangerie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_boulangerie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Boulangerie {
    private Long id;
    private String nom;
    private String code;
    private Long communeId;
    private Long entrepriseId;
    private String telephone;
    private String type;
    private String statut;
}