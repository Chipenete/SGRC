package mz.co.sgrc.controller;



import java.util.List;
import java.util.Set;

import mz.co.sgrc.dao.ItemRequisicaoFornecedorDAO;
import mz.co.sgrc.model.ItemRequisicaoFornecedor;
import mz.co.sgrc.model.Requisicao;
import mz.co.sgrc.model.RequisicaoFornecedor;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class ItemRequisicaoFornecedorController extends SelectorComposer<Component> {

	@Wire
	private Listbox lb_itemRequisicao;
	
	@Wire
	private Button btn_remessaFornecedor;
	@Wire
	private ItemRequisicaoFornecedorDAO itemFornecedorDAO;
	@Wire
	private Window janelaItemRequisicaoFornecedorController;
	
	private ListModelList listRequisicaoFornecedor;
	
	RequisicaoFornecedor requisicaoFornecedor = (RequisicaoFornecedor) Executions.getCurrent().getDesktop().getSession().getAttribute("requisicaoFornecedor");
	
	public void doAfterCompose (Component comp) throws Exception{
		super.doAfterCompose(comp);
		itemFornecedorDAO = new ItemRequisicaoFornecedorDAO();
		mostrarItems();
	
	}
	
	public void mostrarItems(){
		List <ItemRequisicaoFornecedor> listt= requisicaoFornecedor.getListRequisicaoFornecedor();
		listRequisicaoFornecedor = new ListModelList <ItemRequisicaoFornecedor>(listt);
		listRequisicaoFornecedor.setMultiple(true);
		lb_itemRequisicao.setModel(listRequisicaoFornecedor);
	}
	
	@Listen ("onClick = #btn_remessaFornecedor")
	public void onClickRemessaFornecedor(){
		
		Set<Listitem> listRemessados = lb_itemRequisicao.getSelectedItems(); 
		
		for (Listitem listitem : listRemessados) {
			
			ItemRequisicaoFornecedor itemReqFornecedor= (ItemRequisicaoFornecedor) listitem.getValue();
			
			itemReqFornecedor.setRemessada(true);
			itemFornecedorDAO.update(itemReqFornecedor);
			listitem.setDisabled(true);
			
		}
		
		Messagebox.show("Itens Remessados com sucesso");
		//janelaItemRequisicaoFornecedorController.isClosable();
		mostrarItems();
		listRequisicaoFornecedor.setMultiple(false);
		btn_remessaFornecedor.setDisabled(true);
		
	}
}
