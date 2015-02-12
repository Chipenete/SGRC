package mz.co.sgrc.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.sgrc.model.GuiaRemessa;

public class GuiaRemessaDAO extends GenericDAO<GuiaRemessa> {

	public GuiaRemessaDAO() {
		super(GuiaRemessa.class);
		
	}

	@SuppressWarnings("rawtypes")
	public List findAllInverso() {

	List objects = null;
	try {

		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
	org.hibernate.Query query = sess.createQuery("from " +_clazz.getName()+" order by id desc");
	objects = query.list();
	tx.commit();

	} catch (HibernateException e) {
	
	}
	return objects;
	}
	
}
