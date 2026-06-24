package com.wokite.net.tenant.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "entreprise")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tenant extends BaseEntity {

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "logo", length = 120)
    private String logo;

    @Column(name = "denomination", length = 120)
    private String denomination;

    @Column(name = "commune_id", nullable = false)
    private Long communeId;

    @Column(name = "address", length = 150)
    private String address;

    @Column(name = "mobile", length = 30)
    private String mobile;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "statut", length = 30)
    private String statut;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

}