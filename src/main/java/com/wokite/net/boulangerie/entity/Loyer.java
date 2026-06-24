package com.wokite.net.boulangerie.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "loyer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loyer extends BaseEntity {
    private Long boulangerieId;
    private Double montant;
    private LocalDate dateEcheance;
    private String statutPaiement;
}