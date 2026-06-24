package com.wokite.net.rh.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "paiementiemploye")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaiementEmploye extends BaseEntity {
    private Long employeId;
    private Double montant;
    private LocalDate datePaiement;
}