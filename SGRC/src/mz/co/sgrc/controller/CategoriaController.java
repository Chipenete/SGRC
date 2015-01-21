package mz.co.sgrc.controller;

import java.util.List;

import mz.co.sgrc.dao.CategoriaDao;
import mz.co.sgrc.model.Categoria;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class CategoriaController extends SelectorComposer<Component> {
	
	@Wire
	private Textbox tb_nome;
	@Wire
	private Listbox lb_categoria;
	@Wire
	private CategoriaDao dao;
	@Wire
	private Button bt_novo;
	@Wire
	private Button bt_guardar;
	@Wire
	private Button bt_alterar;
	@Wire
	private Button bt_eliminar;
	
	private ListModelList<Categoria> catModel;
	private Categoria selectedCategoria;
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		visualizaCategoria();
	}
	
	public CategoriaController (){
		dao = new CategoriaDao();
	}
	
	@Listen ("onClick = #bt_guardar")
	public void onClickGuardar (){
		Categoria cat = new Categoria();
		cat.setNome(tb_nome.getText());
		dao.create(cat);
		limpaCampos();
		visualizaCategoria();
	}
	
	
private void visualizaCategoria() {
		List <Categoria> categorias = dao.findAll();
		catModel = new ListModelList<>(categorias);
		lb_categoria.setModel(catModel);
	}

@Listen ("onClick = #bt_alterar")
public void onClickAlterar (){
	if(selectedCategoria !=null){
		selectedCategoria.setNome(tb_nome.getText());
		dao.update(selectedCategoria);
		limpaCampos();
		visualizaCategoria();}
	else {
		Messagebox.show("Não foi possivel alterar a categoria");
	}
	}
@Listen ("onClick = #bt_eliminar")
public void onClickEliminar (){
	dao.delete(selectedCategoria);
	limpaCampos();
	visualizaCategoria();
}

@Listen ("onClick = #bt_novo")
public void onClickNovo (){
	limpaCampos();
}

@Listen ("onSelect = #lb_categoria")
public void doCategoriaSelect (){
	if(catModel.isSelectionEmpty()){
	selectedCategoria = null;
	}
	
	else {
		selectedCategoria = catModel.getSelection().iterator().next();
	}
	
	refreshCategoriaDetail();
	
}

private void refreshCategoriaDetail() {
	tb_nome.setValue(selectedCategoria.getNome());
}

private void limpaCampos () {
	tb_nome.setValue(null);
	selectedCategoria =null;
}
}
