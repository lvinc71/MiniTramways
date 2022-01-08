package com.stonksco.minitramways.logic.interactions;

/**
 * Utilisée pour les erreurs d'interactions : pas assez d'argent, emplacement déjà occupé...
 */
public class InteractionException extends Exception {
    public InteractionException(String money) {
        super(money);
    }
}
