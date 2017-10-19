package org.geotools.compte;

import javax.swing.ImageIcon;

public class Restaurant  {
	public Restaurant() 
	{
	}

	public Restaurant(String typePlat, String nbrPlaces, String horaire,
			String nomService, ImageIcon image) {
		super();
		this.typePlat = typePlat;
		this.nbrPlaces = nbrPlaces;
		Horaire = horaire;
		this.nomService = nomService;
		this.image = image;
	}

	public String getHoraire() 
	{
		return Horaire;
	}

	public String getNomService() 
	{
		return nomService;
	}

	public ImageIcon getImage() 
	{
		return image;
	}

	public String getTypePlat() 
	{
		return typePlat;
	}
	
	public String getNbrPlaces() 
	{
		return nbrPlaces;
	}
	
	public void setTypePlat(String typePlat) 
	{
		this.typePlat = typePlat;
	}

	public void setNbrPlaces(String nbrPlaces) 
	{
		this.nbrPlaces = nbrPlaces;
	}

	public void setHoraire(String horaire) 
	{
		Horaire = horaire;
	}

	public void setNomService(String nomService) 
	{
		this.nomService = nomService;
	}

	public void setImage(ImageIcon image) 
	{
		this.image = image;
	}

	@Override
	public String toString() {
		return "Resto [typePlat=" + typePlat + ", nbrPlaces=" + nbrPlaces
				+ ", Horaire=" + Horaire + ", nomService=" + nomService
				+ ", image=" + image + "]";
	}



	private String typePlat,nbrPlaces,Horaire, nomService;
	private ImageIcon image;

}
