package org.geotools.model;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geotools.compte.Hotel;
import org.geotools.compte.Objet_G;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.interf.Exe;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.FeatureId;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

public class CarteDAO {
     Map<String,Object> params = new HashMap <String,Object>();
	public static DataStore dataStore;
    
	 Objet_G object = new Objet_G();
   static PreparedStatement prep  = null;
   static PreparedStatement prep1 =null;
   static PreparedStatement prep2 = null;
   static PreparedStatement prep3 = null;
   static FileDataStore fileDataStore1;
	static FileDataStore fileDataStore2;

	public synchronized static void init() throws IOException{
		
		  Map<String,Object> params = new HashMap<String,Object>();
		  params.put( "dbtype", "postgis");
	        params.put( "host", "localhost");
	        params.put( "port", 5432);
	        params.put( "schema", "public");
	        params.put( "database", "projet");
	        params.put( "user", "postgres");
	        params.put( "passwd", "ikol1945");
		try{
			 fileDataStore1 = FileDataStoreFinder.getDataStore(new File("des2.shp"));
			 fileDataStore2 = FileDataStoreFinder.getDataStore(new File("clo2.shp"));
		      dataStore = DataStoreFinder.getDataStore(params);
		}catch(Exception e){
		
		}finally{
			
		}
	}
	
public SimpleFeatureSource getPoint_shape() {
    try {
    
	return fileDataStore1.getFeatureSource();
	} catch (IOException e) {
		System.out.println("1");
		return null;
	}    
}


public  SimpleFeatureSource getLine_shape() {
	   try {		   
			return dataStore.getFeatureSource("roads");
			} catch (IOException e) {
				System.out.println("yea");
				return null;
			}    
}

public  SimpleFeatureSource getClosest_Line() {
	 try {		
			return 	fileDataStore2.getFeatureSource();
			} catch (IOException e) {
				System.out.println("1");
				return null;
			}  
}


public  SimpleFeatureSource getPolygone_shape() {
	try {
		SimpleFeatureSource sm =dataStore.getFeatureSource("hotels");
		return 	sm;
	} catch (IOException e) {
		e.printStackTrace();
		return null;
	}finally{
		
	}
}

public  ArrayList<Hotel> rechercher(Hotel h){
	return null;
	
}

public  Objet_G Selctionner(String geometryAttributeName,ReferencedEnvelope bbox){
	 
	Point point=null;
	FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();  
    try {

     Filter filter = ff.bbox(ff.property(geometryAttributeName), bbox);

     SimpleFeatureCollection selectedFeatures = getPolygone_shape().getFeatures(filter);

     SimpleFeatureIterator iter = selectedFeatures.features();

     Set<FeatureId> IDs = new HashSet<FeatureId>();
      
       try {

          while (iter.hasNext()) {

             SimpleFeature feature = iter.next();

             IDs.add(feature.getIdentifier());

              point = (Point) feature.getAttribute("geom");
              System.out.println(point);
          }

       } finally {

          iter.close();

       }

       if (IDs.isEmpty()) {

          System.out.println("   no feature selected");
         return null;
       }else{
     	   Localisation loc = new Localisation(point.getCoordinate());
     		return object.recherecher_par_localisation(loc);
       }


    } catch (Exception ex) {

       ex.printStackTrace();

       
    }

	return null;
	
}

public  boolean Demander_itinéraire(SimpleFeatureCollection routeSource) throws IOException{
	
	  SimpleFeatureStore store = (SimpleFeatureStore) Exe.getCarte().getClosest_Line() ;
   	  FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2( GeoTools.getDefaultHints() ); 
   	  Set<FeatureId> fids = new HashSet<FeatureId>();
   	  fids.add( ff.featureId("clo2.1") );
   	
   	  Filter filter = ff.id( fids );  
   	  supprimerfeature(store, filter); 
   
      Transaction transaction = new DefaultTransaction("Add Example");
	   store.setTransaction( transaction );
	         try {
              store.addFeatures( routeSource );
              transaction.commit();
         }
         catch( Exception eek){
              transaction.rollback();
          }
	         return true;
}

public static void supprimerfeature(SimpleFeatureStore store,Filter filter) throws IOException{

	Transaction transaction = new DefaultTransaction("removeExample");
	    store.setTransaction(transaction);
	    try {
	        store.removeFeatures(filter);
	        transaction.commit();
	    } catch (Exception eek) {
	   
			transaction.rollback();
	    }

}
public static void supprimerfeature(SimpleFeatureStore store,String geom) throws IOException{
			try {                 
	            Exe.getDb().setAutoCommit(false); //Added
	            Statement pStmnt = Exe.getDb().createStatement();
	            int resutlts =pStmnt.executeUpdate("delete from hotels where geom = '"+geom+"'");
	            if (resutlts > 0) {
	            	Exe.getDb().commit(); 
	            	Exe.getDb().setAutoCommit(true);
	            }       
	        } catch (SQLException ex) {
	        }

}
public static void ajouterfeature(SimpleFeatureStore store ,Coordinate c) throws IOException{
	System.out.println(store.getName()+" yeah yeah");
	
	if(!store.getName().toString().equals("hotels")){
	  GeometryBuilder geom = new GeometryBuilder();
       SimpleFeatureType featureType = store.getSchema();
       SimpleFeatureBuilder build = new SimpleFeatureBuilder(featureType);
       List<SimpleFeature> list = new ArrayList<SimpleFeature>();
       list.add(build.buildFeature("fid1", new Object[]{ geom.point(c.x,c.y), "hello" }));
       SimpleFeatureCollection collection = new ListFeatureCollection(featureType, list);
       
       Transaction transaction = new DefaultTransaction("Add Example");
         store.setTransaction( transaction );
       try {
            store.addFeatures( collection );
            transaction.commit();
       }
       catch( Exception eek){
            transaction.rollback();
        }
       FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2( GeoTools.getDefaultHints() ); 
    	  Set<FeatureId> fids = new HashSet<FeatureId>();
    	  fids.add( ff.featureId("clo2.1") );
    	
    	  Filter filter = ff.id( fids ); 
    	  store=(SimpleFeatureStore) Exe.getCarte().getClosest_Line();
    	  supprimerfeature(store, filter); 
	}else{
		
		try {                 
            Exe.getDb().setAutoCommit(false); //Added
            Statement pStmnt = Exe.getDb().createStatement();
            int resutlts =pStmnt.executeUpdate("insert into hotels(geom) values('Point("+c.x+" "+c.y+")')");
            if (resutlts > 0) {
            	Exe.getDb().commit(); //Added
            	Exe.getDb().setAutoCommit(true); //Added
            }       
        } catch (SQLException ex) {
        }
	}
}


}
