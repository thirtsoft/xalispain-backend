package com.wokite.net.livraison.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "comptelivreur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompteLivreur extends BaseEntity {
    private Long livreurId;
    private BigDecimal soldeActuel;
}