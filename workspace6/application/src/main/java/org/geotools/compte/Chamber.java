package org.geotools.compte;

import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Chamber {
	public Chamber() 
	{
	}
	
	public Chamber(String type, String prix,
			ArrayList<String> caracteristiques, ImageIcon image) 
	{
		this.type = type;
		this.prix = prix;
		this.caracteristiques = caracteristiques;
		this.image = image;
	}
	
	public Chamber(Chamber chambre) 
	{
		this.type = chambre.getType();
		this.prix = chambre.getPrix();
		this.caracteristiques = chambre.getCaracteristiques();
		this.image = chambre.getImage();
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}

	public void setPrix(String prix) 
	{
		this.prix = prix;
	}

	public void setCaracteristiques(String caracteristiques) 
	{
		this.caracteristiques.add(caracteristiques);
	}
	
	public void setCaracteristiques(ArrayList<String> caracteristiques) 
	{
		this.caracteristiques = caracteristiques;
	}

	public void setImage(ImageIcon image) 
	{
		this.image = image;
	}

	public String getPrix() 
	{
		return prix;
	}
	
	public ArrayList<String> getCaracteristiques() 
	{
		return caracteristiques;
	}
	
	public ImageIcon getImage() 
	{
		return image;
	}

	public void supprimeConfort(int i) 
	{
		this.caracteristiques.remove(i) ;
	}

	

	@Override
	public String toString() {
		return "Chambre [type=" + type + ", prix=" + prix
				+ ", image=" + image
				+ "]";
	}



	private String type, prix;
	private ArrayList<String> caracteristiques= new ArrayList<String>();
	private ImageIcon image ;
}
