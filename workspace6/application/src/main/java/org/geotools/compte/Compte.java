package org.geotools.compte;

public class Compte {
    CompteDAO compteDao = new CompteDAO();
	private int hotel_id;
	private String mot_de_passe;
	private String pseaudo;
    private String type;
    public Compte(String mot_de_pass,String pseaudo){
    	this.mot_de_passe=mot_de_pass;
    	this.pseaudo=pseaudo;
    }
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	public String getPseaudo() {
		return pseaudo;
	}

	public void setPseaudo(String pseaudo) {
		this.pseaudo = pseaudo;
	}

	public  void  Consulter() {
		
     compteDao.consulter(pseaudo,mot_de_passe,this);
     
	}


	public void supprimer_Compte() {

	}

	
}
