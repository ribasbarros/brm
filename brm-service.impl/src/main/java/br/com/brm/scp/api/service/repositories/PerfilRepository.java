package br.com.brm.scp.api.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.PerfilDocument;

@Repository
public interface PerfilRepository extends MongoRepository<PerfilDocument, String>{

	@Query("{ 'nome' : ?0 }")
	PerfilDocument findByNome(String nome);
	
	@Query("{ '_id' : ?0 }")
	PerfilDocument findById(String id);

	@Query("{ 'nome': {'$regex': ?0 , $options: 'i'} }")
	Page<PerfilDocument> findByNome(String searchTerm, Pageable pageable);	
}
