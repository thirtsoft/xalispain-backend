package com.wokite.net.produit.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "inventaire")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventaire extends BaseEntity {
    private Long boulangerieId;
    private LocalDate dateInventaire;
}