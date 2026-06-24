package com.wokite.net.referentiel.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "continent",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"libelle", "code"})
        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Continent extends BaseEntity {

    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;
}