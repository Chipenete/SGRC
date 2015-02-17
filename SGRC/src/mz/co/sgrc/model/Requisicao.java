package mz.co.sgrc.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "requisicao")
public class Requisicao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private double custo;
	
	private double custoRemessado;

	private double total;

	@Temporal(TemporalType.DATE)
	private Date data;



	@ManyToOne
	@JoinColumn(name = "org_id")
	private Orgao orgao;
	
	
	private String responsavel;
	
	private boolean remessada;
	

	@OneToMany(mappedBy="requisicao", fetch = FetchType.LAZY)
	private List <Item_requisicao> listRequisicao;

	
	public List<Item_requisicao> getListRequisicao() {
		return listRequisicao;
	}

	public void setListRequisicao(List<Item_requisicao> listRequisicao) {
		this.listRequisicao = listRequisicao;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public Date getData() {
		return data;
	}

	public void setData(Date datas) {
		this.data = datas;
	}

	public Orgao getOrgao() {
		return orgao;
	}

	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}

	public boolean getRemessada() {
		return remessada;
	}

	public void setRemessada(boolean remessada) {
		this.remessada = remessada;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getCustoRemessado() {
		return custoRemessado;
	}

	public void setCustoRemessado(double custoRemessado) {
		this.custoRemessado = custoRemessado;
	}
	
	

	
	

}
