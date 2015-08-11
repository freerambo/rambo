package com.sudoku.model;

/**
 * Enumeration used to inform observers what to update.
 *
 * @author rambo
 */
public enum UpdateAction {
    NEW_GAME,
    CHECK,
    SELECTED_NUMBER,
    CANDIDATES,
    HELP
}