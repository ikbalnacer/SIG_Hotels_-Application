package org.geotools.interf;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.geotools.compte.Budget;
import org.geotools.compte.Hotel;
import org.geotools.controlers.Ctrl_rechercher;
import org.geotools.model.Manupulateur;

public class Interface_rechercher extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JRadioButton min, moy,max,sing, doubl,plus_proche, plus_proche_and,ignorer,affaire,tourism,transit;
	private Hotel hotel = new Hotel();
    private Ctrl_rechercher ctrl_rechercher = new Ctrl_rechercher();
	public Interface_rechercher(JFrame parent,ArrayList<String> divertissement)
	{
		super(parent, "Chercher Hotel", true);
		this.setSize(300,300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
		this.setVisible(true);
	}

	private void initComponent()
	{
		JPanel panbudget = new JPanel();
		panbudget.setBackground(Color.white);
		panbudget.setBorder(BorderFactory.createTitledBorder("Budget en Dinar Algrien "));
		panbudget.setPreferredSize(new Dimension(220, 60));
		min = new JRadioButton("De 0 DA , 5 000 DA");
		moy= new JRadioButton("De 5 000 DA , 10 000 DA");
		max= new JRadioButton("Plus de 10 000 DA");
		ButtonGroup bgBudget = new ButtonGroup();
		min.setSelected(true);
		bgBudget.add(min);
		bgBudget.add(moy);
		bgBudget.add(max);
		panbudget.add(min);
		panbudget.add(moy);
		panbudget.add(max);

		JPanel panTypeChambre = new JPanel();
		panTypeChambre.setBackground(Color.white);
		panTypeChambre.setBorder(BorderFactory.createTitledBorder("Type De Chambre"));
		panTypeChambre.setPreferredSize(new Dimension(240, 100));
		sing = new JRadioButton("Single");
		doubl= new JRadioButton("Double");
		ButtonGroup bgTypeChambre = new ButtonGroup();
		sing.setSelected(true);
		bgTypeChambre.add(sing);
		bgTypeChambre.add(doubl);
		panTypeChambre.add(sing);
		panTypeChambre.add(doubl);
	
		JPanel panSituation = new JPanel();
		panSituation.setBackground(Color.white);
		panSituation.setBorder(BorderFactory.createTitledBorder("Situation"));
		panSituation.setPreferredSize(new Dimension(240, 100));
		plus_proche = new JRadioButton("plus proche ");
		plus_proche_and= new JRadioButton(" juste le plus proche :");
		ignorer= new JRadioButton("ignorer ");
		ButtonGroup  bgSituation = new ButtonGroup();
		ignorer.setSelected(true);
		if(Manupulateur.getLoc()!=null){
		bgSituation.add(plus_proche);
		bgSituation.add(plus_proche_and);
		bgSituation.add(ignorer);
		panSituation.add(plus_proche);
		panSituation.add(plus_proche_and);
		panSituation.add(ignorer);
		}else{
			plus_proche.setEnabled(false);
			bgSituation.add(plus_proche);
			plus_proche_and.setEnabled(false);
			bgSituation.add(plus_proche_and);
			ignorer.setEnabled(false);
			bgSituation.add(ignorer);
			panSituation.add(plus_proche);
			panSituation.add(plus_proche_and);
			panSituation.add(ignorer);
		}
		JPanel panStyle = new JPanel();
		panStyle.setBackground(Color.white);
		panStyle.setBorder(BorderFactory.createTitledBorder("Type d'etablissement"));
		panStyle.setPreferredSize(new Dimension(220, 60));
		affaire = new JRadioButton("Affaire");
		transit= new JRadioButton("Transite");
		tourism= new JRadioButton("Tourisme");
		ButtonGroup bgStyle = new ButtonGroup();
		affaire.setSelected(true);
		bgStyle.add(affaire);
		bgStyle.add(transit);
		bgStyle.add(tourism);
		panStyle.add(affaire);
		panStyle.add(transit);
		panStyle.add(tourism);


		JPanel contentNorth = new JPanel();
		contentNorth.setLayout(new GridLayout(2, 2));
		contentNorth.add(panbudget);
		contentNorth.add(panTypeChambre);
		contentNorth.add(panSituation);
		contentNorth.add(panStyle);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("Chercher");

		okBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{        
	            hotel = new Hotel();
	            hotel.setStyle(getStyle());
	            hotel.setType_chamber(getTypeChambre());
	            hotel.setPoint_interet(getSituation());	            
	            hotel.setBudget(getBudget());
	            ctrl_rechercher.rechercher(hotel);
				setVisible(false);
			}

			private String getTypeChambre()
			{
				return (sing.isSelected())   ? sing.getText()  :
					   (doubl.isSelected())  ? doubl.getText() :
				    	sing.getText();
			}
			
			private String getSituation() 
			{
				return (ignorer.isSelected())    ? ignorer.getText()  :
					   (plus_proche_and.isSelected())  ? plus_proche_and.getText():
					   (plus_proche.isSelected())     ? plus_proche.getText()   :
						ignorer.getText();
			}

			private String getStyle() 
			{
				return (affaire.isSelected())    ? affaire.getText()  :
					   (tourism.isSelected())  ? tourism.getText():
					   (transit.isSelected())     ? transit.getText()   :
						affaire.getText();
			}

			private Budget getBudget() 
			{
				return (min.isSelected())  ? new Budget(0, 5000)  :
					   (moy.isSelected())  ? new Budget(5000, 10000):
					   (max.isSelected())  ? new Budget(10000, 25000)   :
						new Budget(0, 5000);
			}
		});

		JButton cancelBouton = new JButton("Annuler");
	
		cancelBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				setVisible(false);
			}      
		});

		control.add(okBouton);
		control.add(cancelBouton);
       
		//ajouter une immage 
		this.add(contentNorth,BorderLayout.CENTER);

		this.getContentPane().add(control, BorderLayout.SOUTH);
	}  


}