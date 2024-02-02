package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Squadra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "nome_team")
	private String nome;
	
	@Column(name = "anno_fondazione")
	private int annoFondazione;
	
	@Column(name = "indirizzo")
	private String indirizzoSede;
	
	//@Column(name = "presidente")
	@OneToOne
	private Presidente presidente;
	
	@OneToMany(mappedBy = "squadra")
	//lista dei giocatori per cui la squadra è la squadra
	private List<Giocatore> giocatori;

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

	public int getAnnoFondazione() {
		return annoFondazione;
	}

	public void setAnnoFondazione(int annoFondazione) {
		this.annoFondazione = annoFondazione;
	}

	public String getIndirizzoSede() {
		return indirizzoSede;
	}

	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}

	public Presidente getPresidente() {
		return presidente;
	}

	public void setPresidente(Presidente presidente) {
		this.presidente = presidente;
	}

	public List<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(List<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoFondazione, giocatori, indirizzoSede, nome, presidente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return annoFondazione == other.annoFondazione && Objects.equals(giocatori, other.giocatori)
				&& Objects.equals(indirizzoSede, other.indirizzoSede) && Objects.equals(nome, other.nome)
				&& Objects.equals(presidente, other.presidente);
	}

	
}
