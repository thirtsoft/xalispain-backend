package com.wokite.net.utils.dto.referentiel;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RechercheAddressDTO {

    private String libelle;

    private Long departmentId;

    private Long regionId;

    private Long paysId;

    private Long continentId;

    private String mobile;

}