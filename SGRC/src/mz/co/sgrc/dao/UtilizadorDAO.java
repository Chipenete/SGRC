package mz.co.sgrc.dao;

import java.io.Serializable;
import java.util.List;

import mz.co.sgrc.model.Utilizador;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;


public class UtilizadorDAO  extends GenericDAO <Utilizador> implements Serializable{

	public UtilizadorDAO() {
		super(Utilizador.class);
		
	}
	

	
}
