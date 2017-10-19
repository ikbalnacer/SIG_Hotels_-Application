package org.geotools.compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.geotools.interf.Exe;

public class CompteDAO {
	Connection con;
	PreparedStatement prep;
	PreparedStatement prep1;
	PreparedStatement prep2;
	PreparedStatement prep4;
	public CompteDAO(){
		try {
		con=Exe.getDb();
		prep  = con.prepareStatement("select *from compte where password=? and pseaudo=?");
		prep1 = con.prepareStatement("");
		prep2 = con.prepareStatement("delete from compte where id_h_etr=?");
		
		prep4 = con.prepareStatement("modifier");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public  Compte consulter(String pseaudo, String mot_de_pass, Compte compte ) {
		 Integer i =0;
		 String str=null;
		 ResultSet result;
		try {
			
		prep.setString(1, mot_de_pass);
		prep.setString(2, pseaudo);
		result = prep.executeQuery();

		while (result.next()) {
			 str = result.getString(4);
			 i = result.getInt(5);

		}
		prep.close();
		result.close();
	
	} catch (SQLException e) {
	}
	 compte.setHotel_id(i);
	 compte.setMot_de_passe(mot_de_pass);
	 compte.setPseaudo(pseaudo);
	 compte.setType(str);
	
	 return compte;
	}


	public  void supprimer_compte(int i) {
		try {                 
            Exe.getDb().setAutoCommit(false); //Added
            PreparedStatement pStmnt = Exe.getDb().prepareStatement("delete from compte where id_h_etr=?");
            pStmnt.setInt(1, i);
            int results = pStmnt.executeUpdate();
           System.out.println("result " +results);
            if (results > 0) {
               
            	Exe.getDb().commit(); //Added
            	Exe.getDb().setAutoCommit(true); //Added
            }       
        } catch (SQLException ex) {
          ex.printStackTrace();
        
        }
	
		
	}

	public  void ajouter_compte(Compte c,int i){
		try {                 
			Exe.getDb().setAutoCommit(false); 
            PreparedStatement pStmnt = Exe.getDb().prepareStatement("insert into compte(pseaudo,password,type,id_h_etr) values(?,?,?,?)");
            pStmnt.setString(1,c.getPseaudo());
            pStmnt.setString(2,c.getMot_de_passe());
            pStmnt.setString(3,"gestion");
            pStmnt.setInt(4,i);       
            int results = pStmnt.executeUpdate();

            if (results > 0) {
               
            	Exe.getDb().commit(); //Added
            	Exe.getDb().setAutoCommit(true); //Added
            }       
        } catch (SQLException ex) {
        	new Exception();
        }
	
	}

	public  void modifier(Compte c) {
		try{
		if(c.getMot_de_passe()!=null){
		Exe.getDb().setAutoCommit(false); 
        PreparedStatement pStmnt = Exe.getDb().prepareStatement("update compte set password=? where pseaudo=?");
        System.out.println(c.getMot_de_passe()+"     "+c.getPseaudo());
        pStmnt.setString(1,c.getMot_de_passe());   
        pStmnt.setString(2,c.getPseaudo());
        int results = pStmnt.executeUpdate();
        
        if (results > 0) {
        	Exe.getDb().commit(); //Added
        	Exe.getDb().setAutoCommit(true); //Added
        }
        }  } catch (SQLException ex) {
        	new Exception();
        }
	}
}
