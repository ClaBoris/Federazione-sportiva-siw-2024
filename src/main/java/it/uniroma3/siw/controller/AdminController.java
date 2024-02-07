package it.uniroma3.siw.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.PresidenteRepository;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.PresidenteService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.validator.PresidenteValidator;
import it.uniroma3.siw.validator.SquadraValidator;

@Controller
//@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	@Autowired
	private SquadraService squadraService;

	@Autowired
	private GiocatoreService giocatoreService;

	@Autowired
	private PresidenteService presidenteService;

	@Autowired
	private SquadraValidator squadraValidator;

	@Autowired
	private PresidenteValidator presidenteValidator;

	@Autowired
	private SquadraRepository squadraRepository;

	@Autowired
	private PresidenteRepository presidenteRepository;

	/*************ADMIN_HOME**************/	

	@GetMapping("/admin/admin_home")
	public String admin_home() {
		return "/admin/admin_home.html";
	}

	/*************METODI PER LE SQUADRE**************/	
	@GetMapping("/admin/formNewSquadra")
	public String formNewSquadra(Model model){
		model.addAttribute("squadra",new Squadra());
		return "/admin/formNewSquadra.html";
	}

	@PostMapping("/admin/squadre")
	public String salvaSquadra(@ModelAttribute Squadra squadra) {
		// Validazione e conversione dei dati possono essere eseguite automaticamente
		// grazie agli attributi dell'entità e alle annotazioni di validazione

		squadraRepository.save(squadra);
		return "admin_home.html";
	}

	@GetMapping("/admin/elenco_squadre")
	public String elenco_squadre(Model model) {
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "/admin/squadre.html";
	}

	@PostMapping("/admin/newSquadra")
	public String newSquadra(@ModelAttribute("squadra") Squadra squadra,BindingResult bindingResult, Model model){
		squadraValidator.validate(squadra, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.squadraService.save(squadra);
			model.addAttribute("squadre", this.squadraRepository.findAll());
			return "/admin/admin_home.html";
		}else{
			if(bindingResult.hasErrors()) {
				model.addAttribute("formNewSquadraNome", "Nome squadra già in uso");
			}
		}
		return "/admin/formNewSquadra.html";

	}

	@GetMapping("/admin/rimuoviSquadraEGiocatori/{squadraId}")
	public String rimuoviSquadraEGiocatori(@PathVariable Long squadraId, Model model) {
		// Codice per eliminare la squdra e i suoi giocatori
		squadraService.rimuoviSquadraEGiocatori(squadraId);
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "/admin/squadre.html";
	}

	@GetMapping("/admin/formModificaSquadra/{id}")
	public String formModificaSquadra(Model model, @PathVariable("id") Long squadraId){
		model.addAttribute("squadra", this.squadraRepository.findById(squadraId).get());
		model.addAttribute("presidenti", this.presidenteRepository.findAll());
		return "/admin/formModificaSquadra.html";
	}

	@GetMapping("/admin/squadra/{squadraId}/giocatori")
	public String getGiocatoriDaSquadra(@PathVariable Long squadraId, Model model) {

		if (squadraRepository.existsById(squadraId)) {
			// Ottieni i giocatori della squadra
			List<Giocatore> giocatori = giocatoreService.getGiocatoriBySquadra(squadraId);

			model.addAttribute("squadraId", squadraId);
			model.addAttribute("giocatori", giocatori);

			// Restituisci il nome della vista HTML
			return "/admin/squadra_e_giocatori.html";
		} else {
			// Gestisci il caso in cui l'artista non esiste, ad esempio reindirizzando a una pagina di errore
			return "/admin/squadre.html";
		}
	}



	@PostMapping("/admin/newSquadra/{id}")
	public String modificaSquadra(@ModelAttribute("squadra") Squadra squadra,@PathVariable("id") Long squadraId,  Model model){
		this.squadraService.edit(squadra, squadraId);
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "/admin/admin_home.html";
	}

	/*************METODI PER  I GIOCATORI**************/	

	@GetMapping("/admin/giocatori")
	public String elenco_giocatori(Model model) {
		model.addAttribute("giocatori", this.squadraRepository.findAll());
		return "/admin/giocatori.html";
	}



	/*************L'ADMIN PUO' AGGIUNGERE PRESIDENTI**************/	


	@GetMapping("/admin/formNewPresidente")
	public String formNewPresidente(Model model){
		model.addAttribute("presidente",new Presidente());
		return "/admin/formNewPresidente.html";
	}

	@PostMapping("/admin/newPresidente")
	public String newPresidente(@ModelAttribute("presidente") Presidente presidente,BindingResult bindingResult, Model model){
		presidenteValidator.validate(presidente, bindingResult);
		if(!bindingResult.hasErrors()) {
			presidente.setRuolo(Credentials.PRESIDENT_ROLE);
			this.presidenteService.save(presidente);
			model.addAttribute("presidenti", this.presidenteRepository.findAll());
			return "/admin/presidenti.html";
		}else {
			return "/admin/formNewPresidente.html";
		}
	}

	@GetMapping("admin/presidenti")
	public String elenco_presidenti(Model model) {
		model.addAttribute("presidenti", this.presidenteRepository.findAll());
		return "/admin/presidenti.html";
	}

	/***ORDINAMENTO CRESCENTE DI NOME DEI PRESIDENTI***/
	//	@GetMapping("admin/presidenti")
	//	public String elenco_presidenti(Model model) {
	//		model.addAttribute("presidenti", this.presidenteRepository.findAllWithSquadra());
	//		return "/admin/presidenti.html";
	//	}











}
