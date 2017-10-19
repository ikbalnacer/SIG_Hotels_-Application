package org.geotools.controlers;

import org.geotools.model.Localisation;
import org.geotools.model.Manupulateur;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Coordinate;

public class Ctrl_Selectionner extends abstract_Controle{
    
	public Ctrl_Selectionner(){
		manupulateur = new Manupulateur();
	}
	
	public void Demander_itinéraire(Coordinate loc,CoordinateReferenceSystem crs ){
		manupulateur.Demander_itinéraire(loc,crs);
		}
	
	public void noter_hotel(){
	}
	
	public void supprimer(Localisation loc){
		manupulateur.supprimer_compte(loc);
	}
	
	public void consulter(Localisation loca){
		manupulateur.consulter(loca);
	}
	
}
