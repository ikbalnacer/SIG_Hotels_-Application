package org.geotools.model;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;

public interface Administrateur_application  {
     void supprimer_compte(Localisation loc);
     void ajouter_compte(Hotel h ,Compte c)throws Exception;
	
}
