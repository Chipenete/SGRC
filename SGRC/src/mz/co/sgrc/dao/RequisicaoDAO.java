package mz.co.sgrc.dao;

import java.util.List;

import javax.management.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.sgrc.model.Item_requisicao;
import mz.co.sgrc.model.Requisicao;

public class RequisicaoDAO extends GenericDAO <Requisicao>{
	
	public RequisicaoDAO(){
	  
		super(Requisicao.class);
	
	}

//	public static void main(String [] args){
//		RequisicaoDAO req = new RequisicaoDAO();
//		Requisicao r = new Requisicao();
//	
//		req.create(r);
//		
//	}
	
	
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
