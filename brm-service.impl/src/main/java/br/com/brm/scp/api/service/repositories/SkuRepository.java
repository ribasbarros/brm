package br.com.brm.scp.api.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.brm.scp.api.service.document.SkuDocument;

public interface SkuRepository extends MongoRepository<SkuDocument, Long> {

}
