package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import jakarta.transaction.Transactional;


@Service
public class GiocatoreService {

	@Autowired
	GiocatoreRepository giocatoreRepository;

	@Autowired
	SquadraRepository squadraRepository;

	public List<Giocatore> getGiocatoriBySquadra(Long squadraId) {
		// TODO Auto-generated method stub
		return giocatoreRepository.findBySquadraId(squadraId);
	}

	public void save(Giocatore giocatore) {
		// TODO Auto-generated method stub
		giocatoreRepository.save(giocatore);
	}

	public void edit(Giocatore giocatore, Long giocatoreId) {
		// TODO Auto-generated method stub

		Giocatore giocatore_personale= this.giocatoreRepository.findById(giocatoreId).orElse(null);
		if(giocatore_personale!=null) {
			if (giocatore.getBirthDate() == null) {
				giocatore.setBirthDate(giocatore_personale.getBirthDate());
			}
		}
		giocatore_personale.setNome(giocatore.getNome());
		giocatore_personale.setCognome(giocatore.getCognome());
		giocatore_personale.setBirthDate(giocatore.getBirthDate());
		giocatore_personale.setRuolo(giocatore.getRuolo());
		giocatore_personale.setInizioTesseramento(giocatore.getInizioTesseramento());
		giocatore_personale.setFineTesseramento(giocatore.getFineTesseramento());
		giocatore_personale.setSquadra(giocatore.getSquadra());
		this.giocatoreRepository.save(giocatore_personale);
	}

	public Iterable<Giocatore> getAllGiocatori(){
		return giocatoreRepository.findAll();
	}
	
	@Transactional
	public boolean rimuoviGiocatore(Long giocatoreId) {
		if(giocatoreRepository.existsById(giocatoreId)) {
			giocatoreRepository.deleteById(giocatoreId);
			return true; //ho trovato il disegno
		}
		return false;
	}
	
	
}
