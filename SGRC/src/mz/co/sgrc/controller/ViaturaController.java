package mz.co.sgrc.controller;

import java.util.List;

import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.ViaturaDAO;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.TipoCombustive;
import mz.co.sgrc.model.Viatura;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ViaturaController extends GenericForwardComposer{
	
	
	private Textbox txt_marca;
	private Textbox txt_matricula;
	private Combobox cbx_orgao;
	private Button btn_registar;
	private Button btn_cancelar;
	private Listbox lbx_viatura;
	private ViaturaDAO viaturaDAO;
	private OrgaoDAO orgaoDAO;
	
	
	Window win=new Window();
	private ListModelList <Viatura> listViatura;
	private ListModelList <Orgao> listOrgao;
	private Viatura viatura;
	
	
	
	@Override
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		visualizarViaturas();
		preencherOrgaos();
		
	}
	
	public ViaturaController(){
		viaturaDAO = new ViaturaDAO();
		orgaoDAO = new OrgaoDAO();
	}
	
	
	public void onClick$btn_registar(){
		Viatura v = new Viatura();
		v.setMarca(txt_marca.getValue());
		v.setMatricula(txt_matricula.getValue().toUpperCase());
		viaturaDAO.create(v);
		visualizarViaturas();
		limparCampos();
		Clients.showNotification("Inserido com sucesso", "info", win, "middle_center", 4000);
		
	}
	
	public void onClick$btn_cancelar(){
		
		limparCampos();
	}
	
	
	
	public void visualizarViaturas(){
		List <Viatura> viat = viaturaDAO.findAll();
		listViatura = new ListModelList<Viatura>(viat);
		lbx_viatura.setModel(listViatura);
		
	}
	
	public void limparCampos(){
		txt_marca.setRawValue(null);
		txt_matricula.setRawValue(null);
		cbx_orgao.setRawValue(null);
	}
	
	public void preencherOrgaos(){
	  	List <Orgao> org = orgaoDAO.findAll();
    	listOrgao = new ListModelList <Orgao> (org);
    	cbx_orgao.setModel(listOrgao);
	}

}
