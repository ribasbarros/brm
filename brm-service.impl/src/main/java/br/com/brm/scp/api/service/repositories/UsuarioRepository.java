package br.com.brm.scp.api.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brm.scp.api.service.document.UsuarioDocument;

@Repository
public interface UsuarioRepository extends
		MongoRepository<UsuarioDocument, String> {
	
	@Query("{ '_id' : ?0 }")
	UsuarioDocument findById(String id);

	@Query("{ 'nome' : ?0 }")
	UsuarioDocument findByNome(String nome);
	
	@Query("{ 'email' : ?0 }")
	UsuarioDocument findByEmail(String email);
	
}
