package com.wokite.net.utils.dto.referentiel;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegionAddEditDTO {

    private Long id;

    private String code;

    private String libelle;

    private String tenantId;

    private Long paysId;

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