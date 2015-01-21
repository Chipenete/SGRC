package mz.co.sgrc.controller;

import java.util.List;

import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.TipoCombustiveDAO;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.TipoCombustive;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class TipoCombustiveController extends SelectorComposer <Component>{
	
	

	@Wire 
	private Textbox tb_designacao;
	
	@Wire 
	private Textbox tb_descricao;
	
	@Wire
	private Listbox lb_tipoCombustive;
	
	@Wire
	private Button btn_gravar;
	
	@Wire
	private Button btn_actualizar;
	
	@Wire
	private Button btn_cancelar;
	
	private ListModelList <TipoCombustive> listTipoCombustive;
	TipoCombustive selectedTipoCombustive;
	private TipoCombustiveDAO dao;
	
	
	public TipoCombustiveController(){
		
		dao = new TipoCombustiveDAO();
	}
	

	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		visualizarTipoCombustive();
	
	}
	
	@Listen("onClick = #btn_gravar")
	public void onClickGravar(){
		TipoCombustive tc = new TipoCombustive();
		tc.setDesignacao(tb_designacao.getText());
		tc.setDescricao(tb_descricao.getText());
		dao.create(tc);
		visualizarTipoCombustive();
		 Messagebox.show("Inserido com sucesso.");
		 limparCampos();
	}
	
	@Listen("onClick = #btn_actualizar")
	public void onclickActualizar(){
		if(selectedTipoCombustive != null){
			selectedTipoCombustive.setDesignacao(tb_designacao.getText());
			selectedTipoCombustive.setDescricao(tb_descricao.getText());
			dao.update(selectedTipoCombustive);
			limparCampos();
			Messagebox.show("Actualizado com sucesso");
		}
		
	}
	
	@Listen("onClick = #btn_cancelar")
	public void onclickCancelar(){
		
		limparCampos();
	}
	
	
	@Listen ("onSelect = #lb_tipoCombustive")
	public void doTipoCombustiveSelect (){
		if(listTipoCombustive.isSelectionEmpty()){
		selectedTipoCombustive = null;
		}
		
		else {
			selectedTipoCombustive = listTipoCombustive.getSelection().iterator().next();
		}
		
		refreshOrgaoDetail();
		
	}
	
	
	 public void visualizarTipoCombustive(){
		 List <TipoCombustive> tipos = dao.findAll();
		 listTipoCombustive = new ListModelList <TipoCombustive> (tipos); 
		 lb_tipoCombustive.setModel(listTipoCombustive);
		
	 }
	 
	 public void limparCampos(){
		 tb_designacao.setRawValue(null);
		 tb_descricao.setRawValue(null);
		 selectedTipoCombustive=null;
		 
	 }
	 
	 
	 private void refreshOrgaoDetail() {
			tb_designacao.setValue(selectedTipoCombustive.getDesignacao());
			tb_descricao.setValue(selectedTipoCombustive.getDescricao());	
		
		}

}
