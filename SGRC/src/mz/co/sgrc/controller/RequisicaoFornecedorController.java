package mz.co.sgrc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.co.sgrc.dao.ConectandoBanco;
import mz.co.sgrc.dao.FornecedorDao;
import mz.co.sgrc.dao.ItemRequisicaoFornecedorDAO;
import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.RequisicaoFornecedorDAO;
import mz.co.sgrc.dao.TipoCombustiveDAO;
import mz.co.sgrc.dao.UtilizadorDAO;
import mz.co.sgrc.model.Fornecedor;
import mz.co.sgrc.model.Item;
import mz.co.sgrc.model.ItemRequisicaoFornecedor;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.Produto;
import mz.co.sgrc.model.Requisicao;
import mz.co.sgrc.model.RequisicaoFornecedor;
import mz.co.sgrc.model.TipoCombustive;
import mz.co.sgrc.model.Utilizador;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class RequisicaoFornecedorController extends GenericForwardComposer {

	ConectandoBanco conecta = new ConectandoBanco();
	private Connection con;
	
	private Doublebox dbx_quantidade;
	
	private Button btn_adicionar;
	private Button btn_requisitar;
	private Button btn_pesquisar;
	private Button btn_imprimir;
	
	private Textbox txt_responsavel;
	private Textbox txt_fornecedor;
	
	private Combobox cbx_combustivel;
	private Combobox cbx_fornecedor;
	
	private Listbox lb_requisicao;
	private Listbox lb_itemRequisicao;
	
	private RequisicaoFornecedorDAO rqDAO;
	private ItemRequisicaoFornecedorDAO itemDAO;
	private TipoCombustiveDAO dao;
	private FornecedorDao forDAO;
	private UtilizadorDAO u;
	private OrgaoDAO orgaoDAO;
	private RequisicaoFornecedorDAO requisicaoFornecedorDAO;
	
	private Utilizador ut;
	private Orgao orgao;
	
	Window win= new Window();
	Item item = new Item();
	Fornecedor selectedFornecedor = new Fornecedor();
	List <ItemRequisicaoFornecedor> lista;
	List <Item> listaItem = new ArrayList <>();
	Listitem list = new Listitem();
	Checkbox check, check1;
	ItemRequisicaoFornecedor itemRequisicao;
	TipoCombustive selectedTipoCombustive = new TipoCombustive();
	

	List <ItemRequisicaoFornecedor> lista2 = new ArrayList<ItemRequisicaoFornecedor>();
	
	
	ListModelList <ItemRequisicaoFornecedor> listItemFornecedor;
	ListModelList <RequisicaoFornecedor> listRequisicoes, listRequisicoesPesquisa;
	ListModelList <TipoCombustive> listCombustivel;
	ListModelList <Fornecedor> listFornecedor;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		prencherComboboxCombustivel();
		prencherComboboxFornecedor();
		prencherRequisicoes();
		btn_requisitar.setDisabled(true);	
		//conecta.conexao();
	}
	
	public RequisicaoFornecedorController(){
		itemDAO = new ItemRequisicaoFornecedorDAO();
		rqDAO = new RequisicaoFornecedorDAO();
		dao = new TipoCombustiveDAO();
		forDAO = new FornecedorDao();
		u = new UtilizadorDAO();
		orgaoDAO = new OrgaoDAO();
		requisicaoFornecedorDAO = new RequisicaoFornecedorDAO();
		
		
		ut = u.findById((long)1);
		
	}

	
  
   public void onSelect$cbx_fornecedor(Event e) {
			// TODO Auto-generated method stub
			if(listFornecedor.isSelectionEmpty()){
				selectedFornecedor =null;
			}
			else {
				//cbx_fornecedor.disableClientUpdate(true);
				selectedFornecedor = listFornecedor.getSelection().iterator().next();				
			}
		}
    	
    	
	@SuppressWarnings("unchecked")
	public void onClick$btn_adicionar(Event e) {
			item = new Item();
			item.setQuantidadeForn(dbx_quantidade.getValue());
			item.setCombustivelString(cbx_combustivel.getText());
			
			listaItem.add(item);
			
			   cbx_combustivel.addEventListener("onSelect", new EventListener(){
				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					if(listCombustivel.isSelectionEmpty())
						selectedTipoCombustive= null;
					else
						selectedTipoCombustive = listCombustivel.getSelection().iterator().next();
						
				}
				   
				   
			   });
					
			prencherItemRequisicao();
			limparCampos();
			cbx_fornecedor.setDisabled(true);
			btn_requisitar.setDisabled(false);
		}	
    
	
	
	@SuppressWarnings("unchecked")
	public void onClick$btn_requisitar(Event e) {
		listItemFornecedor =new ListModelList<>();
		
		lista = new ArrayList <ItemRequisicaoFornecedor>();
		
		final RequisicaoFornecedor reqFornecedor = new RequisicaoFornecedor();
	
		orgao = orgaoDAO.findById(ut.getOrgao().getId());
		
		reqFornecedor.setData(new Date());
		reqFornecedor.setResponsavel(ut.getNome());
		reqFornecedor.setOrgao(orgao);
		rqDAO.create(reqFornecedor);
		
		
		
		//criar os items de requisicao
		for (int i = 0; i < listaItem.size(); i++) {
			
			itemRequisicao = new ItemRequisicaoFornecedor();
			itemRequisicao.setCombustivelString(listaItem.get(i).getCombustivelString());
			itemRequisicao.setQuantidade(listaItem.get(i).getQuantidadeForn());
			itemRequisicao.setRequisicaoFornecedor(reqFornecedor);
			itemDAO.create(itemRequisicao);
			listItemFornecedor.add(itemRequisicao);
		}
		
			reqFornecedor.setListRequisicaoFornecedor(listItemFornecedor);
			reqFornecedor.setFornecedor(selectedFornecedor);
			rqDAO.update(reqFornecedor);
			
//			Listitem listaRequisicao = new Listitem();
//			new Listcell(""+reqFornecedor.getData()).setParent(listaRequisicao);
//			new Listcell(reqFornecedor.getResponsavel()).setParent(listaRequisicao);
//			new Listcell(reqFornecedor.getFornecedor().getDesignacao()).setParent(listaRequisicao);
//			
//			Listcell listcell = new Listcell();
//			final Checkbox cb_remessar = new Checkbox();
//			cb_remessar.setParent(listcell);
//			cb_remessar.setChecked(reqFornecedor.isRemessada());
//			listcell.setParent(listaRequisicao);
//			
//			
//			Listcell listcell1 = new Listcell();
//			final Button btn = new Button("Ver items");
//			//btn.setVisible(reqFornecedor.isRemessada());
//			btn.setParent(listcell1);
//			listcell1.setParent(listaRequisicao);
//			
//			lb_requisicao.appendChild(listaRequisicao);
//			
//			cb_remessar.addEventListener("onCheck", new EventListener(){
//
//				@Override
//				public void onEvent(Event arg0) throws Exception {
//					
//                   if(cb_remessar.isChecked()){
////                	   reqFornecedor.setRemessada(false);
////                	   check2.setChecked(false);
////                	   btn.setVisible(false);
////                	   rqDAO.update(reqFornecedor);
//                	   alert("sim");
//                   }
//                   else{ 
////                	   reqFornecedor.setRemessada(true);
////                	   check2.setChecked(true);
////                	   btn.setVisible(true);
////                	   rqDAO.update(reqFornecedor);
//                	   alert("nao");
//                   }
//				
//					
//				
//				}	
//					
//				});
//			
//			btn.addEventListener("onClick", new EventListener(){
//
//				@Override
//				public void onEvent(Event arg0) throws Exception {
//					
//					RequisicaoFornecedor requisicaoFornecedor = (RequisicaoFornecedor) Executions.getCurrent().getDesktop().getSession().setAttribute("requisicaoFornecedor", reqFornecedor);
//					
//					Executions.createComponents("/ItemRequisicaoFornecedor.zul",null,null);
//					
//					
//				}
//				
//				
//			});
		
		cbx_fornecedor.setDisabled(false);
		limparItemRequisicao();
		limparCampos();
	
		Clients.showNotification("Requisicao feita com sucesso", "info", win, "middle_center", 4000);
		
		btn_requisitar.setDisabled(true);
		lb_requisicao.getItems().clear();
		prencherRequisicoes();
	}
	

	
	private void limparCampos(){
	
		cbx_combustivel.setRawValue(null);
		dbx_quantidade.setRawValue(null);
		
	}
	
	
	
	
	private void prencherRequisicoes(){
		
		
		
		List <RequisicaoFornecedor> lRequisicaoFornecedor = rqDAO.findAllInverso();
		
		for (final RequisicaoFornecedor reqFornecedor: lRequisicaoFornecedor){
		
		
		Listitem listitem = new Listitem();
		new Listcell(""+reqFornecedor.getData()).setParent(listitem);
		new Listcell(reqFornecedor.getResponsavel()).setParent(listitem);
		new Listcell(reqFornecedor.getFornecedor().getDesignacao()).setParent(listitem);
		
		Listcell listcell = new Listcell();
		final Checkbox cb_remessar = new Checkbox();
		cb_remessar.setChecked(reqFornecedor.isRemessada());
		cb_remessar.setDisabled(reqFornecedor.isRemessada());
		cb_remessar.setParent(listcell);
		listcell.setParent(listitem);
		
		Listcell listcell1 = new Listcell();
		final Button btn_VErItens = new Button("Ver items");
		btn_VErItens.setVisible(reqFornecedor.isRemessada());
		btn_VErItens.setParent(listcell1);
		listcell1.setParent(listitem);
		
		Listcell listcell2 = new Listcell();
		final Button btn_Imprimir = new Button ("Imprimir");
		btn_Imprimir.setVisible(reqFornecedor.isRemessada());
		btn_Imprimir.setParent(listcell2);
		listcell2.setParent(listitem);

		lb_requisicao.appendChild(listitem);
		
		cb_remessar.addEventListener("onCheck", new EventListener(){

				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
                   if(cb_remessar.isChecked()){   
                	   reqFornecedor.setRemessada(true);
                	   cb_remessar.setChecked(true);
                	   btn_VErItens.setVisible(true);
                	   rqDAO.update(reqFornecedor);
                	   //alert("sim");
                   }
                   else{ 
                	   reqFornecedor.setRemessada(false);
                	   cb_remessar.setChecked(false);
                	   btn_VErItens.setVisible(false);
                	   rqDAO.update(reqFornecedor);
                	   //alert("nao");
                   }
				
					
				
				}	
					
				});
		
		
		btn_VErItens.addEventListener("onClick", new EventListener(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				RequisicaoFornecedor requisicaO = requisicaoFornecedorDAO.returnar(reqFornecedor);
				
				RequisicaoFornecedor requisicaoFornecedor = (RequisicaoFornecedor) Executions.getCurrent().getDesktop().getSession().setAttribute("requisicaoFornecedor", requisicaO);
				
				Executions.createComponents("/ItemRequisicaoFornecedor.zul",null,null);
				
				
			}
			
			
		});
		
		btn_Imprimir.addEventListener("onClick", new EventListener(){
			
			@Override
			public void onEvent(Event arg0) throws Exception{
				
				Map<String, Object> param = new HashMap<String,Object>();
				
				try{
				Class.forName("com.mysql.jdbc.Driver");
			    con = DriverManager.getConnection("jdbc:mysql://localhost/vendas","root",null);
				
			} catch(ClassNotFoundException e1){
				e1.printStackTrace();
				
			}
			
			try{
				
				String reporte = "C:/Users/Marcelo/report/RequisicaoFornecedor.jrxml";
		        
				param.put("codigo_requisicao",reqFornecedor.getId());
				
				JasperReport jasperReport = JasperCompileManager.compileReport(reporte);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, con);
				JasperViewer.viewReport(jasperPrint);
				con.close();
				
			}catch(JRException ex){
				ex.printStackTrace();
			}
				
			}
			
		});
		
		}
		
	}
	
	private void prencherComboboxCombustivel(){
		List <TipoCombustive> bustive = dao.findAll();
		listCombustivel = new ListModelList<> (bustive);
		cbx_combustivel.setModel(listCombustivel);
	}
	
	private void prencherComboboxFornecedor(){
		List <Fornecedor> fornece = forDAO.findAll();
		listFornecedor = new ListModelList <Fornecedor> (fornece);
		cbx_fornecedor.setModel(listFornecedor);
	}
	
	private void prencherItemRequisicao(){
		
		
		list= new Listitem();
		new Listcell(""+item.getQuantidadeForn()).setParent(list);
		new Listcell(cbx_combustivel.getText()).setParent(list);
		Listcell listcell = new Listcell();
		listcell.setParent(list);
		check= new Checkbox();
		check.setParent(listcell);
		
		
		check.addEventListener("onCheck", new EventListener(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
			for(int i=0; i<lista2.size(); i++){
				
				if(check.isChecked()){

					itemRequisicao.setRemessada(true);
				}
				else{

					itemRequisicao.setRemessada(false);
				}
				
			
			}
				}
			
		});
		
		list.setParent(lb_itemRequisicao);
	}
	
	
	public void onClick$btn_pesquisar(Event e) {
		String nome = txt_fornecedor.getText();
		List <RequisicaoFornecedor> requisicoes = rqDAO.pesquisaRequisicaoFornecedor(nome);
		listRequisicoesPesquisa = new ListModelList<RequisicaoFornecedor> (requisicoes);
		lb_requisicao.setModel(listRequisicoesPesquisa);
	}
	
	private void limparItemRequisicao(){
		int y= lb_itemRequisicao.getItemCount();
		for (int i = 0; i < y; i++) {
			lb_itemRequisicao.removeItemAt(0);
		}
		
	}
	
	public void onClick$btn_imprimir(Event e) throws SQLException, JRException{
		
		//Map<String, Object> param = new HashMap<String,Object>();
		
		try{
		Class.forName("com.mysql.jdbc.Driver");
	    con = DriverManager.getConnection("jdbc:mysql://localhost/vendas","root",null);
		
	} catch(ClassNotFoundException e1){
		e1.printStackTrace();
		
	}
	
	try{
		
		String reporte = "C:/Users/Marcelo/report/RequisicaoFornecedor.jrxml";
        
		//param.put("id_requisicaoFornecedor",((Long))1);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(reporte);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
		JasperViewer.viewReport(jasperPrint);
		con.close();
		
	}catch(JRException ex){
		ex.printStackTrace();
	}
}
		 

	
	
}
