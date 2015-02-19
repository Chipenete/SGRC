package mz.co.sgrc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import mz.co.sgrc.dao.CotasDAO;
import mz.co.sgrc.dao.HistoricoDAO;
import mz.co.sgrc.dao.OrgaoDAO;
import mz.co.sgrc.dao.Orgao_has_CotaDAO;
import mz.co.sgrc.dao.RequisicaoDAO;
import mz.co.sgrc.dao.TipoCombustiveDAO;
import mz.co.sgrc.model.Cotas;
import mz.co.sgrc.model.Historico;
import mz.co.sgrc.model.Orgao;
import mz.co.sgrc.model.Orgao_has_Cota;
import mz.co.sgrc.model.Requisicao;
import mz.co.sgrc.model.TipoCombustive;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class CotasController extends GenericForwardComposer{

	
	private Doublebox dbx_quantidadeGasolina;
	private Doublebox dbx_quantidadeGasoleo;
	//--------------------------------------------------Visualizar-------------------------------------------------------------
	private Listbox lb_orgaosVisualizar;
	private Button btn_procurarVisualizar;
	private Textbox tb_designacaoVisualizar;
	//-------------------------------------------------------------------------------------------------------------------------
	
	//-----------------------------------------------------Criar---------------------------------------------------------------
	private Button btn_gravar;
	private Button btn_actualizar;
	private Button btn_cancelar;
	private Listbox lb_cotasExistentes;
	private Combobox cbb_tipoCombustivel;
	private Doublebox db_quantidade;
	private Orgao_has_Cota _selectedOHC;
	//----------------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------Atribuir--------------------------------------------------------------------
	private Button btn_novo;
	private Button btn1_gravar;
	private Button btn1_cancelar;
	private Button btn_procurarAtribuir;
	private Button btn1_historico;
	private Button btn1_historico1;
	private Combobox cbb_cotasAtribuir;
	private Combobox cbb_tipoCombustivelAtribuir;
	private Combobox cbx_orgaos;
	private Listbox lb_orgaosAtribuir;
	private Listbox lb_cotasAtribuir;
	private Listbox lb_historico = new Listbox();
    private Textbox tb_designacaoAtribuir;
	//---------------------------------------------------------------------------------------------------------------------------
	private Listbox lb_cotas;
	private Listbox lb_orgao;
	

	private CotasDAO _cotasDao;
	private OrgaoDAO _orgaoDao;
	private HistoricoDAO _historicoDao;
	private Orgao_has_CotaDAO _orgao_has_CotaDao;
	private TipoCombustiveDAO _tipoCombustiveDao;
	private boolean _novoOrgao;

	Cotas _selectedCota;
	Orgao _selectedOrgao;
	Orgao _selectedOrgao1;
	TipoCombustive _selectedTipoCombustivelCriar;
	TipoCombustive _selectedTipoCombustivelAtribuir;
	Window _win;

	private List <Cotas> _listCota;
	private ListModelList <Cotas> _listModelCotas, _listModelCota;
	private ListModelList <Orgao> _listModelOrgao, _listModelOrgao1,_listModelOrgaoAtribuir,_listModelOrgaoVisualizar;
	private ListModelList <Historico> _listModelHistorico;
	private ListModelList <Orgao_has_Cota> _listModelOrgaoCota;
	private ListModelList <TipoCombustive> _listModelTipoCombustive, _listModelTipoCombustiveAtribuir;
	
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		_cotasDao = new CotasDAO();
		_orgaoDao = new OrgaoDAO(); 
		_historicoDao = new HistoricoDAO();
		_orgao_has_CotaDao = new Orgao_has_CotaDAO();
		_tipoCombustiveDao = new TipoCombustiveDAO();
		
		preencherOrgaoVisualizar();
		visualizarCotasCriar();
	    preencherOrgaoAtribuir();
	    preenchertipoCombustivelAtribuir();
	    preenchertipoCombustivel();
	    
		_novoOrgao = false;
		_selectedOrgao1=null;
		_selectedTipoCombustivelCriar=null;
		_selectedTipoCombustivelAtribuir=null;
	}
	
	

	public CotasController(){}
	
	//------------------------------------------------------------------Atribuir------------------------------------------------------------
	//******************************************************************EVENTOS***************************************************************************
	/*  @Listen ("onClick = #btn_novo")
		  public void onClickNovo(){
	
					novoOrgao =true;
					Executions.createComponents("/Orgao.zul", null, null);	
	    	}*/
	
		 
		 public void onClick$btn1_gravar(Event e){
			 if(_selectedCota!=null){
				 _selectedOHC = new Orgao_has_Cota();
				 _selectedOHC.setCotas(_selectedCota);
				 _selectedOHC.setData(new Date());
				 _orgao_has_CotaDao.create(_selectedOHC);
				 
				 Set<Listitem> listItens = lb_orgaosAtribuir.getSelectedItems();
				
				 for (Listitem listItem : listItens){	
					 Orgao orgao= (Orgao) listItem.getValue(); 
					 //Orgao orgao = _orgaoDao.returnar(org);	
					
					orgao.setCota_id(_selectedCota.getId());
				 
					 if(_selectedCota.getTipoCombustive().getDesignacao().equals("Gasoleo")){
						 orgao.setQuantidadeGasoleo(_selectedCota.getQuantidade());
						 orgao.setCotaGasoleo(_selectedCota.getQuantidade());
					 }
					 else if(_selectedCota.getTipoCombustive().getDesignacao().equals("Gasolina")){
						 orgao.setQuantidadeGasolina(_selectedCota.getQuantidade()); 
						 orgao.setCotaGasolina(_selectedCota.getQuantidade()); 
					 }
					 else if(_selectedCota.getTipoCombustive().getDesignacao().equals("Gas")){
						 orgao.setQuantidadeGas(_selectedCota.getQuantidade());
						 orgao.setCotaGas(_selectedCota.getQuantidade());
						 
					 }
					 _orgaoDao.update(orgao);
					 _selectedOHC.setOrgao(orgao);
					 _orgao_has_CotaDao.update(_selectedOHC);
				 }

				 preencherOrgaoAtribuir();
				 Clients.showNotification("Cota atribuida com sucesso", "info",_win, "middle_center", 3000, true);
				 limpar1();
		   }
		   else{
			   Clients.showNotification("Selecione uma cota", "info",_win, "middle_center", 2000, true); 
			   }
			 _selectedCota=null;	 
		 }
	
		 
		public void onSelect$lb_cotasAtribuir(Event e){
			if(_listModelCotas.isSelectionEmpty())
				_selectedCota = null;	
			else{
				_selectedCota=_listModelCotas.getSelection().iterator().next();
				cbb_cotasAtribuir.setValue(""+_selectedCota.getId());	
				
			}	
		}
		
		
		public void onSelect$cbb_tipoCombustivelAtribuir(Event e){
			if(_listModelTipoCombustiveAtribuir.isSelectionEmpty())
				_selectedTipoCombustivelAtribuir = null;
			else{
				_selectedTipoCombustivelAtribuir = (TipoCombustive) _listModelTipoCombustiveAtribuir.getSelection().iterator().next();
				VisualizarCotasAtribuir();
			}	
		}
		
		
        public void onClick$btn1_cancelar(Event e){
			limpar1();
		}
		
		
		public void onSelect$lb_orgaos1(Event e){
			if(_listModelOrgao1.isSelectionEmpty())
				_selectedOrgao1=null;
			else{	
				_selectedOrgao1=_listModelOrgao1.getSelection().iterator().next();
				Messagebox.show(_selectedOrgao1+"");	
			}	
		}

		
		public void onClick$btn_procurarAtribuir(Event e){
			List<Orgao> listOrg = _orgaoDao.pesquisaOrgaos(tb_designacaoAtribuir.getText());
			_listModelOrgaoAtribuir = new ListModelList<Orgao>(listOrg);
			_listModelOrgaoAtribuir.setMultiple(true);
			lb_orgaosAtribuir.setModel(_listModelOrgaoAtribuir);	
		}

	
		//*************************************************************METODOS********************************************************************************************
		  public void preencherOrgao(){
				List <Orgao> org = _orgaoDao.findAll();
				_listModelOrgao = new ListModelList<>(org);
				cbx_orgaos.setModel(_listModelOrgao);
				}
		   
		  
		  public void preencherOrgaoAtribuir(){
				List <Orgao> org = _orgaoDao.findAll();
				_listModelOrgaoAtribuir = new ListModelList<Orgao>(org);
				_listModelOrgaoAtribuir.setMultiple(true);
				lb_orgaosAtribuir.setModel(_listModelOrgaoAtribuir);
				
				}
		  
		  public void limpar1(){
			  cbb_cotasAtribuir.setValue(null);
			  cbb_tipoCombustivelAtribuir.setValue(null);  
		  }
		  
			private void VisualizarCotasAtribuir() { 
				List <Cotas> cotaTemp = _cotasDao.findAll(); 
				List <Cotas> cota = new ArrayList<Cotas>();
				for(Cotas c : cotaTemp){
					if(c.getTipoCombustive().getDesignacao().equals(_selectedTipoCombustivelAtribuir.getDesignacao())){
						cota.add(c);
					}
				}
				_listModelCotas = new ListModelList <Cotas> (cota);
				lb_cotasAtribuir.setModel(_listModelCotas);    
        	}
			
			public void   preenchertipoCombustivelAtribuir(){
				List <TipoCombustive> listTipo = _tipoCombustiveDao.findAll();
				_listModelTipoCombustiveAtribuir = new ListModelList <TipoCombustive>(listTipo);
				cbb_tipoCombustivelAtribuir.setModel(_listModelTipoCombustiveAtribuir);	
			}
			
		  //---------------------------------------------------------------------------------------------------------------------------
	

		  
		  //-------------------------------------------------------Criar---------------------------------------------------------------
			
	//**************************************************************Eventos*************************************************************************
            public void onClick$btn_gravar(Event e){
				  Cotas c = new Cotas();
				  c.setQuantidade(db_quantidade.getValue());
				  c.setTipoCombustive(_selectedTipoCombustivelCriar);
				  _cotasDao.create(c);
				  Clients.showNotification("Cota criada com sucesso", "info", _win, "middle_center", 4000, true);
				  visualizarCotasCriar();
				  //VisualizarCotasAtribuir();
				  limpar();
			}
			
			public void onClick$btn_cancelar(Event e){
			   limpar();		
			}
			
            public void onSelect$cbb_tipoCombustivel(Event e){
				if(_listModelTipoCombustive.isSelectionEmpty())
					_selectedTipoCombustivelCriar = null;
				else
					_selectedTipoCombustivelCriar = (TipoCombustive) _listModelTipoCombustive.getSelection().iterator().next();	
			}
				
			//***************************************************************METODOS*********************************************************8
			  public void visualizarCotasCriar(){
					List <Cotas> cota = _cotasDao.findAll(); 
					_listModelCotas = new ListModelList <Cotas> (cota);
					lb_cotasExistentes.setModel(_listModelCotas);
					
			      	}
				
		    	public void limpar(){
		    		db_quantidade.setRawValue(null);
					cbb_tipoCombustivel.setRawValue(null);	
					}
						
			
			public void   preenchertipoCombustivel(){
				List <TipoCombustive> listTipo = _tipoCombustiveDao.findAll();
				_listModelTipoCombustive = new ListModelList <TipoCombustive>(listTipo);
				cbb_tipoCombustivel.setModel(_listModelTipoCombustive);
				
			}
			
	//----------------------------------------------------------------------------------------------------------------------------------

	
		//--------------------------------------------------Visualizar--------------------------------------------------------------------------------
			
			public void preencherOrgaoVisualizar(){
			
				List <Orgao> listOrgaoV = _orgaoDao.findAll();
			
	           for(final Orgao selectedOrgao1 : listOrgaoV){
				Listitem listItem = new Listitem();
				new Listcell(selectedOrgao1.getDesignacao()).setParent(listItem);
				
				Listcell listcell = new Listcell();
				listcell.setParent(listItem);
				Button btn_verCotas = new Button("Ver Cotas");
				btn_verCotas.setParent(listcell);
				
				Listcell listcell1 = new Listcell();
				listcell1.setParent(listItem);
				Button btn_verHistorico = new Button("Ver historico");
				btn_verHistorico.setParent(listcell1);
				
				lb_orgaosVisualizar.appendChild(listItem);
				
			
				btn_verHistorico.addEventListener("onClick", new EventListener() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						// TODO Auto-generated method stub
						
						Orgao orgao = (Orgao) Executions.getCurrent().getDesktop().getSession().setAttribute("orgao", selectedOrgao1);
							
						Executions.createComponents("/Historico.zul", null, null);
						
					}
					
				});
				
				btn_verCotas.addEventListener("onClick", new EventListener() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						// TODO Auto-generated method stub
						
						Orgao orgao = (Orgao) Executions.getCurrent().getDesktop().getSession().setAttribute("orgao", selectedOrgao1);
							
						Executions.createComponents("/VerCotas.zul", null, null);
						
					}
					
				});
			
			   }
	        
		}
			
	   //----------------------------------------------------------------------------------------------------------------------------------

	
	
}
