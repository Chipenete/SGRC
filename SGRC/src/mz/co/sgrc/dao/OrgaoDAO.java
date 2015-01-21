package mz.co.sgrc.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.Produto;
import mz.co.sgrc.model.Requisicao;

public class OrgaoDAO extends GenericDAO <Orgao> implements Serializable{

	public OrgaoDAO() {
		super(Orgao.class);
		
	}
	
	public List<Orgao> pesquisaOrgaos (String designacao){
		
		Session sess = getSession();
		Transaction tx =sess.beginTransaction();
		Criteria criteria = sess.createCriteria(_clazz);
		criteria.add(Restrictions.like("designacao", "%"+designacao+"%"));
		List<Orgao> items = criteria.list();
		tx.commit();
		return items;
}

	public Orgao returnar(Orgao orgao){
		Orgao org = null;
		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
	
		String hql = "FROM Orgao org JOIN FETCH org.listOrgaoHasCota orgao_has_cota where org=:orgao";
		
		org.hibernate.Query query = sess.createQuery(hql);
		query.setParameter("orgao", orgao);
		org = (Orgao)query.uniqueResult();
		tx.commit();

	   return org;
	}
	
}
