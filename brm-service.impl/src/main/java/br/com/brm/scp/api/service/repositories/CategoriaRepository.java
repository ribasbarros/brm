package br.com.brm.scp.api.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.CategoriaDocument;

@Repository
public interface CategoriaRepository extends MongoRepository<CategoriaDocument, String> {

}
