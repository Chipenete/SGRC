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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class TipoCombustiveController extends GenericForwardComposer{
	
	

	 
	private Textbox tb_designacao; 
	private Textbox tb_descricao;
	private Listbox lb_tipoCombustive;
	private Button btn_gravar;
	private Button btn_actualizar;
	private Button btn_cancelar;
	
	Window win = new Window();
	private ListModelList <TipoCombustive> listTipoCombustive;
	TipoCombustive selectedTipoCombustive;
	private TipoCombustiveDAO tipoCombustiveDAO;
	
	
	public TipoCombustiveController(){
		
		tipoCombustiveDAO = new TipoCombustiveDAO();
	}
	
	@Override
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		visualizarTipoCombustive();
	
	}
	
	
	public void onClick$btn_gravar(){
		TipoCombustive tc = new TipoCombustive();
		tc.setDesignacao(tb_designacao.getText());
		tc.setDescricao(tb_descricao.getText());
		tipoCombustiveDAO.create(tc);
		visualizarTipoCombustive();
		Clients.showNotification("Inserido com sucesso", "info", win, "middle_center", 4000);
		 limparCampos();
	}
	
	
	public void onClick$btn_actualizar(){
		if(selectedTipoCombustive != null){
			selectedTipoCombustive.setDesignacao(tb_designacao.getText());
			selectedTipoCombustive.setDescricao(tb_descricao.getText());
			tipoCombustiveDAO.update(selectedTipoCombustive);
			limparCampos();
			Clients.showNotification("Actualizado com sucesso", "info", win, "middle_center", 2000);
			visualizarTipoCombustive();
		}
		
	}
	
	
	public void onClick$btn_cancelar(){
		
		limparCampos();
	}
	
	
	
	public void onSelect$lb_tipoCombustive (){
		if(listTipoCombustive.isSelectionEmpty()){
		selectedTipoCombustive = null;
		}
		
		else {
			selectedTipoCombustive = listTipoCombustive.getSelection().iterator().next();
		}
		
		refreshOrgaoDetail();
		
	}
	
	
	 public void visualizarTipoCombustive(){
		 List <TipoCombustive> tipos = tipoCombustiveDAO.findAll();
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
