package br.com.brm.scp.api.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.ItemDocument;

@Repository
public interface ItemRepository extends MongoRepository<ItemDocument, String> {

	@Query("{ 'nome' : ?0 }")
	ItemDocument findByNome(String value);

	@Query("{ 'nomeReduzido' : ?0 }")
	ItemDocument findByNomeReduzido(String value);
	
	@Query("{ $or: [ { 'nome': {'$regex': ?0 , $options: 'i'} } , "
			+ "{ 'nomeReduzido': {'$regex': ?0, $options: 'i'} }, "
			+ "{ 'descricao': {'$regex': ?0 , $options: 'i'} }, "
			+ "{ 'categoria.nome': {'$regex': ?0 , $options: 'i'} }] }")
	Page<ItemDocument> findByNameOrNomeReduzidoOrDescricaoOrCategoria(String searchTerm,
			org.springframework.data.domain.Pageable constructPageSpecification);

}
