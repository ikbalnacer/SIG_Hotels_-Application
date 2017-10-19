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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.geotools.compte.Compte;
import org.geotools.controlers.Ctrl_Authentification;

public class Interface_Identification extends JDialog 
{
	

	private static final long serialVersionUID = 1L;
	private JLabel nomLabel, passLabel;
	private JTextField nom;
	private JPasswordField pass;
	private Ctrl_Authentification ctrl_auth = new Ctrl_Authentification();
	public Interface_Identification(JFrame parent, String title, boolean modal)
	{
		
		super(parent, title, modal);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			SwingUtilities.updateComponentTreeUI(this);
			}catch (InstantiationException e) {}
			catch (ClassNotFoundException e) {}
			catch (UnsupportedLookAndFeelException e) {}
			catch (IllegalAccessException e) {}
		this.setSize(250, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initComponent();
		this.setVisible(true);   
	}


	private void initComponent()
	{   

		//Le nom
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(220, 60));
		nom = new JTextField();
		nom.setPreferredSize(new Dimension(100, 25));
		panNom.setBorder(BorderFactory.createTitledBorder("Votre ID..."));
		nomLabel = new JLabel("User :");
		panNom.add(nomLabel);
		panNom.add(nom);

		//Le pass
		JPanel panPass = new JPanel();
		panPass.setBackground(Color.white);
		panPass.setPreferredSize(new Dimension(220, 60));
		pass = new JPasswordField();
		pass.setPreferredSize(new Dimension(100, 25));
		panPass.setBorder(BorderFactory.createTitledBorder("Votre Paseword..."));
		passLabel = new JLabel("Pass :");
		panPass.add(passLabel);
		panPass.add(pass);

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panPass);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");

		okBouton.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0)
			{   if(nom.getText() !=null && pass.getText()!=null)
				{setVisible(false);
				Compte c = new Compte(pass.getText(), nom.getText());
			     ctrl_auth.consulter(c);
				}
			}
			
			 			 
		});

		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}      
		});
   
		control.add(okBouton);
		control.add(cancelBouton);

		//ajouter une immage 
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}

   


	
	
}