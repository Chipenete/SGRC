package mz.co.sgrc.controller;


import java.util.List;

import mz.co.sgrc.dao.CombustiveDAO;
import mz.co.sgrc.dao.FornecedorDao;
import mz.co.sgrc.model.Combustive;
import mz.co.sgrc.model.Fornecedor;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class FornecedorController extends GenericForwardComposer {
	

	private Textbox designacao ;
	private Textbox email;
	private Textbox endereco;
	private Textbox  descricao;
	private Textbox txt_pesquisar;
	
	private Intbox telefone1;
	private Intbox telefone2;
	
	private Button btn_gravar;
	private Button  btn_cancelar;
	private Button btn_actualizar;
	private Button btn_pesquisar;
	
	private Listbox lb_fornecedor;
	
	private FornecedorDao dao;
	
	Window win= new Window();
	Fornecedor selectedFornecedor;
	ListModelList<Fornecedor> combModel;

	@Override
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		visualizaFornecedorTabela();
	}
	
	public FornecedorController (){
		dao = new FornecedorDao();
	}
	
	
	public void onClick$btn_gravar(){
		
		
		Fornecedor forn= new Fornecedor();
		forn.setDesignacao(designacao.getText());
		forn.setEndereco(endereco.getText());
		forn.setEmail(email.getText());
		forn.setTelefone1(telefone1.getValue());
		forn.setTelefone2(telefone2.getValue());
		forn.setDescricao(descricao.getText());
		dao.create(forn);
		
		Clients.showNotification("Inserido com sucesso", "info", win, "middle_center", 4000);
		limparCampos();
		visualizaFornecedorTabela();
		
		
	}
	
	

	public void limparCampos() {
		designacao.setRawValue(null);
		endereco.setRawValue(null);
		email.setRawValue(null);
		telefone1.setRawValue(null);
		telefone2.setRawValue(null);
		descricao.setRawValue(null);
		
		
	}
	
	public void visualizaFornecedorTabela(){
		
		List <Fornecedor> fornece = dao.findAll();
		combModel= new ListModelList<Fornecedor>(fornece);
		lb_fornecedor.setModel(combModel);
		
		
	}
	
	
	public void onClick$btn_actualizar(){
		if(selectedFornecedor != null){
			selectedFornecedor.setDescricao(descricao.getText());
			selectedFornecedor.setEndereco(endereco.getText());
			selectedFornecedor.setEmail(email.getText());
			selectedFornecedor.setTelefone1(telefone1.getValue());
			selectedFornecedor.setTelefone2(telefone2.getValue());
			selectedFornecedor.setDescricao(descricao.getText());
			dao.update(selectedFornecedor);
			limparCampos();
			visualizaFornecedorTabela();
			Clients.showNotification("Actualizado com sucesso", "info", win, "middle_center", 2000);
			}
			else
				Messagebox.show("Nenhum fornecedor foi seleccionado");
		}
	
	
	
	public void onClick$btn_cancelar(){
		
		limparCampos();
	
	}
	
	
	public void onSelect$lb_fornecedor(){
		if(combModel.isSelectionEmpty())
			selectedFornecedor=null;
		else
		{
			selectedFornecedor= combModel.getSelection().iterator().next();
		}
		
		refreshFornecedorDetail();
		
	}
	
	
	public void refreshFornecedorDetail(){
		designacao.setText(selectedFornecedor.getDesignacao());
		email.setText(selectedFornecedor.getEmail());
		endereco.setText(selectedFornecedor.getEndereco());
		telefone1.setValue(selectedFornecedor.getTelefone1());
		telefone2.setValue(selectedFornecedor.getTelefone2());
		descricao.setText(selectedFornecedor.getDescricao());
	}
	
	
	public void onClick$btn_eliminar(){
		
		if(selectedFornecedor!=null){
			dao.delete(selectedFornecedor);
			limparCampos();
			visualizaFornecedorTabela();
		}
		else
			Messagebox.show("selecione um fornecedor");
		
	}
	
	
	
	public void onClick$btn_pesquisar(){
			String designacao= txt_pesquisar.getText();
			List<Fornecedor> fornecedores=dao.pesquisaFornecedor(designacao);
			combModel= new ListModelList<Fornecedor> (fornecedores);
			lb_fornecedor.setModel(combModel);
			
		
		
		
	}
	
}


