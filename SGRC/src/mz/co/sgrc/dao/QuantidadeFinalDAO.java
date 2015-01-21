package mz.co.sgrc.dao;

import mz.co.sgrc.model.QuantidadeFinal;

public class QuantidadeFinalDAO extends GenericDAO<QuantidadeFinal>{

	public QuantidadeFinalDAO(){
		super(QuantidadeFinal.class);
	}
	
	
	/*public static void main (String [] args){
		QuantidadeFinalDAO qDAO = new QuantidadeFinalDAO();
		QuantidadeFinal quantFinal = new QuantidadeFinal();
		quantFinal.setQuantidadeGasoleo(0);
		quantFinal.setQuantidadeGasolina(0);
		qDAO.create(quantFinal);
		
	}*/
}
