package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Giocatore;

import it.uniroma3.siw.repository.GiocatoreRepository;


@Component
public class GiocatoreValidator implements Validator{
	@Autowired
    private GiocatoreRepository giocatoreRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Giocatore.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		  Giocatore giocatore= (Giocatore) target;
	        if(giocatore.getNome() != null
	            && this.giocatoreRepository.existsByNome(giocatore.getNome())){
	            errors.rejectValue("nome", "giocatore.duplicate", "Il nome del giocatore è già in uso.");
	        }
		
	}

}
