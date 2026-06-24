package com.wokite.net.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuperBuilder(toBuilder = true)
public class ModelEntity extends AdminEntity {

    @Column(name = "code", length = 40, nullable = false)
    private String code;

    @Column(name = "libelle", length = 90, nullable = false)
    private String libelle;
}