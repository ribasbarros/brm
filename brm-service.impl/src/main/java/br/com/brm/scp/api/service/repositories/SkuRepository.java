package br.com.brm.scp.api.service.repositories;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoActionOperation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.SkuDocument;

@Repository
public interface SkuRepository extends MongoRepository<SkuDocument, String> {

	@Query("{ item : {'$ref' : 'itemDocument' , '$id' : ?0}}")
	Collection<SkuDocument> findSkuByItem(String id);
	
	@Query("{ tags : {'$ref' : 'tagDocument' , '$id' : ?0}}")
	Collection<SkuDocument> findSkuByTag(String id);
	
	@Query("{origens :{ $elemMatch: {'_id' : ?0 }}}")
	Collection<SkuDocument> findSkuByFornecedor(String id);
	
	@Query("{ 'item' : {'$ref' : 'itemDocument' , '$id' : ?0}, "
			+ " 'tags.$id' : { '$all' : ?1 } , "
			+ " 'tags' : { '$exists' : true}, "
			+ " '$where' : 'this.tags.length = ?2' }")
	SkuDocument findSku(String id, ObjectId[] tags, int size);

	//TODO Pesquisar por mais atributos!
	@Query("{ $or: [ { 'descricao': {'$regex': ?0 , $options: 'i'} } ] }")
	Page<SkuDocument> findByDescricao(String searchTerm, Pageable pageable);
}
