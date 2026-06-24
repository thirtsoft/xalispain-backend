package com.wokite.net.produit.entity;

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
        name = "unite_mesure",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "libelle")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniteMesure extends BaseEntity {

    @Column(name = "libelle", nullable = false, length = 150)
    private String libelle; // // PIECE, KILOGRAMME, SACHET, CARTON, BOUTEILLE, STERE


}