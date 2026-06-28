package com.wokite.net.utils.dto.referentiel;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FournisseurAddEditDTO {

    private Long id;

    private String libelle;

    private String tenantId;

    private String sigle;

    private String denomination;

    private String telephone;

    private String mobile;

    private String email;

    private String adresse;

    private Long communeId;

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