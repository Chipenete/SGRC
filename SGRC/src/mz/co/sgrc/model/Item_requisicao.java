package mz.co.sgrc.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="item_requisicao")
public class Item_requisicao implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private double quantidade_requisitada;
	
	private double quantidade_remessada;
	
	private double custo;
	
	private double custoRemessado;
	
	@ManyToOne
	@JoinColumn(name="viatura", insertable=true, updatable=true)
	private Viatura viatura;
	
	@OneToOne
	@JoinColumn(name="combustive_id")
	private Combustive combustive;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="requisicao_id", insertable=true, updatable=true)
	private Requisicao requisicao;
	

	private boolean remessada;

	public boolean getRemessada() {
		return remessada;
	}

	public void setRemessada(boolean remessada) {
		this.remessada = remessada;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public double getQuantidade_requisitada() {
		return quantidade_requisitada;
	}

	public void setQuantidade_requisitada(double quantidade_requisitada) {
		this.quantidade_requisitada = quantidade_requisitada;
	}

	public double getQuantidade_remessada() {
		return quantidade_remessada;
	}

	public void setQuantidade_remessada(double quantidade_remessada) {
		this.quantidade_remessada = quantidade_remessada;
	}

	public Viatura getViatura() {
		return viatura;
	}

	public void setViatura(Viatura viatura) {
		this.viatura = viatura;
	}

	public Combustive getCombustive() {
		return combustive;
	}

	public void setCombustive(Combustive combustive) {
		this.combustive = combustive;
	}

	public Requisicao getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}

	/*@OneToOne
	@JoinColumn(name="item_remessa")
	private Item_remessa Item_remessa;*/
	private String combustivelString;
	
	/*public Item_remessa getItem_remessa() {
		return Item_remessa;
	}

	public void setItem_remessa(Item_remessa item_remessa) {
		Item_remessa = item_remessa;
	}*/
	

	public String getCombustivelString() {
		return combustivelString;
	}

	public void setCombustivelString(String combustivelString) {
		this.combustivelString = combustivelString;
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

	@Override
	public String toString() {
		return "Item_requisicao [combustivelString=" + combustivelString + "]";
	}

	
	
	
}