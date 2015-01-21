package mz.co.sgrc.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mz.co.sgrc.dao.CategoriaDao;
import mz.co.sgrc.dao.ProdutoDao;
import mz.co.sgrc.model.Categoria;
import mz.co.sgrc.model.Produto;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class ProdutoController extends SelectorComposer<Component>{

	@Wire
	private Textbox txt_nome;
	@Wire
	private Textbox txt_pesquisa;
	
	@Wire
	private Button btn_pesquisa;
	@Wire
	private Intbox itx_codigo;
	@Wire
	private Doublebox dbx_preco;
	@Wire
	private Combobox cbb_categoria;
	@Wire
	private Listbox lst_prods;
	
	@Wire
	private Intbox itx_stock;
	
	@Wire
	private Button btn_registar;
	
	@Wire
	private Button btn_novo;
	
	@Wire
	private Button btn_alterar;
	@Wire
	private Button btn_eliminar;
	
	ListModelList<Produto> produtoModel;
	Produto selectedProduto;
	private ProdutoDao dao;
	
	private CategoriaDao catDao;
	Categoria selectedCategoria;
	ListModelList<Categoria> categoriaModel;
	
	private boolean novaCategoria;
	
	public ProdutoController () {
		dao = new ProdutoDao();
		catDao = new CategoriaDao();
		novaCategoria = false;
	}
	
	@Override
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		preencheCategoria();
		visualizaProdutos();
	}
	
	@Listen ("onClick = #btn_registar")
	public void onClickRegist() {
		Produto prod = new Produto();
		prod.setNome(txt_nome.getText());
		prod.setCod(itx_codigo.getValue());
		prod.setPrecoUnitario(dbx_preco.getValue());
		prod.setCategoria(selectedCategoria);
		prod.setStock(itx_stock.getValue());
		dao.create(prod);
		
		visualizaProdutos();
		limparCampos();
	}
	
	private void visualizaProdutos (){
		List <Produto> produtos = dao.findAll();
		produtoModel = new ListModelList<Produto> (produtos);
		lst_prods.setModel(produtoModel);
		
	}
	
	@Listen ("onSelect = #lst_prods")
	public void doProdutoSelect () {
		if( produtoModel.isSelectionEmpty()){
			selectedProduto = null;
		}
		else{
			selectedProduto = produtoModel.getSelection().iterator().next();
		}
		
		refreshDetailView();
			
	}
	
	private void inserirProdutoLista(Produto produto){
		Listitem item = new Listitem();
		item.setValue(produto);
		Listcell cell1 = new Listcell(String.valueOf(produto.getCod()));
		Listcell cell2 = new Listcell(produto.getNome());
		Listcell cell3 = new Listcell(String.valueOf(produto.getPrecoUnitario()));
		Listcell cell4 = new Listcell(String.valueOf(produto.getStock()));
		Listcell cell5 = new Listcell(produto.getCategoria().getNome());
		item.appendChild(cell1);
		item.appendChild(cell2);
		item.appendChild(cell3);
		item.appendChild(cell4);
		item.appendChild(cell5);
		lst_prods.appendChild(item);
	}
	
	private void limparCampos() {
		txt_nome.setRawValue(null);
		itx_codigo.setRawValue(null);
		dbx_preco.setRawValue(null);
		cbb_categoria.setSelectedIndex(-1);
		itx_stock.setRawValue(null);
		preencheCategoria();
	}
	
	private void refreshDetailView (){
		if (selectedProduto == null){
			txt_nome.setValue(null);
			itx_codigo.setValue(null);
			itx_stock.setValue(null);
			dbx_preco.setValue(null);
			cbb_categoria.setSelectedIndex(-1);
		}
		else {
			txt_nome.setValue(selectedProduto.getNome());
			itx_codigo.setValue(selectedProduto.getCod());
			itx_stock.setValue(selectedProduto.getStock());
			dbx_preco.setValue(selectedProduto.getPrecoUnitario());
			selectedCategoria = selectedProduto.getCategoria();
			categoriaDoProduto();
			/*Comboitem item = new Comboitem(selectedCategoria.getNome());
			cbb_categoria.appendChild(item);
			cbb_categoria.setSelectedItem(item);*/
		}
	}
	
	@Listen ("onClick = #btn_novo")
	public void novo (){
		selectedProduto = null;
		limparCampos();
	}
	
	@Listen("onClick = #btn_eliminar")
	public void removeProduto (){
		dao.delete(selectedProduto);
		visualizaProdutos();
		limparCampos();
	}
	
	@Listen("onClick = #btn_alterar")
	public void alteraProduto() {
		selectedProduto.setNome(txt_nome.getText());
		selectedProduto.setPrecoUnitario(dbx_preco.getValue());
		selectedProduto.setCategoria(selectedCategoria);
		selectedProduto.setStock(itx_stock.getValue());
		dao.update(selectedProduto);
		visualizaProdutos();
		limparCampos();
	}
	
	public void preencheCategoria (){
		List<Categoria> categorias = catDao.findAll();
		categoriaModel = new ListModelList<Categoria>(categorias);
		cbb_categoria.setModel(categoriaModel);
	}
	
	@Listen ("onSelect = #cbb_categoria")
	public void doCategoriaSelected (){
		if(categoriaModel.isSelectionEmpty()){
			selectedCategoria =null;
		}
		else {
			selectedCategoria = categoriaModel.getSelection().iterator().next();
		}
	}
	
	@Listen ("onOpen = #cbb_categoria")
	public void comboAberto(){
		if(novaCategoria)
		preencheCategoria();
		novaCategoria =false;
	}
	
	@Listen ("onClick = #btn_addCat")
	public void addCat () {
		novaCategoria =true;
		Executions.createComponents("/categoria.zul", null, null);
	}
	
	private void categoriaDoProduto (){
		int indexSelecao =-1,cont=-1;
		int id_selecao = selectedCategoria.getId();
		for (Iterator iterator = categoriaModel.iterator(); iterator.hasNext();) {
			Categoria cat = (Categoria) iterator.next();
			cont++;
			if (cat.getId()==id_selecao){
				cbb_categoria.setSelectedIndex(cont);
			}
		}
	}
	
	
	@Listen ("onClick = #btn_pesquisa")
	public void pesquisar () {
		String nome = txt_pesquisa.getText();
		List <Produto> produtos = dao.pesquisaProdutos(nome);
		produtoModel = new ListModelList<Produto> (produtos);
		lst_prods.setModel(produtoModel);
	}
}