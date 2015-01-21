package br.com.jeebrasil.dominio;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Anota��o que informa que a classe mapeada � persistente
@Entity

//Informando nome e esquema da tabela mapeada
@Table(name="aluno", schema="anotacoes")

public class Aluno {
	
	//Defini��o da chave prim�ria
	@Id
	
	//Defini��o do mecanismo de defini��o da chave prim�ria
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	
	
	//Informa o nome da coluna mapeada para o atributo
	@Column(name="id_aluno")
	
	private int id;
	private int matricula;
	private String nome;
	private long cpf;
	
	Aluno(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}
	
	

}
