package org.geotools.controlers;

import org.geotools.compte.Compte;
import org.geotools.compte.Hotel;

public class Ctrl_Ajouter_un_Compte extends abstract_Controle{
    public void ajouter_un_compte(Hotel h,Compte c){
    	manupulateur.ajouter_compte(h,c);
    }
}
