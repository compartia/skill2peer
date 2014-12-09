package org.az.skill2peer.nuclei.user.service;

/**
 * The exception is thrown when the email given during the registration
 * phase is already found from the database.
  */
public class DuplicateEmailException extends Exception {

    private static final long serialVersionUID = -2989921994597365911L;

    public DuplicateEmailException(final String message) {
        super(message);
    }
}
