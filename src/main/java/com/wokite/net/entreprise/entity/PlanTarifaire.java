package com.wokite.net.entreprise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_plantarifaire")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanTarifaire {

    private Long id;
    private String nom;
    private Double prixMensuel;
    private Integer nombreMaxBoulangeries;
}