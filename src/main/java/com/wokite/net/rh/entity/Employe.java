package com.wokite.net.rh.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employe extends BaseEntity {

    @Column(name = "nom", length = 50)
    private String nom;

    @Column(name = "prenom", length = 120)
    private String prenom;

    @Column(name = "telephone", length = 30)
    private String telephone;

    @Column(name = "type_remuneration", length = 50)
    private String typeRemuneration;

    @Column(name = "montant_remuneration")
    private Double montantRemuneration;
}