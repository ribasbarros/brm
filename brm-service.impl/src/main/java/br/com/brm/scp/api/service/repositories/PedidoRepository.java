package br.com.brm.scp.api.service.repositories;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.PedidoDocument;

@Repository
public interface PedidoRepository extends MongoRepository<PedidoDocument, String>{

	@Query("{ 'origem' : ?0 }")
	Collection<PedidoDocument> listByOrigem(String sku);
	
	
}
