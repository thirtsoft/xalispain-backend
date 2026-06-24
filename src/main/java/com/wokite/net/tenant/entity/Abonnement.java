package com.wokite.net.tenant.entity;

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
@Table(name = "abonnement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Abonnement extends BaseEntity {

    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Column(name = "designation", nullable = false)
    private LocalDateTime date_debut;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    @Column(name = "etat_id", length = 50, nullable = false)
    private String etatId;
}