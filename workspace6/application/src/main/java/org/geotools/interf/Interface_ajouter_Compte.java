
package org.geotools.interf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;
import org.geotools.controlers.Ctrl_Ajouter_un_Compte;
import org.geotools.model.Localisation;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;

import com.vividsolutions.jts.geom.Coordinate;
public class Interface_ajouter_Compte extends JDialog
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel nomLabel, passLabel,passLabelAffiche;
	private JTextField nomSociet;
	private Boolean can_i=true;
    
	public Interface_ajouter_Compte(JFrame parent) 
	{
		super(parent, "Ajouter Compte", true);
		init();
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	public void getUser()
	{
		this.setVisible(true);	     
	}

	private void init() 
	{
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(100, 60));
		nomSociet = new JTextField();
		nomSociet.setPreferredSize(new Dimension(100, 25));
		panNom.setBorder(BorderFactory.createTitledBorder("Le Nom de La Société, Id compte"));
		nomLabel = new JLabel("User :");
		panNom.add(nomLabel);
		panNom.add(nomSociet);

		//Le pass
		JPanel panPass = new JPanel();
		panPass.setBackground(Color.white);
		panPass.setPreferredSize(new Dimension(100, 60));
		panPass.setBorder(BorderFactory.createTitledBorder("Paseword"));
		passLabel = new JLabel("Pass :");
		panPass.add(passLabel);
		passLabelAffiche = new JLabel();
		panPass.add(passLabelAffiche);
		JButton gener= new JButton("generer mot de passe");
		panPass.add(gener);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");

		gener.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent arg0) 
			{
				passLabelAffiche.setText(gener());
			}

			private String gener()
			{

				String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; 
				String pass = "";
				for(int x=0;x<10;x++)
				{
					int i = (int)Math.floor(Math.random() * 62); 
					pass += chars.charAt(i);
				}
				return pass;
			}
		});

		okBouton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				 if( nomSociet.getText().length()>4 &&passLabelAffiche.getText().length()>4){	
						setVisible(false);
					    Frame.paneauMapPane.setCursorTool(new CursorTool() {
				   @Override
				   public void onMouseClicked(MapMouseEvent ev) {
					if(can_i ){	
							 Compte compte = new Compte(passLabelAffiche.getText(), nomSociet.getText());
							 Hotel hotel = new Hotel();
							 @SuppressWarnings("deprecation")
							Localisation loc = new Localisation(new Coordinate(ev.getMapPosition().getX(),ev.getMapPosition().getY()));
							 hotel.setLocalisation(loc);
							 Ctrl_Ajouter_un_Compte ctrl = new Ctrl_Ajouter_un_Compte();
							 ctrl.ajouter_un_compte(hotel,compte);	
							 can_i=false;
					  }
					}
				});
						dispose(); }else {
							JOptionPane.showMessageDialog(null, "gener mot de pass et ajouter nom >4 caracter", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}}
			
		});

		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}      
		});

		control.add(okBouton);
		control.add(cancelBouton);


		this.add(panNom, BorderLayout.NORTH);
		this.add(panPass, BorderLayout.CENTER);
		this.add(control, BorderLayout.SOUTH);	

	}

	

    
}

