package br.com.brm.scp.api.service.sample.repositories.test;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.sample.document.test.TestDocument;

@Repository
public interface TestRepository extends MongoRepository<TestDocument, String> {

	@Query("{ 'testString' : ?0 }")
	List<TestDocument> findTest(String testString);

}
