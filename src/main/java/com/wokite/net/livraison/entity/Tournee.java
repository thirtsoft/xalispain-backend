package com.wokite.net.livraison.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tournee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tournee extends BaseEntity {
    private Long livreurId;
    private LocalDate dateTournee;
    private LocalTime heureDepart;
    private LocalTime heureRetour;
    private String numero;
    private String type; // MATIN, MIDI, SOIR
    private Long etatId; // EN_COURS, TERMINEE, ANNULEE
}