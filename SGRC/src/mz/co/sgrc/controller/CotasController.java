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

public class CotasController extends SelectorComposer<Component>{

	@Wire
	private Doublebox db_quantidadeGasolina;
	
	@Wire 
	private Doublebox db_quantidadeGasoleo;
	
	//--------------------------------------------------Visualizar-------------------------------------------------------------
	@Wire
	private Listbox lb_orgaosVisualizar;
	
	@Wire
	private Button btn_procurarVisualizar;
	
	@Wire
	private Textbox tb_designacaoVisualizar;
	
	
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	//-----------------------------------------------------Criar---------------------------------------------------------------
	@Wire
	private Button btn_gravar;
	
	@Wire
	private Button btn_actualizar;
	
	@Wire
	private Button btn_cancelar;
	
	@Wire
	private Listbox lb_cotasExistentes;
	
	
	private Orgao_has_Cota selectedOHC;
	@Wire
	private Combobox cbb_tipoCombustivel;
	@Wire
	private Doublebox db_quantidade;
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------Atribuir--------------------------------------------------------------------
	@Wire 
	private Button btn_novo;
	
	@Wire 
	private Button btn1_gravar;
	
	@Wire
	private Button btn1_cancelar;
	
	@Wire
	private Button btn_procurarAtribuir;
	
	@Wire 
	private Button btn1_historico;
	
	@Wire 
	private Button btn1_historico1;
	
	@Wire
	private Combobox cbb_cotasAtribuir;
	
	@Wire
	private Combobox cbb_tipoCombustivelAtribuir;
	
	@Wire
	private Combobox cbb_orgaos;
	
	@Wire
	private Listbox lb_orgaosAtribuir;
	
	@Wire
	private Listbox lb_cotasAtribuir;
	
	@Wire 
	private Listbox lb_historico = new Listbox();
	
    @Wire
    private Textbox tb_designacaoAtribuir;
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------

	
	
	@Wire
	private Listbox lb_cotas;
	
	@Wire
	private Listbox lb_orgao;
	

	

	@Wire
	private CotasDAO cotasDAO;
	
	@Wire
	private OrgaoDAO orgaoDAO;
	
	@Wire
	private HistoricoDAO historicoDAO;
	
	@Wire
	private Orgao_has_CotaDAO orgao_has_CotaDAO;
	
	@Wire
	private TipoCombustiveDAO tipoCombustiveDAO;
	

	
	private boolean novoOrgao;
	

	
	Cotas selectedCota;
	
	Orgao selectedOrgao;
	
	Orgao selectedOrgao1;
	
	TipoCombustive selectedTipoCombustivelCriar;
	TipoCombustive selectedTipoCombustivelAtribuir;
	
	private List <Cotas> listCot;

	
	private ListModelList <Cotas> listCotas, listCota;
	private ListModelList <Orgao> listOrgao, listOrgao1,listOrgaoAtribuir,listOrgaoVisualizar;
	private ListModelList <Historico> listHistorico;
	private ListModelList <Orgao_has_Cota> listOrgaoCota;
	private ListModelList <TipoCombustive> listTipoCombustive, listTipoCombustiveAtribuir;
	
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
	
		cotasDAO = new CotasDAO();
		orgaoDAO = new OrgaoDAO(); 
		historicoDAO = new HistoricoDAO();
		orgao_has_CotaDAO = new Orgao_has_CotaDAO();
		tipoCombustiveDAO = new TipoCombustiveDAO();
		
		preencherOrgaoVisualizar();
		
		visualizarCotasCriar();
		//VisualizarCotasAtribuir();
		//preencherCotas();
	//	preencherOrgao();
	    preencherOrgaoAtribuir();
	    preenchertipoCombustivelAtribuir();
	    preenchertipoCombustivel();
		novoOrgao = false;
		selectedOrgao1=null;
		selectedTipoCombustivelCriar=null;
		selectedTipoCombustivelAtribuir=null;
	}
	
	

	public CotasController(){}
	

	
	//------------------------------------------------------------------Atribuir------------------------------------------------------------
	
	/*  @Listen ("onClick = #btn_novo")
		  public void onClickNovo(){
	
					novoOrgao =true;
					Executions.createComponents("/Orgao.zul", null, null);
				
					
					
				}*/
	
		  
		  @Listen ("onOpen = #cbb_cotasAtribuir")
			public void comboAberto(){
				//alert("fdfgh");
			}
		  
		  
		 @Listen("onClick = #btn1_gravar")
		 public void onClickGravar1(){
			 if(selectedCota!=null){
			
				
				 selectedOHC = new Orgao_has_Cota();
				
				
				 selectedOHC.setCotas(selectedCota);
				 
			
				 selectedOHC.setData(new Date());
				 
				 orgao_has_CotaDAO.create(selectedOHC);
				 
				 Set<Listitem> listItens = lb_orgaosAtribuir.getSelectedItems();
				 
				 for (Listitem listItem : listItens){
					
					 Orgao org = (Orgao) listItem.getValue(); 
                     
					 Orgao orgao = orgaoDAO.returnar(org);
					 
					 //orgao.getListOrgaoHasCota().add(selectedOHC);
					 
					 orgao.setCota_id(selectedCota.getId());
					 
					 if(selectedCota.getTipoCombustive().getDesignacao().equals("Gasoleo")){
						 orgao.setQuantidadeGasoleo(selectedCota.getQuantidade());
						 orgao.setCotaGasoleo(selectedCota.getQuantidade());
					 }
					 else if(selectedCota.getTipoCombustive().getDesignacao().equals("Gasolina")){
						 orgao.setQuantidadeGasolina(selectedCota.getQuantidade()); 
						 orgao.setCotaGasolina(selectedCota.getQuantidade());
						 
					 }
					 else if(selectedCota.getTipoCombustive().getDesignacao().equals("Gas")){
						 orgao.setQuantidadeGas(selectedCota.getQuantidade());
						 orgao.setCotaGas(selectedCota.getQuantidade());
						 
					 }
					 orgaoDAO.update(orgao);
					 selectedOHC.setOrgao(orgao);
					 orgao_has_CotaDAO.update(selectedOHC);
				 }
				 
				 
				 
				 
				 
				
				 preencherOrgaoAtribuir();
				 Messagebox.show("Cota atribuida com sucesso.");
				 limpar1();
				
				 
			 }
			 else{
				 Messagebox.show("Seleccione a cota.");
				 
			 }
			 
			 selectedCota=null;
			 
		 }
		 
		 
		 
		  
		/*@Listen("onSelect = #cbb_orgaos")
		public void selectedOrgaos(){
			if(listOrgao.isSelectionEmpty()){
				selectedOrgao = null;
			}
			else{
				selectedOrgao = listOrgao.getSelection().iterator().next();
				}
			
		}*/
		 
		 
		 
		 
		@Listen("onSelect=#lb_cotasAtribuir")
		public void onSelectCotas(){
			
			if(listCotas.isSelectionEmpty()){
				selectedCota = null;
				
				
			}
			else{
				selectedCota=listCotas.getSelection().iterator().next();
				cbb_cotasAtribuir.setValue(""+selectedCota.getId());
				
			}
			
		}
		
		
		@Listen("onSelect=#cbb_tipoCombustivelAtribuir")
		public void onSelectTipoAtribuir(){
			if(listTipoCombustiveAtribuir.isSelectionEmpty()){
				selectedTipoCombustivelAtribuir = null;
			}
			else{
				selectedTipoCombustivelAtribuir = (TipoCombustive) listTipoCombustiveAtribuir.getSelection().iterator().next();
				
				VisualizarCotasAtribuir();
			}
			
			
		}
		
		
		@Listen("onClick = #btn1_cancelar")
		public void onClickCancelar1(){
			
			limpar1();
		}
		
		
		@Listen("onSelect = #lb_orgaos1")
		public void onClickHistorico(){
			
			if(listOrgao1.isSelectionEmpty()){
				selectedOrgao1=null;
			}
			else{
				
				selectedOrgao1=listOrgao1.getSelection().iterator().next();
				Messagebox.show(selectedOrgao1+"");
				
			}
			
			
		}

		
		@Listen("onClick = #btn_procurarAtribuir")
		public void onClickProcurar(){
			List<Orgao> listOrg = orgaoDAO.pesquisaOrgaos(tb_designacaoAtribuir.getText());
			listOrgaoAtribuir = new ListModelList<Orgao>(listOrg);
			listOrgaoAtribuir.setMultiple(true);
			lb_orgaosAtribuir.setModel(listOrgaoAtribuir);
			
		}
		
		
		
		
		
		/*  public void preencherCotas(){
				List <Cotas> cot = cotasDAO.findAll();
				listCota = new ListModelList<Cotas>(cot);
				cbb_cotasAtribuir.setModel(listCota);
				
			}*/
			
	
		  public void preencherOrgao(){
				List <Orgao> org = orgaoDAO.findAll();
				listOrgao = new ListModelList<>(org);
				cbb_orgaos.setModel(listOrgao);
				}
		   
		  
		  public void preencherOrgaoAtribuir(){
				List <Orgao> org = orgaoDAO.findAll();
				listOrgaoAtribuir = new ListModelList<Orgao>(org);
				listOrgaoAtribuir.setMultiple(true);
				lb_orgaosAtribuir.setModel(listOrgaoAtribuir);
				
				}
		  
		  public void limpar1(){
			  cbb_cotasAtribuir.setValue(null);
			  cbb_tipoCombustivelAtribuir.setValue(null);
			  
			  
			  
		  }
		  
			private void VisualizarCotasAtribuir() {
				 
				List <Cotas> cotaTemp = cotasDAO.findAll(); 
				List <Cotas> cota = new ArrayList<Cotas>();
				for(Cotas c : cotaTemp){
					if(c.getTipoCombustive().getDesignacao().equals(selectedTipoCombustivelAtribuir.getDesignacao())){
						cota.add(c);
					}
				}
				
				listCotas = new ListModelList <Cotas> (cota);
				lb_cotasAtribuir.setModel(listCotas);
				
			    
        	}
			
			public void   preenchertipoCombustivelAtribuir(){
				List <TipoCombustive> listTipo = tipoCombustiveDAO.findAll();
				listTipoCombustiveAtribuir = new ListModelList <TipoCombustive>(listTipo);
				cbb_tipoCombustivelAtribuir.setModel(listTipoCombustiveAtribuir);
				
			}
			
		 /* public void preencherHistorico(){
				List <Historico> hist = dd.findAll();
				listHistorico = new ListModelList<Historico>(hist);
				lb_historico.setModel(listHistorico);
		  }*/
		  //---------------------------------------------------------------------------------------------------------------------------
	

		  
		  //-------------------------------------------------------Criar---------------------------------------------------------------
	
	
			
			@Listen ("onClick = #btn_gravar")
			public void onClickGravar(){
				
				  Cotas c = new Cotas();
				  c.setQuantidade(db_quantidade.getValue());
				  c.setTipoCombustive(selectedTipoCombustivelCriar);
				  cotasDAO.create(c);
				  Messagebox.show("Cota criada Com sucesso.");
				  visualizarCotasCriar();
				  //VisualizarCotasAtribuir();
				  limpar();
			}
			
			@Listen("onClick = #btn_cancelar")
			public void onClickCancelar(){
			   limpar();
				
			}
			
			@Listen("onSelect=#cbb_tipoCombustivel")
			public void onSelectTipo(){
				if(listTipoCombustive.isSelectionEmpty()){
					selectedTipoCombustivelCriar = null;
				}
				else{
					selectedTipoCombustivelCriar = (TipoCombustive) listTipoCombustive.getSelection().iterator().next();
					
				}
				
				
			}
				
			
			  public void visualizarCotasCriar(){
					List <Cotas> cota = cotasDAO.findAll(); 
					listCotas = new ListModelList <Cotas> (cota);
					lb_cotasExistentes.setModel(listCotas);
					
			      	}
				
						public void limpar(){
							db_quantidade.setRawValue(null);
							cbb_tipoCombustivel.setRawValue(null);
							
						}
						
			
			public void   preenchertipoCombustivel(){
				List <TipoCombustive> listTipo = tipoCombustiveDAO.findAll();
				listTipoCombustive = new ListModelList <TipoCombustive>(listTipo);
				cbb_tipoCombustivel.setModel(listTipoCombustive);
				
			}
			
	//----------------------------------------------------------------------------------------------------------------------------------

	
		//--------------------------------------------------Visualizar--------------------------------------------------------------------------------
			
			public void preencherOrgaoVisualizar(){
			
				List <Orgao> listOrgaoV = orgaoDAO.findAll();
			
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
			
			
//			@Listen("onClick = #btn_procurarVisualizar")
//			public void onClickProcurarV(){
//				List <Orgao> listOrgVisualizar = orgaoDAO.pesquisaOrgaos(tb_designacaoAtribuir.getText());
//				listOrgaoVisualizar = new ListModelList<Orgao>(listOrgVisualizar);
//				listOrgaoVisualizar.setMultiple(true);
//				lb_orgaosVisualizar.setModel(listOrgaoVisualizar);
//				
//			}
			
	   //----------------------------------------------------------------------------------------------------------------------------------

	
	
}
