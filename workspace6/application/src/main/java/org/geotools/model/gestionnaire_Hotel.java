package org.geotools.model;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;

public interface gestionnaire_Hotel {
	String mettre_a_jour_un_compte(Compte compte,Hotel hotel) throws Exception;
	 Hotel consulter(int i);

}
