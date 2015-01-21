package mz.co.sgrc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mz.co.sgrc.model.Produto;

public class ProdutoDao extends GenericDAO<Produto>{

	public ProdutoDao() {
		super(Produto.class);	
	}
	
	public List<Produto> pesquisaProdutos (String nome){
		
			Session sess = getSession();
			Transaction tx =sess.beginTransaction();
			Criteria criteria = sess.createCriteria(_clazz);
			criteria.add(Restrictions.like("nome", "%"+nome+"%"));
			List<Produto> items = criteria.list();
			tx.commit();
			return items;
	}
}
