package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Presidente;

@Repository
public interface PresidenteRepository extends CrudRepository<Presidente, Long> {
	@Query(value = "SELECT * FROM presidente order by id desc limit :limit", nativeQuery = true)
	public List<Presidente> findTopN(@Param("limit") int limit);
	
	@Query (value = "SELECT p FROM Presidente p WHERE p.username = :username")
	public Presidente findPresidente(@Param("username") String username);
	
	boolean existsByNome(String nome);
	
;
}
