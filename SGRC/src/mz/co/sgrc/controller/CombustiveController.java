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
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class CombustiveController extends SelectorComposer<Component>{
	
	
	@Wire
	private Textbox tb_quantidadeGasolina;
	
	@Wire
	private Textbox tb_quantidadeGasoleo;
	
	@Wire
	private Textbox tb_quantidadeGas;
	
	@Wire
	private   Doublebox db_quantidade;
	
	@Wire
	private Textbox tb_tipo;
	
	@Wire
	private Combobox cbb_tipo;
	
	@Wire
	private Combobox cbb_fornecedor;
	
	@Wire
	private Button btn_novo;
	
	@Wire
	private Button btn_guardar;
	
	@Wire
	private Button btn_actualizar;
	
	@Wire
	private Button btn_cancelar;
	
	@Wire
	private CombustiveDAO dao;
	
	@Wire
	private TipoCombustiveDAO d;
	
	@Wire
	private FornecedorDao daofor;
	
	@Wire
	private QuantidadeFinalDAO quantidadeFinalDAO;
	
	@Wire 
	private Listbox lb_cota;
	
	@Wire 
	private Listbox lb_combustive;
	
	Fornecedor selectedFornecedor;
    Combustive selectedCombustive;
    TipoCombustive selectedTipoCombustive;
    ListModelList <Fornecedor> listFornecedor;
	ListModelList <Combustive> combbModel;
	ListModelList <TipoCombustive> listTipoCombustive;
	
	private boolean novoCombustive;
	

	
	private ListModelList<Combustive> combModel;

	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		visualizaCombustive();
		listarTipoCombustive();
		prencherFornecedor();
		preencherQuantidadeFinal();
		selectedTipoCombustive = null;
		selectedCombustive = null;
		//btn_guardar.setVisible(false); 
	}
	
	public CombustiveController (){
		dao = new CombustiveDAO();
		d = new TipoCombustiveDAO();
		daofor = new FornecedorDao();
		quantidadeFinalDAO = new QuantidadeFinalDAO();
		
	}
	
	@Listen ("onClick = #btn_guardar")
	public void onClickGuardar (){
		
		QuantidadeFinal quantidadeFinal = new QuantidadeFinal();	
		quantidadeFinal = quantidadeFinalDAO.findById((long)1);
		
		if(selectedTipoCombustive.getDesignacao().equals("Gasolina")){
			quantidadeFinal.setQuantidadeGasolina(quantidadeFinal.getQuantidadeGasolina()+db_quantidade.getValue());
		}
		else if(selectedTipoCombustive.getDesignacao().equals("Gasoleo")){
			quantidadeFinal.setQuantidadeGasoleo(quantidadeFinal.getQuantidadeGasoleo()+db_quantidade.getValue());
		}
		else if(selectedTipoCombustive.getDesignacao().equals("Gas")){
			quantidadeFinal.setQuantidadeGas(quantidadeFinal.getQuantidadeGas()+db_quantidade.getValue());
		}
		
		quantidadeFinalDAO.update(quantidadeFinal);
		
		Combustive cb = new Combustive();
		cb.setQuantidade(db_quantidade.getValue());
		cb.setTipoCombustive(selectedTipoCombustive);
		cb.setTcA(selectedTipoCombustive.getDesignacao());
		cb.setFornecedor(cbb_fornecedor.getText());
		
		Date data = new Date();
		
		cb.setData(data);
		
		dao.create(cb);
		
		Messagebox.show("Inserido com sucesso");
		
	
		visualizaCombustive();
		selectedTipoCombustive = null;
		onClickCancelar();
		preencherQuantidadeFinal();
	}
	


    @Listen ("onClick = #btn_cancelar")
    public void onClickCancelar(){
    	db_quantidade.setRawValue(null);
    	cbb_tipo.setRawValue(null);
    	cbb_fornecedor.setRawValue(null);
    }
	
    
    @Listen ("onClick = #btn_actualizar")
    public void onClickActualizar(){
 
    		//selectedCombustive.setQuantidade(db_quantidade.getValue()+selectedCombustive.getQuantidade());
    		selectedCombustive.setQuantidade(db_quantidade.getValue());
    		selectedCombustive.setTcA(cbb_tipo.getText());
    		selectedCombustive.setFornecedor(cbb_fornecedor.getText());
    		
    		dao.update(selectedCombustive);
    		visualizaCombustive();
    		onClickCancelar();
    	
    }
    
	@Listen ("onSelect = #lb_combustive")
	public void doCombustiveSelect () {
      if(combModel.isSelectionEmpty()){
    	  
    	  selectedCombustive = null;
      }
      else{
    	  
    	  selectedCombustive = combModel.getSelection().iterator().next();  	
    	  cbb_tipo.setText(selectedCombustive.getTcA());
    	  cbb_fornecedor.setText(selectedCombustive.getFornecedor());
    	  db_quantidade.setValue(selectedCombustive.getQuantidade());
      }
		
   
	}
	
	@Listen("onSelect = #cbb_tipo")
	public void onSelectTipo(){
		if(listTipoCombustive.isSelectionEmpty()){
			selectedTipoCombustive = null;
		}
		else{
			
			selectedTipoCombustive = listTipoCombustive.getSelection().iterator().next();
		}
		
	}
	
	@Listen("onSelect = #cbb_fornecedor")
	public void onSelectFornecedor(){
		if(listFornecedor.isSelectionEmpty())
			selectedFornecedor= null;
		else
			selectedFornecedor = listFornecedor.getSelection().iterator().next();
		
	}
	
	
	@Listen("onClick = #btn_novo")
	public void onClickNovo(){
		
		
		Executions.createComponents("/TipoCombustivell.zul", null,null);
		
	}
	
	

    private void visualizaCombustive() {
		List <Combustive> categorias = dao.findAll();
		combModel = new ListModelList<>(categorias);
		lb_combustive.setModel(combModel);
	}
    
    public void listarTipoCombustive(){
    	
    	List <TipoCombustive> combs = d.findAll();
    	listTipoCombustive = new ListModelList <TipoCombustive> (combs);
    	cbb_tipo.setModel(listTipoCombustive);
		
    	
    }
    
    public void prencherFornecedor(){
    	List <Fornecedor> forn = daofor.findAll();
    	listFornecedor = new ListModelList<Fornecedor>(forn);
    	cbb_fornecedor.setModel(listFornecedor);
    }
    
    public void refresh(){
    	
    	db_quantidade.setValue(selectedCombustive.getQuantidade());
    	cbb_tipo.setText(selectedCombustive.getTcA());
    	cbb_fornecedor.setText(selectedCombustive.getFornecedor());
    }
    
    
    public void preencherQuantidadeFinal(){
    	QuantidadeFinal quantidadeFinal = quantidadeFinalDAO.findById((long)1);
    	tb_quantidadeGasolina.setValue(""+quantidadeFinal.getQuantidadeGasolina());
    	tb_quantidadeGasoleo.setValue(""+quantidadeFinal.getQuantidadeGasoleo());
    	tb_quantidadeGas.setValue(""+quantidadeFinal.getQuantidadeGas());
    }
    

}
