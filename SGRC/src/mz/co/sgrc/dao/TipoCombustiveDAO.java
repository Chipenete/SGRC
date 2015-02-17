package mz.co.sgrc.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.sgrc.model.Requisicao;
import mz.co.sgrc.model.TipoCombustive;

public class TipoCombustiveDAO extends GenericDAO <TipoCombustive>{
	
	
	public TipoCombustiveDAO(){
		super(TipoCombustive.class);
	}

	public TipoCombustive returnar(String nome){
		TipoCombustive tc = null;
		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
	
		String hql = "FROM TipoCombustive tc  where designacao=:nome";
		
		org.hibernate.Query query = sess.createQuery(hql);
		query.setParameter("nome", nome);
		tc = (TipoCombustive)query.uniqueResult();
		tx.commit();

	   return tc;
	}
	
}
