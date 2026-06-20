package com.wokite.net.referentiel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "xalispain_pays")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pays {
    private Long id;
    private String code;
    private String libelle;
    private String indicatif;
}