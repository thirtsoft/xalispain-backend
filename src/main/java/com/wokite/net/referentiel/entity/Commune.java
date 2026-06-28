package com.wokite.net.referentiel.entity;


import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(
        name = "commune",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"libelle", "department_id"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Commune extends BaseEntity {

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;
}