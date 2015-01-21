package mz.co.sgrc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mz.co.sgrc.model.Combustive;
import mz.co.sgrc.model.Fornecedor;
import mz.co.sgrc.model.Produto;

public class FornecedorDao extends GenericDAO<Fornecedor> {
	
	
	public FornecedorDao(){
		
		super(Fornecedor.class);
		
		
	}
	

	public List <Fornecedor> pesquisaFornecedor(String designacao){
		
			Session sess = getSession();
			Transaction tx =sess.beginTransaction();
			Criteria criteria = sess.createCriteria(_clazz);
			criteria.add(Restrictions.like("nome", "%"+designacao+"%"));
			List<Fornecedor> items = criteria.list();
			tx.commit();
			return items;
	}
}
