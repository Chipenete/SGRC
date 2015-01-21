package mz.co.sgrc.controller;

import java.util.List;
import java.util.Set;

import mz.co.sgrc.dao.Item_requisicaoDAO;
import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.QuantidadeFinalDAO;
import mz.co.sgrc.dao.RequisicaoDAO;
import mz.co.sgrc.model.Item_requisicao;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.QuantidadeFinal;
import mz.co.sgrc.model.Requisicao;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.impl.LabelElement;

public class RemessarItensController extends SelectorComposer<Component>{

	@Wire
	private Listbox lb_remessa;
	@Wire
	private Button btn_remessas;
	@Wire
	private Button btn_cancelar;
	@Wire
	private RequisicaoDAO requisicaoDAO;
	@Wire
	private QuantidadeFinalDAO quantidadeFinalDAO;
	@Wire
	private Item_requisicaoDAO item_requisicaoDAO;
	@Wire
	private OrgaoDAO orgaoDAO;

	private double temp;
	
	ListModelList <Item_requisicao> listItem_requisicao ;
	
	Requisicao requisicao = (Requisicao) Executions.getCurrent().getDesktop().getSession().getAttribute("requisicao");
	
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		orgaoDAO = new OrgaoDAO();
		requisicaoDAO = new RequisicaoDAO(); 
		item_requisicaoDAO = new Item_requisicaoDAO();
		quantidadeFinalDAO = new QuantidadeFinalDAO();
		preencherItensRemessas();
		
		
		
 	}
	
	public RemessarItensController(){}
	
	
    @Listen("onClick = #btn_remessas")
    public void onClickRemessa(){
    	Set <Listitem> listItems =  lb_remessa.getSelectedItems();
    	
    	QuantidadeFinal quantidadeFinal = new QuantidadeFinal();	
		
    	quantidadeFinal = quantidadeFinalDAO.findById((long)1);
    	
		
		//variavel para controlar se todos itens foram remessados com sucesso
		boolean todosRemessados = true ;
		
    	for (Listitem l : listItems){
    		Item_requisicao itemReq = new Item_requisicao();
    	    itemReq = (Item_requisicao)l.getValue();
    		
    	if(itemReq==null){
    		Messagebox.show("vazio");


    	}}
//    	
 
    	
    }
	
    public void preencherItensRemessas(){
    	List <Item_requisicao> listItemRequisicao = requisicao.getListRequisicao();
    	listItem_requisicao = new ListModelList <Item_requisicao> (listItemRequisicao);
    	//listItem_requisicao.setMultiple(true);
    	lb_remessa.setModel(listItem_requisicao);
    	
    }
    
    
    @SuppressWarnings("unchecked")
	@Listen("onSelect = #lb_remessa")
	public void selecionarItem(){
   
    	Listitem listItem = lb_remessa.getSelectedItem();
    	
    
    	final Item_requisicao item_requisicao = (Item_requisicao)listItem.getValue();
    //	alert(item_requisicao.getRequisicao().getOrgao().getDesignacao());
    	
	    Listcell listCell1 = (Listcell) listItem.getChildren().get(5);
	    final Doublebox db_quantidade  = ((Doublebox) listCell1.getFirstChild());
    	
    	
    	Listcell listcell = (Listcell) listItem.getChildren().get(5);
    	final Checkbox cb_quantidade = (Checkbox) listcell.getLastChild();
    	
    	Listcell listcell2 = (Listcell) listItem.getChildren().get(6);
    	final Checkbox cb_remessar = (Checkbox) listcell2.getFirstChild();
    	
    	cb_quantidade.addEventListener("onCheck", new EventListener(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				if(cb_quantidade.isChecked()){
					  
					item_requisicao.setQuantidade_remessada(db_quantidade.getValue());
						item_requisicaoDAO.update(item_requisicao);
						db_quantidade.setDisabled(true);
						
						
					}else{
						
						db_quantidade.setDisabled(false);
						
					}
			}
    		
    	});	
    	
    	
    	
    	cb_remessar.addEventListener("onCheck", new EventListener(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				if(cb_remessar.isChecked()){
					
					
					QuantidadeFinal quantidadeFinal = new QuantidadeFinal();	
					
			    	quantidadeFinal = quantidadeFinalDAO.findById((long)1);
					
					
					if(item_requisicao.getCombustivelString().equals("Gasolina") && quantidadeFinal.getQuantidadeGasolina()>=item_requisicao.getQuantidade_remessada()){
					
						item_requisicao.setRemessada(true);
					    item_requisicaoDAO.update(item_requisicao);
					    
					    quantidadeFinal.setQuantidadeGasolina(quantidadeFinal.getQuantidadeGasolina()-item_requisicao.getQuantidade_remessada());
					    quantidadeFinalDAO.update(quantidadeFinal);
					    
					    
					    Orgao orgao = (Orgao) item_requisicao.getRequisicao().getOrgao();
					    orgao.setQuantidadeGasolina(orgao.getQuantidadeGasolina()+(item_requisicao.getQuantidade_requisitada()-item_requisicao.getQuantidade_remessada()));
					    orgaoDAO.update(orgao);
					    
					    Messagebox.show("Item remessado com sucesso");
						
					    cb_remessar.setDisabled(true);
					
				}
				else if(item_requisicao.getCombustivelString().equals("Gasolina") && quantidadeFinal.getQuantidadeGasolina()<item_requisicao.getQuantidade_remessada()){
					
				   Messagebox.show("Quantidade de Gasolina indisponivel");
					
				}
				else  if(item_requisicao.getCombustivelString().equals("Gasoleo") && quantidadeFinal.getQuantidadeGasoleo()>=item_requisicao.getQuantidade_remessada()){
					
					item_requisicao.setRemessada(true);
					item_requisicaoDAO.update(item_requisicao);
					quantidadeFinal.setQuantidadeGasoleo(quantidadeFinal.getQuantidadeGasoleo()-item_requisicao.getQuantidade_remessada());
				    quantidadeFinalDAO.update(quantidadeFinal);
				    
				    
				    Orgao orgao = (Orgao) item_requisicao.getRequisicao().getOrgao();
				    orgao.setQuantidadeGasoleo(orgao.getQuantidadeGasoleo()+(item_requisicao.getQuantidade_requisitada()-item_requisicao.getQuantidade_remessada()));
				    orgaoDAO.update(orgao);
					    
				    Messagebox.show("Item remessado com sucesso");
				    
				    cb_remessar.setDisabled(true);
				}
				else if(item_requisicao.getCombustivelString().equals("Gasoleo") && quantidadeFinal.getQuantidadeGasoleo()<item_requisicao.getQuantidade_remessada()){
					
					  Messagebox.show("Quantidade de Gasoleo indisponivel");
				}
				
				else  if(item_requisicao.getCombustivelString().equals("Gas") && quantidadeFinal.getQuantidadeGas()>=item_requisicao.getQuantidade_remessada()){
					item_requisicao.setRemessada(true);
					item_requisicaoDAO.update(item_requisicao);
					quantidadeFinal.setQuantidadeGas(quantidadeFinal.getQuantidadeGas()-item_requisicao.getQuantidade_remessada());
				    quantidadeFinalDAO.update(quantidadeFinal);
				    
				    
				    Orgao orgao = (Orgao) item_requisicao.getRequisicao().getOrgao();
				    orgao.setQuantidadeGas(orgao.getQuantidadeGas()+(item_requisicao.getQuantidade_requisitada()-item_requisicao.getQuantidade_remessada()));
				    orgaoDAO.update(orgao);
					    
				    Messagebox.show("Item remessado com sucesso");
				    
				    cb_remessar.setDisabled(true);
				}
				else if(item_requisicao.getCombustivelString().equals("Gas") && quantidadeFinal.getQuantidadeGas()<item_requisicao.getQuantidade_remessada()){
					
					Messagebox.show("Quantidade de Gas indisponivel");
				}
				
					
				
			}
						
			   else{
						  item_requisicao.setRemessada(false);
						  item_requisicaoDAO.update(item_requisicao);
						
						
					}
			}
    		
    	});	
    	
    }
////    	Listitem l = lb_remessa.getSelectedItem();
////    	 Item_requisicao i = (Item_requisicao)l.getValue();
////    	 if(i!=null)
////    		 alert("item selecionado");
//////    	//item_requisicao.setQuantidade_combustivel(db_quantidade.getValue());
////    	  //      alert("item  selecionado >>> " +    ((Doublebox)((Listcell)(lb_remessa.getSelectedItem().getChildren().get(4))).getFirstChild()).getValue());
//////      
//////      selectedQuantidade = 100;
//////      item_requisicao.setQuantidade_combustivel(selectedQuantidade);
//////      item_requisicaoDAO.update(item_requisicao);    	//String x = doublebox.getValue();
////    	
//////    	Messagebox.show(""+listCell.getChildren().get(0));
//////    	//Doublebox db_quantidadeFinal = (Doublebox) listCell.getChildren();
////    	    
//       alert("item  selecionado >>> " + ( (LabelElement) ( lb_remessa.getSelectedItem().getChildren().get(4)).getFirstChild()).getLabel());
////      
////    	Listitem listItem = (Listitem)lb_remessa.getSelectedItem();
////    	Listcell listCell = (Listcell) listItem.getChildren().get(5);
////    	double quantidade = ((Doublebox) listCell.getFirstChild()).getValue();
////
////       alert(""+quantidade);
////       i.setQuantidade_combustivel(quantidade);
////       item_requisicaoDAO.update(i);
////       alert(""+i.getQuantidade_combustivel());
////     	
////    	
//	}
////	
	
//	@SuppressWarnings("unchecked")
//	public void preencherItensRemessas(){
//		List <Item_requisicao> listRequisicao = requisicao.getListRequisicao();
//		
//		
//		for (final Item_requisicao itemRequisicao : listRequisicao){
//			
//			final Listitem listItem = new Listitem();
//			new Listcell("").setParent(listItem); 
//			new Listcell(itemRequisicao.getViatura().getMarca()).setParent(listItem);
//			new Listcell (itemRequisicao.getViatura().getMatricula()).setParent(listItem);
//			new Listcell(itemRequisicao.getCombustivelString()).setParent(listItem);
//			
////			Listcell listcell1 = new Listcell();
////			listcell1.setParent(listItem);
//			
//			
//			Listcell listcell2 = new Listcell();
//			listcell2.setParent(listItem);
//			Doublebox db_quantidadePedida = new Doublebox();
//			db_quantidadePedida.setValue(itemRequisicao.getQuantidade_combustivel());
//			db_quantidadePedida.setWidth("45%");
//			db_quantidadePedida.setDisabled(true);
//			db_quantidadePedida.setParent(listcell2);
//		
//			final Doublebox db_quantidade = new Doublebox();
//			db_quantidade.setWidth("45%");
//			db_quantidade.setParent(listcell2);
//			
//			final Checkbox cb_quantidade = new Checkbox();
//			cb_quantidade.setWidth("8%");
//			cb_quantidade.setParent(listcell2);
//			
//			Listcell listcell = new Listcell();
//			listcell.setParent(listItem);
//			Checkbox cb_remessada = new Checkbox();
//			cb_remessada.setChecked(itemRequisicao.getRemessada());
//			cb_remessada.setDisabled(true);
//			cb_remessada.setParent(listcell);
//			
//			if(itemRequisicao.getRemessada()){
//				listItem.setDisabled(true);	
//				cb_quantidade.setDisabled(true);
//			}
//		    
//			lb_remessa.setMultiple(true);
//			listItem.setParent(lb_remessa);
//			
//			cb_quantidade.addEventListener("onCheck", new EventListener(){
//
//				@Override
//				public void onEvent(Event arg0) throws Exception {
//					// TODO Auto-generated method stub
//				if(cb_quantidade.isChecked()){
//				  
//					itemRequisicao.setQuantidade_combustivel(db_quantidade.getValue());
//					item_requisicaoDAO.update(itemRequisicao);
//					db_quantidade.setDisabled(true);
//					
//				}else{
//					
//					db_quantidade.setDisabled(false);
//					
//				}
//				}
//				
//			});
//			
//			
//		}
//		
//	}
//	
	
//	///////

//	if(itemReq.getCombustivelString().equals("Gasolina") && quantidadeFinal.getQuantidadeGasolina()>=itemReq.getQuantidade_combustivel()){
//		
//		itemReq.setRemessada(true);
//		
//		item_requisicaoDAO.update(itemReq);
//		quantidadeFinal.setQuantidadeGasolina(quantidadeFinal.getQuantidadeGasolina()-itemReq.getQuantidade_combustivel());
//		
//	}
//	else if(itemReq.getCombustivelString().equals("Gasolina") && quantidadeFinal.getQuantidadeGasolina()<itemReq.getQuantidade_combustivel()){
//		
//		todosRemessados = false;
//		
//	}
//	else  if(itemReq.getCombustivelString().equals("Gasoleo") && quantidadeFinal.getQuantidadeGasoleo()>=itemReq.getQuantidade_combustivel()){
//		itemReq.setRemessada(true);
//		item_requisicaoDAO.update(itemReq);
//		quantidadeFinal.setQuantidadeGasoleo(quantidadeFinal.getQuantidadeGasoleo()-itemReq.getQuantidade_combustivel());
//	}
//	else if(itemReq.getCombustivelString().equals("Gasoleo") && quantidadeFinal.getQuantidadeGasoleo()<itemReq.getQuantidade_combustivel()){
//		
//		todosRemessados=false;
//	}
//	
//	else  if(itemReq.getCombustivelString().equals("Gaso") && quantidadeFinal.getQuantidadeGas()>=itemReq.getQuantidade_combustivel()){
//		itemReq.setRemessada(true);
//		item_requisicaoDAO.update(itemReq);
//		quantidadeFinal.setQuantidadeGas(quantidadeFinal.getQuantidadeGas()-itemReq.getQuantidade_combustivel());
//	}
//	else if(itemReq.getCombustivelString().equals("Gas") && quantidadeFinal.getQuantidadeGas()<itemReq.getQuantidade_combustivel()){
//		
//		todosRemessados=false;
//	}
//	
//	
//}
//
//quantidadeFinalDAO.update(quantidadeFinal);
//
//if(todosRemessados){
//	Messagebox.show("Itens remessados com sucesso");
//}
//else if(!todosRemessados){
//	Messagebox.show("Alguns itens nao foram remessados");
//}
//////////
	
	
}
