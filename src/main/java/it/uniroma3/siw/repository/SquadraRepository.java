package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;


@Repository
public interface SquadraRepository extends CrudRepository<Squadra, Long> {
	@Query(value = "SELECT * FROM squadra order by id desc limit :limit", nativeQuery = true)
	public List<Squadra> findTopN(@Param("limit") int limit);
	
	/***ORDINAMENTO CRESCENTE DI NOME DELLE SQUADRE***/	
//	@Query("SELECT s FROM Squadra sorder by p.nome asc")
//	List<Squadra> findAllInNameOrderASC();
	
	public boolean existsByNome(String nome);

	@Query(value = "SELECT s FROM Squadra s WHERE s.presidente = :presidente")
	public Squadra findSquadra(@Param("presidente")Presidente presidente);

}
