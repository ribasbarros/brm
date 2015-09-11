package br.com.brm.scp.api.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.GrupoDocument;

@Repository
public interface GrupoRepository extends MongoRepository<GrupoDocument, String>{
	@Query("{ 'nome' : ?0 }")
	GrupoDocument findByNome(String nome);
	
	@Query("{ '_id' : ?0 }")
	GrupoDocument findById(String id);
}
