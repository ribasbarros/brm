package br.com.brm.scp.api.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.FornecedorDocument;

@Repository
public interface FornecedorRepository extends MongoRepository<FornecedorDocument, String> {

	@Query("{ 'razaoSocial' : ?0 }")
	FornecedorDocument findByRazaoSocial(String razaoSocial);

	@Query("{ 'cnpj' : ?0 }")
	FornecedorDocument findByCnpj(String cnpj);
	
	@Query("{ '_id' : ?0, 'centros' : { $elemMatch: { 'centro' : ?1}  } }")
	FornecedorDocument findByIdAndCentro(String id, Integer centro);

}
