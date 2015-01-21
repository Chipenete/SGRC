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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="cotas")
public class Cotas implements Serializable{
	

	private static final long serialVersionUID = 1L;


		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private long id;
		
		
		private double quantidade;
		
	    @ManyToOne
		@JoinColumn(name="tipoCombustivel_id")
		private TipoCombustive tipoCombustive;
		
		@Temporal(TemporalType.DATE)
		private Date data;
		
		
		/*@OneToMany(mappedBy = "cotas" , fetch = FetchType.LAZY)
		private List <Orgao_has_Cota> listOrgaoHasCota;*/


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


		public double getQuantidade() {
			return quantidade;
		}


		public void setQuantidade(double quantidade) {
			this.quantidade = quantidade;
		}


		public TipoCombustive getTipoCombustive() {
			return tipoCombustive;
		}


		public void setTipoCombustive(TipoCombustive tipoCombustive) {
			this.tipoCombustive = tipoCombustive;
		}
		
	}



