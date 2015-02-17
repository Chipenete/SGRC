package mz.co.sgrc.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;





	@Entity
	public class RequisicaoFornecedor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	
	private Date data;
	private String responsavel;
	private boolean remessada;
	private double custo;
	
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "orgao_id")
	private Orgao orgao;
	
	@OneToOne
    @JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;}
	
	public Orgao getOrgao() {
		return orgao;
	}
	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}
	@OneToMany(mappedBy="requisicaoFornecedor", fetch = FetchType.LAZY)
	private List <ItemRequisicaoFornecedor> listRequisicaoFornecedor;
	
	public boolean isRemessada() {
		return remessada;
	}
	public void setRemessada(boolean remessada) {
		this.remessada = remessada;
	}
	
	public List<ItemRequisicaoFornecedor> getListRequisicaoFornecedor() {
		return listRequisicaoFornecedor;
	}
	public void setListRequisicaoFornecedor(
			List<ItemRequisicaoFornecedor> listRequisicaoFornecedor) {
		this.listRequisicaoFornecedor = listRequisicaoFornecedor;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String descrição) {
		this.responsavel = descrição;
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
	public void setData(Date data) {
		this.data = data;
	}
	public double getCusto() {
		return custo;
	}
	public void setCusto(double custo) {
		this.custo = custo;
	}
	
	
	
}
