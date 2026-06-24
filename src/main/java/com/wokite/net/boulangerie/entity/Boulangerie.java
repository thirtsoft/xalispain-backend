package com.wokite.net.boulangerie.entity;

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
        name = "boulangerie",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"libelle", "code"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Boulangerie extends BaseEntity {

    @Column(name = "code", nullable = false, length = 50, unique = true)
    private String code;

    @Column(name = "libelle", length = 120, nullable = false, unique = true)
    private String libelle;

    @Column(name = "commune_id", nullable = false)
    private Long communeId;

    @Column(name = "address", length = 150)
    private String address;

    @Column(name = "etat_id")
    private Long etatId;

    @Column(name = "telephone", length = 30)
    private String telephone;

    @Column(name = "mobile", length = 30, nullable = false)
    private String mobile;

    @Column(name = "type", length = 90)
    private String type;

    @Column(name = "statut", length = 80)
    private String statut;
}