package com.wokite.net.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "session_utilisateur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUtilisateur extends AdminEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id", nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "date_connexion")
    private LocalDateTime dateConnexion;

    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;

    @Column(name = "adresse_ip", length = 90)
    private String adresseIp;
}