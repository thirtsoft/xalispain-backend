package com.wokite.net.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "utilisateur",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"telephone", "email"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur extends AdminEntity {

    @Column(name = "prenom", length = 150, nullable = false)
    private String prenom;

    @Column(name = "nom", length = 90, nullable = false)
    private String nom;

    @Column(name = "telephone", length = 30)
    private String telephone;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "mot_de_passe", length = 200)
    private String motDePasse;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;// PROPRIETAIRE, GERANT, VENDEUR, LIVREUR, ADMINISTRATEUR

    @Column(name = "activation", length = 150, unique = true)
    private String activation;

    @Column(name = "est_admin_boulangerie", columnDefinition = "integer DEFAULT 0")
    private int estAdminBoulangerie;


}