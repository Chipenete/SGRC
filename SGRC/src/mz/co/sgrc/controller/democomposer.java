package mz.co.sgrc.controller;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;

public class democomposer extends GenericForwardComposer {

    public void onClickMenu(MouseEvent event) {

        String zulFilePathName;
        Borderlayout bl = (Borderlayout) Path.getComponent("/main/mainlayout");
        /* get an instance of the searched CENTER layout area */
        Center center = bl.getCenter();

        /* clear the center child comps */
        center.getChildren().clear();

        //Messagebox.show("inside"  + event.getTarget().getId());
        zulFilePathName = event.getTarget().getId() + ".zul";
        /* create the page and put it in the center layout area */
        Executions.createComponents(zulFilePathName, center, null);
    }
}