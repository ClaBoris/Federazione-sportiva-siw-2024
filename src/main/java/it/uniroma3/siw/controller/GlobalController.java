package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.PresidenteRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.CredentialsValidator;
import it.uniroma3.siw.validator.UserValidator;
import jakarta.validation.Valid;

@Controller
public class GlobalController {

	@Autowired
	private SquadraRepository squadraRepository ;

	@Autowired
	private PresidenteRepository presidenteRepository ;

	@Autowired
	private GiocatoreRepository giocatoreRepository ;

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	/*************GENERALI**************/	

	@GetMapping("/")
	public String index(Model model) {
		return "index.html";
	}



	//RITORNA LA PAGINA CON LE CARD CON TUTTE LE SQUADRE
	@GetMapping("/squadre")
	public String squadre(Model model){
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "/admin/admin_home.html";
	}



	/*************ACCESSO**************/	

	//	In breve, questo metodo gestisce le richieste GET all'URL "/success" in base al ruolo dell'utente autenticato, 
	//	aggiungendo attributi al modello e reindirizzando l'utente a pagine specifiche in base al ruolo.
	@GetMapping("/success")
	public String index2(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//viene utilizzato per ottenere l'oggetto di autenticazione corrente
		UserDetails userDetails = null;
		Credentials credentials = null;
		if(!(authentication instanceof AnonymousAuthenticationToken)){//Si verifica se l'autenticazione corrente non è un'istanza di AnonymousAuthenticationToken
			userDetails = (UserDetails)authentication.getPrincipal();//Se è così, ottiene i dettagli dell'utente corrente tramite authentication.getPrincipal(), assumendo che il principal sia un'istanza di UserDetails.
			credentials = this.credentialsService.getCredentials(userDetails.getUsername());//Usa il servizio credentialsService per ottenere le credenziali dell'utente corrente tramite getCredentials(userDetails.getUsername()).
		}
		if(credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) {//Se le credenziali sono presenti e il ruolo è "ADMIN" 
			model.addAttribute("squadre", this.squadraRepository.findAll());//viene aggiunta l'attributo "squadre" al modello, ottenuto tramite squadraRepository.findAll(), 
			return "/admin/admin_home.html";//e restituisce la vista "/admin/admin_home.html".
		}
		if(credentials != null && credentials.getRole().equals(Credentials.PRESIDENT_ROLE)) {

			Presidente pres = this.presidenteRepository.findPresidente(userDetails.getUsername());
			if(pres!=null) {
				model.addAttribute("squadre", this.squadraRepository.findAll());	
				return "/president/president_home.html";
			}

		}
		/*model.addAttribute("userDetails", userDetails);*/
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "home.html";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult userBindingResult, @Valid @ModelAttribute("credentials") Credentials credentials,
			BindingResult credentialsBindingResult, Model model) {
		this.userValidator.validate(user,userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);                        
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			userService.saveUser(user);
			model.addAttribute("user", user);
			return "formLogin.html";
		}else {
			if(userBindingResult.hasErrors()){
				model.addAttribute("registrationErrorUser", "Mail già in uso");
			}
			model.addAttribute("registrationError", "Username già in uso");
		}
		return "formRegister.html";
	}

	//OK
	@GetMapping(value = "/login")
	public String showLoginForm (Model model) {
		return "formLogin.html";
	}

	@GetMapping(value = "/register")
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegister.html";
	}


	/*************METODI USER PER LE PAGINE**************/	

	@GetMapping("/presidenti")
	public String show_elenco_presidenti(Model model) {
		model.addAttribute("presidenti", this.presidenteRepository.findAll());
		return "elenco_presidenti.html";
	}


	@GetMapping("/user_home")
	public String show_user_home(Model model) {
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "home.html";
	}

	@GetMapping("/giocatori")
	public String show_elenco_giocatori(Model model) {
		model.addAttribute("giocatori", this.giocatoreRepository.findAll());
		return "elenco_giocatori.html";
	}


	/***ORDINAMENTO CRESCENTE DI NOME DEI PRESIDENTI***/		
	//	@GetMapping("/presidenti")
	//	public String show_elenco_presidenti(Model model) {
	//		model.addAttribute("presidenti", this.presidenteRepository.findAllInNameOrderASC());
	//		return "elenco_presidenti.html";
	//	}

}
