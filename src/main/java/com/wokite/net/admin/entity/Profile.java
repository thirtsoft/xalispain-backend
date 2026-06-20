package com.wokite.net.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "xalispain_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements Serializable {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "actions_par_profil", joinColumns = @JoinColumn(name = "profil_uid"),
            inverseJoinColumns = @JoinColumn(name = "action_uid"))
    private Set<Action> action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_compte_uid", referencedColumnName = "id", nullable = true)
    private TypeCompe typeCompte;// PROPRIETAIRE, GERANT, VENDEUR, LIVREUR, ADMINISTRATEUR

}