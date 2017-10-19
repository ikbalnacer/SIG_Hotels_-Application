package org.geotools.interf;

import java.awt.event.ActionEvent;

import org.geotools.compte.Objet_G;
import org.geotools.controlers.Ctrl_Carte;
import org.geotools.model.Manupulateur;
import org.geotools.model.observer;
import org.geotools.swing.MapPane;
import org.geotools.swing.action.InfoAction;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;

public class InfoToolAction extends InfoAction implements observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Manupulateur manupulateur = new Manupulateur();
	Ctrl_Carte ctrl = new Ctrl_Carte(manupulateur);
    static boolean bool_intr = false;
    Objet_G obj = new Objet_G();
    static Interface_Selection  intr;
	public InfoToolAction(MapPane mapPane) 
	{
		super(mapPane);
        manupulateur.addObservateur(this);
	}

	
	public void actionPerformed(ActionEvent ev) {
        getMapPane().setCursorTool(new CursorTool() {
				@SuppressWarnings("deprecation")
				@Override
                public void onMouseClicked(MapMouseEvent ev) {
					java.awt.Point screenPos = ev.getPoint();
					System.out.println(ev.getMapPosition().getX()+"  "+ev.getMapPosition().getY());
					ctrl.selectionner( getMapPane().getMapContent().getCoordinateReferenceSystem(),screenPos,getMapPane().getScreenToWorldTransform());
					
					if(obj!=null){
						  intr = new  Interface_Selection(obj,bool_intr);
						Frame.ajoute_s(intr);
					  
					 }
				}
        });
        }
	
	public static void destroy_selection(){
		intr.setVisible(false);
		intr=null;
	}

	public void update(Objet_G objet) {
		this.obj=objet;
	}


	public void update() {
		// TODO Auto-generated method stub
		
	}
        
    }

