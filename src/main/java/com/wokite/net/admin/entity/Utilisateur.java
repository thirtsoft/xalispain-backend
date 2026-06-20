package com.wokite.net.admin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "xalispain_utilisateur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur extends AdminEntity {

    private String prenom;

    private String nom;

    private String telephone;

    private String email;

    private String motDePasse;

    private LocalDate dateCreation;

    private boolean actif;


}