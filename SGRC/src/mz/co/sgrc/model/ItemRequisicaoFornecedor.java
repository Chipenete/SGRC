package mz.co.sgrc.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

	@Entity
public class ItemRequisicaoFornecedor {

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		long id;
		private double quantidade;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="requisicaoFornecedor_id", insertable= true, updatable= true)
		private RequisicaoFornecedor requisicaoFornecedor;
		
		@OneToOne
		@JoinColumn(name="combustive_id")
		private Combustive combustive;
		
		private String combustivelString;
		
		private boolean remessada;
		
		public boolean isRemessada() {
			return remessada;
		}

		public void setRemessada(boolean remessada) {
			this.remessada = remessada;
		}

		public String getCombustivelString() {
			return combustivelString;
		}

		public void setCombustivelString(String combustivelString) {
			this.combustivelString = combustivelString;
		}

		public RequisicaoFornecedor getRequisicaoFornecedor() {
			return requisicaoFornecedor;
		}

		public void setRequisicaoFornecedor(RequisicaoFornecedor requisicaoFornecedor) {
			this.requisicaoFornecedor = requisicaoFornecedor;
		}

		public Combustive getCombustive() {
			return combustive;
		}

		public void setCombustive(Combustive combustive) {
			this.combustive = combustive;
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

		
}
