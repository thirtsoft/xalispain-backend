package com.wokite.net.produit.entity;

import com.wokite.net.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "subcategory",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "libelle")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory extends BaseEntity {

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle; // "PAINS", "VIENNOISERIES", "BEIGNETS", "FARINES", "LEVURES", "COMBUSTIBLES"

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}