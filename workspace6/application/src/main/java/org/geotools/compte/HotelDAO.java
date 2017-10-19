package org.geotools.compte;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.geotools.geometry.jts.JTS;
import org.geotools.interf.Exe;
import org.geotools.model.Localisation;
import org.geotools.model.Manupulateur;
import org.opengis.referencing.operation.TransformException;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import com.vividsolutions.jts.geom.Coordinate;

public class HotelDAO {
	
	private Hotel hotel = new Hotel();
	private PreparedStatement prep ;
	private PreparedStatement prep3 ;
	private PreparedStatement prep4 ;
	private PreparedStatement prep5 ;
	private PreparedStatement prep6 ;
	private PreparedStatement prep7 ;
	private PreparedStatement prep8 ;
	private PreparedStatement prep10 ;
	private PreparedStatement prep11 ;
	private PreparedStatement prep12 ;
	private PreparedStatement prep13 ;

	Connection con ;
	ArrayList<Localisation> list = new ArrayList<Localisation>();
    public HotelDAO(){
    	try {
    	    con = Exe.getDb();
			prep=con.prepareStatement("select *from hotel where x=? and y=?");
	    	prep3=con.prepareStatement("select *from bar");
	    	prep4=con.prepareStatement("select (image)from images where id_h=?");
	    	prep5=con.prepareStatement("select *from restaurant");  
	    	prep6=con.prepareStatement("select *from chambers");  
	    	prep7=con.prepareStatement("select (photo_bar)from bar_contient where  id_h=? ");  
	    	prep8=con.prepareStatement("select *from restaurant_contient where  id_h=? ");  
	    	prep10=con.prepareStatement("select *from chamber_contient where id_h=? "); 
	    	prep11=con.prepareStatement("select (id_h)from hotel where x=? and y=?");
	    	prep12=con.prepareStatement("select *from divertissement where id_h=?");  
	    	prep13=con.prepareStatement("select  x , y from hotel where id_h=? ");  
	     
    	} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
    public ResultSet getresultSet(Localisation coor) throws SQLException{
    	prep.setDouble(1, coor.getX());
		prep.setDouble(2, coor.getY());
		return prep.executeQuery();
    }
	public  Hotel recuperer_hotel(Localisation coor) {
     try {
    	 Exe.getDb().setAutoCommit(false); 
		ResultSet rset =getresultSet(coor);
		while (rset.next()) {
			hotel.setNom(rset.getString(1));
			hotel.setEtoile(rset.getInt(2));
			hotel.setAdress(rset.getString(5));
			hotel.setTelephone(rset.getInt(6));
			hotel.setNote(rset.getDouble(7));
			hotel.setHotel_ID(rset.getInt(12));
			System.out.println(rset.getInt(12));
		}
		hotel.setLocalisation(coor);
		hotel.setConfort(recuperer_confort());
		
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
     return hotel;
	}
	public Confort recuperer_confort(){
		
		Confort confort=null;
		 try {
		 prep4.setInt(1, hotel.getHotel_ID());
		 ResultSet rs = prep4.executeQuery();
		 ArrayList<ImageIcon> listimage = new ArrayList<ImageIcon>();
		 if (rs != null) {
		    while(rs.next()) {
		    	listimage.add(getphoto(rs, "image"));
		    }
		  confort = new Confort();
	     confort.setList_image(listimage);   
		 }
		 rs.close();
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
		return confort;
		 
	}
	
	private ImageIcon getphoto(ResultSet rs,String str){
		
		Blob blob;
		  byte[] bytes=null;
		try {
			blob = rs.getBlob(str);
			if(blob!=null){
	    int blobLength = (int) blob.length();
	    bytes = blob.getBytes(1, blobLength);
	      
	    blob.free();
	    return   new ImageIcon(bytes);}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public Hotel consulter (Localisation loc){
    	try {
       recuperer_hotel(loc);
       prep=con.prepareStatement("select *from hotel where x=? and y=?");
	   ResultSet result=getresultSet(loc);   
   	   int id =hotel.getHotel_ID();

	   while (result.next()) {
			hotel.setSite_WEb(result.getString(8));
			hotel.setStyle(result.getString(9));	
			hotel.setPoint_interet(result.getString(11));
			hotel.setNbr(result.getInt(13));
			hotel.setNomService(result.getString(10));

	    }
	
   	 ResultSet rs = prep3.executeQuery();
	
	 while(rs.next()){
     hotel.getBar().setHoraire(rs.getString(2));
     hotel.getBar().setNomService(rs.getString(3));
	 }
	 prep7.setInt(1, id);
	 rs=prep7.executeQuery();
	 while(rs.next()){
		 hotel.getBar().setImage( getphoto(rs, "photo_bar"));
	 }
	 
	 Restaurant  restau= new Restaurant();
	 rs =prep5.executeQuery();
	 while(rs.next()){
			restau.setHoraire(rs.getString(3));
			restau.setNbrPlaces(String.valueOf(rs.getInt(2)));
		 }
	 Chamber chamber = new Chamber();
	 Chamber chamber1 = new Chamber();
	 rs =prep6.executeQuery();
	 ArrayList<String> listCarac_Single = new ArrayList<String>();
	 ArrayList<String> listCarac_double = new ArrayList<String>();

	 while(rs.next()){
			if(rs.getString(2).equalsIgnoreCase("Single")){
				listCarac_Single.add(rs.getString(3));
			}else{
				listCarac_double.add(rs.getString(3));
			}
			System.out.println("single-double");
		 }
	 chamber.setCaracteristiques(listCarac_Single);
	 chamber.setType("Single");
	 chamber1.setCaracteristiques(listCarac_double);
	 chamber1.setType("double");
	 prep10.setInt(1,id);
	 rs=prep10.executeQuery();
	 while(rs.next()){
		 if(rs.getInt(1)==1){
		 chamber.setPrix(String.valueOf(rs.getDouble(3)));
		 chamber.setImage(getphoto(rs,"photo_chamber"));}
		 else{ chamber1.setPrix(String.valueOf(rs.getDouble(3)));
		 chamber1.setImage(getphoto(rs,"photo_chamber")); 
		 }
	 }
	 ArrayList<Chamber> list_chamber = new ArrayList<Chamber>();
	 list_chamber.add(chamber);
	 list_chamber.add(chamber1);
	 hotel.setListchamber(list_chamber);
	 prep8.setInt(1, id);
	 
	rs= prep8.executeQuery();
	while(rs.next()){
		 restau.setImage(getphoto(rs,"restaurant_photo"));
		 restau.setTypePlat(rs.getString(4));
	}
	hotel.setRestaurant(restau);
	prep12.setInt(1,hotel.getHotel_ID());
    rs=prep12.executeQuery();
    ArrayList<String> div = new ArrayList<String>();
    while(rs.next()){
		 div.add(rs.getString(2));
	}
    hotel.getConfort().setDivertissement(div);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
	return hotel;
    }
    
	public  void Noter(int i,Localisation loc) {
        
        try {                 
            Exe.getDb().setAutoCommit(false); //Added
            PreparedStatement pStmnt = Exe.getDb().prepareStatement("update hotel set note=? where x=? and y=?");
            pStmnt.setDouble(1, i);
			System.out.println(i);
			pStmnt.setDouble(2,loc.getX());
			pStmnt.setDouble(3,loc.getY());             
            int results = pStmnt.executeUpdate();

            if (results > 0) {
               
            	Exe.getDb().commit(); //Added
            	Exe.getDb().setAutoCommit(true); //Added
            }       
        } catch (SQLException ex) {
        }
		 
	}

	

	public ArrayList<Localisation> chercher_un_hotel(Hotel hotel) throws SQLException{	
	 ArrayList<Localisation> list = new ArrayList<Localisation>();
	 Budget budget =hotel.getBudget();
	 String type = hotel.getType_chamber();
	 String plus_proche_ou_no = hotel.getPoint_interet();
	 String style = hotel.getStyle();
	 System.out.println(style);
	 if(plus_proche_ou_no.contains("juste")){
	 ResultSet rs = Exe.getDb().createStatement().executeQuery("select x,y from hotel");
	 double distance =999999;
	 Localisation closest=null;
	 while(rs.next()){
		 double x = rs.getDouble(1);
	     double y =rs.getDouble(2);
	     double dist=0;
		 try {
		   dist = JTS.orthodromicDistance(new Coordinate(Manupulateur.getLoc().getX(), Manupulateur.getLoc().getY())
			,new Coordinate(x, y), Exe.crs);
			} catch (TransformException e) {
				e.printStackTrace();
			}
		 if(dist<distance){
			 distance=dist;
    		 closest=new Localisation(new Coordinate(x, y));
    		 System.out.println(x+" moh "+y);  		
    	 }
		 list.add(closest);

	 }
	 }else{
	 PreparedStatement prep = Exe.getDb().prepareStatement("select  x,y from chamber_contient inner join hotel on "
	 		+ "chamber_contient.id_h =hotel.id_h  where  chamber_contient.prix_chamber>? and chamber_contient.prix_chamber<? and chamber_contient.id_chamber=? and hotel.style=? ");
     prep.setInt(1, budget.getMinPrix());
     prep.setInt(2, budget.getMaxPrix());
      if(type.contains("Single"))
      { prep.setInt(3, 1);}
      else{
    	prep.setInt(3,2);
      }
     prep.setString(4, style);
     ResultSet rs = prep.executeQuery();
     double distance =999999999;
     Localisation closest = null ;
     while(rs.next()){
    	double x = rs.getDouble(1);
    	double y =rs.getDouble(2);
    	list.add(new Localisation(new Coordinate(x, y)));
    	if(!plus_proche_ou_no.contains("ignorer")){
        	
        
    	 double dist=0;
		try {
			dist = JTS.orthodromicDistance(new Coordinate(Manupulateur.getLoc().getX(), Manupulateur.getLoc().getY())
			 ,new Coordinate(x, y), Exe.crs);
		} catch (TransformException e) {
			e.printStackTrace();
		}
    	 if(dist<distance){
			 distance=dist;
    		 closest=new Localisation(new Coordinate(x, y));
    	 }}
     }
         if(plus_proche_ou_no.contains("ignorer")){
        	
         }else if(plus_proche_ou_no.contains("plus proche")){
			 list = new ArrayList<Localisation>();
			 list.add(closest);
			
		 }
	 }
	 return list;
	}
	
	public int get_hote_id(Hotel h) throws SQLException{
		 prep11.setDouble(1, h.getLocalisation().getX());
		    prep11.setDouble(2,  h.getLocalisation().getY());
		    ResultSet rs = prep11.executeQuery();
		    int i =0;
		    while (rs.next()) {
				 i = rs.getInt(1);
				
			}
		    return i;
	}
	
    public int ajouter_hotel(Hotel h){
    	try {                 
            Exe.getDb().setAutoCommit(false); //Added
            
            PreparedStatement pStmnt = Exe.getDb().prepareStatement("insert into hotel(x,y) values(?,?)");
            pStmnt.setDouble(1, h.getLocalisation().getX());
            pStmnt.setDouble(2, h.getLocalisation().getY());           
            int results = pStmnt.executeUpdate();

            if (results > 0) {
               
            	Exe.getDb().commit(); //Added
            	Exe.getDb().setAutoCommit(true); //Added
            } 
            return get_hote_id(h);
        } catch (SQLException ex) {
        	
        }
    
		return 0;
    	
    }
    public void supprimer(Localisation coor){
    	try {                 
            Exe.getDb().setAutoCommit(false); //Added
            ResultSet rs =Exe.getDb().createStatement().executeQuery("select (id_h)from hotel where x="+coor.getX()+" and y="+coor.getY()+"");            
            int i =0;
            while(rs.next()){
            	i = rs.getInt(1);
            }
            
            Exe.getDb().createStatement().executeUpdate("delete from bar_contient where id_h="+i+"");
            Exe.getDb().createStatement().executeUpdate("delete from restaurant_contient where id_h="+i+"");
            Exe.getDb().createStatement().executeUpdate("delete from chamber_contient where id_h="+i+"");
            Exe.getDb().createStatement().executeUpdate("delete from divertissement where id_h="+i+"");
            Exe.getDb().createStatement().executeUpdate("delete from images where id_h="+i+"");
            
            PreparedStatement pStmnt = Exe.getDb().prepareStatement("delete from hotel where id_h="+i+"");
            
            int results = pStmnt.executeUpdate();

            if (results > 0) {
               
            	Exe.getDb().commit(); //Added
            	Exe.getDb().setAutoCommit(true); //Added
            }       
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
      
    }
    public Localisation consulter(int id ){
    	Localisation loc=null;
    	try {
    	
    		double x =0;
    		double y=0;
			prep13.setInt(1, id);
			ResultSet rs =prep13.executeQuery();
			System.out.println();
			while(rs.next()){
				x = rs.getDouble(1);
				y= rs.getDouble(2);
				loc=new Localisation(new Coordinate(x,y));
			}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	return loc;
    }
    
    public String modifier(Hotel h) throws Exception{
    	String message ="";
    	 try { 
             Exe.getDb().setAutoCommit(false); //Added
             Statement pStmnt = Exe.getDb().createStatement();
             pStmnt.executeUpdate("update hotel set nom='"+h.getNom()+"' where id_h ="+h.getHotel_ID()+" ");

             if(h.getAdress().length()>8){
            	 pStmnt.executeUpdate("update hotel set adress='"+h.getAdress()+"' where id_h ="+h.getHotel_ID()+" ");
             }else{
            	 message+=" l'adress ";
             }
             
             pStmnt.executeUpdate("update hotel set etoil="+h.getEtoile()+" where id_h ="+h.getHotel_ID()+" ");
             
             pStmnt.executeUpdate("update hotel set nom_service='"+h.getNomService()+"' where id_h ="+h.getHotel_ID()+" ");
             pStmnt.executeUpdate("update hotel set nbr="+h.getNbr()+" where id_h ="+h.getHotel_ID()+" ");
             
             
             if(h.getStyle().length()>4){
            	 pStmnt.executeUpdate("update hotel set style='"+h.getStyle()+"' where id_h ="+h.getHotel_ID()+" ");
            	 System.out.println(h.getStyle());
             }else{
            	 message+=" Style ";
             }
           
            	 pStmnt.executeUpdate("update hotel set telephone="+h.getTelephone()+" where id_h ="+h.getHotel_ID()+" ");
            
             if(h.getPoint_interet().length()>5){
            	 pStmnt.executeUpdate("update hotel set point_d_interet='"+h.getPoint_interet()+"' where id_h ="+h.getHotel_ID()+" ");
             }else{
        	     message+=" Point_interet ";
             }
             if(h.getSite_WEb().length()>5){
            	 pStmnt.executeUpdate("update hotel set web_site='"+h.getSite_WEb()+"' where id_h ="+h.getHotel_ID()+" ");
             }else{
        	     message+=" Web_Site ";
             }

             ArrayList<String> list1 = h.getConfort().getDivertissement();
             Exe.getDb().createStatement().executeUpdate("delete from divertissement where id_h="+h.getHotel_ID());
             for (int j = 0; j < list1.size(); j++) {
            	 System.out.println("div "+list1.get(j));
             Exe.getDb().createStatement().executeUpdate("insert into divertissement(type,id_h) values('"+list1.get(j)+"',"+h.getHotel_ID()+")");					 
             }
             ArrayList<ImageIcon> list = h.getConfort().getList_image();
             try {
					ajouter_photos(list,h.getHotel_ID());
			 } catch (IOException e) {
			 e.printStackTrace();
			 }            
             Restaurant resto = h.getRestaurant();
             if(resto.getTypePlat().length()>4){
             ResultSet result =Exe.getDb().createStatement().executeQuery("select *from  restaurant_contient where id_h ="+hotel.getHotel_ID());
             int i =0;
             while(result.next()){
             Exe.getDb().createStatement().executeUpdate("update  restaurant_contient set type_plat='"+resto.getTypePlat()+"'  where id_h="+h.getHotel_ID());
	         i++;
             }
             if(i==0)
             Exe.getDb().createStatement().executeUpdate("insert into restaurant_contient(type_plat,id_h,id_r) values('"+resto.getTypePlat()+"', "+h.getHotel_ID()+","+1+")");
             }else{
             message+=" type de plat ";
             }
             ImageIcon img =resto.getImage();
           
			ajouter_photo(img,h.getHotel_ID(), "restaurant");
				
			 
            img = h.getBar().getImage();
        
			ajouter_photo(img,h.getHotel_ID(), "bar");
		
            ArrayList<Chamber> chamber_list = h.getListchamber();
            for (int i = 0; i < chamber_list.size(); i++) {
				if(chamber_list.get(i).getType().contains("double")){					
				ajouter_photo( chamber_list.get(i).getImage() , h.getHotel_ID(),"double");
	    	    Exe.getDb().createStatement().executeUpdate("update chamber_contient  set prix_chamber="+chamber_list.get(i).getPrix()+" where id_h="+h.getHotel_ID()+"and id_chamber="+2);    	
				}else{
				ajouter_photo( chamber_list.get(i).getImage() , h.getHotel_ID(),"Single");
	    	    Exe.getDb().createStatement().executeUpdate("update chamber_contient  set prix_chamber="+chamber_list.get(i).getPrix()+" where id_h="+h.getHotel_ID()+"and id_chamber="+1);    	
				}
			}

            Exe.getDb().commit(); 
            Exe.getDb().setAutoCommit(true); 
            } catch (SQLException ex) {
        	 try {
		    Exe.getDb().rollback();
		    ex.printStackTrace();
			 } catch (SQLException e) {
				e.printStackTrace();
			 }
        	 ex.printStackTrace();
         }
 		 
    	return message;
    }
    public void ajouter_photo(ImageIcon img ,int id,String str) {
    	try{
    	if(str.contains("restaurant"))
    	{  Exe.getDb().createStatement().executeUpdate("update restaurant_contient  set restaurant_photo="+recupererIOD(img)+" where id_h="+id);
    	}else{
    		if(str.contains("bar")){
				System.out.println("bar");

    	    ResultSet result =Exe.getDb().createStatement().executeQuery("select *from   bar_contient where id_h ="+hotel.getHotel_ID());
    	    int i =0;
    		while(result.next()){
    	    	   Exe.getDb().createStatement().executeUpdate("update bar_contient  set photo_bar="+recupererIOD(img)+" where id_h="+id);    	
    		 i++;}
    		if(i==0)
    	   Exe.getDb().createStatement().executeUpdate("insert into bar_contient  values(1, "+id+","+recupererIOD(img)+")");    	
    		}else{
    		 if(str.contains("double")){
 				System.out.println("yeah yeah yeah 1");

    	    	   ResultSet result =Exe.getDb().createStatement().executeQuery("select *from  chamber_contient where id_h ="+hotel.getHotel_ID()+" and id_chamber="+2);
    	    	   int i =0;
    	    	   while(result.next()){
    	    	    Exe.getDb().createStatement().executeUpdate("update chamber_contient  set photo_chamber="+recupererIOD(img)+" where id_h="+id+"and id_chamber="+2);    	
    	    	    i++;}
    	    	   if(i==0)
    	           Exe.getDb().createStatement().executeUpdate("insert into chamber_contient values (2,"+id+","+recupererIOD(img)+")");
    	    		
    		 }else{
 				System.out.println("yeah yeah yeah 2");

    	    	   ResultSet result =Exe.getDb().createStatement().executeQuery("select *from  chamber_contient where id_h ="+hotel.getHotel_ID()+" and id_chamber="+1);
    	    	   int i =0;
    	    	   while(result.next()){
    	    	    Exe.getDb().createStatement().executeUpdate("update chamber_contient  set photo_chamber="+recupererIOD(img)+" where id_h="+id+"and id_chamber="+1);    	

    	    	    i++;}
    	    	   if(i==0)
    	           Exe.getDb().createStatement().executeUpdate("insert into chamber_contient values (1,"+id+","+recupererIOD(img)+")");
    	           
    		 }
    		}
    	}}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
	private void ajouter_photos(ArrayList<ImageIcon> img_list,int id) throws SQLException, IOException{
 
            Exe.getDb().createStatement().executeUpdate("delete from images where id_h="+id);
		  for (int j = 0; j < img_list.size(); j++) {
	      Exe.getDb().createStatement().executeUpdate("insert into images(image,id_h) values("+recupererIOD(img_list.get(j))+","+id+")");
		 }
   }
   private Integer recupererIOD(ImageIcon img) throws SQLException, IOException{
	   	 LargeObjectManager lobj = ((org.postgresql.PGConnection)Exe.getDb()).getLargeObjectAPI();
			 @SuppressWarnings("deprecation")
			int oid = lobj.create(LargeObjectManager.READ | LargeObjectManager.WRITE);
			 @SuppressWarnings("deprecation")
			LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);
	
			 Image image = img.getImage();
			     BufferedImage bImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
			     Graphics bg = bImage.getGraphics();
			     bg.drawImage(image,0,0,null);
			     bg.dispose();
			     ByteArrayOutputStream ou = new ByteArrayOutputStream();
			     ImageIO.write(bImage,"jpeg",ou);
			     byte[] buf = ou.toByteArray();
			     // setup stream for blob
			     ByteArrayInputStream inStream = new ByteArrayInputStream(buf);
			 
			
			 int s;
			 while ((s = inStream.read(buf, 0, 2048)) > 0)
			 {
			         obj.write(buf, 0, s);
			 }

			 obj.close();
			 System.out.println("oid"+oid);
			return oid;
   }
    
}
