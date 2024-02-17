package it.uniroma3.siw.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Giocatore;
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

	@Autowired
	private GiocatoreService giocatoreService;

	@Transactional
	public void save(Squadra squadra){
		squadraRepository.save(squadra);
	}

	@Transactional
	public void rimuoviSquadra(Long squadraId) {
		// Ottieni la squadra

		Squadra squadra= squadraRepository.findById(squadraId).orElse(null);

		if (squadra != null) {
			// Elimina i giocatori associati alla squadra
			List<Giocatore> giocatori = this.giocatoreRepository.findBySquadraId(squadraId);
			for(Giocatore g : giocatori) {
				g.setSquadra(null);
				g.setInizioTesseramento(null);
				g.setFineTesseramento(null);
				this.giocatoreService.save(g);
			}

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
		squadra_personale.setIndirizzoSede(squadra.getIndirizzoSede()); //levo questo set così almeno evito di non avere l'indirizzo
		squadra_personale.setPresidente(squadra.getPresidente());
		this.squadraRepository.save(squadra_personale);
	}
}
