package mz.co.sgrc.controller;

import java.util.ArrayList;
import java.util.List;

import mz.co.sgrc.dao.CotasDAO;
import mz.co.sgrc.dao.HistoricoDAO;
import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.Orgao_has_CotaDAO;
import mz.co.sgrc.dao.TipoCombustiveDAO;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.Orgao_has_Cota;
import mz.co.sgrc.model.TipoCombustive;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class HistoricoController extends GenericForwardComposer{

	private Listbox lb_historico;
	private Combobox cbb_tipoCombustivelAtribuir;
	private Button btn_procurar;
	
	
	private Orgao_has_CotaDAO orgao_has_CotaDAO;
	private TipoCombustiveDAO tipoCombustiveDAO;
	
	private ListModelList <TipoCombustive> listTipoCombustiveAtribuir;
	private ListModelList <Orgao_has_Cota> listTemp;
	
	private TipoCombustive selectedTipoCombustivelAtribuir;

	Orgao orgao = (Orgao) Executions.getCurrent().getDesktop().getSession().getAttribute("orgao");
	

	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		tipoCombustiveDAO = new TipoCombustiveDAO();
		orgao_has_CotaDAO = new Orgao_has_CotaDAO();
		selectedTipoCombustivelAtribuir=null;
		
		preenchertipoCombustivelAtribuir();
		
 	}
	
	
	public HistoricoController(){}
	
	
    public void onSelect$cbb_tipoCombustivelAtribuir(Event e){	
		if(listTipoCombustiveAtribuir.isSelectionEmpty())
			selectedTipoCombustivelAtribuir = null;
		else
			selectedTipoCombustivelAtribuir = (TipoCombustive) listTipoCombustiveAtribuir.getSelection().iterator().next();
	}
	
    public void onClick$btn_procurar(Event e){
		preencher();	
	}
	
	
	public void   preenchertipoCombustivelAtribuir(){
		List <TipoCombustive> listTipo = tipoCombustiveDAO.findAll();
		listTipoCombustiveAtribuir = new ListModelList <TipoCombustive>(listTipo);
		cbb_tipoCombustivelAtribuir.setModel(listTipoCombustiveAtribuir);	
	}
	
	
	public void preencher(){
			
			List <Orgao_has_Cota> list = orgao_has_CotaDAO.findAll();
	        
			for(Orgao_has_Cota hc : list){
				if(hc.getOrgao().getDesignacao().equals(orgao.getDesignacao()) && hc.getCotas().getTipoCombustive()
						.getDesignacao().equals(selectedTipoCombustivelAtribuir.getDesignacao())){
	               Listitem lm = new Listitem();
	               new Listcell (""+hc.getData()).setParent(lm);
	               new Listcell (""+hc.getCotas().getQuantidade()).setParent(lm);
	               new Listcell (""+hc.getCotas().getTipoCombustive().getDesignacao()).setParent(lm);
	               new Listcell (hc.getOrgao().getDesignacao()).setParent(lm);
	               lb_historico.appendChild(lm);
		        }
			}
        	  
	}
	

}