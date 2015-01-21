package mz.co.sgrc.dao;

import java.util.Date;

import javax.swing.JOptionPane;

import mz.co.sgrc.model.Orgao_has_Cota;

public class Orgao_has_CotaDAO extends GenericDAO <Orgao_has_Cota>{
	
	public Orgao_has_CotaDAO(){
		
		super(Orgao_has_Cota.class);
	}

	
	public static void main (String [] args){
		
		Orgao_has_CotaDAO o = new Orgao_has_CotaDAO();
		Orgao_has_Cota oc = new Orgao_has_Cota();
		Date a = new Date();
		oc.setData(a);
        o.create(oc);
        JOptionPane.showMessageDialog(null, "das");
		
	}
}
