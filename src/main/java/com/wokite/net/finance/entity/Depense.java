package com.wokite.net.finance.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "depense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Depense extends BaseEntity {

    @Column(name = "boulangerie_id")
    private Long boulangerieId;

    @Column(name = "type_depense_id", nullable = false)
    private Long typeDepenseId;

    @Column(name = "montant_depense", nullable = false)
    private Double montantDepense;

    @Column(name = "date_depense", nullable = false)
    private LocalDateTime dateDepense;

    @Column(name = "justificatif", length = 150)
    private String justificatif;

    @Column(name = "libelle", length = 150, nullable = false)
    private String libelle;

}