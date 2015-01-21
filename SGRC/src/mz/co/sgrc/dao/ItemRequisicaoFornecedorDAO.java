package mz.co.sgrc.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.sgrc.model.ItemRequisicaoFornecedor;
import mz.co.sgrc.model.RequisicaoFornecedor;

public class ItemRequisicaoFornecedorDAO extends
		GenericDAO<ItemRequisicaoFornecedor> {

	public ItemRequisicaoFornecedorDAO() {
		super(ItemRequisicaoFornecedor.class);

	}

	public List<ItemRequisicaoFornecedor> listaRequisicaoFornecedor(
			RequisicaoFornecedor rq) {
		Session session = super.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from ItemRequisicaoFornecedor i where i.requisicaoFornecedor=:rq1");
		query.setParameter("rq1", rq);
		List<ItemRequisicaoFornecedor> reqs = (List<ItemRequisicaoFornecedor>) query
				.list();
		tx.commit();

		return reqs;
	}

}
