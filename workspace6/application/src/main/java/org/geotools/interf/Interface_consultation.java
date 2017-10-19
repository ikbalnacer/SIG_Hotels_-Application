package org.geotools.interf;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.geotools.compte.Bar;
import org.geotools.compte.Chamber;
import org.geotools.compte.Hotel;
import org.geotools.compte.Restaurant;

public class Interface_consultation extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel imagePanel=new JPanel(), 
			hotelPanel= new JPanel(),
			controlPanel = new JPanel();

	public Interface_consultation(Hotel hotel) 
	{
		this.setLayout(new BorderLayout());
		initComponentImagesPanel(hotel);
		initComponenthotelPanel(hotel);
		initComponentContolPanel();

		this.add(imagePanel, BorderLayout.NORTH);
		this.add(hotelPanel,BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);

		this.setSize(650, 730);	
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);

	}
	
	private void initComponentImagesPanel(Hotel hotel) 
	{
		JPanel panP1 = new JPanel();		
		int i =hotel.getConfort().getList_image().size();

		if(i>0)
			panP1.add(new JLabel(hotel.getConfort().getList_image().get(0)));
		if(i>1)
			panP1.add(new JLabel(hotel.getConfort().getList_image().get(1)));
		if(i>2)
			panP1.add(new JLabel(hotel.getConfort().getList_image().get(2)));
		for (int j= i; j <3; j++) {
			imagePanel.add(new JLabel(new ImageIcon("index.png")));
		}
		
		imagePanel.add(panP1, BorderLayout.CENTER);
		imagePanel.setPreferredSize(new Dimension(100,130));
	}
	
	private void initComponenthotelPanel(Hotel hotel) 
	{
		JPanel centralPanel = new JPanel();
		JPanel centralNPanel = new JPanel();
		JPanel centralSPanel = new JPanel();

		JPanel descriptionPanel = new JPanel();
		JPanel info_PratiquePanel = new JPanel();
		JPanel divertissementPanel = new JPanel();
		JPanel chambrePanel = new JPanel();
		JPanel restorationPanel = new JPanel();

		centralNPanel.setLayout(new GridLayout(1,0));
		centralNPanel.add(descriptionPanel);
		centralNPanel.add(chambrePanel);
        chambrePanel.setPreferredSize(new Dimension(120,180));
		centralSPanel.setLayout(new FlowLayout());
		centralSPanel.add(restorationPanel);
		centralSPanel.add(info_PratiquePanel);
		centralSPanel.setBackground(Color.white);

		centralPanel.setLayout(new BorderLayout());
		centralPanel.add(centralNPanel, BorderLayout.NORTH);
		centralPanel.add(centralSPanel,BorderLayout.CENTER);
		centralPanel.add(divertissementPanel,BorderLayout.SOUTH);
		info_PratiquePanel.setPreferredSize(new Dimension(240,120));
		restorationPanel.setPreferredSize(new Dimension(390,180));


		initComponenthotelDescriptionPanel(descriptionPanel,hotel);
		initComponenthotelInfo_PratiquePanel(info_PratiquePanel,hotel);
		initComponenthotelDivertissementPanel(divertissementPanel,hotel.getConfort().getDivertissement());
		initComponenthotelChambrePanel(chambrePanel, hotel.getListchamber());
		initComponenthotelResto_BarPanel(restorationPanel,hotel);
		hotelPanel.setLayout(new BorderLayout());
		hotelPanel.add(centralPanel, BorderLayout.CENTER);
		hotelPanel.add(descriptionPanel, BorderLayout.NORTH);
			}

	private void initComponenthotelDescriptionPanel(JPanel descriptionPanel,Hotel hotel) 
	{   String str ="";
		if(hotel.getNom()!=null) {
			str=hotel.getNom();
		}
		JLabel nomLabel = new JLabel(" Nom : "+str);
		JLabel etoilLabel = new JLabel(" Etoile : "+hotel.getEtoile());
		JLabel notelabel = new JLabel(" note : "+hotel.getNote());
		if(hotel.getStyle()!=null) {
			str=hotel.getStyle();
		}else{str="";}
		JLabel styleLabel = new JLabel(" Style : "+str);
		JLabel telLabel = new JLabel(" Telephone:"+hotel.getTelephone());
		if(hotel.getAdress()!=null) {
			str=hotel.getAdress();
		}else{str="";}
		JLabel adressLabel = new JLabel(" Adresse : "+str);
		JLabel nbr_de_place = new JLabel(" number des chambers : "+hotel.getNbr());
		if(hotel.getNomService()!=null) {
			str=hotel.getNomService();
		}else{str="";}
		JLabel service_type = new JLabel(" service : "+str);

		descriptionPanel.setBackground(Color.white);
		descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"));
		descriptionPanel.setLayout(new GridLayout(3,0));
		descriptionPanel.add(nomLabel);
		descriptionPanel.add(etoilLabel);
		descriptionPanel.add(notelabel);
		descriptionPanel.add(styleLabel);
		descriptionPanel.add(telLabel);
		descriptionPanel.add(adressLabel);
		descriptionPanel.add(nbr_de_place);
		descriptionPanel.add(service_type);
		//size	
	}

	private void initComponenthotelInfo_PratiquePanel(JPanel info_PratiquePanel,Hotel hotel) 
	{   String str ="";
	    if(hotel.getPoint_interet()!=null){
	    	str =hotel.getPoint_interet();	
	    }
		JLabel pointIntertLabel = new JLabel("Point Interet :"+str);
		 if(hotel.getSite_WEb()!=null){
		    	str =hotel.getSite_WEb();	
		    }else{
		    	str="";
		    }
		JLabel siteWebLabel = new JLabel("Site Web : "+str);
		info_PratiquePanel.setBorder(BorderFactory.createTitledBorder("Info Pratiques"));
		info_PratiquePanel.setLayout(new GridLayout(3,0));
		//info_PratiquePanel.add(localisationLabel);
		info_PratiquePanel.add(pointIntertLabel);
		info_PratiquePanel.add(siteWebLabel);

		info_PratiquePanel.setBackground(Color.white);
	}

	private void initComponenthotelDivertissementPanel(JPanel divertissementPanel,ArrayList<String> divertissement) 
	{
		JLabel tempLabel ;
		divertissementPanel.setBackground(Color.white);;	
		divertissementPanel.setBorder(BorderFactory.createTitledBorder("Divertissement"));
		divertissementPanel.setLayout(new GridLayout((divertissement.size()/4)+1,0));
		for (String diver : divertissement) 
		{
			tempLabel = new JLabel(diver);
			divertissementPanel.add(tempLabel);
		}		
	}

	private void initComponenthotelChambrePanel(JPanel chambrePanel, ArrayList<Chamber> listChambre) 
	{
		chambrePanel.setBorder(BorderFactory.createTitledBorder("Type Chambres "));
		JTabbedPane tabbedPane = new JTabbedPane();
		chambrePanel.setBackground(Color.white);
		ImageIcon icon = new ImageIcon("rooms_icon.png");
		tabbedPane.setBackground(Color.gray);
		JComponent tempPanel;

		for (int i = 0; i < listChambre.size(); i++)
		{
			tempPanel = ConstructionPanel(listChambre.get(i));
			tabbedPane.addTab(listChambre.get(i).getType(),icon ,  tempPanel);
		}
		chambrePanel.add(tabbedPane);

	}

	protected JComponent ConstructionPanel(Chamber chambe) 
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());


		JPanel iconPanel = new JPanel();
		if(chambe.getImage()!=null){
		iconPanel.add(new JLabel(chambe.getImage()));}else{
		iconPanel.add(new JLabel(new ImageIcon("index.png")));
		}
		iconPanel.setBackground(Color.white);

		JPanel caractPanle = new JPanel();
		caractPanle.setLayout(new BorderLayout());

		JPanel prixPanel= new JPanel();
		prixPanel.setBackground(Color.white);
		if(chambe.getPrix()!=null){
		prixPanel.add(new JLabel(" Prix : "+ chambe.getPrix()));
		}else{
			prixPanel.add(new JLabel(" Prix : "));

		}

		JPanel caractPanel= new JPanel();
		caractPanel.setBackground(Color.white);
		caractPanel.setLayout(new GridLayout(0,4));
		for (String caract : chambe.getCaracteristiques()) 
		{
			caractPanel.add(new JLabel(caract));
			caractPanel.add(new JLabel("    "));
		}

		caractPanle.add(prixPanel,BorderLayout.NORTH);
		caractPanle.add(caractPanel,BorderLayout.CENTER);

		panel.add(iconPanel, BorderLayout.CENTER);
		panel.add(caractPanle,BorderLayout.EAST);
		return panel;
	}

	private void initComponenthotelResto_BarPanel(JPanel restorationPanel,Hotel hotel) 
	{
		restorationPanel.setBackground(Color.WHITE);
		restorationPanel.setBorder(BorderFactory.createTitledBorder("Restaurant -- Bar "));

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = new ImageIcon("image_Room.png");
		tabbedPane.setBackground(Color. gray);
		JComponent tempPanel;

		tempPanel = ConstructionRestoPanel(hotel.getRestaurant());
		tabbedPane.addTab( "restaurant", icon,  tempPanel);

		tempPanel = ConstructionBarPanel(hotel.getBar());
		tabbedPane.addTab("Bar", icon,  tempPanel);

		restorationPanel.add(tabbedPane);
	}

	protected JComponent ConstructionRestoPanel(Restaurant resto) 
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());

		JPanel iconPanel = new JPanel();
		
		if(resto.getImage()!=null){
		JLabel label = new JLabel(resto.getImage());
		label.setPreferredSize(new Dimension(100, 100));
		iconPanel.add(label);
		}else{
			iconPanel.add(new JLabel(new ImageIcon("index.png")));
		}
		iconPanel.setBackground(Color.white);

		JPanel caractPanle = new JPanel();
		caractPanle.setBackground(Color.white);
		caractPanle.setLayout(new GridLayout(3,0));

		caractPanle.add(new JLabel(" Horaires Restorant : "+resto.getHoraire()));
		caractPanle.add(new JLabel(" Capacity Restaurant : "+resto.getNbrPlaces()));
		if(resto.getTypePlat()!=null){
		caractPanle.add(new JLabel(" Type de Restauration :"+resto.getTypePlat()));
		}else{
			caractPanle.add(new JLabel(" Type de Restauration :"));
		}
		panel.add(iconPanel, BorderLayout.CENTER);
		panel.add(caractPanle,BorderLayout.EAST);
		return panel;
	}

	protected JComponent ConstructionBarPanel(Bar bar) 
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());


		JPanel iconPanel = new JPanel();
		if(bar.getImage()!=null){
		JLabel label =new JLabel(bar.getImage());
		label.setPreferredSize(new Dimension(100, 100));
		iconPanel.add(label);
		}else{
			iconPanel.add(new JLabel(new ImageIcon("index.png")));
		}
		iconPanel.setBackground(Color.white);

		JPanel caractPanle = new JPanel();
		caractPanle.setBackground(Color.white);
		caractPanle.setLayout(new BorderLayout());

		caractPanle.add(new JLabel("Horaires Bar : "+bar.getHoraire()), BorderLayout.WEST);		

		panel.add(iconPanel, BorderLayout.WEST);
		panel.add(caractPanle,BorderLayout.CENTER);
		return panel;
	}

	private void initComponentContolPanel() 
	{
		JButton retour = new JButton("Retour");
		retour.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				setVisible(false);
			}
		});
		controlPanel.add(retour);
	}

}
