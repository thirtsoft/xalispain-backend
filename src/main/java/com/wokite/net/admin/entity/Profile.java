package com.wokite.net.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(
        name = "profile",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"libelle", "code"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends AdminEntity implements Serializable {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "actions_par_profil", joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "action_id"))
    private Set<Action> action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_compte_id", referencedColumnName = "id", nullable = false)
    private TypeCompe typeCompte;// PROPRIETAIRE, GERANT, VENDEUR, LIVREUR, ADMINISTRATEUR

}