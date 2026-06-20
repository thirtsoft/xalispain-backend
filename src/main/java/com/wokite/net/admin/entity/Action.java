package com.wokite.net.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "xalispain_action")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Action extends ModelEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_compte_uid", referencedColumnName = "id", nullable = true)
    private TypeCompe typeCompte;
}