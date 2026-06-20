package com.wokite.net.entreprise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_abonnement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Abonnement {

    private Long id;
    private Long entrepriseId;
    private Long plan;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
}