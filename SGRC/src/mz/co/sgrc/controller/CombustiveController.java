package mz.co.sgrc.controller;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.co.sgrc.dao.CategoriaDao;
import mz.co.sgrc.dao.CombustiveDAO;
import mz.co.sgrc.dao.FornecedorDao;
import mz.co.sgrc.dao.ProdutoDao;
import mz.co.sgrc.dao.QuantidadeFinalDAO;
import mz.co.sgrc.dao.TipoCombustiveDAO;


import mz.co.sgrc.model.Categoria;
import mz.co.sgrc.model.Combustive;
import mz.co.sgrc.model.Fornecedor;
import mz.co.sgrc.model.Produto;
import mz.co.sgrc.model.QuantidadeFinal;
import mz.co.sgrc.model.TipoCombustive;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class CombustiveController extends GenericForwardComposer{
	
	private Textbox txt_quantidadeGasolina;
	private Textbox txt_quantidadeGasoleo;
	private Textbox txt_quantidadeGas;
	private Doublebox dbx_quantidade;
	private Textbox txt_tipo;
	private Combobox cbx_tipoCombustive;
	private Combobox cbx_fornecedor;
	private Button btn_novo;
	private Button btn_guardar;
	private Button btn_actualizar;
	private Button btn_cancelar;
	private Listbox lbx_cota;
	private Listbox lbx_combustive;
	private CombustiveDAO _combustiveDao;
	private TipoCombustiveDAO _tipoCombustiveDao;
	private FornecedorDao _fornecedorDao;
	private QuantidadeFinalDAO _quantidadeFinalDao;
	private boolean _novoCombustive;
	
	Fornecedor _selectedFornecedor;
    Combustive _selectedCombustive;
    TipoCombustive _selectedTipoCombustive;
    ListModelList <Fornecedor> _listModelFornecedor;
	ListModelList <Combustive> _listModelCombustive;
	ListModelList <TipoCombustive> _listModelTipoCombustive;
    ListModelList<Combustive> _listModelCombustive1;

	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		_combustiveDao = new CombustiveDAO();
		_tipoCombustiveDao = new TipoCombustiveDAO();
		_fornecedorDao = new FornecedorDao();
		_quantidadeFinalDao = new QuantidadeFinalDAO();
		
		visualizaCombustive();
		listarTipoCombustive();
		prencherFornecedor();
		preencherQuantidadeFinal();
		_selectedTipoCombustive = null;
		_selectedCombustive = null;
		 
	}
	
	public CombustiveController (){}
	
	
    //*************************************************************************EVENTOS*****************************************************************************

	public void onClick$btn_guardar(Event e){
		
		QuantidadeFinal quantidadeFinal = new QuantidadeFinal();	
		quantidadeFinal = _quantidadeFinalDao.findById((long)1);
		
		if(_selectedTipoCombustive.getDesignacao().equals("Gasolina")){
			quantidadeFinal.setQuantidadeGasolina(quantidadeFinal.getQuantidadeGasolina()+dbx_quantidade.getValue());
		}
		else if(_selectedTipoCombustive.getDesignacao().equals("Gasoleo")){
			quantidadeFinal.setQuantidadeGasoleo(quantidadeFinal.getQuantidadeGasoleo()+dbx_quantidade.getValue());
		}
		else if(_selectedTipoCombustive.getDesignacao().equals("Gas")){
			quantidadeFinal.setQuantidadeGas(quantidadeFinal.getQuantidadeGas()+dbx_quantidade.getValue());
		}
		
		_quantidadeFinalDao.update(quantidadeFinal);
		
		Combustive combustive= new Combustive();
		combustive.setQuantidade(dbx_quantidade.getValue());
		combustive.setTipoCombustive(_selectedTipoCombustive);
		combustive.setTcA(_selectedTipoCombustive.getDesignacao());
		combustive.setFornecedor(cbx_fornecedor.getText());
		
		Date data = new Date();
		
		combustive.setData(data);
		
		_combustiveDao.create(combustive);
		
		Messagebox.show("Inserido com sucesso");
		_selectedTipoCombustive = null;	
	
		visualizaCombustive();
		limparCampos();
		preencherQuantidadeFinal();
	}
	


    public void onClick$btn_cancelar(Event e){
    	limparCampos();
    }
	

    public void onClick$btn_actualizar(Event e){
    		_selectedCombustive.setQuantidade(dbx_quantidade.getValue());
    		_selectedCombustive.setTcA(cbx_tipoCombustive.getText());
    		_selectedCombustive.setFornecedor(cbx_fornecedor.getText());
    		_combustiveDao.update(_selectedCombustive);
    		visualizaCombustive();
    		limparCampos();	
    }
    
    public void onSelect$lbx_combustive(Event e){
      if(_listModelCombustive.isSelectionEmpty())
    	  _selectedCombustive = null;
      else{
    	  
    	  _selectedCombustive = _listModelCombustive.getSelection().iterator().next();  	
    	  cbx_tipoCombustive.setText(_selectedCombustive.getTcA());
    	  cbx_fornecedor.setText(_selectedCombustive.getFornecedor());
    	  dbx_quantidade.setValue(_selectedCombustive.getQuantidade());
      }
		
   
	}
	
    public void onSelect$cbx_tipoCombustive(Event e){
		if(_listModelTipoCombustive.isSelectionEmpty())
			_selectedTipoCombustive = null;
		else
			_selectedTipoCombustive = _listModelTipoCombustive.getSelection().iterator().next();
	}
	
    public void onSelect$cbb_fornecedor(Event e){
		if(_listModelFornecedor.isSelectionEmpty())
			_selectedFornecedor= null;
		else
			_selectedFornecedor = _listModelFornecedor.getSelection().iterator().next();
	}
	
	
    public void onClick$btn_novo(Event e){
		Executions.createComponents("/TipoCombustivell.zul", null,null);	
	}
	
	
    
    //*************************************************************************METODOS*****************************************************************************
    private void visualizaCombustive() {
		List <Combustive> _listCombustive = _combustiveDao.findAll();
		_listModelCombustive = new ListModelList<Combustive>(_listCombustive);
		lbx_combustive.setModel(_listModelCombustive);
	}
    
    public void listarTipoCombustive(){
    	List <TipoCombustive> list =  _tipoCombustiveDao.findAll();
    	_listModelTipoCombustive = new ListModelList <TipoCombustive> (list);
    	cbx_tipoCombustive.setModel(_listModelTipoCombustive);	
    }
    
    public void prencherFornecedor(){
    	List <Fornecedor> _listFornecedor = _fornecedorDao.findAll();
    	_listModelFornecedor = new ListModelList<Fornecedor>(_listFornecedor);
    	cbx_fornecedor.setModel(_listModelFornecedor);
    }
    
    public void refresh(){
    	dbx_quantidade.setValue(_selectedCombustive.getQuantidade());
    	cbx_tipoCombustive.setText(_selectedCombustive.getTcA());
    	cbx_fornecedor.setText(_selectedCombustive.getFornecedor());
    }
    
    
    private void limparCampos() {
    	dbx_quantidade.setRawValue(null);
    	cbx_tipoCombustive.setRawValue(null);
    	cbx_fornecedor.setRawValue(null);	
	}
    
    public void preencherQuantidadeFinal(){
    	QuantidadeFinal quantidadeFinal = _quantidadeFinalDao.findById((long)1);
    	txt_quantidadeGasolina.setValue(""+quantidadeFinal.getQuantidadeGasolina());
    	txt_quantidadeGasoleo.setValue(""+quantidadeFinal.getQuantidadeGasoleo());
    	txt_quantidadeGas.setValue(""+quantidadeFinal.getQuantidadeGas());
    }
    

}
