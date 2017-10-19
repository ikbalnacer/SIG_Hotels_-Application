package org.geotools.compte;

import java.awt.Image;
import java.util.ArrayList;

public class Hotel extends Objet_G {
	private int Hotel_ID;
	
	private Image hotel_photo;
    private String point_interet;
	private String Site_WEb;
    private String style;
    private String NomService;
    private Budget  budget ;
    private String type_chamber;
    private ArrayList<Chamber> listchamber = new ArrayList<Chamber>();
    private Bar  bar = new Bar();
    private Restaurant restaurant = new Restaurant();
    private int nbr ;
  

	public int getNbr() {
		return nbr;
	}

	public void setNbr(int nbr) {
		this.nbr = nbr;
	}

	public Bar getBar() {
		return bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public ArrayList<Chamber> getListchamber() {
		return listchamber;
	}

	public void setListchamber(ArrayList<Chamber> listchamber) {
		this.listchamber = listchamber;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}


	public String getType_chamber() {
		return type_chamber;
	}

	public void setType_chamber(String type_chamber) {
		this.type_chamber = type_chamber;
	}

	public Image getHotel_photo() {
		return hotel_photo;
	}

	public void setHotel_photo(Image hotel_photo) {
		this.hotel_photo = hotel_photo;
	}

	public String getPoint_interet() {
		return point_interet;
	}

	public void setPoint_interet(String point_interet) {
		this.point_interet = point_interet;
	}

	public String getSite_WEb() {
		return Site_WEb;
	}

	public void setSite_WEb(String site_WEb) {
		Site_WEb = site_WEb;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getNomService() {
		return NomService;
	}

	public void setNomService(String nomService) {
		NomService = nomService;
	}

	public int getHotel_ID() {
		return Hotel_ID;
	}

	public void setHotel_ID(int hotel_ID) {
		Hotel_ID = hotel_ID;
	}




	public void modifier(Hotel hotel) {

	}
    
	

}
