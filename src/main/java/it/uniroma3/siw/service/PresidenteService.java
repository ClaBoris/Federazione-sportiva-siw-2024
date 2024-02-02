package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.PresidenteRepository;
import jakarta.transaction.Transactional;

@Service
public class PresidenteService {
	
	@Autowired 
	private PresidenteRepository presidenteRepository;
	
	
	@Transactional
	public void save(Presidente presidente){
		presidenteRepository.save(presidente);
	}
	
	
//	@Transactional
//	public void rimuoviGiocatori(Long squadraId) {
//		// Ottieni la squadra
//		Squadra squadra= squadraRepository.findById(squadraId).orElse(null);
//
//		if (squadra != null) {
//			// Elimina i giocatori associati alla squadra
//			giocatoreRepository.eliminaDallaSquadraId(squadraId);
//
//			// Elimina la squadra
//			//squadraRepository.delete(squadra);
//		}
//	}
}
