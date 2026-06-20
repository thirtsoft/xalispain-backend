package com.wokite.net.referentiel.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_commune")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commune {
    private Long id;
    private Long departmentId;
    private String libelle;
}