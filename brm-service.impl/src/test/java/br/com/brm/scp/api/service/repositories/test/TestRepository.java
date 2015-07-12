package br.com.brm.scp.api.service.repositories.test;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.test.TestDocument;

@Repository
public interface TestRepository extends MongoRepository<TestDocument, String> {

}
