package org.geotools.controlers;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;

public class Ctrl_Modification extends abstract_Controle{

	
	public void modifier(Hotel hotel,Compte compte){
		manupulateur.mettre_a_jour_un_compte(compte, hotel);
	}
}
