package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;


@Component
public class SquadraValidator implements Validator{

	@Autowired
    private SquadraRepository squadraRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return Squadra.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		  Squadra squadra= (Squadra) target;
	        if(squadra.getNome() != null
	            && this.squadraRepository.existsByNome(squadra.getNome())){
	            errors.reject("squadra.duplicate");
	        }
	}

}
