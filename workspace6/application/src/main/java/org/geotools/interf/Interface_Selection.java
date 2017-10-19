package org.geotools.interf;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.geotools.compte.Objet_G;
import org.geotools.controlers.Ctrl_Selectionner;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.model.Localisation;

import com.vividsolutions.jts.geom.Coordinate;


public class Interface_Selection extends JPanel
{   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Ctrl_Selectionner ctrl_s = new Ctrl_Selectionner();
	private double x ;
	private double y ;
	
	private JPanel hotelPanel= new JPanel(),
			imagePanel=new JPanel(), 
			InfoPanel=new JPanel(),
			boutonPanel=new JPanel();
    private static JPanel Etoile = new JPanel();
    private static JPanel Note = new JPanel();

	private JLabel  nomLabel	= new JLabel("  Nom Hotel : "), 
			addLabel	= new JLabel("  Adresse Hotel :"),
			tellLabel 	= new JLabel("  telephone :"),
			noteLabel	= new JLabel("  Note :"),
			etoilelable	= new JLabel("  etoile :"),
			nomHotelLabel, 
			adressLabel,
			telLabel;

	private JButton consultBouton= new JButton("Consulter"),
			itinerBouton = new JButton("  Itinéraires  "),
			notBouton = new JButton("  Notter  "), 
			sortirBouton = new JButton(" BACK ");
    public static Interface_Noter noter_intr;
	private static ImageIcon imageAvant= new ImageIcon("etoil_avant_Select.png"),imageApres= new ImageIcon("etoil_apres_Select.jpg");
	
    static Objet_G obj=null;
	public Interface_Selection(){}
	public Interface_Selection(Objet_G hotel,Boolean bool) 
	{
		this.setLayout(new BorderLayout());
		this.setSize(450, 230);
		
		this.setVisible(true);
		
		this.x=hotel.getLocalisation().getX();
		this.y=hotel.getLocalisation().getY();
		System.out.println(x+"        "+y);
	    obj=hotel;
		nomHotelLabel = new JLabel(hotel.getNom());
		adressLabel= new JLabel(hotel.getAdress());
		telLabel= new JLabel(String.valueOf(hotel.getTelephone()));
		Etoile = new JPanel();
		Etoile=getEtoil(hotel.getEtoile(),Etoile);
		Note = new JPanel();
		Note=getEtoil(hotel.getNote(), Note);
		InfoPanel.setLayout(new GridLayout(5, 0));

		InfoPanel.setBackground(Color.white);
		InfoPanel.add(nomLabel);
		InfoPanel.add(nomHotelLabel);
		InfoPanel.add(addLabel);
		InfoPanel.add(adressLabel);
		InfoPanel.add(tellLabel);
		InfoPanel.add(telLabel);
		InfoPanel.add(etoilelable);
		InfoPanel.add(Etoile);
		InfoPanel.add(noteLabel);
		InfoPanel.add(Note);
        
		try{
		int i =hotel.getConfort().getList_image().size();
		
		if(i>0)
		imagePanel.add(new JLabel(hotel.getConfort().getList_image().get(0)));
		if(i>1)
		imagePanel.add(new JLabel(hotel.getConfort().getList_image().get(1)));
		if(i>2)
		imagePanel.add(new JLabel(hotel.getConfort().getList_image().get(2)));
		for (int j= i; j <3; j++) {
			imagePanel.add(new JLabel(new ImageIcon("index.png")));

		}
		}catch(Exception e){
			
			
		}
		hotelPanel.setLayout(new BorderLayout());
		hotelPanel.add(InfoPanel, BorderLayout.NORTH);
		imagePanel.setBackground(Color.BLACK);
		hotelPanel.add(imagePanel, BorderLayout.CENTER);

		// boutons consulter itine,notter, sortir
	
		boutonPanel.add(consultBouton);
		consultBouton.setPreferredSize(new Dimension(80,20));
		boutonPanel.add(notBouton);
		notBouton.setPreferredSize(new Dimension(80,20));
		boutonPanel.add(itinerBouton);
		itinerBouton.setPreferredSize(new Dimension(95,20));
		boutonPanel.add(sortirBouton);
		sortirBouton.setPreferredSize(new Dimension(75,20));
		JButton supprimer = new  JButton("supprimer ");
		boutonPanel.setPreferredSize(new Dimension(190,80));
		if(bool){
			boutonPanel.setPreferredSize(new Dimension(215,80));
		boutonPanel.add(supprimer);
		supprimer.setPreferredSize(new Dimension(95,20));
		supprimer.addActionListener(new supprimer_action());
		}
	
		this.add(hotelPanel,BorderLayout.CENTER);
		this.add(boutonPanel,BorderLayout.SOUTH);
		
		consultBouton.addActionListener(new consultBouton_action());
		
		itinerBouton.addActionListener(new itinerBouton_action());
		
		notBouton.addActionListener(new notbouton_action());
		
		sortirBouton.addActionListener(new sortirbouton_action());
		
	}
	

			
			public static void afficher_erreur(String str){
				JOptionPane.showMessageDialog(null,str, "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
			
			 class notbouton_action implements ActionListener{

					public void actionPerformed(ActionEvent arg0) {
						noter_intr = new Interface_Noter(null,obj);
					}
					
				}
			class sortirbouton_action implements ActionListener{

				public void actionPerformed(ActionEvent arg0) {
					 InfoToolAction.destroy_selection();
				     Frame.First_Look();
				}
				}
			class itinerBouton_action implements ActionListener{
					
				public void actionPerformed(ActionEvent arg0) 
						{			            
							ctrl_s.Demander_itinéraire(new Coordinate(x,y),Exe.map.getCoordinateReferenceSystem());
							
				}
						
			}	
			class consultBouton_action implements ActionListener{

				public void actionPerformed(ActionEvent arg0) {
					ctrl_s.consulter(new Localisation(new Coordinate(x,y)));
				}
				
			}
			class supprimer_action implements ActionListener{
				public void actionPerformed(ActionEvent arg0) {
					int option = JOptionPane.showConfirmDialog(null,
					"Voulez-vous Supprimer cette Hôtel ?",
					"Suppression",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
					if(option == JOptionPane.OK_OPTION){
						Localisation loc = new Localisation(new Coordinate(x, y));
						System.out.println(x+"   "+ y);
						ctrl_s.supprimer(loc);
						SimpleFeatureStore store = (SimpleFeatureStore) Exe.getCarte().getClosest_Line();
						Exe.init_Shape_File("clo2.1", store);
				        store = (SimpleFeatureStore) Exe.getCarte().getPoint_shape();
				        Exe.init_Shape_File("des2.3", store);
						}
					            
				}
			}
			private static JPanel getEtoil(double note,JPanel notEtoile)
			{
				notEtoile.setBackground(Color.white);
			
				for (int i = 0; i < 5; i++)
				{
					if (note>i)
					{
						notEtoile.add(new JLabel(imageApres));
					}else 
					{
						notEtoile.add(new JLabel(imageAvant));
					}
				}	
				return notEtoile;
			}
			
			public static void distroy_interface_noter(){
				noter_intr=null;
			}
			
			public static void update_note(int i){
			  obj.setNote(i);
			  Note.removeAll();
			  getEtoil(i, Note);
			  Note.revalidate();
			}
			
			
}

