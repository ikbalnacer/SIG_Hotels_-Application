package org.geotools.compte;

import org.geotools.model.Localisation;

public class Objet_G {
protected String nom ;
protected int Etoile;
protected double note;
protected Confort confort = new Confort();

protected String adress;
protected int telephone;
protected Localisation localisation;
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public Localisation getLocalisation() {
	return localisation;
}
public void setLocalisation(Localisation localisation) {
	this.localisation = localisation;
}

public Confort getConfort() {
	return confort;
}
public void setConfort(Confort confort) {
	this.confort = confort;
}
public int getEtoile() {
	return Etoile;
}
public void setEtoile(int etoile) {
	Etoile = etoile;
}
public double getNote() {
	return note;
}
public void setNote(double note) {
	this.note = note;
}
public String getAdress() {
	return adress;
}
public void setAdress(String adress) {
	this.adress = adress;
}
public int getTelephone() {
	return telephone;
}
public void setTelephone(int telephone) {
	this.telephone = telephone;
}
public Objet_G recherecher_par_localisation(Localisation coor){
	
	HotelDAO hotelDao = new HotelDAO();
    hotelDao.recuperer_hotel(coor);
    return (Objet_G)hotelDao.getHotel();
}
public void Noter(Objet_G obj){
	
}
public void Consulter(Objet_G obj){
	
}

}
