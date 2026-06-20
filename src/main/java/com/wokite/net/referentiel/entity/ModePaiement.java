package com.wokite.net.referentiel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_modepaiement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModePaiement {
    private Long id;
    private String libelle;
}