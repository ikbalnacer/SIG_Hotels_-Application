package org.geotools.model;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;
import org.geotools.compte.Objet_G;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Coordinate;

public interface Utilisateur {
  ArrayList<Localisation>  chercher_un_hotel(Hotel hotel);
  Objet_G selectionner_hotel(CoordinateReferenceSystem crs,java.awt.Point coor,AffineTransform aff);
  void noter(int i,Localisation loc);
  void localiser(Coordinate c );
  Compte s_authenrifier(Compte c);
  void Demander_itinéraire(Localisation loca ,Localisation loc,CoordinateReferenceSystem crs);
  Hotel consulter(Localisation loc);
}
