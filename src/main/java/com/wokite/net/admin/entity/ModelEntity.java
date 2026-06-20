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

    @Column(length = 40)
    private String code;

    @Column(length = 90)
    private String libelle;
}