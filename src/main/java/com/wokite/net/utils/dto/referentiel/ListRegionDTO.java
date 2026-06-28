package com.wokite.net.utils.dto.referentiel;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListRegionDTO {

    private Long id;

    private String code;

    private String libelle;

    private String pays;

    private String tenant;

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