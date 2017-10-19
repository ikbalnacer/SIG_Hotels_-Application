package org.geotools.interf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;
import org.geotools.map.FeatureLayer;
import org.geotools.swing.JMapPane;
import org.opengis.geometry.Envelope;


public class Frame 
{  protected static JMapPane paneauMapPane;
   private static JFrame frame = new JFrame();
   private static JPanel pan = new JPanel();
   private static OutilBar outil ;
   private static MenuItem ajoutCompt ;
   private static MenuItem modiferHotel  ;
   private  static MenuBar menub ;
   public static int id_h=0;
   public static boolean admin = false;
   public static  Interface_Mettre_ajour_Hotel inter_mettre_ajour;
   public static Interface_Selection selection;
   private static Compte compte ;
	public Frame(org.geotools.interf.Interface_Carte map) 
	{	paneauMapPane =   map.getMapPane(); 	
 		init();		
		frame.setSize(800, 650);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);		
	}
	
	public static  void refrech(double a){
		Envelope env = paneauMapPane.getDisplayArea();
	    double delta = a;
	    ((com.vividsolutions.jts.geom.Envelope) env).expandBy(delta);
	    paneauMapPane.setDisplayArea(env);    
	    paneauMapPane.repaint();
	    
	    }
	
	public static void refraiche_hotel_map(String str){
	 if(str.length()>3){
		    JOptionPane.showMessageDialog(null, str, "erreur",
					JOptionPane.ERROR_MESSAGE);
	 }
		refrech(0);
	 
	}
	
	public static  void refrech(double a,String str){
		if(!str.contains("null")){
		Envelope env = paneauMapPane.getDisplayArea();
	    double delta = a;
	    ((com.vividsolutions.jts.geom.Envelope) env).expandBy(delta);
	    paneauMapPane.setDisplayArea(env);    
	    paneauMapPane.repaint();
	    JOptionPane.showMessageDialog(null, "La distance entre votre localisation et l'hotel est : "+str, "LA distance",
				JOptionPane.INFORMATION_MESSAGE);
	    }
	    }
	 public static void refraichir(){
		 pan.removeAll();
		 do_the_rest();
	 }
	public static void ajoute_s(Interface_Selection s){
		  pan.removeAll();
          do_the_rest();
          selection=s;
          selection.setPreferredSize(new Dimension(200,400));
		  pan.add(selection,BorderLayout.WEST);
		  frame.revalidate();
		  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  if(Exe.recherche){
			  Exe.first_Style();
		  }
	  }
	  
	  public static void First_Look(){
		  pan.removeAll();   
		  do_the_rest();   		
          frame.revalidate();		 
	  }
	  
	  public static void first_menu_look(){
	  pan.removeAll();   
	  init();
	  ajoutCompt  = null;
	 
	  modiferHotel=null;
      frame.revalidate();

	  }
	  
      public static void init(){
    	  System.out.println("yeah");
    		outil = new OutilBar(paneauMapPane);
    		  System.out.println("yeah");
     		menub =new MenuBar(outil,paneauMapPane);
     		do_the_rest();
      }
      
      public static void do_the_rest(){
    	pan.setLayout(new BorderLayout());
    	pan.add(menub, BorderLayout.NORTH);
   		paneauMapPane.setBackground(Color.LIGHT_GRAY);
   		pan.add(paneauMapPane,BorderLayout.CENTER);
   		pan.add(outil, BorderLayout.EAST);
   	 
   		frame.add(pan);
   		frame.revalidate();
      }
      
      public static void look_after_deconnection(){
		  InfoToolAction.bool_intr=false;
		  menub.userm_menu();
		  first_menu_look();
		  admin=false;
		  compte=null;
	  }
	
  	  public static void refraicher_linterface(Compte t_c ){
  		  if(t_c.getType()==null){
  			 JOptionPane.showMessageDialog(null, "verifier votre mot de passe ou pseaudo ", "Erreur",
						JOptionPane.ERROR_MESSAGE);
  		  }else{
  		  if(t_c.getType().contains("admin")){
  			ajoutCompt  = new MenuItem("Ajouter Compte",true,'A');
  			InfoToolAction.bool_intr=true;
  			ajouter__gestion_app_a_menu();
  		  }else{
  			  if(t_c.getType().contains("gestion")){
  				modiferHotel  = new MenuItem("Modifier Information Hôtel",true,'M');
  				ajouter_gestion_hotel_a_menu();
  	  		    compte =t_c;
  	  			id_h = t_c.getHotel_id();
  			  }
  		  }
  		    pan.removeAll();
			do_the_rest();

  		  }
      }
  	private static void ajouter__gestion_app_a_menu(){
	    menub.admin_menu();
  		menub.getMenu(1).setEnabled(true);
  		menub.getMenu(1).add(ajoutCompt);
  		ajoutCompt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ajoutCompte();
			}
		});}
    
  	private static void ajouter_gestion_hotel_a_menu(){  	
		menub.admin_menu();
    	menub.getMenu(1).setEnabled(true);
    	menub.getMenu(1).add(modiferHotel);
    	modiferHotel.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent arg0) {
			  MenuBar.consulter(id_h);
			}});
  	}
   
    public static void refraicher_hotel_map(String str){
    	if(str!=null){
    		JOptionPane.showMessageDialog(null, str, "Erreur",
					JOptionPane.ERROR_MESSAGE);
    	}else{
    		refrech(0.0000000000000000000000000001d);
    	}
    }
    
    public static void ajoutCompte(){
    	Interface_ajouter_Compte i_a_c = new Interface_ajouter_Compte(null);
    	i_a_c.getUser();
    }
    
     public static void afficher_consultatin(Hotel hotel){
    	 new Interface_consultation(hotel);
     }
     
     public static void afficher_consultation(Hotel h){
    	 {
    		new Interface_Mettre_ajour_Hotel(h,compte);
    	 }
     }
     public static void message(String str){
    	 if(str.length()>3)
 	 JOptionPane.showMessageDialog(null, str, "Vous aves pas remplir les champs ",
 				JOptionPane.INFORMATION_MESSAGE);
    	 
    	 pan.removeAll();
		 do_the_rest();
     }
     public static void message(Exception e){
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur",
					JOptionPane.ERROR_MESSAGE);
     }
}
