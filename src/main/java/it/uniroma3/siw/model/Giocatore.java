package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Giocatore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "nome_giocatore")
	private String nome;
	@Column(name = "cognome_giocatore")
	private String cognome;
	@Column(name = "ruolo")
	private String ruolo;
	@Column(name = "data_nascita")
	private LocalDate birthDate;
	@Column(name = "inizio_tex")
	private LocalDate inizioTesseramento;
	@Column(name = "fine_tex")
	private LocalDate fineTesseramento;

	@ManyToOne
	private Squadra squadra;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setInizioTesseramento(LocalDate inizioTesseramento) {
		this.inizioTesseramento = inizioTesseramento;
	}

	public void setFineTesseramento(LocalDate fineTesseramento) {
		this.fineTesseramento = fineTesseramento;
	}
	
	public LocalDate getInizioTesseramento() {
		return inizioTesseramento;
	}
	
	public LocalDate getFineTesseramento() {
		return fineTesseramento;
	}
	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, id, nome, cognome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(cognome, other.cognome);
	}

}
