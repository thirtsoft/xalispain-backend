package com.wokite.net.rh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_paiementiemploye")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaiementEmploye {

    private Long id;
    private Long employeId;
    private Double montant;
    private LocalDate datePaiement;
}