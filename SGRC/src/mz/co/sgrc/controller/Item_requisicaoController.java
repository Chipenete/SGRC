package mz.co.sgrc.controller;

import java.util.List;

import mz.co.sgrc.dao.Item_requisicaoDAO;
import mz.co.sgrc.dao.Orgao_has_CotaDAO;
import mz.co.sgrc.model.Item_requisicao;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.Orgao_has_Cota;
import mz.co.sgrc.model.Requisicao;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class Item_requisicaoController extends GenericForwardComposer{

	private Listbox lb_visualizaRequisicoes;
	private Item_requisicaoDAO item_requisicaoDAO;
	
	private ListModelList listItemRequisicao;	
	private ListModelList<Item_requisicao> listItemRequi;
	
	
	Requisicao requisicao = (Requisicao) Executions.getCurrent().getDesktop().getSession().getAttribute("requisicao");
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		item_requisicaoDAO = new Item_requisicaoDAO(); 
		preencher2();
 	}
	
	
	public Item_requisicaoController(){}
	
	
 	public void preencher(){
		List<Item_requisicao> itemR = requisicao.getListRequisicao();
		listItemRequisicao = new ListModelList<Item_requisicao>(itemR);
		lb_visualizaRequisicoes.setModel(listItemRequisicao);
		
	}
	
 	public void preencher2(){
 		List<Item_requisicao> l = item_requisicaoDAO.findAll();
 		for (Item_requisicao item_requisicao : l){
 			if(item_requisicao.getRequisicao().getId()==requisicao.getId()){
 				Listitem listitem = new Listitem();
 				new Listcell(item_requisicao.getViatura().getMarca()).setParent(listitem);
 				new Listcell(item_requisicao.getViatura().getMatricula()).setParent(listitem);
 				new Listcell(item_requisicao.getCombustivelString()).setParent(listitem);
 				new Listcell (""+item_requisicao.getQuantidade_requisitada()).setParent(listitem);
 				new Listcell (""+item_requisicao.getQuantidade_remessada()).setParent(listitem);
 				Listcell listcell = new Listcell();
 				listcell.setParent(listitem);
 				Checkbox cb_remessada = new Checkbox();
 				cb_remessada.setChecked(item_requisicao.getRemessada());
 				cb_remessada.setDisabled(true);
 				cb_remessada.setParent(listcell);
 				
 				lb_visualizaRequisicoes.appendChild(listitem);
 				
 			}
 		}
 		
 	}

}

