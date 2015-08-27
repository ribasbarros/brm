package br.com.brm.scp.api.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.FornecedorDocument;

@Repository
public interface FornecedorRepository extends MongoRepository<FornecedorDocument, String> {

}
