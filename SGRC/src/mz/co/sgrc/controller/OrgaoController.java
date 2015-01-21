package mz.co.sgrc.controller;

import java.util.List;

import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.model.Orgao;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class OrgaoController extends SelectorComposer <Component>{
	
	@Wire 
	private Textbox tb_designacao;
	
	@Wire
	private Listbox lb_orgao;
	
	@Wire
	private Button btn_gravar;
	
	@Wire
	private Button btn_actualizar;
	
	@Wire
	private Button btn_cancelar;
	
	private ListModelList <Orgao> listOrgao;
	Orgao selectedOrgao;
	private OrgaoDAO dao;
	
	
	public OrgaoController (){
		
		dao = new OrgaoDAO();
	}
	
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		visualizarOrgao();
	
	}
	
	@Listen("onClick = #btn_gravar")
	public void onClickGravar(){
		Orgao o = new Orgao();
		o.setDesignacao(tb_designacao.getText());
		dao.create(o);
		visualizarOrgao();
		 Messagebox.show("Inserido com sucesso.");
		 limparCampos();
	}
	
	@Listen("onClick = #btn_actualizar")
	public void onclickActualizar(){
		if(selectedOrgao != null){
			selectedOrgao.setDesignacao(tb_designacao.getText());
			dao.update(selectedOrgao);
			limparCampos();
			Messagebox.show("Actualizado com sucesso");
		}
		
	}
	
	@Listen("onClick = #btn_cancelar")
	public void onclickCancelar(){
		
		limparCampos();
	}
	
	
	@Listen ("onSelect = #lb_orgao")
	public void doOrgaoSelect (){
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
