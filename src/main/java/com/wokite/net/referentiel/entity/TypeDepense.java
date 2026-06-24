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
        name = "typedepense",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "libelle")
        }
)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeDepense extends BaseEntity {

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;
}