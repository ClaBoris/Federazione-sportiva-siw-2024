package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Giocatore;

@Repository
public interface GiocatoreRepository extends CrudRepository<Giocatore,Long>{
	 boolean existsByNome(String nome);
	
	@Query(value = "SELECT * FROM giocatore order by id desc limit :limit", nativeQuery = true)
	public List<Giocatore> findTopN(@Param("limit") int limit);
	
	List<Giocatore> findBySquadraId(Long squadraId);
	@Modifying
	@Query("DELETE FROM Giocatore g WHERE g.squadra.id = :squadraId")
	void eliminaDallaSquadraId(Long squadraId);

}
