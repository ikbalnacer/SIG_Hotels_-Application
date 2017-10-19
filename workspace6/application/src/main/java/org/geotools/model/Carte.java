package org.geotools.model;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import org.geotools.compte.Hotel;
import org.geotools.compte.Objet_G;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.interf.Exe;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory2;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Point;

public class Carte {
private  SimpleFeatureSource point_shape;
private  SimpleFeatureSource Line_shape;
private  SimpleFeatureSource Polygone_shape;
private  SimpleFeatureSource closest_Line;
 FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();   
 

 Routers calc_R = new Routers();
CarteDAO carteDao = new CarteDAO();
public Carte(){
	try {
		CarteDAO.init();
	} catch (IOException e) {
		e.printStackTrace();
	}
    setClosest_Line(carteDao.getClosest_Line());
    setLine_shape(carteDao.getLine_shape());
    setPoint_shape(carteDao.getPoint_shape());
    setPolygone_shape(carteDao.getPolygone_shape());
   
}

public SimpleFeatureSource getClosest_Line() {
	return closest_Line;
}

public void setClosest_Line(SimpleFeatureSource closest_Line) {
	this.closest_Line = closest_Line;
}

public SimpleFeatureSource getPoint_shape() {
	return point_shape;
}

public void setPoint_shape(SimpleFeatureSource point_shape) {
	this.point_shape = point_shape;
}

public SimpleFeatureSource getLine_shape() {
	return Line_shape;
}

public void setLine_shape(SimpleFeatureSource line_shape) {
	Line_shape = line_shape;
}

public SimpleFeatureSource getPolygone_shape() {
	return Polygone_shape;
}

public void setPolygone_shape(SimpleFeatureSource polygone_shape) {
	Polygone_shape = polygone_shape;
}



public ArrayList<Hotel> rechercher(Hotel h){
	return null;
	
}

public  Objet_G Selctionner(AffineTransform screenToWorld,CoordinateReferenceSystem crs, java.awt.Point screenPos){
	  

       // Not collecting WayPoints, select features based on click and highlight them

       Rectangle screenRect = new Rectangle(screenPos.x-2, screenPos.y-2, 5, 5);


       Rectangle2D worldRect = screenToWorld.createTransformedShape(screenRect).getBounds2D();
       ReferencedEnvelope bbox = new ReferencedEnvelope(
               worldRect,
               crs);


       SimpleFeatureType schema = Polygone_shape.getSchema();


       String geometryAttributeName = schema.getGeometryDescriptor().getLocalName();
	 return(Objet_G)carteDao.Selctionner(geometryAttributeName,bbox);    
	
}

public  void Demander_itinéraire(Localisation loc,Localisation loc1,CoordinateReferenceSystem crs)throws IOException, TransformException, SchemaException, NoSuchAuthorityCodeException, FactoryException{
   	 /////	     
      GeometryBuilder geom = new GeometryBuilder();
      
      Point p= geom.point(loc.getX(), loc.getY());
      Point p1= geom.point(loc1.getX(), loc1.getY());
      
	  calc_R.creer_network(Exe.getCarte().getLine_shape().getFeatures(),p , crs);
	  calc_R.find_the_shortest_path(p, p1, 1,Exe.getCarte().getClosest_Line().getSchema());
      
	  SimpleFeatureCollection routeSource =calc_R.getRouteFeatures();
	    System.out.println("yeah "+routeSource.size());
	   carteDao.Demander_itinéraire(routeSource);

	
}


}
