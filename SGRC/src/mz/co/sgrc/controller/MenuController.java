package mz.co.sgrc.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public class MenuController extends GenericForwardComposer {
	
	public void onClickOpenProd(ForwardEvent evt){
		Executions.createComponents("/produto.zul", null, null);
	}
	
	public void onClickOpenCat(ForwardEvent evt){
		//Executions.createComponents("/categoria.zul", null, null);
	}
}

