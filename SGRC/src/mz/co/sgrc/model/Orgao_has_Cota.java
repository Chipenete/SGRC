package mz.co.sgrc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="orgao_has_cota")
public class Orgao_has_Cota implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="org_id")
	private Orgao orgao;
	
	@ManyToOne
	@JoinColumn(name="cota_id")
	private Cotas cotas;


	@Temporal(TemporalType.DATE)
	private Date data;


	public Orgao getOrgao() {
		return orgao;
	}


	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}


	public Cotas getCotas() {
		return cotas;
	}


	public void setCotas(Cotas cotas) {
		this.cotas = cotas;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	
	

	
    
	
	
	
}
