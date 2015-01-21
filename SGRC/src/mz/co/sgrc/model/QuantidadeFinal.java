package mz.co.sgrc.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="quantidadefinal")
public class QuantidadeFinal implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private double quantidadeGasolina;
	
	private double quantidadeGasoleo;
	
	private double quantidadeGas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getQuantidadeGasolina() {
		return quantidadeGasolina;
	}

	public void setQuantidadeGasolina(double quantidadeGasolina) {
		this.quantidadeGasolina = quantidadeGasolina;
	}

	public double getQuantidadeGasoleo() {
		return quantidadeGasoleo;
	}

	public void setQuantidadeGasoleo(double quantidadeGasoleo) {
		this.quantidadeGasoleo = quantidadeGasoleo;
	}

	public double getQuantidadeGas() {
		return quantidadeGas;
	}

	public void setQuantidadeGas(double quantidadeGas) {
		this.quantidadeGas = quantidadeGas;
	}
	
	
}
