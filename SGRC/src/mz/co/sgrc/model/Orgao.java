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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;





@Entity
@Table(name = "orgao")
public class Orgao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String designacao;
	private long cota_id;
	
	private double quantidadeGasolina;
	private double cotaGasolina;
	
	private double quantidadeGasoleo;
	private double cotaGasoleo;
	
	private double quantidadeGas;
	private double cotaGas;


	@OneToMany(mappedBy="orgao", fetch = FetchType.LAZY)
	private List <Requisicao> listRequisicao;
	
	@OneToMany(mappedBy="orgao", fetch=FetchType.LAZY)
	private List <Viatura> listViatura;
	
	@OneToMany(mappedBy="orgao", fetch=FetchType.LAZY)
	private List <Orgao_has_Cota> listOrgaoHasCota;
	
	
	
	
	@OneToMany(mappedBy = "orgao", fetch = FetchType.LAZY)
	private List <Utilizador> listUtilizadores;
	
	
	/*@OneToMany(mappedBy = "orgao", fetch = FetchType.EAGER)
	private List <Cotas> ListCotas;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cota_id", insertable=true, updatable=true)
	private Cotas cotas;*/

	/*@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="Orgao_has_Cota", schema="vendas",
	joinColumns=@JoinColumn(name="id_Orgao"),
	inverseJoinColumns=@JoinColumn(name="id_Cotas"))
	private List <Cotas> listcotas;*/
	
	
	public List<Viatura> getListViatura() {
		return listViatura;
	}


	public void setListViatura(List<Viatura> listViatura) {
		this.listViatura = listViatura;
	}


	public List<Utilizador> getListUtilizadores() {
		return listUtilizadores;
	}


	public void setListUtilizadores(List<Utilizador> listUtilizadores) {
		this.listUtilizadores = listUtilizadores;
	}





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


	public List<Requisicao> getListRequisicao() {
		return listRequisicao;
	}


	public void setListRequisicao(List<Requisicao> listRequisicao) {
		this.listRequisicao = listRequisicao;
	}


	public long getCota_id() {
		return cota_id;
	}


	public void setCota_id(long cota_id) {
		this.cota_id = cota_id;
	}


	public double getQuantidadeGasolina() {
		return quantidadeGasolina;
	}


	public void setQuantidadeGasolina(double quantidadeGasolina) {
		this.quantidadeGasolina = quantidadeGasolina;
	}


	public double getQuantidadeGasoleo() {
		return quantidadeGasoleo;
	}


	public void setQuantidadeGasoleo(double quantidadeGasoleo) {
		this.quantidadeGasoleo = quantidadeGasoleo;
	}


	public List<Orgao_has_Cota> getListOrgaoHasCota() {
		return listOrgaoHasCota;
	}


	public void setListOrgaoHasCota(List<Orgao_has_Cota> listOrgaoHasCota) {
		this.listOrgaoHasCota = listOrgaoHasCota;
	}


	public double getQuantidadeGas() {
		return quantidadeGas;
	}


	public void setQuantidadeGas(double quantidadeGas) {
		this.quantidadeGas = quantidadeGas;
	}


	public double getCotaGasolina() {
		return cotaGasolina;
	}


	public void setCotaGasolina(double cotaGasolina) {
		this.cotaGasolina = cotaGasolina;
	}


	public double getCotaGasoleo() {
		return cotaGasoleo;
	}


	public void setCotaGasoleo(double cotaGasoleo) {
		this.cotaGasoleo = cotaGasoleo;
	}


	public double getCotaGas() {
		return cotaGas;
	}


	public void setCotaGas(double cotaGas) {
		this.cotaGas = cotaGas;
	}


	
}
