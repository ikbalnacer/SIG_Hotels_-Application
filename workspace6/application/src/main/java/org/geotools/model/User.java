package org.geotools.model;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;
import org.geotools.compte.HotelDAO;
import org.geotools.compte.Objet_G;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.feature.SchemaException;
import org.geotools.interf.Exe;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.FeatureId;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;

public class User implements Utilisateur{

	private static Localisation loc ;
	HotelDAO hoteld = new HotelDAO();
	Carte carte = new Carte();
	public ArrayList<observer> listObserver = new
			ArrayList<observer>();
   // private Compte compte=new Compte();
	public static Localisation getLoc() {
		return loc;
	}

	public static void setLoc(Localisation loc) {
		User.loc = loc;
	}

	public ArrayList<Localisation> chercher_un_hotel(Hotel hotel) {
		try {
			return hoteld.chercher_un_hotel(hotel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public Objet_G  selectionner_hotel(CoordinateReferenceSystem crs,java.awt.Point ev,AffineTransform aff) {
		  return carte.Selctionner(aff, crs, ev);
	     
	}

	

	public Compte s_authenrifier(Compte compte) {
		
	    compte.Consulter();
	    if(compte.getType()!=null)
	    if(compte.getType().contains("admin")){
	    Administrateur_application admin = new administrateur();
		Manupulateur.setAdmin(admin);}else {
			if(compte.getType().contains("gestion")){
			gestionnaire_Hotel gestion = new gestionnaire();
			Manupulateur.setGest_hotel(gestion);
			}
		}
	    System.out.println(compte.getType());
		return compte;
		
	}

	

    public void Demander_itinéraire(Localisation loca ,Localisation loc,CoordinateReferenceSystem crs){		
    	
    	try {
    	
			carte.Demander_itinéraire(loca,loc, crs);
		
		} catch (NoSuchAuthorityCodeException e) {
		} catch (IOException e) {
						e.printStackTrace();
		} catch (TransformException e) {
						e.printStackTrace();
		} catch (SchemaException e) {
						e.printStackTrace();
		} catch (FactoryException e) {
						e.printStackTrace();
		}   
    }
   
	public void localiser(Coordinate c) {
              SimpleFeatureStore store = (SimpleFeatureStore) Exe.getCarte().getPoint_shape();
		try{		
	    	  FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2( GeoTools.getDefaultHints() ); 
		   	  Set<FeatureId> fids = new HashSet<FeatureId>();
		   	  fids.add( ff.featureId("des2.3") );
		   	  Filter filter = ff.id( fids );  
			  CarteDAO.supprimerfeature(store, filter);
			  CarteDAO.ajouterfeature(store, c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Hotel consulter(Localisation loc) {
		return hoteld.consulter(loc);
		
	}

	public void noter(int i,Localisation loc) {
		hoteld.Noter(i,loc);
	}


}
