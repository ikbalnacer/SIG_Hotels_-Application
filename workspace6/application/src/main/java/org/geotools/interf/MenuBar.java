package org.geotools.interf;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuBar;

import org.geotools.compte.Objet_G;
import org.geotools.controlers.Ctrl_Carte;
import org.geotools.controlers.Ctrl_deconnecter;
import org.geotools.model.CarteDAO;
import org.geotools.model.Manupulateur;
import org.geotools.model.observer;
import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;

public class MenuBar extends JMenuBar implements observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Menu file = new Menu("File",'F');
	private MenuItem exit = new MenuItem("Exit",true,'Q');
	private MenuItem connect  = new MenuItem("Connecte",true,'C');
	private MenuItem deconnect  = new MenuItem("Se Déconnecter",false,'D');
	private Menu edit = new Menu("Edit",'e');
	private MenuItem cherche  = new MenuItem("Chercher Hotel",true,'R');
	private MenuItem manuel  = new MenuItem("se localiser ",true,'R');
	private Menu aide = new Menu("Aide",'A');
	private MenuItem aPropos  = new MenuItem("à Propos",true,'I');
	private Ctrl_deconnecter ctrl_deconnecter = new Ctrl_deconnecter();
	private  static Manupulateur manupulateur = new Manupulateur();
	private static Ctrl_Carte ctrl_carte = new Ctrl_Carte(manupulateur);
	static  Objet_G objet;
	Interface_Selection intr = new Interface_Selection();
	
	public MenuBar(final OutilBar outil,JMapPane pan)//,JMapPane paneauMapPane) 
	{   manupulateur.addObservateur(this);
		//this.paneauMapPane = paneauMapPane;
		this.add(file);	
		file.add(connect);
		file.add(deconnect);
		file.add(exit);
       
		this.add(edit);
		edit.add(cherche);// lui ajouter un action listener 
        edit.add(manuel);		

		this.add(aide);
		aide.add(aPropos);
		ajouterAction(outil,pan);
	}

	private void ajouterAction(final OutilBar outil,final JMapPane pan)
	{
		exit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if( CarteDAO.dataStore != null ){
					CarteDAO.dataStore.dispose();
					CarteDAO.dataStore = null;
					}
				System.exit(0);
			}
		});

	connect.addActionListener(new ActionListener() //a continuer la methode de la base de donnee
		{
			public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
			 new Interface_Identification(null, "Connexion ", true);
				
			}}
		);

		deconnect.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e) 
			{
			ctrl_deconnecter.deconnecter();	
			}
		});

	
		
		
	
		
		aPropos.addActionListener(new ActionListener()//a continuer la methode 
		{	
			
			public void actionPerformed(ActionEvent arg0) {
			new APropos(null, "A propos", true);
			}
		});
		
		cherche.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			ArrayList<String> divertissement = new ArrayList<String>();
			new Interface_rechercher(null, divertissement);
			}
		});

		manuel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				pan.setCursorTool(new CursorTool() {
                    public void onMouseClicked(MapMouseEvent ev) {
					 mappan.localiser=true;
                     ctrl_carte.localiser(ev);
					}
				});
			}
		});
	
	}
	
	public void userm_menu()
	{
		connect.setEnabled(true);
		deconnect.setEnabled(false);

	}

	public void admin_menu()
	{
		connect.setEnabled(false);
		deconnect.setEnabled(true);

	}

	
	public void update() {
	     Frame.refrech(0.000000000000000000000000000000001d);
	     System.out.println("refreche");
	}

	
	public void update(Objet_G obje) {
		objet=obje;
	}
    public static void consulter(int id_h){
    	ctrl_carte.consulter(id_h);
    }
}
