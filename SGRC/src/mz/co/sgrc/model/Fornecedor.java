package mz.co.sgrc.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Fornecedor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	
	private String designacao;
	private String endereco;
	private String email;
	private int telefone1;
	private int telefone2;
	private String descricao;
	
	
	@OneToMany(mappedBy="fornecedor", fetch=FetchType.LAZY)
	private List <Combustive> listCombustive;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesignacao() {
		return designacao;
	}
	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(int telefone1) {
		this.telefone1 = telefone1;
	}
	public int getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(int telefone2) {
		this.telefone2 = telefone2;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public String toString() {
		return  designacao;
	}

	
}
