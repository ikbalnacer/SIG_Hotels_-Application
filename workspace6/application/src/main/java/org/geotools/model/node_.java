package org.geotools.model;

import org.geotools.geometry.jts.JTS;
import org.geotools.graph.build.line.BasicLineGraphGenerator;
import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;


public class node_ {
	private  Node nodeleplusproche = null;
	private static Double distancedeleplusprochenode = 0.0;

	public  Node get_plus_proche_node(BasicLineGraphGenerator lsgg, Graph g,Point pointy, CoordinateReferenceSystem crs) throws TransformException{	
	    double dist = 777777777;
	 	    for (Object o:g.getNodes()) {
	    	Node n = (Node) o;
	    	Point p = ((Point)n.getObject());
	    	double nodeDist = CalculateDistance(pointy.getX(), pointy.getY(), p.getCoordinate().x, p.getCoordinate().y, crs);
	    	
    		if (nodeDist < dist) {
    			dist=nodeDist;
    			nodeleplusproche = n;
	    	}
	    }
	 	   distancedeleplusprochenode = dist;
		return nodeleplusproche;
		
        
} 
	
	public static Double get_la_distance(){
		return distancedeleplusprochenode;
	}
	
	
	
	private  double CalculateDistance(double xOrig, double yOrig, double xDest, double yDest, CoordinateReferenceSystem crs) throws TransformException{
		Coordinate start = new Coordinate(xOrig, yOrig);
		Coordinate end = new Coordinate(xDest, yDest);
		
		double distance = JTS.orthodromicDistance(start, end, crs);
		return distance;
		
	}

}

