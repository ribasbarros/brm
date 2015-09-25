package br.com.brm.scp.api.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.DfuDocument;

@Repository
public interface DfuRepository extends MongoRepository<DfuDocument, String>{

	@Query("{ '_id' : ?0 }")
	DfuDocument findById(String id);

}
