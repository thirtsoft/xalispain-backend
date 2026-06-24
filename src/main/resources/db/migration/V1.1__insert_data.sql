-- =============================================
-- V1.1__insert_data.sql
-- Données de test pour le SaaS Xalispain
-- =============================================

-- =============================================
-- 1. TABLES SANS DÉPENDANCES
-- =============================================

-- 1.1 CONTINENTS
INSERT INTO continent (code, libelle, tenant_id)
VALUES ('AF', 'Afrique', 'default'),
       ('EU', 'Europe', 'default'),
       ('AS', 'Asie', 'default'),
       ('NA', 'Amérique du Nord', 'default'),
       ('SA', 'Amérique du Sud', 'default'),
       ('OC', 'Océanie', 'default'),
       ('AN', 'Antarctique', 'default');

-- 1.2 ETATS
INSERT INTO etat (code, libelle, tenant_id)
VALUES ('ACT', 'Actif', 'default'),
       ('INA', 'Inactif', 'default'),
       ('SUS', 'Suspendu', 'default'),
       ('BLO', 'Bloqué', 'default'),
       ('ARC', 'Archivé', 'default');

-- 1.3 CATEGORIES
INSERT INTO category (libelle)
VALUES ('PRODUITS FINIS'),
       ('MATIERES PREMIERES'),
       ('ENERGIE');

-- 1.4 UNITES DE MESURE
INSERT INTO unite_mesure (libelle)
VALUES ('PIECE'),
       ('KILOGRAMME'),
       ('SACHET'),
       ('CARTON'),
       ('BOUTEILLE'),
       ('STERE');

-- 1.5 MODES DE PAIEMENT
INSERT INTO modepaiement (libelle, tenant_id)
VALUES ('Espèces', 'default'),
       ('Carte Bancaire', 'default'),
       ('Chèque', 'default'),
       ('Virement', 'default'),
       ('Mobile Money', 'default'),
       ('Wave', 'default'),
       ('Orange Money', 'default');

-- 1.6 TYPES DE DEPENSE
INSERT INTO typedepense (libelle, tenant_id)
VALUES ('Achat Farine', 'default'),
       ('Achat Levure', 'default'),
       ('Achat Sel', 'default'),
       ('Achat Sucre', 'default'),
       ('Achat Beurre', 'default'),
       ('Achat Œufs', 'default'),
       ('Achat Lait', 'default'),
       ('Achat Huile', 'default'),
       ('Eau', 'default'),
       ('Électricité', 'default'),
       ('Gaz', 'default'),
       ('Transport', 'default'),
       ('Main d''œuvre', 'default'),
       ('Loyer', 'default'),
       ('Marketing', 'default'),
       ('Entretien', 'default');

-- 1.7 TYPES DE COMPTE
INSERT INTO typecompte (code, libelle)
VALUES ('ADMIN', 'ADMINISTRATEUR'),
       ('PROP', 'PROPRIETAIRE'),
       ('GERANT', 'GERANT'),
       ('VENDEUR', 'VENDEUR'),
       ('LIVREUR', 'LIVREUR');

-- 1.8 EMPLOYES
INSERT INTO employe (nom, prenom, telephone, type_remuneration, montant_remuneration)
VALUES ('Diop', 'Moussa', '770000020', 'MENSUEL', 150000.00),
       ('Fall', 'Aminata', '770000021', 'JOURNALIER', 5000.00),
       ('Sow', 'Ibrahim', '770000022', 'LIVRAISON', 1000.00),
       ('Ndiaye', 'Fatou', '770000023', 'MENSUEL', 120000.00),
       ('Ba', 'Ousmane', '770000024', 'JOURNALIER', 4500.00);

-- 1.9 LIVREURS
INSERT INTO livreur (nom, prenom, telephone, type_remuneration, montant_remuneration)
VALUES ('Diop', 'Moussa', '770000020', 'MENSUEL', 150000.00),
       ('Fall', 'Aminata', '770000021', 'JOURNALIER', 5000.00),
       ('Sow', 'Ibrahim', '770000022', 'LIVRAISON', 1000.00);

-- =============================================
-- 2. PAYS
-- =============================================
INSERT INTO pays (code, libelle, indicatif, continent_id, tenant_id)
VALUES ('SN', 'Sénégal', '+221', (SELECT id FROM continent WHERE code = 'AF'), 'default'),
       ('CI', 'Côte d''Ivoire', '+225', (SELECT id FROM continent WHERE code = 'AF'), 'default'),
       ('ML', 'Mali', '+223', (SELECT id FROM continent WHERE code = 'AF'), 'default'),
       ('FR', 'France', '+33', (SELECT id FROM continent WHERE code = 'EU'), 'default'),
       ('BE', 'Belgique', '+32', (SELECT id FROM continent WHERE code = 'EU'), 'default'),
       ('CH', 'Suisse', '+41', (SELECT id FROM continent WHERE code = 'EU'), 'default'),
       ('US', 'États-Unis', '+1', (SELECT id FROM continent WHERE code = 'NA'), 'default'),
       ('CA', 'Canada', '+1', (SELECT id FROM continent WHERE code = 'NA'), 'default');

-- =============================================
-- 3. REGIONS
-- =============================================
INSERT INTO region (code, libelle, pays_id, tenant_id)
VALUES ('DK', 'Dakar', (SELECT id FROM pays WHERE code = 'SN'), 'default'),
       ('TH', 'Thiès', (SELECT id FROM pays WHERE code = 'SN'), 'default'),
       ('SL', 'Saint-Louis', (SELECT id FROM pays WHERE code = 'SN'), 'default'),
       ('KF', 'Kaffrine', (SELECT id FROM pays WHERE code = 'SN'), 'default'),
       ('IDF', 'Île-de-France', (SELECT id FROM pays WHERE code = 'FR'), 'default'),
       ('ARA', 'Auvergne-Rhône-Alpes', (SELECT id FROM pays WHERE code = 'FR'), 'default'),
       ('PAC', 'Provence-Alpes-Côte d''Azur', (SELECT id FROM pays WHERE code = 'FR'), 'default');

-- =============================================
-- 4. DEPARTEMENTS
-- =============================================
INSERT INTO departement (code, libelle, region_id, tenant_id)
VALUES ('DK1', 'Dakar-Plateau', (SELECT id FROM region WHERE code = 'DK'), 'default'),
       ('DK2', 'Grand-Dakar', (SELECT id FROM region WHERE code = 'DK'), 'default'),
       ('DK3', 'Pikine', (SELECT id FROM region WHERE code = 'DK'), 'default'),
       ('TH1', 'Thiès-ville', (SELECT id FROM region WHERE code = 'TH'), 'default'),
       ('TH2', 'Mbour', (SELECT id FROM region WHERE code = 'TH'), 'default'),
       ('PAR', 'Paris', (SELECT id FROM region WHERE code = 'IDF'), 'default'),
       ('SEINE', 'Seine-Saint-Denis', (SELECT id FROM region WHERE code = 'IDF'), 'default'),
       ('HAUTS', 'Hauts-de-Seine', (SELECT id FROM region WHERE code = 'IDF'), 'default');

-- =============================================
-- 5. COMMUNES
-- =============================================
INSERT INTO commune (libelle, departement_id, tenant_id)
VALUES ('Médina', (SELECT id FROM departement WHERE code = 'DK1'), 'default'),
       ('Gueule Tapée', (SELECT id FROM departement WHERE code = 'DK1'), 'default'),
       ('Pikine-Ouest', (SELECT id FROM departement WHERE code = 'DK3'), 'default'),
       ('Diamniadio', (SELECT id FROM departement WHERE code = 'DK3'), 'default'),
       ('Thiès-Est', (SELECT id FROM departement WHERE code = 'TH1'), 'default'),
       ('Mbour-Est', (SELECT id FROM departement WHERE code = 'TH2'), 'default'),
       ('Paris 1er', (SELECT id FROM departement WHERE code = 'PAR'), 'default'),
       ('Paris 2ème', (SELECT id FROM departement WHERE code = 'PAR'), 'default'),
       ('Montreuil', (SELECT id FROM departement WHERE code = 'SEINE'), 'default'),
       ('Nanterre', (SELECT id FROM departement WHERE code = 'HAUTS'), 'default');

-- =============================================
-- 6. BOULANGERIES
-- =============================================
INSERT INTO boulangerie (code, libelle, commune_id, address, etat_id, telephone, mobile, type, statut)
VALUES ('BOUL001', 'Boulangerie Principale',
        (SELECT id FROM commune WHERE libelle = 'Médina'),
        '123 Rue de la Paix',
        (SELECT id FROM etat WHERE code = 'ACT'),
        '338000001', '770000010', 'PRINCIPALE', 'ACTIF'),
       ('BOUL002', 'Boulangerie Secondaire',
        (SELECT id FROM commune WHERE libelle = 'Gueule Tapée'),
        '456 Avenue Centrale',
        (SELECT id FROM etat WHERE code = 'ACT'),
        '338000002', '770000011', 'SECONDAIRE', 'ACTIF');

-- =============================================
-- 7. FOURNISSEURS
-- =============================================
INSERT INTO fournisseur (sigle, denomination, telephone, mobile, email, adresse, commune_id, tenant_id)
VALUES ('GFS', 'Grands Moulins de Dakar', '338211234', '771234567', 'contact@gmd.sn', 'Route de Rufisque',
        (SELECT id FROM commune WHERE libelle = 'Médina'), 'default'),
       ('SBF', 'Sénégalaise de Boulangerie', '338456789', '772345678', 'info@sbf.sn', 'Rue de la République',
        (SELECT id FROM commune WHERE libelle = 'Pikine-Ouest'), 'default'),
       ('LDS', 'La Délivrance Sénégal', '338987654', '773456789', 'contact@ladelivrance.sn', 'Avenue Cheikh Anta Diop',
        (SELECT id FROM commune WHERE libelle = 'Gueule Tapée'), 'default'),
       ('FGF', 'Farines du Sénégal', '338543210', '774567890', 'commercial@farines.sn',
        'Zone industrielle de Diamniadio', (SELECT id FROM commune WHERE libelle = 'Diamniadio'), 'default'),
       ('GBF', 'Grands Moulins de France', '339123456', '775678901', 'contact@gmf.fr', 'Porte de la Chapelle',
        (SELECT id FROM commune WHERE libelle = 'Paris 1er'), 'default');

-- =============================================
-- 8. SOUS-CATEGORIES
-- =============================================
INSERT INTO subcategory (libelle, category_id)
VALUES ('PAINS', (SELECT id FROM category WHERE libelle = 'PRODUITS FINIS')),
       ('VIENNOISERIES', (SELECT id FROM category WHERE libelle = 'PRODUITS FINIS')),
       ('BEIGNETS', (SELECT id FROM category WHERE libelle = 'PRODUITS FINIS')),
       ('FARINES', (SELECT id FROM category WHERE libelle = 'MATIERES PREMIERES')),
       ('LEVURES', (SELECT id FROM category WHERE libelle = 'MATIERES PREMIERES')),
       ('COMBUSTIBLES', (SELECT id FROM category WHERE libelle = 'ENERGIE'));

-- =============================================
-- 9. PRODUITS
-- =============================================
INSERT INTO product (reference, libelle, subcategory_id, prix_vente, prix_appro, unite_mesure_id, duree_conservation, est_perissable, seuil_alerte)
VALUES ('PROD001', 'Baguette', (SELECT id FROM subcategory WHERE libelle = 'PAINS'), 150.00, 50.00, (SELECT id FROM unite_mesure WHERE libelle = 'PIECE'), 1.0, true, null),
       ('PROD002', 'Pain Mil', (SELECT id FROM subcategory WHERE libelle = 'PAINS'), 200.00, 75.00, (SELECT id FROM unite_mesure WHERE libelle = 'PIECE'), 2.0, true, null),
       ('PROD003', 'Croissant', (SELECT id FROM subcategory WHERE libelle = 'VIENNOISERIES'), 250.00, 100.00, (SELECT id FROM unite_mesure WHERE libelle = 'PIECE'), 1.0, true, null),
       ('PROD004', 'Farine T55', (SELECT id FROM subcategory WHERE libelle = 'FARINES'), null, 15000.00, (SELECT id FROM unite_mesure WHERE libelle = 'KILOGRAMME'), null, true, 10),
       ('PROD005', 'Levure Fraîche', (SELECT id FROM subcategory WHERE libelle = 'LEVURES'), null, 5000.00, (SELECT id FROM unite_mesure WHERE libelle = 'SACHET'), null, true, 5),
       ('PROD006', 'Beignet Nature', (SELECT id FROM subcategory WHERE libelle = 'BEIGNETS'), 100.00, 40.00, (SELECT id FROM unite_mesure WHERE libelle = 'PIECE'), 1.0, true, null);

-- =============================================
-- 10. PRODUCTIONS
-- =============================================
INSERT INTO production (boulangerie_id, employe_id, etat_id, quantite_production, montant_production, date_production, heure, commentaire)
VALUES ((SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM employe WHERE nom = 'Diop' AND prenom = 'Moussa'), (SELECT id FROM etat WHERE code = 'ACT'), 200, 45000.00, '2024-01-15 06:00:00', '06:00:00', 'Production matinale'),
       ((SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM employe WHERE nom = 'Fall' AND prenom = 'Aminata'), (SELECT id FROM etat WHERE code = 'ACT'), 150, 35000.00, '2024-01-15 14:00:00', '14:00:00', 'Production après-midi'),
       ((SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM employe WHERE nom = 'Diop' AND prenom = 'Moussa'), (SELECT id FROM etat WHERE code = 'INA'), 300, 65000.00, '2024-02-01 06:00:00', '06:00:00', 'Production spéciale');

-- =============================================
-- 11. APPROVISIONNEMENTS
-- =============================================
INSERT INTO approvisionnement (numero_approvisionnement, boulangerie_id, fournisseur_id, fournisseur_name, montant_total, approvisionnement_date, etat_id)
VALUES ('FACT-2024-001', (SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM fournisseur WHERE sigle = 'GFS'), 'Grands Moulins de Dakar', 500000.00, '2024-01-15', (SELECT id FROM etat WHERE code = 'ACT')),
       ('FACT-2024-002', (SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM fournisseur WHERE sigle = 'SBF'), 'Sénégalaise de Boulangerie', 350000.00, '2024-02-20', (SELECT id FROM etat WHERE code = 'ACT'));

-- =============================================
-- 12. VENTES
-- =============================================
INSERT INTO vente (numero_vente, boulangerie_id, vendeur_id, date_vente, montant_total_vente, quantite_total_vente, mode_paiement_id, type_vente)
VALUES ('VENTE-2024-001', (SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM employe WHERE nom = 'Sow' AND prenom = 'Ibrahim'), '2024-01-15 08:00:00', 25000.00, 50, (SELECT id FROM modepaiement WHERE libelle = 'Espèces'), 'CAISSE'),
       ('VENTE-2024-002', (SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM employe WHERE nom = 'Ndiaye' AND prenom = 'Fatou'), '2024-01-15 09:00:00', 15000.00, 30, (SELECT id FROM modepaiement WHERE libelle = 'Carte Bancaire'), 'CAISSE'),
       ('VENTE-2024-003', (SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM employe WHERE nom = 'Ba' AND prenom = 'Ousmane'), '2024-01-15 10:00:00', 18000.00, 35, (SELECT id FROM modepaiement WHERE libelle = 'Espèces'), 'LIVREUR');

-- =============================================
-- 13. CONSOMMATIONS PRODUCTION
-- =============================================
INSERT INTO consommationproduction (production_id, product_id, product_name, quantite_consommee, unite_utilisee)
VALUES ((SELECT id FROM production WHERE commentaire = 'Production matinale'), (SELECT id FROM product WHERE reference = 'PROD004'), 'Farine T55', 25.0, 'Kg'),
       ((SELECT id FROM production WHERE commentaire = 'Production matinale'), (SELECT id FROM product WHERE reference = 'PROD005'), 'Levure Fraîche', 2.0, 'Sachet'),
       ((SELECT id FROM production WHERE commentaire = 'Production après-midi'), (SELECT id FROM product WHERE reference = 'PROD004'), 'Farine T55', 15.0, 'Kg'),
       ((SELECT id FROM production WHERE commentaire = 'Production après-midi'), (SELECT id FROM product WHERE reference = 'PROD005'), 'Levure Fraîche', 1.0, 'Sachet');

-- =============================================
-- 14. LIGNES PRODUCTION
-- =============================================
INSERT INTO ligneproduction (production_id, product_id, product_name, prix_unitaire_production, quantite_produite)
VALUES ((SELECT id FROM production WHERE commentaire = 'Production matinale'), (SELECT id FROM product WHERE reference = 'PROD001'), 'Baguette', 100.00, 100),
       ((SELECT id FROM production WHERE commentaire = 'Production matinale'), (SELECT id FROM product WHERE reference = 'PROD002'), 'Pain Mil', 150.00, 50),
       ((SELECT id FROM production WHERE commentaire = 'Production matinale'), (SELECT id FROM product WHERE reference = 'PROD003'), 'Croissant', 200.00, 50),
       ((SELECT id FROM production WHERE commentaire = 'Production après-midi'), (SELECT id FROM product WHERE reference = 'PROD001'), 'Baguette', 100.00, 80),
       ((SELECT id FROM production WHERE commentaire = 'Production après-midi'), (SELECT id FROM product WHERE reference = 'PROD006'), 'Beignet Nature', 75.00, 70);

-- =============================================
-- 15. LIGNES APPROVISIONNEMENT
-- =============================================
INSERT INTO ligneapprovisionnement (approvisionnement_id, product_id, product_name, quantite_approvisionnement, prix_unitaire_achat)
VALUES ((SELECT id FROM approvisionnement WHERE numero_approvisionnement = 'FACT-2024-001'), (SELECT id FROM product WHERE reference = 'PROD004'), 'Farine T55', 10, 15000.00),
       ((SELECT id FROM approvisionnement WHERE numero_approvisionnement = 'FACT-2024-001'), (SELECT id FROM product WHERE reference = 'PROD005'), 'Levure Fraîche', 5, 5000.00),
       ((SELECT id FROM approvisionnement WHERE numero_approvisionnement = 'FACT-2024-002'), (SELECT id FROM product WHERE reference = 'PROD004'), 'Farine T55', 8, 15500.00);

-- =============================================
-- 16. LIGNES VENTE
-- =============================================
INSERT INTO lignevente (vente_id, product_id, product_name, prix_unitaire_vente, quantite_vente)
VALUES ((SELECT id FROM vente WHERE numero_vente = 'VENTE-2024-001'), (SELECT id FROM product WHERE reference = 'PROD001'), 'Baguette', 150.00, 20),
       ((SELECT id FROM vente WHERE numero_vente = 'VENTE-2024-001'), (SELECT id FROM product WHERE reference = 'PROD002'), 'Pain Mil', 200.00, 15),
       ((SELECT id FROM vente WHERE numero_vente = 'VENTE-2024-001'), (SELECT id FROM product WHERE reference = 'PROD003'), 'Croissant', 250.00, 15),
       ((SELECT id FROM vente WHERE numero_vente = 'VENTE-2024-002'), (SELECT id FROM product WHERE reference = 'PROD006'), 'Beignet Nature', 100.00, 10),
       ((SELECT id FROM vente WHERE numero_vente = 'VENTE-2024-002'), (SELECT id FROM product WHERE reference = 'PROD003'), 'Croissant', 250.00, 20),
       ((SELECT id FROM vente WHERE numero_vente = 'VENTE-2024-003'), (SELECT id FROM product WHERE reference = 'PROD001'), 'Baguette', 150.00, 25),
       ((SELECT id FROM vente WHERE numero_vente = 'VENTE-2024-003'), (SELECT id FROM product WHERE reference = 'PROD002'), 'Pain Mil', 200.00, 10);

-- =============================================
-- 17. DEPENSES
-- =============================================
INSERT INTO depense (boulangerie_id, type_depense_id, montant_depense, date_depense, libelle)
VALUES ((SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM typedepense WHERE libelle = 'Électricité'), 50000.00, '2024-01-10 10:00:00', 'Facture électricité janvier'),
       ((SELECT id FROM boulangerie WHERE code = 'BOUL001'), (SELECT id FROM typedepense WHERE libelle = 'Entretien'), 25000.00, '2024-01-25 14:30:00', 'Maintenance four');

-- =============================================
-- 18. CLOTURES CAISSE
-- =============================================
INSERT INTO cloturecaisse (boulangerie_id, date_cloture, montant_theorique, montant_declare, montant_reel, ecart, cloturee_par)
VALUES ((SELECT id FROM boulangerie WHERE code = 'BOUL001'), '2024-01-15 22:00:00', 58000.00, 58000.00, 57500.00, -500.00, (SELECT id FROM employe WHERE nom = 'Diop' AND prenom = 'Moussa')),
       ((SELECT id FROM boulangerie WHERE code = 'BOUL001'), '2024-01-16 22:00:00', 45000.00, 45000.00, 45100.00, 100.00, (SELECT id FROM employe WHERE nom = 'Fall' AND prenom = 'Aminata')),
       ((SELECT id FROM boulangerie WHERE code = 'BOUL001'), '2024-02-01 22:00:00', 63000.00, 63000.00, 63000.00, 0.00, (SELECT id FROM employe WHERE nom = 'Diop' AND prenom = 'Moussa'));

-- =============================================
-- 19. MOUVEMENTS STOCK
-- =============================================
INSERT INTO mouvementstock (product_id, product_name, prix_unitaire_applique, type, quantite, date_mouvement, approvisionnement_id, production_id, motif)
VALUES ((SELECT id FROM product WHERE reference = 'PROD004'), 'Farine T55', 15000.00, 'ENTREE', 10, '2024-01-15 08:00:00', (SELECT id FROM approvisionnement WHERE numero_approvisionnement = 'FACT-2024-001'), null, null),
       ((SELECT id FROM product WHERE reference = 'PROD004'), 'Farine T55', null, 'SORTIE', 25, '2024-01-15 08:30:00', null, (SELECT id FROM production WHERE commentaire = 'Production matinale'), 'PRODUCTION'),
       ((SELECT id FROM product WHERE reference = 'PROD005'), 'Levure Fraîche', 5000.00, 'ENTREE', 5, '2024-01-15 08:00:00', (SELECT id FROM approvisionnement WHERE numero_approvisionnement = 'FACT-2024-001'), null, null),
       ((SELECT id FROM product WHERE reference = 'PROD005'), 'Levure Fraîche', null, 'SORTIE', 2, '2024-01-15 08:30:00', null, (SELECT id FROM production WHERE commentaire = 'Production matinale'), 'PRODUCTION');

-- =============================================
-- 20. ACTIONS
-- =============================================
INSERT INTO action (code, libelle, type_compte_id)
VALUES ('GEST_BOUL', 'Gestion des boulangeries', (SELECT id FROM typecompte WHERE code = 'ADMIN')),
       ('GEST_USER', 'Gestion des utilisateurs', (SELECT id FROM typecompte WHERE code = 'ADMIN')),
       ('GEST_PROD', 'Gestion des productions', (SELECT id FROM typecompte WHERE code = 'PROP')),
       ('GEST_VENTE', 'Gestion des ventes', (SELECT id FROM typecompte WHERE code = 'GERANT')),
       ('VOIR_VENTE', 'Visualisation des ventes', (SELECT id FROM typecompte WHERE code = 'VENDEUR')),
       ('LIVRAISON', 'Gestion des livraisons', (SELECT id FROM typecompte WHERE code = 'LIVREUR')),
       ('GEST_STOCK', 'Gestion des stocks', (SELECT id FROM typecompte WHERE code = 'PROP')),
       ('RAPPORT', 'Consultation des rapports', (SELECT id FROM typecompte WHERE code = 'PROP'));

-- =============================================
-- 21. PROFILES
-- =============================================
INSERT INTO profile (code, libelle, type_compte_id)
VALUES ('ADMIN_SYS', 'Administrateur Système', (SELECT id FROM typecompte WHERE code = 'ADMIN')),
       ('PROP_BAK', 'Propriétaire Boulangerie', (SELECT id FROM typecompte WHERE code = 'PROP')),
       ('GERANT_BAK', 'Gérant Boulangerie', (SELECT id FROM typecompte WHERE code = 'GERANT')),
       ('VENDEUR_BAK', 'Vendeur Boulangerie', (SELECT id FROM typecompte WHERE code = 'VENDEUR')),
       ('LIVREUR_BAK', 'Livreur Boulangerie', (SELECT id FROM typecompte WHERE code = 'LIVREUR'));

-- =============================================
-- 22. ACTIONS PAR PROFIL
-- =============================================
INSERT INTO actions_par_profil (profil_id, action_id)
VALUES ((SELECT id FROM profile WHERE code = 'ADMIN_SYS'), (SELECT id FROM action WHERE code = 'GEST_BOUL')),
       ((SELECT id FROM profile WHERE code = 'ADMIN_SYS'), (SELECT id FROM action WHERE code = 'GEST_USER')),
       ((SELECT id FROM profile WHERE code = 'ADMIN_SYS'), (SELECT id FROM action WHERE code = 'GEST_PROD')),
       ((SELECT id FROM profile WHERE code = 'ADMIN_SYS'), (SELECT id FROM action WHERE code = 'GEST_VENTE')),
       ((SELECT id FROM profile WHERE code = 'ADMIN_SYS'), (SELECT id FROM action WHERE code = 'VOIR_VENTE')),
       ((SELECT id FROM profile WHERE code = 'ADMIN_SYS'), (SELECT id FROM action WHERE code = 'LIVRAISON')),
       ((SELECT id FROM profile WHERE code = 'ADMIN_SYS'), (SELECT id FROM action WHERE code = 'GEST_STOCK')),
       ((SELECT id FROM profile WHERE code = 'ADMIN_SYS'), (SELECT id FROM action WHERE code = 'RAPPORT')),
       ((SELECT id FROM profile WHERE code = 'PROP_BAK'), (SELECT id FROM action WHERE code = 'GEST_PROD')),
       ((SELECT id FROM profile WHERE code = 'PROP_BAK'), (SELECT id FROM action WHERE code = 'GEST_VENTE')),
       ((SELECT id FROM profile WHERE code = 'PROP_BAK'), (SELECT id FROM action WHERE code = 'GEST_STOCK')),
       ((SELECT id FROM profile WHERE code = 'PROP_BAK'), (SELECT id FROM action WHERE code = 'RAPPORT')),
       ((SELECT id FROM profile WHERE code = 'GERANT_BAK'), (SELECT id FROM action WHERE code = 'GEST_VENTE')),
       ((SELECT id FROM profile WHERE code = 'GERANT_BAK'), (SELECT id FROM action WHERE code = 'VOIR_VENTE')),
       ((SELECT id FROM profile WHERE code = 'GERANT_BAK'), (SELECT id FROM action WHERE code = 'GEST_STOCK')),
       ((SELECT id FROM profile WHERE code = 'VENDEUR_BAK'), (SELECT id FROM action WHERE code = 'VOIR_VENTE')),
       ((SELECT id FROM profile WHERE code = 'LIVREUR_BAK'), (SELECT id FROM action WHERE code = 'LIVRAISON'));

-- =============================================
-- 23. UTILISATEURS
-- =============================================
INSERT INTO utilisateur (prenom, nom, telephone, email, mot_de_passe, date_creation, active, profile_id, activation, est_admin_boulangerie)
VALUES ('Admin', 'Système', '770000001', 'admin@boulangerie.sn', 'hashed_password_1', '2024-01-01 08:00:00', true, (SELECT id FROM profile WHERE code = 'ADMIN_SYS'), 'ACTIVATED1', 1),
       ('Proprio', 'Bakery', '770000002', 'proprio@boulangerie.sn', 'hashed_password_2', '2024-01-01 08:00:00', true, (SELECT id FROM profile WHERE code = 'PROP_BAK'), 'ACTIVATED2', 1),
       ('Gérant', 'Bakery', '770000003', 'gerant@boulangerie.sn', 'hashed_password_3', '2024-01-01 08:00:00', true, (SELECT id FROM profile WHERE code = 'GERANT_BAK'), 'ACTIVATED3', 0),
       ('Vendeur1', 'Bakery', '770000004', 'vendeur1@boulangerie.sn', 'hashed_password_4', '2024-01-01 08:00:00', true, (SELECT id FROM profile WHERE code = 'VENDEUR_BAK'), 'ACTIVATED4', 0),
       ('Livreur1', 'Bakery', '770000005', 'livreur1@boulangerie.sn', 'hashed_password_5', '2024-01-01 08:00:00', true, (SELECT id FROM profile WHERE code = 'LIVREUR_BAK'), 'ACTIVATED5', 0);

-- =============================================
-- 24. SESSIONS UTILISATEUR
-- =============================================
INSERT INTO session_utilisateur (utilisateur_id, date_connexion, date_expiration, adresse_ip)
VALUES ((SELECT id FROM utilisateur WHERE email = 'admin@boulangerie.sn'), '2024-01-15 08:00:00', '2024-01-15 20:00:00', '192.168.1.100'),
       ((SELECT id FROM utilisateur WHERE email = 'proprio@boulangerie.sn'), '2024-01-15 08:30:00', '2024-01-15 18:30:00', '192.168.1.101'),
       ((SELECT id FROM utilisateur WHERE email = 'gerant@boulangerie.sn'), '2024-01-15 09:00:00', '2024-01-15 17:00:00', '192.168.1.102');