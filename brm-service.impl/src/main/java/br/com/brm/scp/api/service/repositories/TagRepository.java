package br.com.brm.scp.api.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.TagDocument;

@Repository
public interface TagRepository extends MongoRepository<TagDocument, String> {

}
