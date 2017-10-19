package org.geotools.interf;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.geotools.swing.JMapPane;
import org.geotools.swing.action.PanAction;
import org.geotools.swing.action.ResetAction;
import org.geotools.swing.action.ZoomInAction;
import org.geotools.swing.action.ZoomOutAction;

public class OutilBar extends JPanel  
{  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OutilBar() 
	{
		super();
	}
	//ajouter bouon chercher qui fait apparaitre la fenaitre de recherche
	public OutilBar (JMapPane paneauMapPane) 
	{
		JToolBar temp = new JToolBar(1);
		temp.setFloatable(false);
		temp.setRollover(true);
		temp.add(new InfoToolAction(paneauMapPane));
		temp.add(new ZoomInAction(paneauMapPane)).addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
			// Frame.refrech(0.00000000000000000000000001);
			}
		});;
		temp.add(new ZoomOutAction(paneauMapPane));
		temp.add(new ResetAction(paneauMapPane));
		temp.add(new PanAction(paneauMapPane));
		this.setLayout(new BorderLayout());
		this.add(temp,BorderLayout.CENTER);
	}
	
	// à supprimer cette methode et trouver une autre maniere
	public void ajoutBar(JPanel pan,int pos)
	{
		//essayer de faire pass.setVisible
		if (pos==0) 
		{
			this.add(pan,BorderLayout.NORTH);
		} else 
		{
			this.add(pan,BorderLayout.SOUTH);
		}
	}
	// celle là aussi
	public void supprimBar(JMapPane paneauMapPane)
	{
		new OutilBar(paneauMapPane);
	}

}
