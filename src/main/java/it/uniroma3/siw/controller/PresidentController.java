package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.GiocatoreRepository;
import it.uniroma3.siw.repository.PresidenteRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.service.GiocatoreService;

import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.GiocatoreValidator;

@Controller
public class PresidentController {
	@Autowired
	private SquadraRepository squadraRepository ;

	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GiocatoreRepository giocatoreRepository;

	@Autowired
	private PresidenteRepository presidenteRepository ;
	
	@Autowired
	private GiocatoreValidator giocatoreValidator;

	/*******METODI PER MOSTRARE PAGINE******/
	
	@GetMapping("/president/president_home")
	public String show_president_home(Model model) {
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "/president/president_home.html";
	}
	
	@GetMapping("/president/elenco_squadre")
	public String elenco_squadre(Model model) {
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "/president/elenco_squadre.html";
	}	
	
	@GetMapping("/president/squadra_personale")
	public String squadra_personale(Model model) {
		UserDetails userDetails = this.userService.getUserDetails();
		String username = userDetails.getUsername();
		Presidente presidente = this.presidenteRepository.findPresidente(username);
		Squadra squadra = this.squadraRepository.findSquadra(presidente);
		model.addAttribute("squadra", squadra);
		return "/president/squadra_personale.html";
	}	
	
	/***ORDINAMENTO CRESCENTE DI NOME DELLE SQUADRE***/	
//	@GetMapping("/president/elenco_squadre")
//	public String elenco_squadre(Model model) {
//		model.addAttribute("squadre", this.squadraRepository.findAllInNameOrderASC());
//		return "/president/elenco_squadre.html";
//	}	
	
	@GetMapping("/president/giocatori")
	public String elenco_giocatori(Model model) {
		model.addAttribute("giocatori", this.giocatoreRepository.findAll());
		return "/president/giocatori.html";
	}
	
	@GetMapping("/president/formNewGiocatore")
	public String formNewGiocatore(Model model){
		model.addAttribute("giocatore",new Giocatore());
		return "/president/formNewGiocatore.html";
	}
	

	@GetMapping("/president/squadra/{squadraId}/giocatori")
	public String getGiocatoriDaSquadra(@PathVariable Long squadraId, Model model) {
		
		if (squadraRepository.existsById(squadraId)) {
			// Ottieni i giocatori della squadra
			List<Giocatore> giocatori = giocatoreService.getGiocatoriBySquadra(squadraId);

			model.addAttribute("squadraId", squadraId);
			model.addAttribute("giocatori", giocatori);

			// Restituisci il nome della vista HTML
			return "/president/giocatori_della_squadra.html";
		} else {
			// Gestisci il caso in cui l'artista non esiste, ad esempio reindirizzando a una pagina di errore
			return "/president/elenco_squadre.html";
		}
	}
	
	//nella pagina giocatori clicco su modifica e si apre formModificaGiocatore.html per modificare i giocatori
		@GetMapping("/president/formModificaGiocatore/{id}")
		public String formModificaGiocatore(Model model, @PathVariable("id") Long giocatoreId){
			model.addAttribute("giocatore", this.giocatoreRepository.findById(giocatoreId).get());
			model.addAttribute("squadre", this.squadraRepository.findAll());
			return "/president/formModificaGiocatore.html";
		}
		
	
	/*******GESTIONE GIOCATORI******/
	
	
	
	//dopo che aggiungo il giocatore in formNewGiocatore, se non esiste già, ritorna giocatori.html
	@PostMapping("/president/newGiocatore")
	public String newGiocatore(@ModelAttribute("presidente") Giocatore giocatore,BindingResult bindingResult, Model model){
		giocatoreValidator.validate(giocatore, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.giocatoreService.save(giocatore);
			model.addAttribute("giocatori", this.giocatoreRepository.findAll());
			return "/president/giocatori.html";
		}else {
			return "/president/formNewGiocatore.html";
		}
	}
	
	
	@PostMapping("/president/newGiocatore/{id}")
	public String modificaGiocatore(@ModelAttribute("giocatore") Giocatore giocatore,@PathVariable("id") Long giocatoreId,  Model model){
		this.giocatoreService.edit(giocatore, giocatoreId);
		model.addAttribute("giocatori", this.giocatoreRepository.findAll());
		return "/president/giocatori_con_squadra.html";
	}	
	
	
	@GetMapping("/president/rimuoviGiocatore/{giocatoreId}")
	public String rimuoviGiocatore (@PathVariable("giocatoreId") Long giocatoreId, Model model) {
			giocatoreService.rimuoviGiocatore(giocatoreId);
			model.addAttribute("giocatori", giocatoreService.getAllGiocatori());
			return "/president/giocatori_della_squadra.html";
	}
	
}
