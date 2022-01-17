package services;

import domain.Inscriere;

public interface Observer {
    void update(Inscriere inscriere) throws Exception;
}
