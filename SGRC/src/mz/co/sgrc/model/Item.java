package mz.co.sgrc.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name="item")
public class Item implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String marca;
	
	private String matricula;
	
	private double custo;
	
	private double custoRemessado;
	
	// Atributos de apoio ao Requisicao do fornecedor
	private double quantidadeForn;
	
	private String combustivelString;
	///////////////////////////////////////////////////
	
	public double getQuantidadeForn() {
		return quantidadeForn;
	}


	public void setQuantidadeForn(double quantidadeForn) {
		this.quantidadeForn = quantidadeForn;
	}


	public String getCombustivelString() {
		return combustivelString;
	}


	public void setCombustivelString(String combustivelString) {
		this.combustivelString = combustivelString;
	}
	
	
	@ManyToOne
	@JoinColumn(name="combustive_id", insertable=true, updatable=true)
	private Combustive combustive;
    
	@ManyToOne
	@JoinColumn(name="viatura_id", insertable=true, updatable=true)
	private Viatura viatura;
	
    
    public Viatura getViatura() {
		return viatura;
	}


	public void setViatura(Viatura viatura) {
		this.viatura = viatura;
	}


	private double quantidade;



	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public Combustive getCombustive() {
		return combustive;
	}


	public void setCombustive(Combustive combustive) {
		this.combustive = combustive;
	}


	public double getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}


	public double getCusto() {
		return custo;
	}


	public void setCusto(double custo) {
		this.custo = custo;
	}


	public double getCustoRemessado() {
		return custoRemessado;
	}


	public void setCustoRemessado(double custoRemessado) {
		this.custoRemessado = custoRemessado;
	}
	
    
	
	
	

	
}
