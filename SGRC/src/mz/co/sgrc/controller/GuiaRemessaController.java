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
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import sun.security.action.GetLongAction;

public class GuiaRemessaController extends GenericForwardComposer {

	private Datebox cbx_data;
	private Combobox cbb_orgaos;
	private Combobox cbb_tipoCombustivel;
	private Button btn_procurar;
	private Button btn_remessas;
	private Button btn_cancelar;
	private Listbox lb_remessas;
	private Listbox lb_requisicao; 
	
	private Item_requisicaoDAO _item_requisicaoDAO;
	private GuiaRemessaDAO _guiaRemessaDAO;
	private ViaturaDAO _viaturaDAO;
	private RequisicaoDAO _requisicaoDAO;
	private QuantidadeFinalDAO _quantidadeFinalDAO;
	private OrgaoDAO _orgaoDAO;
	private TipoCombustiveDAO _tipoCombustiveDAO;
	
	ListModelList <GuiaRemessa> _listModelGuiaRemessa;
	ListModelList <Item_requisicao> _listModelItemRequisicao;
	ListModelList <Orgao> _listModelOrgao;
	ListModelList <TipoCombustive> _listModelTipoCombustive;
	ListModelList <Requisicao> _listModelRequisicao;
	
	GuiaRemessa guiaRemessa;
	
	private Connection con;
	
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		_guiaRemessaDAO = new GuiaRemessaDAO();
		_requisicaoDAO = new RequisicaoDAO();
		_viaturaDAO = new ViaturaDAO();
		_item_requisicaoDAO = new Item_requisicaoDAO();
		_orgaoDAO = new OrgaoDAO();
		_tipoCombustiveDAO = new TipoCombustiveDAO();
		_quantidadeFinalDAO = new QuantidadeFinalDAO();
		
		preencherRequisicao();
		preencherOrgaos();
		preencherTipoCombustivel();
	}

	public GuiaRemessaController (){}
	
    public void onClick$btn_procurar(Event e){
		//visualizarRequisicoes();
		//preencherItensRemessas();
	}
	


	  
		@SuppressWarnings("unchecked")
		public void onSelect$lb_requisicao(Event e){
			
	    Listitem listitem = lb_requisicao.getSelectedItem();
		final Requisicao _requisicao = (Requisicao)listitem.getValue();	
	   
		Listcell listcell = (Listcell) listitem.getChildren().get(3);
		final Checkbox _chbx_remessar = (Checkbox) listcell.getFirstChild();
		
		Listcell listcell1 = (Listcell)listitem.getChildren().get(4);
		final Button _btn_verItens = (Button) listcell1.getFirstChild();
		
		 _chbx_remessar.addEventListener("onCheck", new EventListener(){

			public void onEvent(Event arg0) throws Exception {
				_requisicao.setRemessada(true);
				_chbx_remessar.setDisabled(true);
				_requisicaoDAO.update(_requisicao);
				_btn_verItens.setVisible(true);
				
			}
			 
		 });
					
		
    	  _btn_verItens.addEventListener("onClick", new EventListener(){

			
				public void onEvent(Event arg0) throws Exception {
					
					Requisicao requisicaO = _requisicaoDAO.returnar(_requisicao);
					
					Requisicao requisicao = (Requisicao) Executions.getCurrent().getDesktop().getSession().setAttribute("requisicao", requisicaO);
					Executions.createComponents("/RemessarItens.zul", null, null);

					
				}
				  
			  });
			}
	

	    public void preencherRequisicao(){
	    	List <Requisicao> listRequisicao = _requisicaoDAO.findAllInverso();
	    	_listModelRequisicao = new ListModelList <Requisicao>(listRequisicao);
	    	lb_requisicao.setModel(_listModelRequisicao);
	    }
	    
	    public void preencherOrgaos(){
	    	List<Orgao> listOrg = _orgaoDAO.findAll();
	        _listModelOrgao = new ListModelList<Orgao>(listOrg);
	        cbb_orgaos.setModel(_listModelOrgao);
	    }
	    
	    public void preencherTipoCombustivel(){
	    	List <TipoCombustive> tipo = _tipoCombustiveDAO.findAll();
	    	_listModelTipoCombustive = new ListModelList<TipoCombustive>(tipo);
	    	cbb_tipoCombustivel.setModel(_listModelTipoCombustive);
	    }
	    
	    
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
		        //Streaming é a tecnologia que permite o envio de informação multimídia através de pacotes
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

