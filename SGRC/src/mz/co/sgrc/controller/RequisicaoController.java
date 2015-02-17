package mz.co.sgrc.controller;


import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import mz.co.sgrc.dao.CategoriaDao;
import mz.co.sgrc.dao.CotasDAO;
import mz.co.sgrc.dao.ItemDAO;
import mz.co.sgrc.dao.Item_requisicaoDAO;
import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.RequisicaoDAO;
import mz.co.sgrc.dao.TipoCombustiveDAO;
import mz.co.sgrc.dao.UtilizadorDAO;
import mz.co.sgrc.dao.ViaturaDAO;
import mz.co.sgrc.model.Cotas;
import mz.co.sgrc.model.Item;
import mz.co.sgrc.model.Item_requisicao;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.Requisicao;
import mz.co.sgrc.model.TipoCombustive;
import mz.co.sgrc.model.Utilizador;
import mz.co.sgrc.model.Viatura;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
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
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class RequisicaoController extends GenericForwardComposer{

	
	private Listbox lb_dados;
	private Listbox lb_viaturaExistente;
	private Listbox lb_viaturaRequisitar;
	private Listbox lb_requisicao;
	
	private Button btn_requisitar;
	private Button btn_cancelar;
	
	private Listbox lb_visualizaRequisicoes;

	private ViaturaDAO viaturaDAO;
	private TipoCombustiveDAO tipoCombustiveDAO; 
	private UtilizadorDAO utilizadorDAO;
	private Item_requisicaoDAO item_requisicaoDAO;
	private ItemDAO itemDAO;
	private RequisicaoDAO requisicaoDAO;
	private OrgaoDAO orgaoDAO;
	private CotasDAO cotasDAO;
	
	Window win= new Window();
	
	private TipoCombustive selectedTipocombustive;
	
	private Utilizador ut;
	
	private Orgao orgao;
	
	private Cotas cotas;
	
	private Connection con;
	
	private List <Viatura> listViatura;
	
	private byte bytes[] = null;
	
	private Window window;
	
	Combobox cbb;
	
	Listitem listItem,listItem1,listItem2;
	
	Listitem list2;
    
	Listitem lis;
	
	
	int cont=0;
	
	List <Item> lista = new ArrayList<Item>();

	
	//ListModelList <TipoCombustive> listTipoCombustive;
	
	ListModelList <TipoCombustive> listTipoCombustive;
	
	ListModelList <Item_requisicao> listItemRequisicao;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		visualizaViatura();	
		selectedTipocombustive=null;
    	preencherDados();
		preencherRequisicao();
		btn_requisitar.setDisabled(true);
		
		
	}

	public RequisicaoController() {
		viaturaDAO = new ViaturaDAO();
		tipoCombustiveDAO = new TipoCombustiveDAO();
		utilizadorDAO = new UtilizadorDAO();	
		item_requisicaoDAO=new Item_requisicaoDAO();
		itemDAO = new ItemDAO();
		orgaoDAO = new OrgaoDAO();
		cotasDAO = new CotasDAO();
		requisicaoDAO = new RequisicaoDAO();
		
		
		ut = utilizadorDAO.findById((long)1);
		if (ut == null) {


			orgao = new Orgao();
			cotas = new Cotas();
			
		} else {
			orgao = orgaoDAO.findById(ut.getOrgao().getId());
			cotas = cotasDAO.findById(orgao.getCota_id());
		}
		
		
		preencherDados();
		
		
		
	}

	
	
	@SuppressWarnings("unchecked")
	private void visualizaViatura() {
		List<Viatura> viat = (List<Viatura>) viaturaDAO.findAll();
		
		
		for (final Viatura v : viat) {

			Listitem li = new Listitem();
			new Listcell(v.getMarca()).setParent(li);
			new Listcell(v.getMatricula()).setParent(li);
			
			Listcell listcell = new Listcell();
			listcell.setParent(li);
			final Button btn_adicionar = new Button("Adicionar");
			btn_adicionar.setParent(listcell);
			
			lb_viaturaExistente.appendChild(li);
		
		
			
			btn_adicionar.addEventListener("onClick", new EventListener() {

			
				public void onEvent(Event arg0) throws Exception {
					
					btn_requisitar.setDisabled(false);
					final Listitem l = new Listitem();
					new Listcell(v.getMarca()).setParent(l);
					new Listcell(v.getMatricula()).setParent(l);
					Listcell l1 = new Listcell();
					cbb = new Combobox();
					cbb.setWidth("130px");
					//cbb.setConstraint("no empty:este campo nao deve estar vazio");
					preencherTipoCombustive(cbb);
					cbb.setParent(l1);
				    l1.setParent(l);
			
				   
				    Listcell l2 = new Listcell();
				    final Doublebox db_quantidade = new Doublebox();
				    db_quantidade.setParent(l2);
					l2.setParent(l);
					
					Listcell l3 = new Listcell();
					final Checkbox cb_selecionar = new Checkbox();
					cb_selecionar.setParent(l3);
					l3.setParent(l);
					
					Listcell l4 = new Listcell ();
					final Button btn_remover =new Button("remover");
					btn_remover.setParent(l4);
					l4.setParent(l);
					
					lb_viaturaRequisitar.appendChild(l);
					btn_adicionar.setDisabled(true);
					
				    cbb.addEventListener("onSelect", new EventListener(){

						@Override
						public void onEvent(Event arg0) throws Exception {
							// TODO Auto-generated method stub
							if(listTipoCombustive.isSelectionEmpty()){
								selectedTipocombustive =null;
							}
							else {
								selectedTipocombustive = listTipoCombustive.getSelection().iterator().next();
							}
						}
				    	
				    	
				    	
				    });
					
					
				    cb_selecionar.addEventListener("onCheck", new EventListener(){

						@Override
						public void onEvent(Event arg0) throws Exception {
							// TODO Auto-generated method stub
							
							Item item = new Item();
							
							if(cb_selecionar.isChecked()){
								
								
								item.setMarca(v.getMarca());
								item.setMatricula(v.getMatricula());
								item.setCombustive(selectedTipocombustive.getCombustive());
								item.setQuantidade(db_quantidade.getValue());
								item.setViatura(v);
								item.setCusto(db_quantidade.getValue()*selectedTipocombustive.getCusto());
								item.setCustoRemessado(0);
								l.setDisabled(true);
								btn_remover.setDisabled(true);
								
								int controla= 0;
								int cont= 0;
								boolean parar= true;
								
								
							if (lista.size()!= 0){	
								while(parar){
									
										if (lista.get(cont).getMatricula() == item.getMatricula()){
											controla=1;
											parar= false;
										}
										
										cont++;
										if (cont==lista.size())
											parar= false;
								}
							}	
								
							
							
							if (controla != 1){
								lista.add(item);
							}
							
								
								
								
								if(selectedTipocombustive.getDesignacao().equals("Gasoleo")){
								orgao.setQuantidadeGasoleo(orgao.getQuantidadeGasoleo()-db_quantidade.getValue());
								}
								else if (selectedTipocombustive.getDesignacao().equals("Gasolina")){
								orgao.setQuantidadeGasolina(orgao.getQuantidadeGasolina()-db_quantidade.getValue());
								}
								else if (selectedTipocombustive.getDesignacao().equals("Gas")){
									orgao.setQuantidadeGas(orgao.getQuantidadeGas()-db_quantidade.getValue());
								}
								
								orgaoDAO.update(orgao);
							    lb_dados.removeChild(listItem);
							    lb_dados.removeChild(listItem1);
							    lb_dados.removeChild(listItem2);
							   preencherDados();
							}
							else{
								
								
								lista.remove(item);
								l.setDisabled(false);
								btn_remover.setDisabled(false);
								
								if(selectedTipocombustive.getDesignacao().equals("Gasoleo")){
									orgao.setQuantidadeGasoleo(orgao.getQuantidadeGasoleo()+db_quantidade.getValue());
									}
									else if(selectedTipocombustive.getDesignacao().equals("Gasolina")){
									orgao.setQuantidadeGasolina(orgao.getQuantidadeGasolina()+db_quantidade.getValue());
									}
									else if (selectedTipocombustive.getDesignacao().equals("Gas")){
										orgao.setQuantidadeGas(orgao.getQuantidadeGas()+db_quantidade.getValue());
									}
								
								   orgaoDAO.update(orgao);
								   lb_dados.removeChild(listItem);
								   lb_dados.removeChild(listItem1);
								   lb_dados.removeChild(listItem2);
     								preencherDados();
							}
							
							
							
						
						}
							
					});
					
					
				    btn_remover.addEventListener("onClick", new EventListener() {
						
						@Override
						public void onEvent (Event arg0) throws Exception{
							lb_viaturaRequisitar.removeChild(l);
							btn_adicionar.setDisabled(false);
//							for(int i=0; i<lista.size(); i++){
//								if((lista.get(i).getMatricula()).equals(v.getMatricula())){
//									lista.remove(i);
//								}
//							}
						}
					});
				}
			});
			

		

	}}
	
	

	
	
	public void onClick$btn_requisitar(){
		
		btn_requisitar.setDisabled(true);
		
		Requisicao requisicao = new Requisicao();
	
		
		Date data = new Date();
		
		requisicao.setData(data);

		requisicao.setResponsavel(ut.getNome());
		
		requisicao.setOrgao(orgao);
		
		requisicao.setRemessada(false);
		
		requisicao.setCusto(0);
		
		requisicao.setCustoRemessado(0);
		
		requisicao.setTotal(0);
		
		requisicaoDAO.create(requisicao);
		
		
        List <Item> listItem = itemDAO.findAll();
		
		
		Item_requisicao itemReq = null;
		
		List <Item_requisicao> listItemRequisicao = new ArrayList<Item_requisicao>();
		
		for (int i=0; i<lista.size(); i++){
			itemReq = new Item_requisicao();
			
			itemReq.setCombustive(lista.get(i).getCombustive());
			itemReq.setQuantidade_requisitada(lista.get(i).getQuantidade());
			itemReq.setViatura(lista.get(i).getViatura());
			itemReq.setCombustivelString(cbb.getText());
			itemReq.setRemessada(false);
			itemReq.setRequisicao(requisicao);
			itemReq.setCusto(lista.get(i).getCusto());
			itemReq.setCustoRemessado(lista.get(i).getCustoRemessado());
			requisicao.setCusto(requisicao.getCusto()+itemReq.getCusto());
		
			item_requisicaoDAO.create(itemReq);	
			listItemRequisicao.add(itemReq);
			
		}
		
		requisicao.setListRequisicao(listItemRequisicao);
		requisicaoDAO.update(requisicao);
		itemDAO.deleteAll();
	
		   Clients.showNotification("Requisicao feita com sucesso", "info", win, "middle_center", 4000);
		   lista.clear();
		   lb_viaturaRequisitar.getItems().clear();
		   lb_requisicao.getItems().clear();
		   preencherRequisicao();
	
		}
		
	
		
	@SuppressWarnings("unchecked")
	public void preencherRequisicao(){
		List <Requisicao> lrequisicao = requisicaoDAO.findAllInverso();
		
		for (final Requisicao requi : lrequisicao){
			Listitem lm = new Listitem();
			new Listcell (""+requi.getId()).setParent(lm);
			new Listcell (""+requi.getData()).setParent(lm);
			new Listcell (requi.getResponsavel()).setParent(lm);
			new Listcell (requi.getOrgao().getDesignacao()).setParent(lm);
			
			Listcell listcell1 = new Listcell();
			listcell1.setParent(lm);
			
			Checkbox cbx = new Checkbox();
			cbx.setDisabled(true);
			if(requi.getRemessada()){
				cbx.setChecked(true);
			}else{
				cbx.setChecked(false);
				
			}
			cbx.setParent(listcell1);
			
			Listcell listcell = new Listcell();
			listcell.setParent(lm);
			
			Button btn_verItens = new Button("Ver Itens");
			btn_verItens.setParent(listcell);
			lb_requisicao.appendChild(lm);
			
			Listcell listcell2 = new Listcell();
			listcell2.setParent(lm);
			
			Button btn_imprimir = new Button("Imprimir");
			btn_imprimir.setParent(listcell2);
			
		  btn_verItens.addEventListener("onClick", new EventListener(){

		
			public void onEvent(Event arg0) throws Exception {
				//Requisicao requis = requisicaoDAO.returonarRequisicao(requi);
				
				Requisicao requisicao = (Requisicao) Executions.getCurrent().getDesktop().getSession().setAttribute("requisicao", requi);
				
				Executions.createComponents("/ItensRequisicao.zul", null, null);
		
			
			}
			  
		  });
		
		  
		  btn_imprimir.addEventListener("onClick", new EventListener(){

				

				@Override
				public void onEvent(Event arg0) throws Exception , SQLException, JRException{
					// TODO Auto-generated method stub
		
					Map<String, Object> param = new HashMap<String,Object>();
					try{
						Class.forName("com.mysql.jdbc.Driver");
					    con = DriverManager.getConnection("jdbc:mysql://localhost/vendas","root","");
						
					} catch(ClassNotFoundException e1){
						e1.printStackTrace();
					}
					try{	
						String reporte = "C:/Reports/Requisicao.jrxml";
			            param.put("codigo_requisicao",(Long)requi.getId());
						JasperReport jasperReport = JasperCompileManager.compileReport(reporte);
						JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, con);
						//JasperViewer.viewReport(jasperPrint);
					      JRViewer jv = new JRViewer(jasperPrint);
				          JFrame jf = new JFrame();
						  jf.getContentPane().add(jv);
						  jf.validate();
						  jf.setVisible(true);
						  jf.setSize(new Dimension(900,700));
						  jf.setLocation(300,100);
						  jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						con.close();
						
					}catch(JRException e){
						e.printStackTrace();
					}		
				}
			  });
		  
		  
		  
		}
		
	}

		
	
	
	
	public void preencherTipoCombustive(Combobox aux){
		List <TipoCombustive> tipo = tipoCombustiveDAO.findAll();
		listTipoCombustive = new ListModelList<TipoCombustive>(tipo);
		aux.setModel(listTipoCombustive);
		
	}
	
	
	

	public void preencherDados(){

		  listItem = new Listitem();
		  new Listcell("Gasolina").setParent(listItem);
		  new Listcell(""+orgao.getCotaGasolina()).setParent(listItem);
		  new Listcell(""+orgao.getQuantidadeGasolina()).setParent(listItem);
		 
		  listItem.setParent(lb_dados);
		  
		  
		  listItem1 = new Listitem();
		  new Listcell("Gasoleo").setParent(listItem1);
		  new Listcell(""+orgao.getCotaGasoleo()).setParent(listItem1);
		  new Listcell(""+orgao.getQuantidadeGasoleo()).setParent(listItem1);
		
		  listItem1.setParent(lb_dados);
		  
		  listItem2 = new Listitem();
		  new Listcell("Gas").setParent(listItem2);
		  new Listcell(""+orgao.getCotaGas()).setParent(listItem2);
		  new Listcell(""+orgao.getQuantidadeGas()).setParent(listItem2);
		
		  listItem2.setParent(lb_dados);
		  
		  
		
	}
	
	
	
	public void onClick$btn_imprimir() throws SQLException, JRException{
		gerarReports();
		
	}
	
	
	public void gerarReports() throws SQLException, JRException{
		
//		//Funciona
//		try{
//			Class.forName("com.mysql.jdbc.Driver");
//		    con = DriverManager.getConnection("jdbc:mysql://localhost/vendas","root","huo");
//			
//		} catch(ClassNotFoundException e1){
//			e1.printStackTrace();
//			
//		}
//		
//		try{
//			
//			String reporte = "C:/Reports/report3.jrxml";
//            //param.put("varStatus","E");
//			JasperReport jasperReport = JasperCompileManager.compileReport(reporte);
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
//			JasperViewer.viewReport(jasperPrint);
//			con.close();
//			
//		}catch(JRException e){
//			e.printStackTrace();
//		}
//	}
		
		//Teste

		 final Execution exc = Executions.getCurrent();

		JasperReport report = JasperCompileManager
				.compileReport(exc.getDesktop().getWebApp().getRealPath("C:/Users/Marcelo/report/Requisicao.jrxml"));
		
//		Map<String, Object> params = new HashMap<String,Object>();
//		params.put("emblema",  exc.getDesktop().getWebApp().getRealPath("/reports/emblema.png")); 	
//		

		bytes = JasperRunManager.runReportToPdf(report, null,
				new JRBeanCollectionDataSource(listViatura));

		

	final InputStream mediaias = new ByteArrayInputStream(bytes);
	final AMedia amedia = new AMedia("desempenho_"
			+ (int) (Math.random() * 1000) + ".pdf", "pdf",
			"application/pdf", mediaias);

	Iframe frame = new Iframe();
	frame.setWidth("100%");
	frame.setHeight("100%");
	frame.setContent(amedia);
	Window win = new Window();	
	win.setClosable(true);
	win.setBorder(true);
	win.setWidth("80%");
	win.setHeight("90%");
	win.setTitle("report");
	win.setParent(window);
	win.setMode("modal");
	frame.setParent(win);
	
	
	
	//Exemplo
		
//		   String HOST = "jdbc:mysql://localhost:3306/vendas";
//	        String USERNAME = "root";
//	        String PASSWORD = "huo";
//	        try {
//	            Class.forName("com.mysql.jdbc.Driver");
//	        } catch (ClassNotFoundException ex) {
//	            ex.printStackTrace();
//	        }
//	 
//	        try {
//	            con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
//	        } catch (SQLException ex) {
//	            ex.printStackTrace();
//	        }
//	      
//	        //Path to your .jasper file in your package
//	        //pasta/caminho pra teu ficheiro .jasper dentro do pacote
//	        String reportName = "mz/co/venda/report/report3.jasper";
//	         
//	        //Get a stream to read the file
//	        //Streaming é a tecnologia que permite o envio de informação multimídia através de pacotes
//	        //Obtendo o stream para ler o ficheiro
//	        InputStream is = this.getClass().getClassLoader().getResourceAsStream(reportName);
//	 
//	        try {
//	            //Fill the report with parameter, connection and the stream reader    
//	            //Completar o report com parametro, conexao e o leitor stream
//	        	//param.put("identificador","1");
//	            JasperPrint jp = JasperFillManager.fillReport(is, null, con);
//	         
//	     //Viewer for JasperReport
//	     //Visualizador para o JasperReport       
//	            JRViewer jv = new JRViewer(jp);
//	     
//	     //Insert viewer to a JFrame to make it showable
//	     //Inserindo o visualizador num JFrame para torna lo visualizavel        
//	            JFrame jf = new JFrame();
//	            jf.getContentPane().add(jv);
//	            jf.validate();
//	            jf.setVisible(true);
//	            jf.setSize(new Dimension(800,600));
//	            jf.setLocation(300,100);
//	            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	        } catch (JRException ex) {
//	            ex.printStackTrace();
//	        }
//	}
//	

	}
}
