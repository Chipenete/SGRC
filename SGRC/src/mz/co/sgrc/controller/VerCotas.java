package mz.co.sgrc.controller;

import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.Orgao_has_CotaDAO;
import mz.co.sgrc.dao.TipoCombustiveDAO;
import mz.co.sgrc.model.Orgao;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class VerCotas extends SelectorComposer<Component>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox tb_orgao;
	@Wire
	private Listbox lb_cotas;
	@Wire
	private OrgaoDAO orgaoDAO;
	
	Orgao orgao = (Orgao) Executions.getCurrent().getDesktop().getSession().getAttribute("orgao");
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		orgaoDAO = new OrgaoDAO();
		preencherCotas();
		
 	}
	
	public VerCotas(){}



  public void preencherCotas(){
	  tb_orgao.setValue(orgao.getDesignacao());
	  tb_orgao.setDisabled(true);
	 
	  Listitem listItem = new Listitem();
	  new Listcell("Gasolina").setParent(listItem);
	  new Listcell(""+orgao.getCotaGasolina()).setParent(listItem);
	  new Listcell(""+orgao.getQuantidadeGasolina()).setParent(listItem);
	  lb_cotas.appendChild(listItem);
	  
	  
	  Listitem listItem1 = new Listitem();
	  new Listcell("Gasoleo").setParent(listItem1);
	  new Listcell(""+orgao.getCotaGasoleo()).setParent(listItem1);
	  new Listcell(""+orgao.getQuantidadeGasoleo()).setParent(listItem1);
	  lb_cotas.appendChild(listItem1);
	  
	  Listitem listItem2 = new Listitem();
	  new Listcell("Gas").setParent(listItem2);
	  new Listcell(""+orgao.getCotaGas()).setParent(listItem2);
	  new Listcell(""+orgao.getQuantidadeGas()).setParent(listItem2);
	  lb_cotas.appendChild(listItem2);
	  
  }

}