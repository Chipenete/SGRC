package br.com.jeebrasil.dominio;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity 

@Table (name="cliente", schema="anotacoes")

public class Cliente {
	
	
	@Transient 
	private int temporaria;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id_cliente")
	private int id;
	
	
	@Column(unique=true, nullable=false, insertable=true, updatable=true)
	private long cpf;
	
	
	@Column(nullable=false, length=40, insertable=true, updatable=true)
	private String nome;
	
	
	@Column(length=100)
	private String endereco;
	
	
	@Column(name="total_compras", precision=2)
	private double totalCompras;
	
	
	@Column(name="data_nascimento", nullable=true)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	
	@Column(name="data_cadastro", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	
	public Cliente(){}


	public int getTemporaria() {
		return temporaria;
	}


	public void setTemporaria(int temporaria) {
		this.temporaria = temporaria;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public long getCpf() {
		return cpf;
	}


	public void setCpf(long cpf) {
		this.cpf = cpf;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public double getTotalCompras() {
		return totalCompras;
	}


	public void setTotalCompras(double totalCompras) {
		this.totalCompras = totalCompras;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(Date i) {
		this.dataNascimento = i;
	}


	public Date getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(Date i) {
		this.dataCadastro = i;
	}
	
	
}
