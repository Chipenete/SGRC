package mz.co.sgrc.controller;

import java.util.List;

import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.UtilizadorDAO;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.Utilizador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class UtilizadorController extends SelectorComposer <Component>{
	
	
	@Wire 
	private Textbox tb_nome;
	
	@Wire
	private Textbox tb_password;
	
	@Wire
	private Listbox lb_utilizador;
	
	@Wire
	private Button btn_gravar;
	
	@Wire
	private Button btn_actualizar;
	
	@Wire
	private Button btn_cancelar;
	
	@Wire
	private Combobox cbb_orgao;
	
	private ListModelList <Utilizador> listUtilizador;
	private ListModelList <Orgao> listOrgao;

	Utilizador selectedUtilizador;
	private UtilizadorDAO dao;
	private OrgaoDAO d;

	 Orgao selectedOrgao;
	
	
	public UtilizadorController (){
		
		dao = new UtilizadorDAO();
		d = new OrgaoDAO();
	}
	
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		preencherOrgaos();
		visualizarUtilizador();
	    selectedOrgao = null;
	}
	
	
	@Listen("onClick = #btn_gravar")
	public void onClickGravar(){
		Utilizador o = new Utilizador();
		o.setNome(tb_nome.getValue());
		o.setPassword(tb_password.getValue());
		o.setOrgao(selectedOrgao);
		dao.create(o);
		visualizarUtilizador();
		 Messagebox.show("Inserido com sucesso.");
		 limparCampos();
		 selectedOrgao=null;
	}
	
	@Listen("onClick = #btn_actualizar")
	public void onclickActualizar(){
		if(selectedUtilizador != null){
			selectedUtilizador.setNome(tb_nome.getText());
			selectedUtilizador.setPassword(tb_password.getText());
			selectedUtilizador.setOrgao(selectedOrgao);
			dao.update(selectedUtilizador);
			limparCampos();
			Messagebox.show("Actualizado com sucesso");
		}
		
	}
	
	@Listen("onClick = #btn_cancelar")
	public void onclickCancelar(){
		
		limparCampos();
	}
	
	
	@Listen ("onSelect = #lb_utilizador")
	public void doOrgaoSelect (){
		if(listUtilizador.isSelectionEmpty()){
		selectedUtilizador = null;
		}
		
		else {
			selectedUtilizador = listUtilizador.getSelection().iterator().next();
		}
		
		refreshOrgaoDetail();
		
	}
	
	@Listen ("onSelect = #cbb_orgao")
	public void doOrgaoSelected (){
		if(listOrgao.isSelectionEmpty()){
			selectedOrgao =null;
		}
		else {
			selectedOrgao = listOrgao.getSelection().iterator().next();
		}
	}

	
	 public void visualizarUtilizador(){
		 List <Utilizador> ut = dao.findAll();
		 listUtilizador = new ListModelList <Utilizador> (ut); 
		 lb_utilizador.setModel(listUtilizador);
		
	 }
	 
	 public void limparCampos(){
		 tb_nome.setRawValue(null);
		 tb_password.setRawValue(null);

		 selectedUtilizador=null;
		 
	 }
	 
	 
	 private void refreshOrgaoDetail() {
			tb_nome.setValue(selectedUtilizador.getNome());
			tb_password.setValue(selectedUtilizador.getPassword());

		}
	 
		public void preencherOrgaos(){
		  	List <Orgao> org = d.findAll();
	    	listOrgao = new ListModelList <Orgao> (org);
	    	cbb_orgao.setModel(listOrgao);
		}

}
