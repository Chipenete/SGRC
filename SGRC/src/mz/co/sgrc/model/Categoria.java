package mz.co.sgrc.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.zkoss.zk.ui.util.GenericForwardComposer;

@Entity
public class Categoria implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String nome;
	@OneToMany(mappedBy = "categoria")
	private List<Produto> produtoList;
	
	public Categoria (){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public List<Produto> getProdutoList() {
		return produtoList;
	}

	public void setProdutoList(List<Produto> produtoList) {
		this.produtoList = produtoList;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nome=" + nome + "]";
	}
	
	
}
