package mz.co.sgrc.controller;

import java.awt.Dimension;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mz.co.sgrc.dao.CotasDAO;
import mz.co.sgrc.dao.GuiaRemessaDAO;
import mz.co.sgrc.dao.HistoricoDAO;
import mz.co.sgrc.dao.Item_requisicaoDAO;
import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.QuantidadeFinalDAO;
import mz.co.sgrc.dao.RequisicaoDAO;
import mz.co.sgrc.dao.TipoCombustiveDAO;
import mz.co.sgrc.dao.ViaturaDAO;
import mz.co.sgrc.model.GuiaRemessa;
import mz.co.sgrc.model.Item_requisicao;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.QuantidadeFinal;
import mz.co.sgrc.model.Requisicao;
import mz.co.sgrc.model.TipoCombustive;
import mz.co.sgrc.model.Viatura;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import sun.security.action.GetLongAction;

public class GuiaRemessaController extends SelectorComposer<Component> {

	@Wire
	private Datebox txt_data;
	
	@Wire
	private Combobox cbb_orgaos;
	
	@Wire
	private Combobox cbb_tipoCombustivel;
	
	@Wire
	private Button btn_procurar;
	@Wire
	private Button btn_remessas;
	@Wire
	private Button btn_cancelar;

	@Wire
	private Listbox lb_remessas;
	
	@Wire
	private Listbox lb_requisicao; 
	
	@Wire
	private Item_requisicaoDAO item_requisicaoDAO;
	
	@Wire
	private GuiaRemessaDAO guiaRemessaDAO;
	
	@Wire
	private ViaturaDAO viaturaDAO;
	
	@Wire
	private RequisicaoDAO requisicaoDAO;
	
	@Wire
	private QuantidadeFinalDAO quantidadeFinalDAO;
	
	@Wire
	private OrgaoDAO orgaoDAO;
	@Wire
	private TipoCombustiveDAO tipoCombustiveDAO;
	
	
	
	ListModelList <GuiaRemessa> remessaModel;
	ListModelList <Item_requisicao> listItemRequisicao;
	ListModelList <Orgao> orgaoModel;
	ListModelList <TipoCombustive> tipoCombustiveModel;
	GuiaRemessa guia;
	
	private Connection con;
	
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		//visualizarRequisicoes();
		preencherRequisicao();
		preencherOrgaos();
		preencherTipoCombustivel();
	}

	public GuiaRemessaController (){
		
		guiaRemessaDAO = new GuiaRemessaDAO();
		requisicaoDAO = new RequisicaoDAO();
		viaturaDAO = new ViaturaDAO();
		item_requisicaoDAO = new Item_requisicaoDAO();
		orgaoDAO = new OrgaoDAO();
		tipoCombustiveDAO = new TipoCombustiveDAO();
		quantidadeFinalDAO = new QuantidadeFinalDAO();
		
	}
	
	@Listen ("onClick = #btn_procurar")
	public void onClicProcurar(){
		//visualizarRequisicoes();
		//preencherItensRemessas();
	}
	


	    @SuppressWarnings("unchecked")
		public void preencherRequisicao(){
			
	    	List <Requisicao> lrequisicao = requisicaoDAO.findAll();
			
			for ( final Requisicao requi : lrequisicao){
				Listitem lm = new Listitem();
				new Listcell (""+requi.getData()).setParent(lm);
				new Listcell (requi.getResponsavel()).setParent(lm);
				new Listcell (requi.getOrgao().getDesignacao()).setParent(lm);
				
				Listcell listcell1 = new Listcell();
				listcell1.setParent(lm);
				
				final Checkbox cbx = new Checkbox();
				cbx.setDisabled(false);
				if(requi.getRemessada()){
					cbx.setChecked(true);
					cbx.setDisabled(true);
				}else{
					cbx.setChecked(false);
					
				}
				cbx.setParent(listcell1);
				
				
				Listcell listcell = new Listcell();
				listcell.setParent(lm);
				final Button btn_verItens = new Button("Ver Itens");
				btn_verItens.setParent(listcell);
				
				if(requi.getRemessada()){
					btn_verItens.setVisible(true);
				}else{
					btn_verItens.setVisible(false);
					
				}
				
				lb_requisicao.appendChild(lm);
				
				cbx.addEventListener("onCheck", new EventListener(){

					@Override
					public void onEvent(Event arg0) throws Exception {
						// TODO Auto-generated method stub
						requi.setRemessada(true);
						cbx.setDisabled(true);
						requisicaoDAO.update(requi);
						btn_verItens.setVisible(true);
						
					}
					
				});
				
			  btn_verItens.addEventListener("onClick", new EventListener(){

			
				public void onEvent(Event arg0) throws Exception {
					
					Requisicao requisicaO = requisicaoDAO.returnar(requi);
					
					Requisicao requisicao = (Requisicao) Executions.getCurrent().getDesktop().getSession().setAttribute("requisicao", requisicaO);
					Executions.createComponents("/RemessarItens.zul", null, null);
					
//					List<Item_requisicao> itemR = requisicaO.getListRequisicao();
//					listItemRequisicao = new ListModelList<Item_requisicao>(itemR);
//					listItemRequisicao.setMultiple(true);
//					lb_remessas.setModel(listItemRequisicao);
	    	
					
				}
				  
			  });
			}
	
	    	
	    	
	    }
	    
	    
	    
	    
	    public void preencherOrgaos(){
	    	List<Orgao> listOrg = orgaoDAO.findAll();
	        orgaoModel = new ListModelList<Orgao>(listOrg);
	        cbb_orgaos.setModel(orgaoModel);
	    }
	    
	    public void preencherTipoCombustivel(){
	    	List <TipoCombustive> tipo = tipoCombustiveDAO.findAll();
	    	tipoCombustiveModel = new ListModelList<TipoCombustive>(tipo);
	    	cbb_tipoCombustivel.setModel(tipoCombustiveModel);
	    }
	    
	    
	    
	    
	    ///////////////////////////
		public void gerarReports() throws SQLException{
//			try{
//				Class.forName("com.mysql.jdbc.Driver");
//			    con = DriverManager.getConnection("jdbc:mysql://localhost/vendas","root","huo");
//				
//			} catch(ClassNotFoundException e1){
//				e1.printStackTrace();
//				
//			}
//			
//			try{
//				
//				String reporte = "C:/Reports/report3.jrxml";
//	            param.put("varStatus","E");
//				JasperReport jasperReport = JasperCompileManager.compileReport(reporte);
//				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
//				JasperViewer.viewReport(jasperPrint);
//				con.close();
//				
//			}catch(JRException e){
//				e.printStackTrace();
//			}
			
			   String HOST = "jdbc:mysql://localhost:3306/vendas";
		        String USERNAME = "root";
		        String PASSWORD = "huo";
		        try {
		            Class.forName("com.mysql.jdbc.Driver");
		        } catch (ClassNotFoundException ex) {
		            ex.printStackTrace();
		        }
		 
		        try {
		            con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		      
		        //Path to your .jasper file in your package
		        //pasta/caminho pra teu ficheiro .jasper dentro do pacote
		        String reportName = "mz/co/venda/report/report3.jasper";
		         
		        //Get a stream to read the file
		        //Streaming � a tecnologia que permite o envio de informa��o multim�dia atrav�s de pacotes
		        //Obtendo o stream para ler o ficheiro
		        InputStream is = this.getClass().getClassLoader().getResourceAsStream(reportName);
		 
		        try {
		            //Fill the report with parameter, connection and the stream reader    
		            //Completar o report com parametro, conexao e o leitor stream
		        	//param.put("identificador","1");
		            JasperPrint jp = JasperFillManager.fillReport(is, null, con);
		         
		     //Viewer for JasperReport
		     //Visualizador para o JasperReport       
		            JRViewer jv = new JRViewer(jp);
		     
		     //Insert viewer to a JFrame to make it showable
		     //Inserindo o visualizador num JFrame para torna lo visualizavel        
		            JFrame jf = new JFrame();
		            jf.getContentPane().add(jv);
		            jf.validate();
		            jf.setVisible(true);
		            jf.setSize(new Dimension(800,600));
		            jf.setLocation(300,100);
		            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        } catch (JRException ex) {
		            ex.printStackTrace();
		        }
		}
	
}

