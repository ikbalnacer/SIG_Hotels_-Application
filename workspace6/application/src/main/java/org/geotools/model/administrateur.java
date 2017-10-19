package org.geotools.model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.geotools.compte.Compte;
import org.geotools.compte.CompteDAO;
import org.geotools.compte.Hotel;
import org.geotools.compte.HotelDAO;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.interf.Exe;

import com.vividsolutions.jts.geom.Coordinate;

public class administrateur implements Administrateur_application{
	CompteDAO comptedao = new CompteDAO();
	HotelDAO hoteldao = new HotelDAO();
    Carte carte =null;

  
    public void ajouter_compte(Hotel h,Compte c)throws Exception{
    	int results =0;
    	SimpleFeatureStore store =(SimpleFeatureStore) Exe.getCarte().getPolygone_shape();
    	carte = Exe.getCarte();
    	try {
			ResultSet r= Exe.getDb().createStatement().executeQuery("select from compte where pseaudo='"+c.getPseaudo()+"'");
		 while(r.next()){
			 results++;
		 }
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	if(results==0){
    	int i = hoteldao.ajouter_hotel(h);
    	comptedao.ajouter_compte(c,i);
    	try {
			CarteDAO.ajouterfeature(store, new Coordinate(h.getLocalisation().getX(),h.getLocalisation().getY()));
		} catch (IOException e) {
			e.printStackTrace();
		}}else{
		 throw new Exception();
		}
    }
    
    public void supprimer_compte(Localisation loc){
    	HotelDAO hotelDao = new HotelDAO();
        Hotel h =hotelDao.recuperer_hotel(loc);
        comptedao = new CompteDAO();
		comptedao.supprimer_compte(h.getHotel_ID());	
    	hoteldao.supprimer(loc); 	
    	try {
    		String str = "Point("+loc.getX()+" "+loc.getY()+")";
        	SimpleFeatureStore store =(SimpleFeatureStore)  Exe.getCarte().getPolygone_shape();
			CarteDAO.supprimerfeature(store, str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

	public void Deconnecter() {
		// TODO Auto-generated method stub
		
	}
 


	

}
