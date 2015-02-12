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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.LabelElement;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class RemessarItensController extends GenericForwardComposer{

	
	private Listbox lb_remessa;
	private Button btn_remessas;
	private Button btn_cancelar;
	
	private RequisicaoDAO _requisicaoDAO;
	private QuantidadeFinalDAO _quantidadeFinalDAO;
	private Item_requisicaoDAO _item_requisicaoDAO;
	private OrgaoDAO _orgaoDAO;
	private double _temp;
	
	Window _win;
	
	ListModelList <Item_requisicao> _listModellistItem_requisicao ;
	
	Requisicao requisicao = (Requisicao) Executions.getCurrent().getDesktop().getSession().getAttribute("requisicao");
	
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		_orgaoDAO = new OrgaoDAO();
		_requisicaoDAO = new RequisicaoDAO(); 
		_item_requisicaoDAO = new Item_requisicaoDAO();
		_quantidadeFinalDAO = new QuantidadeFinalDAO();
		preencherItensRemessas();
	
 	}
	
	public RemessarItensController(){}
	
	
    public void onClick$btn_remessas(Event e){
    	Set <Listitem> listItems =  lb_remessa.getSelectedItems();
    	QuantidadeFinal quantidadeFinal = new QuantidadeFinal();	
    	quantidadeFinal = _quantidadeFinalDAO.findById((long)1);
  
		//variavel para controlar se todos itens foram remessados com sucesso
		boolean todosRemessados = true ;
    	for (Listitem l : listItems){
    		Item_requisicao itemReq = new Item_requisicao();
    	    itemReq = (Item_requisicao)l.getValue();
    		
    	   if(itemReq==null)
    	   Clients.showNotification("Vazio", "info",_win,"middle_center",4000, true);
        }	
    }
	

    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void onSelect$lb_remessa(Event e){
    	Listitem listItem = lb_remessa.getSelectedItem();
    	final Item_requisicao item_requisicao = (Item_requisicao)listItem.getValue();
    
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
						_item_requisicaoDAO.update(item_requisicao);
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
					
			    	quantidadeFinal = _quantidadeFinalDAO.findById((long)1);
			
					if(item_requisicao.getCombustivelString().equals("Gasolina") && quantidadeFinal.getQuantidadeGasolina()>=item_requisicao.getQuantidade_remessada()){
					
						item_requisicao.setRemessada(true);
					    _item_requisicaoDAO.update(item_requisicao);
					    
					    quantidadeFinal.setQuantidadeGasolina(quantidadeFinal.getQuantidadeGasolina()-item_requisicao.getQuantidade_remessada());
					    _quantidadeFinalDAO.update(quantidadeFinal);
					    
					    
					    Orgao orgao = (Orgao) item_requisicao.getRequisicao().getOrgao();
					    orgao.setQuantidadeGasolina(orgao.getQuantidadeGasolina()+(item_requisicao.getQuantidade_requisitada()-item_requisicao.getQuantidade_remessada()));
					    _orgaoDAO.update(orgao);
					    
					    Clients.showNotification("item remessado com sucesso", "info",_win,"middle_center",4000, true);
						
					    cb_remessar.setDisabled(true);
					
				}
				else if(item_requisicao.getCombustivelString().equals("Gasolina") && quantidadeFinal.getQuantidadeGasolina()<item_requisicao.getQuantidade_remessada()){
		
				   Clients.showNotification("Quantidade de Gasolina indisponivel", "info",_win,"middle_center",4000, true);
					
				}
				else  if(item_requisicao.getCombustivelString().equals("Gasoleo") && quantidadeFinal.getQuantidadeGasoleo()>=item_requisicao.getQuantidade_remessada()){
					
					item_requisicao.setRemessada(true);
					_item_requisicaoDAO.update(item_requisicao);
					quantidadeFinal.setQuantidadeGasoleo(quantidadeFinal.getQuantidadeGasoleo()-item_requisicao.getQuantidade_remessada());
				    _quantidadeFinalDAO.update(quantidadeFinal);
				    
				    
				    Orgao orgao = (Orgao) item_requisicao.getRequisicao().getOrgao();
				    orgao.setQuantidadeGasoleo(orgao.getQuantidadeGasoleo()+(item_requisicao.getQuantidade_requisitada()-item_requisicao.getQuantidade_remessada()));
				    _orgaoDAO.update(orgao);

				    Clients.showNotification("Item remessado com sucesso", "info",_win,"middle_center",4000, true);
				    
				    cb_remessar.setDisabled(true);
				}
				else if(item_requisicao.getCombustivelString().equals("Gasoleo") && quantidadeFinal.getQuantidadeGasoleo()<item_requisicao.getQuantidade_remessada()){
	
					   Clients.showNotification("Quantidade de gasoleo indisponivel", "info",_win,"middle_center",4000, true);
				}
				
				else  if(item_requisicao.getCombustivelString().equals("Gas") && quantidadeFinal.getQuantidadeGas()>=item_requisicao.getQuantidade_remessada()){
					item_requisicao.setRemessada(true);
					_item_requisicaoDAO.update(item_requisicao);
					quantidadeFinal.setQuantidadeGas(quantidadeFinal.getQuantidadeGas()-item_requisicao.getQuantidade_remessada());
				    _quantidadeFinalDAO.update(quantidadeFinal);
				    
				    
				    Orgao orgao = (Orgao) item_requisicao.getRequisicao().getOrgao();
				    orgao.setQuantidadeGas(orgao.getQuantidadeGas()+(item_requisicao.getQuantidade_requisitada()-item_requisicao.getQuantidade_remessada()));
				    _orgaoDAO.update(orgao);
			
				    Clients.showNotification("item remessado com sucesso", "info",_win,"middle_center",4000, true);
				    
				    cb_remessar.setDisabled(true);
				}
				else if(item_requisicao.getCombustivelString().equals("Gas") && quantidadeFinal.getQuantidadeGas()<item_requisicao.getQuantidade_remessada()){
		
					   Clients.showNotification("Quantidade de gas indisponivel", "info",_win,"middle_center",4000, true);
				}
		
				
			}
						
			   else{
						  item_requisicao.setRemessada(false);
						  _item_requisicaoDAO.update(item_requisicao);
		
					}
			}
    		
    	});	
    	
    }
    
    public void preencherItensRemessas(){
    	List <Item_requisicao> listItemRequisicao = requisicao.getListRequisicao();
    	_listModellistItem_requisicao = new ListModelList <Item_requisicao> (listItemRequisicao);
    	//listItem_requisicao.setMultiple(true);
    	lb_remessa.setModel(_listModellistItem_requisicao);
    	
    }
	
}
