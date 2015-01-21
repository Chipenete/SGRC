package mz.co.sgrc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Produto implements Serializable{
	@Column (name="nome")
	private String nome;
	
	
	@JoinColumn(name = "id_categoria", referencedColumnName = "id")
   
	
	@ManyToOne
    private Categoria categoria;
	@Id
	private int cod;
	private int stock;
	private double precoUnitario;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
	
	
	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", categoria=" + categoria + ", cod="
				+ cod + ", stock=" + stock + ", precoUnitario=" + precoUnitario
				+ "]";
	}
	
	
	

}
