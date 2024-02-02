package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.repository.PresidenteRepository;

@Component
public class PresidenteValidator implements Validator  {

	@Autowired
    private PresidenteRepository presidenteRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Presidente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		  Presidente presidente= (Presidente) target;
	        if(presidente.getNome() != null
	            && this.presidenteRepository.existsByNome(presidente.getNome())){
	            errors.rejectValue("nome", "presidente.duplicate", "Il nome del presidente è già in uso.");
	        }
		
	}

}
