package com.wokite.net.produit.entity;

public enum MotifSortie {
    VENTE_CAISSE,
    DISTRIBUTION_LIVREUR,
    RETOUR_INVENDU,    // Spécifique aux pains invendus
    CONSOMMATION,      // Consommation en production (matières premières)
    PERTE_PEREMPTION,
    DON,
    AUTRE
}