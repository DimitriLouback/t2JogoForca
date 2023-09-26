package br.edu.iff.factory;

import br.edu.iff.repository.Repository;

public abstract class EntityFactory {
    
    private static Repository repository;

    protected EntityFactory(Repository repository) {
        if(repository == null) {
            throw new IllegalArgumentException("O repositório não pode ser nulo.");
        }
        this.repository = repository;
    }

    protected Repository getRepository() {
        
        return repository;

    }

    protected static long getProximoId() {
        return repository.getProximoId();
    }
}

