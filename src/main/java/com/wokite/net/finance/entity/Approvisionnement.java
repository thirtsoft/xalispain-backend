package com.wokite.net.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "xalispain_approvisionnement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Approvisionnement {
    private Long id;
    private Long boulangerieId;
    private Long fournisseurId;
    private BigDecimal montantapprovisionnement;
    private LocalDate dateApprovisionnement;
    private Long etatId;
}