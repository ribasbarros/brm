package br.com.brm.scp.api.service.repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.document.SkuDocument;

@Repository
public interface ItemRepository extends MongoRepository<ItemDocument, String> {

	@Query("{ 'nome' : ?0 }")
	ItemDocument findByNome(String value);

	@Query("{ 'nomeReduzido' : ?0 }")
	ItemDocument findByNomeReduzido(String value);

	@Query("{ fornecedor : {'$ref' : 'fornecedorDocument' , '$id' : ?0}}")
	Collection<ItemDocument> findItemByFornecedor(String id);

	@Query("{ categoria : {'$ref' : 'categoriaDocument' , '$id' : ?0}}")
	Collection<ItemDocument> findItemByCategoria(String id);

	
	@Query("{ $or: [ { 'nome': {'$regex': ?0 , $options: 'i'} } , "
			+ "{ 'nomeReduzido': {'$regex': ?0, $options: 'i'} }, "
			+ "{ 'descricao': {'$regex': ?0 , $options: 'i'} }] }")
	Page<ItemDocument> findByNameOrNomeReduzidoOrDescricao(String searchTerm,
			Pageable constructPageSpecification);

}
