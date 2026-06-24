package com.wokite.net.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "action",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"libelle", "code"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Action extends ModelEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_compte_id", referencedColumnName = "id", nullable = false)
    private TypeCompe typeCompte;
}