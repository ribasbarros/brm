package br.com.brm.scp.api.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.DfuDocument;
import br.com.brm.scp.api.service.document.TagDocument;

@Repository
public interface DfuRepository extends MongoRepository<DfuDocument, String>{

	@Query("{ '_id' : ?0 }")
	DfuDocument findById(String id);
	
	@Query("{ 'faseVida': {'$regex': ?0 , $options: 'i'} }")
	Page<DfuDocument> find(String searchTerm, Pageable pageable);
}
