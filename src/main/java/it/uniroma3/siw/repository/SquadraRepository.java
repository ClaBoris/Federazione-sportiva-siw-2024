package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Squadra;


@Repository
public interface SquadraRepository extends CrudRepository<Squadra, Long> {
	@Query(value = "SELECT * FROM squadra order by id desc limit :limit", nativeQuery = true)
	public List<Squadra> findTopN(@Param("limit") int limit);
	
	public boolean existsByNome(String nome);

}
