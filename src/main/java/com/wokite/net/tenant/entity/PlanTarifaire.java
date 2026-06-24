package com.wokite.net.tenant.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "plantarifaire",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"designation"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanTarifaire extends BaseEntity {

    @Column(name = "designation", length = 120)
    private String designation;

    @Column(name = "prix_mensuel")
    private Double prixMensuel;

    @Column(name = "nombre_max_boulangeries", nullable = false, length = 50)
    private String nombreMaxBoulangeries;
}