package it.uniroma3.siw.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import jakarta.transaction.Transactional;

@Service
public class SquadraService {

	@Autowired
	private GiocatoreRepository giocatoreRepository;
	@Autowired 
	private SquadraRepository squadraRepository;
	
	@Transactional
	public void save(Squadra squadra){
		squadraRepository.save(squadra);
	}
	
	@Transactional
	public void rimuoviSquadraEGiocatori(Long squadraId) {
		// Ottieni la squadra
		Squadra squadra= squadraRepository.findById(squadraId).orElse(null);

		if (squadra != null) {
			// Elimina i giocatori associati alla squadra
			giocatoreRepository.eliminaDallaSquadraId(squadraId);

			// Elimina la squadra
			squadraRepository.delete(squadra);
		}
	}

	// Metodo per recuperare una squadra dal database dato il suo ID
    public Squadra getSquadraById(Long squadraId) {
        Optional<Squadra> squadraOptional = squadraRepository.findById(squadraId);
        return squadraOptional.orElse(null);
    }
	public void edit(Squadra squadra, Long squadraId) {
		// TODO Auto-generated method stub
		Squadra squadra_personale= this.squadraRepository.findById(squadraId).orElse(null);
		squadra_personale.setNome(squadra.getNome());
		squadra_personale.setAnnoFondazione(squadra.getAnnoFondazione());
		squadra_personale.setIndirizzoSede(squadra.getIndirizzoSede());
		squadra_personale.setPresidente(squadra.getPresidente());
		this.squadraRepository.save(squadra_personale);
	}
}
