package com.wokite.net.entreprise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_locataire")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entreprise {
    private Long id;
    private String code;
    private String logo;
    private String nom;
    private String telephone;
    private String email;
    private String statut;
    private LocalDate dateCreation;
}