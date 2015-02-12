package mz.co.sgrc.controller;

import java.util.List;

import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.UtilizadorDAO;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.Utilizador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class UtilizadorController extends GenericForwardComposer{

	private Textbox tb_nome;
	private Textbox tb_password;
	private Listbox lb_utilizador;
	private Button btn_gravar;
	private Button btn_actualizar;
	private Button btn_cancelar;
	private Combobox cbb_orgao;
	
	private ListModelList <Utilizador> _listModelUtilizador;
	private ListModelList <Orgao> _listModelOrgao;

	Utilizador _selectedUtilizador;
	private UtilizadorDAO _utilizadorDAO;
	private OrgaoDAO _orgaoDAO;

	 Orgao _selectedOrgao;
	 Window _win;
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		_utilizadorDAO = new UtilizadorDAO();
		_orgaoDAO = new OrgaoDAO();
		preencherOrgaos();
		visualizarUtilizador();
	   _selectedOrgao = null;
	}
	
	public UtilizadorController (){}

	
    public void onClick$btn_gravar(Event e){
		Utilizador user = new Utilizador();
		user.setNome(tb_nome.getValue());
		user.setPassword(tb_password.getValue());
		user.setOrgao(_selectedOrgao);
		_utilizadorDAO.create(user);
		visualizarUtilizador();
	    Clients.showNotification("Utilizador criado com sucesso", "info",_win, "middle_center", 4000, true);
		limparCampos();
	   _selectedOrgao=null;
	}
	
    public void onClick$btn_actualizar(Event e){
		if(_selectedUtilizador != null){
			_selectedUtilizador.setNome(tb_nome.getText());
			_selectedUtilizador.setPassword(tb_password.getText());
			_selectedUtilizador.setOrgao(_selectedOrgao);
			_utilizadorDAO.update(_selectedUtilizador);
			limparCampos();
			 Clients.showNotification("Utilizador actualizado com sucesso", "info",_win, "middle_center", 4000, true);
		}
		
	}
	
   public void onClick$btn_cancelar(Event e){
      limparCampos();
	}
	
	
    public void onSelect$lbx_utilizador(Event e){
		if(_listModelUtilizador.isSelectionEmpty())
		_selectedUtilizador = null;
		else {
			_selectedUtilizador = _listModelUtilizador.getSelection().iterator().next();
		}
		refreshOrgaoDetail();	
	}
	
    public void onSelect$cbx_orgao(Event e){
		if(_listModelOrgao.isSelectionEmpty()){
			_selectedOrgao =null;
		}
		else {
			_selectedOrgao = _listModelOrgao.getSelection().iterator().next();
		}
	}

	
	 public void visualizarUtilizador(){
		 List <Utilizador> ut = _utilizadorDAO.findAll();
		 _listModelUtilizador = new ListModelList <Utilizador> (ut); 
		 lb_utilizador.setModel(_listModelUtilizador);
		
	 }
	 
	 public void limparCampos(){
		 tb_nome.setRawValue(null);
		 tb_password.setRawValue(null);
         _selectedUtilizador=null;	 
	 }
	 
	 
	 private void refreshOrgaoDetail() {
			tb_nome.setValue(_selectedUtilizador.getNome());
			tb_password.setValue(_selectedUtilizador.getPassword());

		}
	 
		public void preencherOrgaos(){
		  	List <Orgao> org = _orgaoDAO.findAll();
	    	_listModelOrgao = new ListModelList <Orgao> (org);
	    	cbb_orgao.setModel(_listModelOrgao);
		}

}
