package com.wokite.net.admin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "xalispain_sessionUtilisateur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUtilisateur extends AdminEntity {

    private Long utilisateurId;
    private LocalDate dateConnexion;
    private LocalDate dateExpiration;
    private String adresseIp;
}