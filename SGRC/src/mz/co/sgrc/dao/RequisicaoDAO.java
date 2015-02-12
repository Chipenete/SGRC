package mz.co.sgrc.dao;

import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.sgrc.model.Item_requisicao;
import mz.co.sgrc.model.Requisicao;

public class RequisicaoDAO extends GenericDAO <Requisicao>{
	
	public RequisicaoDAO(){
	  
		super(Requisicao.class);
	
	}
	
	
	public Requisicao returnar(Requisicao requisicao){
		Requisicao req = null;
		Session sess = getSession();
		Transaction tx = sess.beginTransaction();
	
		String hql = "FROM Requisicao req JOIN FETCH req.listRequisicao item_req where req=:requisicao";
		
		org.hibernate.Query query = sess.createQuery(hql);
		query.setParameter("requisicao", requisicao);
		req = (Requisicao)query.uniqueResult();
		tx.commit();

	   return req;
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
	
	
//	public static void main(String[] a){
//	ArrayList<Requisicao> list = new ArrayList<Requisicao>();
//	list.addAll(new RequisicaoDAO().findAllInverso());
//	for (Requisicao req : list) {
//		System.out.println(req.getId());
//		
//	}
//	
//	}
//	@SuppressWarnings("unchecked")
//	public List <Item_requisicao> returonarItensRequisicao(Requisicao requisicao){
//		List <Item_requisicao> itens = null;
//		
//		Session sess = getSession();
//		Transaction tx = sess.beginTransaction();
//		
//		String hql = "FROM Requisicao req JOIN FETCH req.listRequisicao item_req";
//		org.hibernate.Query query = sess.createQuery(hql);
//		
//		itens = (List<Item_requisicao>)query.list();
//		tx.commit();
//
//	   return itens;
//	}
}
