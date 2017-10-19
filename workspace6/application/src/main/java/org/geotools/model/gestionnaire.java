package org.geotools.model;

import org.geotools.compte.Compte;
import org.geotools.compte.CompteDAO;
import org.geotools.compte.Hotel;
import org.geotools.compte.HotelDAO;

public class gestionnaire implements gestionnaire_Hotel {
    HotelDAO hoteld = new HotelDAO();
    CompteDAO comptedao = new CompteDAO();
	public String mettre_a_jour_un_compte(Compte compte, Hotel hotel) throws Exception {
		String str =hoteld.modifier(hotel);
		comptedao.modifier(compte);
    	return str;	
	}
	
	public Hotel consulter(int i) {
		Localisation loc =hoteld.consulter(i);
		return hoteld.consulter(loc);
	}

}