package com.wokite.net.referentiel.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "modepaiement",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "libelle")
        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModePaiement extends BaseEntity {

    @Column(name = "libelle", nullable = false, length = 100, unique = true)
    private String libelle;
}