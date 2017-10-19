package org.geotools.interf;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.geotools.compte.Bar;
import org.geotools.compte.Chamber;
import org.geotools.compte.Compte;
import org.geotools.compte.Confort;
import org.geotools.compte.Hotel;
import org.geotools.compte.Restaurant;
import org.geotools.controlers.Ctrl_Modification;

public class Interface_Mettre_ajour_Hotel  extends JDialog 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Compte compte ;
	private Hotel hotel = new Hotel();
	private ArrayList<Chamber> chambeList = new ArrayList<Chamber>();
	private Bar bar=new Bar();
	private Restaurant rest = new Restaurant();
	private Confort confortList = new Confort();
	ListConfo conf ;
    private Ctrl_Modification ctrl_mod = new Ctrl_Modification();
    static int  number =0;
	public Interface_Mettre_ajour_Hotel(Hotel hotel,Compte compte) 
	{	this.compte=compte;
	   

		this.hotel = hotel;
		this.chambeList=hotel.getListchamber();
		confortList.setList_image(hotel.getConfort().getList_image());
		for (int i = 0; i < chambeList.size(); i++) {
		  if(chambeList.get(i).getImage()==null){
			  chambeList.get(i).setImage(new ImageIcon("index.png"));
		  }
		}
		this.rest = hotel.getRestaurant();
		
		if(hotel.getRestaurant().getImage()==null){	
			this.rest.setImage(new ImageIcon("index.png"));
		}
		
		this.bar = hotel.getBar();
		if(hotel.getBar().getImage()==null){	
			this.bar.setImage(new ImageIcon("index.png"));
		}
		
		System.out.println(bar.getHoraire());
		this.confortList = hotel.getConfort();
		initConfort();
		this.setSize(570, 400);	
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initComponent();
		this.setVisible(true);
	}

	private void initComponent() 
	{
		JTabbedPane tabbedPane = new JTabbedPane();

		//construction Image Panel
		ImageIcon icon = new ImageIcon("rooms_icon.png");
		tabbedPane.setBackground(Color.gray);
		JComponent imagePanel= ConstructionImagePanel(hotel);
		tabbedPane.addTab("Images Hotel",icon ,  imagePanel);

		JComponent descriptionPanle= ConstructionDescPanel(hotel);
		tabbedPane.addTab("Description / Infos Pratiques ",icon ,  descriptionPanle);


		JComponent chambrePanel= ConstructionChambrePanel(hotel);
		tabbedPane.addTab("Chambres",icon ,  chambrePanel);

		
		JComponent resto_BarPanle= ConstructionRestoPanel(hotel);
		tabbedPane.addTab("Resto--Bar",icon ,  resto_BarPanle);

		
		JComponent  divertPanel= ConstructionDivertPanel();
		tabbedPane.addTab("Divertisements",icon ,  divertPanel);

		JPanel controlPanel = new JPanel();
		ConstructionControlPanle(controlPanel,hotel);
        
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);


	}



	private JComponent ConstructionImagePanel(final Hotel hotel) 
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());

		final JButton modifierImage1 = new JButton("Modifier");
		final JButton modifierImage2 = new JButton("Modifier");
		final JButton modifierImage3 = new JButton("Modifier");

		final JPanel panP1 = new JPanel();
		final JPanel panP2= new JPanel();
		final JPanel panP3 = new JPanel();

		panP1.setBackground(Color.LIGHT_GRAY);
		panP2.setBackground(Color.LIGHT_GRAY);
		panP3.setBackground(Color.LIGHT_GRAY);
        
       
		if(hotel.getConfort().getList_image().size()>1&&hotel.getConfort().getList_image().get(1)!=null){

        	JLabel label = new JLabel(hotel.getConfort().getList_image().get(0));
			label.setPreferredSize(new Dimension(130, 130));
        	panP1.add(label);
		}else{
        	panP1.add(new JLabel(new ImageIcon("index.png")));
		}
        panP1.add(modifierImage1);
		if(hotel.getConfort().getList_image().size()>1&&hotel.getConfort().getList_image().get(1)!=null){
			JLabel label = new JLabel(hotel.getConfort().getList_image().get(1));
			label.setPreferredSize(new Dimension(130, 130));
			panP2.add(label);
		}else{
        	panP2.add(new JLabel(new ImageIcon("index.png")));
		}
		panP2.add(modifierImage2);
		if(hotel.getConfort().getList_image().size()>2&&hotel.getConfort().getList_image().get(2)!=null)
			{
			JLabel label = new JLabel(hotel.getConfort().getList_image().get(2));
			label.setPreferredSize(new Dimension(130, 130));
			panP3.add(label);		
			}else{
	        panP3.add(new JLabel(new ImageIcon("index.png")));
			}
		panP3.add(modifierImage3);
        
		panel.add(panP1,BorderLayout.NORTH);
		panel.add(panP2, BorderLayout.WEST);
		panel.add(panP3, BorderLayout.EAST);

		panel.setBackground(Color.LIGHT_GRAY);
		
		modifierImage1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String urlImage =recypUrlImage();
				if(confortList.getList_image().size()>0)
				{
				confortList.getList_image().remove(0);
				}
				confortList.getList_image().add(new ImageIcon(urlImage));
				panP1.removeAll();
				JLabel label = new JLabel(new ImageIcon(urlImage));
				label.setPreferredSize(new Dimension(130, 130));
				panP1.add(label);
				panP1.add(modifierImage1);
			}
		});

		modifierImage2.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent arg0) 
			{
				String urlImage =recypUrlImage();				
				if(confortList.getList_image().size()>1)
				confortList.getList_image().remove(1);
				confortList.getList_image().add(new ImageIcon(urlImage));
				panP2.removeAll();
				JLabel label = new JLabel(new ImageIcon(urlImage));
				label.setPreferredSize(new Dimension(130, 130));
				panP2.add(label);
				panP2.add(modifierImage2);
			}
		});

		modifierImage3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String urlImage =recypUrlImage();
				if(confortList.getList_image().size()>2)
				confortList.getList_image().remove(2);
				confortList.getList_image().add(new ImageIcon(urlImage));
				panP3.removeAll();
				JLabel label = new JLabel(new ImageIcon(urlImage));
				label.setPreferredSize(new Dimension(130, 130));
				panP3.add(label);
				panP3.add(modifierImage3);
			}
		});

		return panel;
	}

	private String recypUrlImage()
	{
		JFileChooser fileChooser = new JFileChooser(".");
		FileFilter filter1 = new ExtensionFileFilter("*.JPG et *.JPEG", new String[] { "jpg", "jpeg" });
		fileChooser.setFileFilter(filter1);
		int status = fileChooser.showOpenDialog(null);
		String urlFile = null;
		if (status == JFileChooser.APPROVE_OPTION) 
		{
			File selectedFile = fileChooser.getSelectedFile();
			urlFile=selectedFile.getParent()+"\\"+ selectedFile.getName();
		}else if (status == JFileChooser.CANCEL_OPTION){

		}
		return urlFile;
	}

	private void ConstructionControlPanle(JPanel controlPanel,final Hotel hotel)
	{
		controlPanel.setBackground(Color.LIGHT_GRAY);
		JButton retourBouton = new JButton("Retour");
		JButton validBouton = new JButton("Valider Changement ");
		controlPanel.add(validBouton);
		controlPanel.add(retourBouton);

		retourBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				setVisible(false);
				dispose();
			}
		});

		validBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				try {
					getDescription();
					getResto();
					getBar();
					getConfort();
					getChamber();
					
					ctrl_mod.modifier(hotel, compte);
					setVisible(false);
					dispose();
				} catch (Exception_interface e) {
					e.printStackTrace();
					
				}			
				
			}
			 
			private void getChamber(){
	        
			hotel.setListchamber(chambeList);
			}
			
			private void getConfort()
			{
				
				hotel.setConfort(confortList);
			}

			private void getBar() 
			{
				hotel.getBar().setImage(bar.getImage());
				hotel.getBar().setHoraire(horaireBarField.getText());
			}

			private void getResto() 
			{
				hotel.getRestaurant().setHoraire(horaireRestField.getText());
				hotel.getRestaurant().setTypePlat(typeField.getText());
				hotel.getRestaurant().setNbrPlaces(capaciteField.getText());
				if(rest.getImage()==null){
					System.out.println("yeah it's null my son");
				}
				
				hotel.getRestaurant().setImage(rest.getImage());
			}

			private void getDescription() throws Exception_interface
			{   try{
				hotel.setNom(nomField.getText());
				System.out.println(nomField.getText());
				if((Integer.parseInt(etoilField.getText())>0&&Integer.parseInt(etoilField.getText())<=5)||etoilField.getText().length()==0)
				{	
				hotel.setEtoile(Integer.parseInt(etoilField.getText()));	
				}else{
					throw new Exception();
				}
				}
			    catch(Exception e){
				new Exception_interface("numero entier entre 1-5 pour Etoil");
		      	}
		 		hotel.setStyle(styleField.getText());
     		 	try{
     		 	  if(Integer.valueOf(telField.getText())>100000000){		
				  hotel.setTelephone(Integer.valueOf(telField.getText()));
				}else{
				  throw new Exception_interface("votre numero de telephone et fause ");
				  }	
     		 	}
	      	    catch(Exception e){
			    new Exception_interface("numero entier SVP :) ");
	         	}
				hotel.setAdress(adressField.getText());				
				hotel.setPoint_interet(pointIntertField.getText());
				hotel.setSite_WEb(siteWebField.getText());
				try{hotel.setNbr(Integer.valueOf(nbrChambreField.getText()));
				if(hotel.getNbr()<0){
					throw new Exception();
				}
				}
			    catch(Exception e){
				new Exception_interface("numero entier > 0");
		      	}
				compte.setMot_de_passe(mot_de_pass_f.getText());
			
			}
		});

	}
	
	private JComponent ConstructionDescPanel(final Hotel hotel) 
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(9,0));

		initDescritionchamp();
		
		JLabel pointIntertLabel = new JLabel(" Point Interet Le plus Proche: "),
		siteWebLabel = new JLabel("Site Web : "),nomLabel = new JLabel(" Nom : "),etoilLabel = new JLabel(" Etoil : "),
		styleLabel = new JLabel(" Style : "),adressLabel = new JLabel("Adresse : "),
		telLabel = new JLabel(" telephone :"),nbrChambreLabel = new JLabel(" number de Chamber :  "),
		mot_de_pass = new JLabel(" mot de pass :  ");
		panel.add(nomLabel);
		panel.add(nomField);
		panel.add(etoilLabel);
		panel.add(etoilField);
		panel.add(styleLabel);
		panel.add(styleField);
		panel.add(nbrChambreLabel);
		panel.add(nbrChambreField);
		panel.add(telLabel);
		panel.add(telField);
		panel.add(adressLabel);
		panel.add(adressField);
		panel.add(pointIntertLabel);
		panel.add(pointIntertField);
		panel.add(siteWebLabel);
		panel.add(siteWebField);
		panel.add(mot_de_pass);
		panel.add(mot_de_pass_f);
		panel.setBackground(Color.LIGHT_GRAY);
		return panel;
	}

	private void initDescritionchamp() 
	{
		pointIntertField.setText(hotel.getPoint_interet());		
		siteWebField.setText(hotel.getSite_WEb());
		nomField.setText(hotel.getNom());
		etoilField.setText(String.valueOf(hotel.getEtoile()));
		styleField.setText(hotel.getStyle());
		adressField.setText(hotel.getAdress());
		telField.setText(String.valueOf(hotel.getTelephone()));
		nbrChambreField.setText(String.valueOf(hotel.getNbr()));
		mot_de_pass_f.setText(compte.getMot_de_passe());
	}

	private JComponent ConstructionChambrePanel(final Hotel hotel) 
	{
		final JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());
		final JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = new ImageIcon("rooms_icon.png");
		tabbedPane.setBackground(Color.gray);
		JComponent tempPanel;
		for (int i = 0; i < hotel.getListchamber().size(); i++)
		{
			tempPanel = ConstructionChPanel(chambeList.get(i));
			number=i;
			tabbedPane.addTab(hotel.getListchamber().get(i).getType(),icon ,  tempPanel);
		}
		panel.add(tabbedPane,BorderLayout.CENTER);	
		panel.setBackground(Color.LIGHT_GRAY);
		return panel;
	}

	protected JComponent ConstructionChPanel(final Chamber chambre) 
	{
		final JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());
		final JPanel iconPanel = new JPanel();
		iconPanel.add(new JLabel(chambre.getImage()));
        
		iconPanel.setBackground(Color.LIGHT_GRAY);

		JPanel caractPanle = new JPanel();
		caractPanle.setLayout(new BorderLayout());

		final JPanel prixPanel= new JPanel();
		prixPanel.setBackground(Color.LIGHT_GRAY);
		if( chambre.getPrix()!=null){
		prixPanel.add(new JLabel(" Prix : "+ chambre.getPrix()));
		}else{
		prixPanel.add(new JLabel(" Prix : "));
		}
		JPanel caractPanel= new JPanel();
		caractPanel.setBackground(Color.LIGHT_GRAY );
		caractPanel.setLayout(new GridLayout((chambre.getCaracteristiques().size()/2),0));
		for (String caract : chambre.getCaracteristiques()) 
		{
			caractPanel.add(new JLabel(caract));
			caractPanel.add(new JLabel("    "));
		}

		caractPanle.add(prixPanel,BorderLayout.NORTH);
		caractPanle.add(caractPanel,BorderLayout.CENTER);

		JPanel panelControl = new JPanel();
		JButton modifierChambreBouton = new JButton("Modifier");
		panelControl.add(modifierChambreBouton);
		panelControl.setBackground(Color.LIGHT_GRAY);

		modifierChambreBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Interface_Modif_Chambre interfacemodif = new Interface_Modif_Chambre(null,chambre);
				Chamber temp = interfacemodif.getChambre();	
				chambre.setType(temp.getType());
				chambre.setPrix(temp.getPrix());
				chambre.setCaracteristiques(temp.getCaracteristiques());
				chambre.setImage(temp.getImage());
				 chambeList.get(number).setImage(temp.getImage());
				iconPanel.removeAll();
				if(temp.getImage()==null){
					System.out.println("yeaaaaaaaaah nullll");
				}
				JLabel label = new JLabel(temp.getImage());
				label.setPreferredSize(new Dimension(130, 130));
				iconPanel.add(label);
			
				prixPanel.removeAll();
				prixPanel.add(new JLabel(" Prix : "+ chambre.getPrix()));
				panel.updateUI();
			}
		});

		panel.add(iconPanel, BorderLayout.CENTER);
		panel.add(caractPanle,BorderLayout.EAST);
		panel.add(panelControl,BorderLayout.SOUTH);
		return panel;
	}
	
	private JComponent ConstructionRestoPanel(Hotel hotel) 
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = new ImageIcon("image_Room.png");
		tabbedPane.setBackground(Color. gray);
		JComponent tempPanel;

		tempPanel = ConstruRestoPanel();
		tabbedPane.addTab("Restaurant ", icon,  tempPanel);

		tempPanel = ConstructionBarPanel(hotel);
		tabbedPane.addTab("Bar ", icon,  tempPanel);

		panel.add(tabbedPane);

		return panel;
	}
	
	protected JComponent ConstruRestoPanel() 
	{
		initRestoFild();
		
		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());

		// image panel
		final JPanel iconPanel = new JPanel();
		final JButton modifImageBouton = new JButton("Modifier Image");
		
		JLabel label = new JLabel(rest.getImage());
		label.setPreferredSize(new Dimension(100, 100));
		iconPanel.add(label);
		iconPanel.setBackground(Color.lightGray);
		iconPanel.add(modifImageBouton);

		//caracteristique panel
		JPanel caractFiealdPanle = new JPanel();
		caractFiealdPanle.setBackground(Color.lightGray);
		caractFiealdPanle.setLayout(new GridLayout(3,0));
		caractFiealdPanle.add(new JLabel(" Horaires D'ouverture Du Restaurant : "));
		horaireRestField.setEnabled(false);
		caractFiealdPanle.add(horaireRestField);
		caractFiealdPanle.add(new JLabel(" Capacité Du Restaurant : "));
		capaciteField.setEnabled(false);
		caractFiealdPanle.add(capaciteField);
		caractFiealdPanle.add(new JLabel(" Type de Restauration :"));
		caractFiealdPanle.add(typeField);

		modifImageBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String urlImage =recypUrlImage();
				
				rest.setImage(new ImageIcon(urlImage));
				iconPanel.removeAll();
				iconPanel.add(new JLabel(new ImageIcon(urlImage)));
				iconPanel.add(modifImageBouton);
				iconPanel.updateUI();
			}
		});

		panel.add(iconPanel, BorderLayout.CENTER);
		panel.add(caractFiealdPanle,BorderLayout.NORTH);
		return panel;
	}

	private void initRestoFild() 
	{
		horaireRestField.setText(rest.getHoraire());
	
		capaciteField.setText(rest.getNbrPlaces());
		typeField.setText(rest.getTypePlat());	
	}

	protected JComponent ConstructionBarPanel(final Hotel hotel) 
	{
		initBar();
		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());

		final JPanel iconPanel = new JPanel();
		JLabel label =new JLabel(hotel.getBar().getImage());
		label.setPreferredSize(new Dimension(130, 130));
		iconPanel.add(label);
		iconPanel.setBackground(Color.LIGHT_GRAY);
		final JButton modifImageBouton = new JButton("Modifier Image");
		iconPanel.add(modifImageBouton);

		JPanel caractPanle = new JPanel();
		caractPanle.setBackground(Color.lightGray);

		caractPanle.setLayout(new GridLayout(1,0));

		caractPanle.add(new JLabel("Horaires Bar : "));
		caractPanle.add(horaireBarField);

		modifImageBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String urlImage =recypUrlImage();
				bar.setImage(new ImageIcon(urlImage));
				iconPanel.removeAll();
				iconPanel.add(new JLabel(new ImageIcon(urlImage)));
				iconPanel.add(modifImageBouton);
				iconPanel.updateUI();
			}
		});

		panel.add(iconPanel, BorderLayout.CENTER);
		panel.add(caractPanle,BorderLayout.NORTH);
		return panel;
	}

	private void initBar()
	{   horaireBarField.setEnabled(false);
		horaireBarField.setText(bar.getHoraire());
	}
	
	private JComponent ConstructionDivertPanel() 
	{
		initConfort();
		JPanel panel = new JPanel(false);
		panel.setLayout(new BorderLayout());
		JScrollPane paneList = new JScrollPane(conf.getList());

		panel.add(paneList,BorderLayout.CENTER);

		JPanel panG = new JPanel();
		JButton addButton = new JButton("Ajouter ");
		JButton removeButton = new JButton("Supprimer");
		final JTextField ajout = new JTextField("Nouveaux...");

		panG.setLayout(new GridLayout(3,0));
		panG.add(ajout);
		panG.add(addButton);
		panG.add(removeButton);

		panel.add(panG,BorderLayout.SOUTH);

		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String s = ajout.getText();
				conf.ajout(s);
				confortList.getDivertissement().add(s);
			}
		});

		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int index=conf.getList().getSelectedIndex();
				conf.supprimer(index);
				confortList.getDivertissement().remove(index);
			}
		});

		return panel;
	}
	public Compte get_Compte(){
		return compte;
	}	
	private void initConfort() 
	{
		conf = new ListConfo(hotel.getConfort().getDivertissement());
	}

	private JTextField pointIntertField = new JTextField();
	private JTextField siteWebField = new JTextField();
	private JTextField nomField = new JTextField();
	private JTextField etoilField = new JTextField();
	private JTextField styleField = new JTextField();
	private JTextField adressField = new JTextField();
	private JTextField telField = new JTextField();
	private JTextField nbrChambreField = new JTextField();
	private JPasswordField mot_de_pass_f = new JPasswordField();
	private JTextField horaireRestField = new JTextField();
	private JTextField capaciteField = new JTextField();
	private JTextField typeField = new JTextField();
	private JTextField horaireBarField = new JTextField(hotel.getBar().getHoraire());	
}