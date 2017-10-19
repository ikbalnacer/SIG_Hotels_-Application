package org.geotools.compte;

import javax.swing.ImageIcon;

public class Bar  {



	public String getHoraire() {
		return Horaire;
	}
	public void setHoraire(String horaire) {
		Horaire = horaire;
	}
	public String getNomService() {
		return nomService;
	}
	public void setNomService(String nomService) {
		this.nomService = nomService;
	}
	public ImageIcon getImage() {
		return image;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	private String Horaire, nomService;
	private ImageIcon image;


}
