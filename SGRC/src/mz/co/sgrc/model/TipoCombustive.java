package mz.co.sgrc.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="tipoCombustive")
public class TipoCombustive {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long id;
	
	
	private String designacao;
	
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="combustive_id")
	private Combustive combustive;
	
	  @OneToMany(mappedBy="tipoCombustive", fetch = FetchType.LAZY)
    	private List <Cotas> listCotas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesignacao() {
		return designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public String getDescricao() {
		return descricao;
	}

	

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Combustive getCombustive() {
		return combustive;
	}

	public void setCombustive(Combustive combustive) {
		this.combustive = combustive;
	}
	
	
	
	public List<Cotas> getListCotas() {
		return listCotas;
	}

	public void setListCotas(List<Cotas> listCotas) {
		this.listCotas = listCotas;
	}

	@Override
	public String toString() {
		return  designacao ;
	}
	

}
