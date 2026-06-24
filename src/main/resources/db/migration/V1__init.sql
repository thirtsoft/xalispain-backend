-- =============================================
-- V1__init.sql
-- Migration initiale pour le SaaS Xalispain
-- =============================================

-- =============================================
-- TABLES SANS DÉPENDANCES (ordre alphabétique)
-- =============================================

-- 1. TABLE: category
-- =============================================
CREATE TABLE IF NOT EXISTS category (
                                        id BIGSERIAL PRIMARY KEY,
                                        libelle VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default'
    );

-- 2. TABLE: continent
-- =============================================
CREATE TABLE IF NOT EXISTS continent (
                                         id BIGSERIAL PRIMARY KEY,
                                         code VARCHAR(10) UNIQUE,
    libelle VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_continent_libelle_code UNIQUE (libelle, code)
    );

-- 3. TABLE: employe
-- =============================================
CREATE TABLE IF NOT EXISTS employe (
                                       id BIGSERIAL PRIMARY KEY,
                                       nom VARCHAR(50),
    prenom VARCHAR(120),
    telephone VARCHAR(30),
    type_remuneration VARCHAR(50),
    montant_remuneration DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default'
    );

-- 4. TABLE: etat
-- =============================================
CREATE TABLE IF NOT EXISTS etat (
                                    id BIGSERIAL PRIMARY KEY,
                                    code VARCHAR(10) UNIQUE,
    libelle VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_etat_libelle_code UNIQUE (libelle, code)
    );

-- 5. TABLE: livreur
-- =============================================
CREATE TABLE IF NOT EXISTS livreur (
                                       id BIGSERIAL PRIMARY KEY,
                                       nom VARCHAR(50),
    prenom VARCHAR(120),
    telephone VARCHAR(30),
    type_remuneration VARCHAR(50),
    montant_remuneration DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default'
    );

-- 6. TABLE: modepaiement
-- =============================================
CREATE TABLE IF NOT EXISTS modepaiement (
                                            id BIGSERIAL PRIMARY KEY,
                                            libelle VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default'
    );

-- 7. TABLE: typedepense
-- =============================================
CREATE TABLE IF NOT EXISTS typedepense (
                                           id BIGSERIAL PRIMARY KEY,
                                           libelle VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default'
    );

-- 8. TABLE: typecompte
-- =============================================
CREATE TABLE IF NOT EXISTS typecompte (
                                          id BIGSERIAL PRIMARY KEY,
                                          code VARCHAR(40) NOT NULL,
    libelle VARCHAR(90) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_typecompte_libelle_code UNIQUE (libelle, code)
    );

-- 9. TABLE: unite_mesure
-- =============================================
CREATE TABLE IF NOT EXISTS unite_mesure (
                                            id BIGSERIAL PRIMARY KEY,
                                            libelle VARCHAR(150) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default'
    );

-- =============================================
-- TABLES DÉPENDANTES DE CATEGORY
-- =============================================

-- 10. TABLE: subcategory
-- =============================================
CREATE TABLE IF NOT EXISTS subcategory (
                                           id BIGSERIAL PRIMARY KEY,
                                           libelle VARCHAR(100) NOT NULL UNIQUE,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_subcategory_libelle UNIQUE (libelle),
    CONSTRAINT fk_subcategory_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE CONTINENT
-- =============================================

-- 11. TABLE: pays
-- =============================================
CREATE TABLE IF NOT EXISTS pays (
                                    id BIGSERIAL PRIMARY KEY,
                                    code VARCHAR(10) UNIQUE,
    libelle VARCHAR(100) NOT NULL,
    indicatif VARCHAR(10),
    continent_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_pays_libelle_code UNIQUE (libelle, code),
    CONSTRAINT fk_pays_continent FOREIGN KEY (continent_id) REFERENCES continent(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE PAYS
-- =============================================

-- 12. TABLE: region
-- =============================================
CREATE TABLE IF NOT EXISTS region (
                                      id BIGSERIAL PRIMARY KEY,
                                      code VARCHAR(10) UNIQUE,
    libelle VARCHAR(100) NOT NULL,
    pays_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_region_libelle_code UNIQUE (libelle, code),
    CONSTRAINT fk_region_pays FOREIGN KEY (pays_id) REFERENCES pays(id) ON DELETE SET NULL
    );

-- =============================================
-- TABLES DÉPENDANTES DE REGION
-- =============================================

-- 13. TABLE: departement
-- =============================================
CREATE TABLE IF NOT EXISTS departement (
                                           id BIGSERIAL PRIMARY KEY,
                                           code VARCHAR(10) UNIQUE,
    libelle VARCHAR(100) NOT NULL,
    region_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_departement_libelle_code UNIQUE (libelle, code),
    CONSTRAINT fk_departement_region FOREIGN KEY (region_id) REFERENCES region(id) ON DELETE SET NULL
    );

-- =============================================
-- TABLES DÉPENDANTES DE DEPARTEMENT
-- =============================================

-- 14. TABLE: commune
-- =============================================
CREATE TABLE IF NOT EXISTS commune (
                                       id BIGSERIAL PRIMARY KEY,
                                       libelle VARCHAR(100) NOT NULL,
    departement_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_commune_libelle_departement UNIQUE (libelle, departement_id),
    CONSTRAINT fk_commune_departement FOREIGN KEY (departement_id) REFERENCES departement(id) ON DELETE SET NULL
    );

-- =============================================
-- TABLES DÉPENDANTES DE COMMUNE ET ETAT
-- =============================================

-- 15. TABLE: boulangerie
-- =============================================
CREATE TABLE IF NOT EXISTS boulangerie (
                                           id BIGSERIAL PRIMARY KEY,
                                           code VARCHAR(50) NOT NULL UNIQUE,
    libelle VARCHAR(120) NOT NULL UNIQUE,
    commune_id BIGINT NOT NULL,
    address VARCHAR(150),
    etat_id BIGINT,
    telephone VARCHAR(30),
    mobile VARCHAR(30) NOT NULL,
    type VARCHAR(90),
    statut VARCHAR(80),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_boulangerie_libelle_code UNIQUE (libelle, code),
    CONSTRAINT fk_boulangerie_commune FOREIGN KEY (commune_id) REFERENCES commune(id) ON DELETE RESTRICT,
    CONSTRAINT fk_boulangerie_etat FOREIGN KEY (etat_id) REFERENCES etat(id) ON DELETE RESTRICT
    );

-- 16. TABLE: fournisseur
-- =============================================
CREATE TABLE IF NOT EXISTS fournisseur (
                                           id BIGSERIAL PRIMARY KEY,
                                           sigle VARCHAR(10),
    denomination VARCHAR(100) NOT NULL,
    telephone VARCHAR(30),
    mobile VARCHAR(30) NOT NULL,
    email VARCHAR(100),
    adresse VARCHAR(150),
    commune_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_fournisseur_sigle_denom_tel_mobile_email UNIQUE (sigle, denomination, telephone, mobile, email),
    CONSTRAINT fk_fournisseur_commune FOREIGN KEY (commune_id) REFERENCES commune(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE SUBCATEGORY ET UNITE_MESURE
-- =============================================

-- 17. TABLE: product
-- =============================================
CREATE TABLE IF NOT EXISTS product (
                                       id BIGSERIAL PRIMARY KEY,
                                       reference VARCHAR(100) NOT NULL UNIQUE,
    libelle VARCHAR(150) NOT NULL,
    subcategory_id BIGINT NOT NULL,
    prix_vente DOUBLE PRECISION,
    prix_appro DOUBLE PRECISION,
    unite_mesure_id BIGINT NOT NULL,
    duree_conservation DOUBLE PRECISION,
    est_perissable BOOLEAN,
    seuil_alerte INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_product_reference UNIQUE (reference),
    CONSTRAINT fk_product_subcategory FOREIGN KEY (subcategory_id) REFERENCES subcategory(id) ON DELETE RESTRICT,
    CONSTRAINT fk_product_uniteMesure FOREIGN KEY (unite_mesure_id) REFERENCES unite_mesure(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE BOULANGERIE, EMPLOYE, ETAT
-- =============================================

-- 18. TABLE: production
-- =============================================
CREATE TABLE IF NOT EXISTS production (
                                          id BIGSERIAL PRIMARY KEY,
                                          boulangerie_id BIGINT,
                                          employe_id BIGINT,
                                          etat_id BIGINT NOT NULL,
                                          quantite_production INTEGER NOT NULL,
                                          montant_production DOUBLE PRECISION NOT NULL,
                                          date_production TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          heure TIME,
                                          commentaire VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_production_boulangerie FOREIGN KEY (boulangerie_id) REFERENCES boulangerie(id) ON DELETE RESTRICT,
    CONSTRAINT fk_production_employe FOREIGN KEY (employe_id) REFERENCES employe(id) ON DELETE RESTRICT,
    CONSTRAINT fk_production_etat FOREIGN KEY (etat_id) REFERENCES etat(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE FOURNISSEUR, BOULANGERIE, ETAT
-- =============================================

-- 19. TABLE: approvisionnement
-- =============================================
CREATE TABLE IF NOT EXISTS approvisionnement (
    id BIGSERIAL PRIMARY KEY,
    numero_approvisionnement VARCHAR(90) NOT NULL UNIQUE,
    boulangerie_id BIGINT,
    fournisseur_id BIGINT NOT NULL,
    fournisseur_name VARCHAR(100) NOT NULL,
    montant_total DOUBLE PRECISION NOT NULL,
    approvisionnement_date DATE DEFAULT CURRENT_DATE,
    etat_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_approvisionnement_fournisseur FOREIGN KEY (fournisseur_id) REFERENCES fournisseur(id) ON DELETE RESTRICT,
    CONSTRAINT fk_approvisionnement_boulangerie FOREIGN KEY (boulangerie_id) REFERENCES boulangerie(id) ON DELETE RESTRICT,
    CONSTRAINT fk_approvisionnement_etat FOREIGN KEY (etat_id) REFERENCES etat(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE BOULANGERIE, EMPLOYE, MODEPAIEMENT
-- =============================================

-- 20. TABLE: vente
-- =============================================
CREATE TABLE IF NOT EXISTS vente (
                                     id BIGSERIAL PRIMARY KEY,
                                     numero_vente VARCHAR(100) NOT NULL UNIQUE,
    boulangerie_id BIGINT,
    vendeur_id BIGINT NOT NULL,
    date_vente TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    montant_total_vente DOUBLE PRECISION,
    quantite_total_vente INTEGER,
    mode_paiement_id BIGINT,
    type_vente VARCHAR(90) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_vente_boulangerie FOREIGN KEY (boulangerie_id) REFERENCES boulangerie(id) ON DELETE RESTRICT,
    CONSTRAINT fk_vente_vendeur FOREIGN KEY (vendeur_id) REFERENCES employe(id) ON DELETE RESTRICT,
    CONSTRAINT fk_vente_mode_paiement FOREIGN KEY (mode_paiement_id) REFERENCES modepaiement(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE PRODUCTION ET PRODUCT
-- =============================================

-- 21. TABLE: consommationproduction
-- =============================================
CREATE TABLE IF NOT EXISTS consommationproduction (
                                                      id BIGSERIAL PRIMARY KEY,
                                                      production_id BIGINT NOT NULL,
                                                      product_id BIGINT NOT NULL,
                                                      product_name VARCHAR(150) NOT NULL,
    quantite_consommee DOUBLE PRECISION NOT NULL,
    unite_utilisee VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_consommationproduction_production FOREIGN KEY (production_id) REFERENCES production(id) ON DELETE RESTRICT,
    CONSTRAINT fk_consommationproduction_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT
    );

-- 22. TABLE: ligneproduction
-- =============================================
CREATE TABLE IF NOT EXISTS ligneproduction (
                                               id BIGSERIAL PRIMARY KEY,
                                               production_id BIGINT NOT NULL,
                                               product_id BIGINT NOT NULL,
                                               product_name VARCHAR(150) NOT NULL,
    prix_unitaire_production DOUBLE PRECISION NOT NULL,
    quantite_produite INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_ligneproduction_production FOREIGN KEY (production_id) REFERENCES production(id) ON DELETE RESTRICT,
    CONSTRAINT fk_ligneproduction_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE APPROVISIONNEMENT ET PRODUCT
-- =============================================

-- 23. TABLE: ligneapprovisionnement
-- =============================================
CREATE TABLE IF NOT EXISTS ligneapprovisionnement (
                                                      id BIGSERIAL PRIMARY KEY,
                                                      approvisionnement_id BIGINT NOT NULL,
                                                      product_id BIGINT NOT NULL,
                                                      product_name VARCHAR(150) NOT NULL,
    quantite_approvisionnement INTEGER NOT NULL,
    prix_unitaire_achat DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_ligneapprovisionnement_approvisionnement FOREIGN KEY (approvisionnement_id) REFERENCES approvisionnement(id) ON DELETE RESTRICT,
    CONSTRAINT fk_ligneapprovisionnement_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE VENTE ET PRODUCT
-- =============================================

-- 24. TABLE: lignevente
-- =============================================
CREATE TABLE IF NOT EXISTS lignevente (
                                          id BIGSERIAL PRIMARY KEY,
                                          vente_id BIGINT NOT NULL,
                                          product_id BIGINT NOT NULL,
                                          product_name VARCHAR(150) NOT NULL,
    prix_unitaire_vente DOUBLE PRECISION NOT NULL,
    quantite_vente INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_lignevente_vente FOREIGN KEY (vente_id) REFERENCES vente(id) ON DELETE RESTRICT,
    CONSTRAINT fk_lignevente_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE TYPEDEPENSE ET BOULANGERIE
-- =============================================

-- 25. TABLE: depense
-- =============================================
CREATE TABLE IF NOT EXISTS depense (
                                       id BIGSERIAL PRIMARY KEY,
                                       boulangerie_id BIGINT,
                                       type_depense_id BIGINT NOT NULL,
                                       montant_depense DOUBLE PRECISION NOT NULL,
                                       date_depense TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       libelle VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_depense_type_depense FOREIGN KEY (type_depense_id) REFERENCES typedepense(id) ON DELETE RESTRICT,
    CONSTRAINT fk_depense_boulangerie FOREIGN KEY (boulangerie_id) REFERENCES boulangerie(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE BOULANGERIE ET EMPLOYE
-- =============================================

-- 26. TABLE: cloturecaisse
-- =============================================
CREATE TABLE IF NOT EXISTS cloturecaisse (
                                             id BIGSERIAL PRIMARY KEY,
                                             boulangerie_id BIGINT,
                                             date_cloture TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                             montant_theorique NUMERIC(19,2) NOT NULL,
    montant_declare NUMERIC(19,2) NOT NULL,
    montant_reel NUMERIC(19,2) NOT NULL,
    ecart DOUBLE PRECISION NOT NULL,
    cloturee_par BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_cloturecaisse_boulangerie FOREIGN KEY (boulangerie_id) REFERENCES boulangerie(id) ON DELETE RESTRICT,
    CONSTRAINT fk_cloturecaisse_employe FOREIGN KEY (cloturee_par) REFERENCES employe(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE MULTIPLES RÉFÉRENCES
-- =============================================

-- 27. TABLE: mouvementstock
-- =============================================
CREATE TABLE IF NOT EXISTS mouvementstock (
                                              id BIGSERIAL PRIMARY KEY,
                                              product_id BIGINT NOT NULL,
                                              product_name VARCHAR(150) NOT NULL,
    prix_unitaire_applique DOUBLE PRECISION,
    type VARCHAR(100),
    quantite INTEGER,
    date_mouvement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approvisionnement_id BIGINT,
    production_id BIGINT,
    consommation_production_id BIGINT,
    vente_id BIGINT,
    motif VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_mouvementstock_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT,
    CONSTRAINT fk_mouvementstock_approvisionnement FOREIGN KEY (approvisionnement_id) REFERENCES approvisionnement(id) ON DELETE RESTRICT,
    CONSTRAINT fk_mouvementstock_production FOREIGN KEY (production_id) REFERENCES production(id) ON DELETE RESTRICT,
    CONSTRAINT fk_mouvementstock_consommation_production FOREIGN KEY (consommation_production_id) REFERENCES consommationproduction(id) ON DELETE RESTRICT,
    CONSTRAINT fk_mouvementstock_vente FOREIGN KEY (vente_id) REFERENCES vente(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE TYPECOMPTE
-- =============================================

-- 28. TABLE: action
-- =============================================
CREATE TABLE IF NOT EXISTS action (
                                      id BIGSERIAL PRIMARY KEY,
                                      code VARCHAR(40) NOT NULL,
    libelle VARCHAR(90) NOT NULL,
    type_compte_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_action_libelle_code UNIQUE (libelle, code),
    CONSTRAINT fk_action_typecompte FOREIGN KEY (type_compte_id) REFERENCES typecompte(id) ON DELETE RESTRICT
    );

-- 29. TABLE: profile
-- =============================================
CREATE TABLE IF NOT EXISTS profile (
                                       id BIGSERIAL PRIMARY KEY,
                                       code VARCHAR(40) NOT NULL,
    libelle VARCHAR(90) NOT NULL,
    type_compte_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_profile_libelle_code UNIQUE (libelle, code),
    CONSTRAINT fk_profile_typecompte FOREIGN KEY (type_compte_id) REFERENCES typecompte(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DE LIAISON MANY-TO-MANY
-- =============================================

-- 30. TABLE: actions_par_profil
-- =============================================
CREATE TABLE IF NOT EXISTS actions_par_profil (
                                                  profil_id BIGINT NOT NULL,
                                                  action_id BIGINT NOT NULL,
                                                  PRIMARY KEY (profil_id, action_id),
    CONSTRAINT fk_actions_par_profil_profil FOREIGN KEY (profil_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_actions_par_profil_action FOREIGN KEY (action_id) REFERENCES action(id) ON DELETE CASCADE
    );

-- =============================================
-- TABLES DÉPENDANTES DE PROFILE
-- =============================================

-- 31. TABLE: utilisateur
-- =============================================
CREATE TABLE IF NOT EXISTS utilisateur (
                                           id BIGSERIAL PRIMARY KEY,
                                           prenom VARCHAR(150) NOT NULL,
    nom VARCHAR(90) NOT NULL,
    telephone VARCHAR(30),
    email VARCHAR(30),
    mot_de_passe VARCHAR(200),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT true,
    profile_id BIGINT NOT NULL,
    activation VARCHAR(150) UNIQUE,
    est_admin_boulangerie INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT unique_utilisateur_telephone_email UNIQUE (telephone, email),
    CONSTRAINT fk_utilisateur_profile FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE RESTRICT
    );

-- =============================================
-- TABLES DÉPENDANTES DE UTILISATEUR
-- =============================================

-- 32. TABLE: session_utilisateur
-- =============================================
CREATE TABLE IF NOT EXISTS session_utilisateur (
                                                   id BIGSERIAL PRIMARY KEY,
                                                   utilisateur_id BIGINT NOT NULL,
                                                   date_connexion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                   date_expiration TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                   adresse_ip VARCHAR(90),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(50) DEFAULT 'default',
    CONSTRAINT fk_session_utilisateur_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE RESTRICT
    );

-- =============================================
-- INDEX POUR OPTIMISER LES PERFORMANCES
-- =============================================
CREATE INDEX IF NOT EXISTS idx_pays_continent ON pays(continent_id);
CREATE INDEX IF NOT EXISTS idx_region_pays ON region(pays_id);
CREATE INDEX IF NOT EXISTS idx_departement_region ON departement(region_id);
CREATE INDEX IF NOT EXISTS idx_commune_departement ON commune(departement_id);
CREATE INDEX IF NOT EXISTS idx_fournisseur_commune ON fournisseur(commune_id);
CREATE INDEX IF NOT EXISTS idx_boulangerie_commune ON boulangerie(commune_id);
CREATE INDEX IF NOT EXISTS idx_boulangerie_etat ON boulangerie(etat_id);
CREATE INDEX IF NOT EXISTS idx_product_subcategory ON product(subcategory_id);
CREATE INDEX IF NOT EXISTS idx_product_unite_mesure ON product(unite_mesure_id);
CREATE INDEX IF NOT EXISTS idx_production_boulangerie ON production(boulangerie_id);
CREATE INDEX IF NOT EXISTS idx_production_employe ON production(employe_id);
CREATE INDEX IF NOT EXISTS idx_production_etat ON production(etat_id);
CREATE INDEX IF NOT EXISTS idx_approvisionnement_fournisseur ON approvisionnement(fournisseur_id);
CREATE INDEX IF NOT EXISTS idx_approvisionnement_boulangerie ON approvisionnement(boulangerie_id);
CREATE INDEX IF NOT EXISTS idx_approvisionnement_etat ON approvisionnement(etat_id);
CREATE INDEX IF NOT EXISTS idx_vente_boulangerie ON vente(boulangerie_id);
CREATE INDEX IF NOT EXISTS idx_vente_vendeur ON vente(vendeur_id);
CREATE INDEX IF NOT EXISTS idx_vente_mode_paiement ON vente(mode_paiement_id);
CREATE INDEX IF NOT EXISTS idx_mouvementstock_product ON mouvementstock(product_id);
CREATE INDEX IF NOT EXISTS idx_mouvementstock_date ON mouvementstock(date_mouvement);
CREATE INDEX IF NOT EXISTS idx_lignevente_vente ON lignevente(vente_id);
CREATE INDEX IF NOT EXISTS idx_lignevente_product ON lignevente(product_id);
CREATE INDEX IF NOT EXISTS idx_ligneapprovisionnement_appro ON ligneapprovisionnement(approvisionnement_id);
CREATE INDEX IF NOT EXISTS idx_ligneapprovisionnement_product ON ligneapprovisionnement(product_id);
CREATE INDEX IF NOT EXISTS idx_ligneproduction_production ON ligneproduction(production_id);
CREATE INDEX IF NOT EXISTS idx_ligneproduction_product ON ligneproduction(product_id);
CREATE INDEX IF NOT EXISTS idx_consommationproduction_production ON consommationproduction(production_id);
CREATE INDEX IF NOT EXISTS idx_consommationproduction_product ON consommationproduction(product_id);
CREATE INDEX IF NOT EXISTS idx_depense_boulangerie ON depense(boulangerie_id);
CREATE INDEX IF NOT EXISTS idx_depense_type ON depense(type_depense_id);
CREATE INDEX IF NOT EXISTS idx_cloturecaisse_boulangerie ON cloturecaisse(boulangerie_id);
CREATE INDEX IF NOT EXISTS idx_cloturecaisse_date ON cloturecaisse(date_cloture);
CREATE INDEX IF NOT EXISTS idx_utilisateur_profile ON utilisateur(profile_id);
CREATE INDEX IF NOT EXISTS idx_session_utilisateur_user ON session_utilisateur(utilisateur_id);
CREATE INDEX IF NOT EXISTS idx_tenant_category ON category(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_continent ON continent(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_pays ON pays(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_region ON region(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_departement ON departement(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_commune ON commune(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_etat ON etat(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_fournisseur ON fournisseur(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_boulangerie ON boulangerie(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_product ON product(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_production ON production(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_vente ON vente(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_utilisateur ON utilisateur(tenant_id);