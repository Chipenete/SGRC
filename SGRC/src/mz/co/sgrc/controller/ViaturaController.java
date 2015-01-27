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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ViaturaController extends SelectorComposer<Component>{
	
	@Wire
	private Textbox txt_marca;
	@Wire
	private Textbox txt_matricula;
	@Wire
	private Combobox cbb_orgao;
	@Wire
	private Button btn_registar;
	@Wire
	private Button btn_cancelar;
	@Wire
	private Listbox lb_viatura;
	@Wire
	private ViaturaDAO dao;
	@Wire
	private OrgaoDAO d;
	
	
	Window win=new Window();
	private ListModelList <Viatura> listViatura;
	private ListModelList <Orgao> listOrgao;
	private Viatura viatura;
	
	
	
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		visualizarViaturas();
		preencherOrgaos();
		
	}
	
	public ViaturaController(){
		dao = new ViaturaDAO();
		d = new OrgaoDAO();
	}
	
	@Listen("onClick = #btn_registar")
	public void onClickGuardar(){
		Viatura v = new Viatura();
		v.setMarca(txt_marca.getValue());
		v.setMatricula(txt_matricula.getValue().toUpperCase());
		dao.create(v);
		visualizarViaturas();
		limparCampos();
		Clients.showNotification("Inserido com sucesso", "info", win, "middle_center", 4000);
		
	}
	
	@Listen("onClick = #btn_cancelar")
	public void onClickCancelar(){
		
		limparCampos();
	}
	
	
	
	public void visualizarViaturas(){
		List <Viatura> viat = dao.findAll();
		listViatura = new ListModelList<Viatura>(viat);
		lb_viatura.setModel(listViatura);
		
	}
	
	public void limparCampos(){
		txt_marca.setRawValue(null);
		txt_matricula.setRawValue(null);
		cbb_orgao.setRawValue(null);
	}
	
	public void preencherOrgaos(){
	  	List <Orgao> org = d.findAll();
    	listOrgao = new ListModelList <Orgao> (org);
    	cbb_orgao.setModel(listOrgao);
	}

}
