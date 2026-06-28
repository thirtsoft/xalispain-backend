package com.wokite.net.utils.dto.referentiel;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListFournisseurDTO {

    private Long id;

    private String tenant;

    private String sigle;

    private String denomination;

    private String telephone;

    private String mobile;

    private String email;

    private String adresse;

    private String commune;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int actif;

    public void setActif(boolean actif) {
        if (actif)
            this.actif = 1;
        else
            this.actif = 0;
    }

    public boolean isActif() {
        return actif == 1;
    }
}