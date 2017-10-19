package org.geotools.interf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class APropos extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public APropos(JFrame parent, String title, boolean modal)
	{
		super(parent, title, modal);
		this.setSize(700, 450);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
		this.setVisible(true);
	}

	private void initComponent()
	{

		
		JPanel imagePan = new JPanel();
		JLabel textBienVenu = new JLabel(new ImageIcon("propos.png"));
		imagePan.add(textBienVenu, BorderLayout.CENTER);
	
		
		
		JButton cancelBouton = new JButton("Retour");
		cancelBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}      
		});
		
		
		JPanel control = new JPanel();
		control.add(cancelBouton);
		control.setBackground(Color.WHITE);
		

		this.add(imagePan,BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}  
	
}