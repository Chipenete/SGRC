package mz.co.sgrc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;




import mz.co.sgrc.model.Requisicao;
import mz.co.sgrc.model.RequisicaoFornecedor;

public class RequisicaoFornecedorDAO extends GenericDAO<RequisicaoFornecedor> {

	public RequisicaoFornecedorDAO() {
		super(RequisicaoFornecedor.class);
		
	}
	public List<RequisicaoFornecedor> pesquisaRequisicaoFornecedor (String nome){
		
		Session sess = getSession();
		Transaction tx =sess.beginTransaction();
		Criteria criteria = sess.createCriteria(_clazz);
		criteria.add(Restrictions.like("nome", "%"+nome+"%"));
		List<RequisicaoFornecedor> items = criteria.list();
		tx.commit();
		return items;
}
	
	

	public RequisicaoFornecedor returnar(RequisicaoFornecedor requisicao){
		RequisicaoFornecedor req = null;
		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
	
		String hql = "FROM RequisicaoFornecedor req JOIN FETCH req.listRequisicaoFornecedor item_req where req=:requisicao";
		
		org.hibernate.Query query = sess.createQuery(hql);
		query.setParameter("requisicao", requisicao);
		req = (RequisicaoFornecedor)query.uniqueResult();
		tx.commit();

	   return req;
	}
	
}
