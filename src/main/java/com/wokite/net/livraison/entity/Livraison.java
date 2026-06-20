package com.wokite.net.livraison.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "xalispain_livraison")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livraison {

    private Long id;
    private Long tourneeId;
    private Long boulangerieId;
    private LocalDateTime dateSortie;
    private Integer quantiteSortie;
}