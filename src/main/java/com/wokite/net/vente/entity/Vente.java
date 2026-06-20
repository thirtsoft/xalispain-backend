package com.wokite.net.vente.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_vente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vente {

    private Long id;

    private Long boulangerieId;

    private Long vendeurId;

    private LocalDate dateVente;

    private Double montantTotal;

    private Long modePayId;

}