package mz.co.sgrc.controller;

import mz.co.sgrc.dao.UtilizadorDAO;
import mz.co.sgrc.model.Utilizador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

public class LoginController extends SelectorComposer<Component>{
	
	@Wire
	private Textbox tb_nome;
	@Wire
	private Textbox tb_password;
	@Wire
	private Button btn_entrar;
	
	private UtilizadorDAO utilizadorDAO;
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		
	}
	
	
	public LoginController(){
		utilizadorDAO = new UtilizadorDAO();
	}

	
	@Listen("onClick = #btn_entrar")
	public void onClickEntrar(){
		Utilizador ut = utilizadorDAO.findById((long)3);
		if (ut.getPassword().equals("huo")){
			
			Executions.sendRedirect("/Principal.zul");
		}
		
	}
}
