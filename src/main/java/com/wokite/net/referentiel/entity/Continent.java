package com.wokite.net.referentiel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_continent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Continent {
    private Long id;
    private String libelle;
}