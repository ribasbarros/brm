package br.com.brm.scp.api.service.repositories;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.SkuDocument;

@Repository
public interface SkuRepository extends MongoRepository<SkuDocument, String> {

	@Query("{ item : {'$ref' : 'itemDocument' , '$id' : ?0}}")
	Collection<SkuDocument> findSkuByItem(String id);
	
	@Query("{ 'item' : {'$ref' : 'itemDocument' , '$id' : ?0}, "
			+ " 'tags.$id' : { '$all' : ?1 } , "
			+ " 'tags' : { '$exists' : true}, "
			+ " '$where' : 'this.tags.length = ?2' }")
	SkuDocument findSku(String id, ObjectId[] tags, int size);
	
	/*@Query("{'$or':["
			+ "{'email':{'$regex':searchString, '$options':'i'}}, "
			+ "{'first_name':{'$regex':searchString, '$options':'i'}}, "
			+ "{'last_name':{'$regex':searchString, '$options':'i'}}]}")
	Collection<SkuDocument> search(Object value);*/
	
}
