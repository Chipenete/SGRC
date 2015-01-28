package mz.co.sgrc.controller;

import java.util.List;

import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.model.Orgao;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class OrgaoController extends GenericForwardComposer{
	
	
	private Textbox tb_designacao;
	
	private Listbox lb_orgao;
	
	private Button btn_gravar;
	private Button btn_actualizar;
	private Button btn_cancelar;
	
	Window win= new Window();
	
	private ListModelList <Orgao> listOrgao;
	Orgao selectedOrgao;
	private OrgaoDAO dao;
	
	
	public OrgaoController (){
		
		dao = new OrgaoDAO();
	}
	
	@Override
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		visualizarOrgao();
		
	}
	
	public void onClick$btn_gravar(Event e) {
		Orgao o = new Orgao();
		o.setDesignacao(tb_designacao.getText());
		dao.create(o);
		visualizarOrgao();
		Clients.showNotification("Inserido com sucesso", "info", win, "middle_center", 4000);
		 limparCampos();
	}
	
	public void onClick$btn_actualizar(Event e) {
		if(selectedOrgao != null){
			selectedOrgao.setDesignacao(tb_designacao.getText());
			dao.update(selectedOrgao);
			limparCampos();
			Clients.showNotification("Actualizado com sucesso", "info", win, "middle_center", 2000);
			visualizarOrgao();
		}
		
	}
	
	public void onClick$btn_cancelar(Event e) {
		limparCampos();
	}
	
	public void onSelect$lb_orgao(Event e) {
	if(listOrgao.isSelectionEmpty()){
		selectedOrgao = null;
		}
		
		else {
			selectedOrgao = listOrgao.getSelection().iterator().next();
		}
		
		refreshOrgaoDetail();
		
	}
	

	 public void visualizarOrgao(){
		 List <Orgao> orgaos = dao.findAll();
		 listOrgao = new ListModelList <Orgao> (orgaos); 
		 lb_orgao.setModel(listOrgao);
		
	 }
	 
	 public void limparCampos(){
		 tb_designacao.setRawValue(null);
		 selectedOrgao=null;
		 
	 }
	 
	 
	 private void refreshOrgaoDetail() {
			tb_designacao.setValue(selectedOrgao.getDesignacao());
		}

}
