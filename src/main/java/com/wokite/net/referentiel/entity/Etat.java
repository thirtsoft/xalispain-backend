package com.wokite.net.referentiel.entity;


import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(
        name = "etat",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"libelle", "code"})
        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Etat extends BaseEntity {

    @Column(name = "code", length = 10, unique = true)
    private String code;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;
}