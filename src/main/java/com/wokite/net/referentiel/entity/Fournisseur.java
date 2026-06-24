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

@Entity
@Table(
        name = "fournisseur",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"sigle", "denomination", "telephone", "mobile", "email"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fournisseur extends BaseEntity {

    @Column(name = "sigle", length = 10)
    private String sigle;

    @Column(name = "denomination", nullable = false, length = 100)
    private String denomination;

    @Column(name = "telephone", length = 30)
    private String telephone;

    @Column(name = "mobile", nullable = false, length = 30)
    private String mobile;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "adresse", length = 150)
    private String adresse;

    @Column(name = "commune_id", nullable = false)
    private String communeId;
}