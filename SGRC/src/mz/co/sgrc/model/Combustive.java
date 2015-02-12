package mz.co.sgrc.model;

import java.io.Serializable;







import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;



@Entity
@Table(name="combustive")
public class Combustive implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	

    @OneToOne(mappedBy="combustive")
    @Cascade(CascadeType.ALL)
    private TipoCombustive tipoCombustive;

    @OneToMany(mappedBy="combustive", fetch = FetchType.EAGER)
	private List <Item> listItem;
	
    @ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor1;
    
	public Fornecedor getFornecedor1() {
		return fornecedor1;
	}

	public void setFornecedor1(Fornecedor fornecedor1) {
		this.fornecedor1 = fornecedor1;
	}

	private double quantidade;
	
	private String tcA;
	
	private Date data;
	
	private String fornecedor;
	
	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	
	public Date getData() {
		return data;
	}

	public void setData(Date data2) {
		this.data = data2;
	}

	public String getTcA() {
		return tcA;
	}

	public void setTcA(String tcA) {
		this.tcA = tcA;
	}

	public Combustive(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TipoCombustive getTipoCombustive() {
		return tipoCombustive;
	}

	public void setTipoCombustive(TipoCombustive tipoCombustive) {
		this.tipoCombustive = tipoCombustive;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double double1) {
		this.quantidade = double1;
	}

	
	

}
