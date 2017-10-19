package org.geotools.controlers;

import java.awt.geom.AffineTransform;

import org.geotools.model.Manupulateur;
import org.geotools.swing.event.MapMouseEvent;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Coordinate;

public class Ctrl_Carte extends abstract_Controle {

public Ctrl_Carte(Manupulateur manupulateur){
this.manupulateur=manupulateur;;	
}

@SuppressWarnings("deprecation")
public void localiser(MapMouseEvent ev){
    manupulateur.localiser(new Coordinate(ev.getMapPosition().getX(),ev.getMapPosition().getY()));
}


public void selectionner(CoordinateReferenceSystem crs,java.awt.Point ev,AffineTransform aff){
	  manupulateur.selectionner_hotel(crs,ev,aff);
}

public void consulter(int id){
	manupulateur.consulter(id);
}

}
