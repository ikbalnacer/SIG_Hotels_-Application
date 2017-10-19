package org.geotools.model;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;
import org.geotools.compte.Objet_G;
import org.geotools.interf.Exe;
import org.geotools.interf.Frame;
import org.geotools.interf.Interface_Selection;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Coordinate;

public  class Manupulateur implements Observable{
	private static Localisation loc ;
	Compte compte;
	Objet_G obj ;
	private Utilisateur user ;
	private static gestionnaire_Hotel gest_hotel;
	private static Administrateur_application admin;
	public static CoordinateReferenceSystem crs =null;
	public Manupulateur(){
		user = new User();
	}
	

	public static void setGest_hotel(gestionnaire_Hotel gest) {
		gest_hotel = gest;
	}

	public static void setAdmin(Administrateur_application admine) {
		admin = admine;
	}

	private ArrayList<observer> listObserver = new
			ArrayList<observer>();
	public static Localisation getLoc() {
		return loc;
	}

	public static void setLoc(Localisation loc) {
		Manupulateur.loc = loc;
	}

	public ArrayList<Localisation> rechercher(Hotel hotel) {
        
         Exe.Create_Style( user.chercher_un_hotel(hotel));
		return null;
	}

	public void  selectionner_hotel(CoordinateReferenceSystem crs,java.awt.Point coor,AffineTransform aff) {
		  obj=user.selectionner_hotel(crs, coor, aff);
		  
	      updateObservateur();
	}

    public void noter(int i,Localisation loc){
    	user.noter(i,loc);
    	Interface_Selection.update_note(i);
    }
    
	public void s_authenrifier(Compte c) {
		compte =user.s_authenrifier(c);
		
		Frame.refraicher_linterface(compte);
	}


	public void Deconnecter() {
		admin=null;
		gest_hotel=null;
		Frame.look_after_deconnection();
	}



	public void mettre_a_jour_un_compte(Compte compte, Hotel hotel) {
		try{
			Frame.message(gest_hotel.mettre_a_jour_un_compte(compte, hotel));
	
		}catch(Exception e){
			Frame.message(e);
		}
	}
  
    public  void consulter(Localisation loc){
    	
    	 Frame.afficher_consultatin( user.consulter(loc));
	}
    public  void consulter(int i){
   	 Frame.afficher_consultation( gest_hotel.consulter(i));
	}
    public void Demander_itinéraire(Coordinate loc,CoordinateReferenceSystem crs){
		Localisation loca = new Localisation(loc);
		if(getLoc()==null){
			Interface_Selection.afficher_erreur("localisez vous svp !");
		}else{
    	user.Demander_itinéraire(getLoc(), loca, crs);
    	}
        Frame.refrech(0,String.valueOf(Routers.distance));
    }
 
    public void ajouter_compte(Hotel h,Compte c){
    	try{
    	admin.ajouter_compte(h,c);   
        Frame.refraiche_hotel_map("");
    	}catch(Exception e){
    	  	
            Frame.refraiche_hotel_map("pseaudo exist deja !");
    	}
  
    }
    
    public void supprimer_compte(Localisation loc){
    	admin.supprimer_compte(loc);
    	Frame.refraichir();
    }
  

	public void localiser(Coordinate c) {
		   user.localiser(c);
		   setLoc(new Localisation(c));
	    	Frame.refrech(0.000000000000000000000001);
	}


	public void addObservateur(observer obs) {
		this.listObserver.add(obs);
	}

	public void updateObservateur() {
		for(observer obs : listObserver)
		obs.update(obj);
	}

	public void delObservateur() {
	
	}


	public void updateObservateur1() {
		for(observer obs : listObserver)
		{	obs.update();
		}
	}

	

}
