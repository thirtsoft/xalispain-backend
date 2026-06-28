package com.wokite.net.referentiel.entity;


import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "region",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"libelle", "code"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Region extends BaseEntity {

    @Column(name = "code", length = 10, unique = true)
    private String code;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    @Column(name = "pays_id", nullable = false)
    private Long paysId;
}