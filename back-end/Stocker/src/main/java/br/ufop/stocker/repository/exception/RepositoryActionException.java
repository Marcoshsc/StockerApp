package br.ufop.stocker.repository.exception;

public class RepositoryActionException extends Exception {

    private String message;

    public RepositoryActionException(String message) {
        super(message);
    }
}
