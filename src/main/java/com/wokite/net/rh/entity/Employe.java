package com.wokite.net.rh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_employe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employe {

    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String typeRemuneration;
    private Double montantRemuneration;
}