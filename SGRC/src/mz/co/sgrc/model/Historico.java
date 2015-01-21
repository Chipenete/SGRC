package mz.co.sgrc.model;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="historico")
public class Historico implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private double quantGasolina;
	
	private double quantGasoleo;
	
	private Date data;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getQuantGasolina() {
		return quantGasolina;
	}

	public void setQuantGasolina(double quantGasolina) {
		this.quantGasolina = quantGasolina;
	}

	public double getQuantGasoleo() {
		return quantGasoleo;
	}

	public void setQuantGasoleo(double quantGasoleo) {
		this.quantGasoleo = quantGasoleo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	

}
