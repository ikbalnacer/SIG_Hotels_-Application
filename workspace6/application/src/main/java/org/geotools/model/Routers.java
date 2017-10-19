package org.geotools.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.geotools.data.DataUtilities;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.graph.build.feature.FeatureGraphGenerator;
import org.geotools.graph.build.line.LineStringGraphGenerator;
import org.geotools.graph.path.DijkstraShortestPathFinder;
import org.geotools.graph.path.Path;
import org.geotools.graph.structure.Edge;
import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;
import org.geotools.graph.structure.basic.BasicEdge;
import org.geotools.graph.traverse.standard.DijkstraIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

public class Routers {

	private Graph networkGraph;
	private LineStringGraphGenerator lineStringGen = new LineStringGraphGenerator();
	private SimpleFeatureCollection routeFeatures;
	
	private DijkstraShortestPathFinder dspf;
	
	private  CoordinateReferenceSystem crs;
	///////////
	public static String distance ;
	node_ nodefinder = new node_();
	public void creer_network(SimpleFeatureCollection networkFC,Point originPoint,CoordinateReferenceSystem cr) throws TransformException{
		SimpleFeatureCollection fCollection = networkFC;
        crs=cr;
		LineStringGraphGenerator lineStringGen = new LineStringGraphGenerator();
		FeatureGraphGenerator featureGen = new FeatureGraphGenerator( lineStringGen );

		FeatureIterator<?> iter = fCollection.features();
		try {
		  while(iter.hasNext()){
		     Feature feature = iter.next();
		     featureGen.add( feature );
		  }
		} finally {
		  iter.close();
		}
		 networkGraph = featureGen.getGraph();
		 

				DijkstraIterator.EdgeWeighter weighter = new DijkstraIterator.EdgeWeighter() {
				
				public double getWeight(org.geotools.graph.structure.Edge e) {
					 SimpleFeature feature = (SimpleFeature) e.getObject();
				      Geometry geometry = (Geometry) feature.getDefaultGeometry();
				      return geometry.getLength();
				}
				};
				 Node start = nodefinder.get_plus_proche_node(lineStringGen, networkGraph, originPoint, crs);

				 dspf = new DijkstraShortestPathFinder( networkGraph, start, weighter );
				 dspf.calculate();
        
	}
	
public void find_the_shortest_path (Point originPoint, Point destinationPoint, int ID,SimpleFeatureType type) throws SchemaException, TransformException {
	Double distanceDestinationtoGraph = node_.get_la_distance();

	Node destination = nodefinder.get_plus_proche_node(lineStringGen, networkGraph,destinationPoint,crs);
	 
	  Path path = dspf.getPath( destination );
		  Vector<Edge> result = new Vector<Edge>();
		  Node previous = null, node = null;
			  if (path == null){
				  System.out.println("Could not calculate the route to this destination" +
				  		". There is probably a problem with the network dataset (dangles etc...");
				  return;
			  }
			  for (    Iterator<?> ritr = path.riterator();
		                ritr.hasNext();
		            ) {
		                node =  (Node) ritr.next();
		                if (previous != null) {
		                	result.add(node.getEdge(previous));
		                }
		                previous = node;
		            }

			  Geometry[] geomArray = new Geometry[result.size()];
			  
			  for(int i=0;i<result.size();i++)
		        {
				  BasicEdge eg = (BasicEdge) result.get(i);
				  SimpleFeature feature = (SimpleFeature) eg.getObject();
				  Geometry geom = (Geometry) feature.getDefaultGeometry();
				  geomArray[i] = geom;
		        }
			  
			  Geometry routeGeometry = fusionner_geom(geomArray);
			
			  List<SimpleFeature> list = new ArrayList<SimpleFeature>();

			  if (dspf.getCost(destination) != 0)//path is not null
				  {
				    System.out.println("yeaaaaaaah");

					    System.out.println("yeaaaaaaah");
			  SimpleFeature routeFeature = Creerfeature(routeGeometry, dspf, destination, distanceDestinationtoGraph , ID );
			    System.out.println("after"
			    		+ "");

			  list.add(routeFeature);
			  }
			  System.out.println("yeah");
			 routeFeatures = new ListFeatureCollection(type, list);
			 System.out.println(routeFeatures.size());
	}
	
	private Geometry fusionner_geom(Geometry[] geomArray){
		Geometry all = null;
		  double distacee =0;
		  for(int i=0;i<geomArray.length;i++){
			  Geometry geomtoAddtemp = geomArray[i];
			  if(i<geomArray.length-1)
				try {
					distacee+= JTS.orthodromicDistance(geomArray[i].getCoordinate(), geomArray[i+1].getCoordinate(), crs);
				} catch (TransformException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  if(i+1<geomArray.length){
			  geomtoAddtemp.getCoordinate(); 
			  }
			  if( all == null ){
	    			all = geomtoAddtemp;
	    		}
	    		else {
	    			all = all.union( geomtoAddtemp );
	    		}
		  }
		  int totalmeters = (int) distacee;
		    int km = totalmeters / 1000;
		    int meters = totalmeters - (km * 1000);
		    distance="Distance = " + km + "km " + meters + "m ";
		    
		return all;
		
	}
	
	private  SimpleFeature Creerfeature(Geometry routeGeometry, DijkstraShortestPathFinder pf, Node destination, Double distancetoGraph,int objectId) throws SchemaException{
	    System.out.println("yeaaaaaaah");

	    final SimpleFeatureType TYPE = DataUtilities.createType("Location",
	            "the_geom:MultiLineString:srid=4326," + // <- the geometry attribute: Polyline type
	                    "travel_distance_m:Double," + "objectid:Integer"// <- a String attribute
	                    ); 
		
		System.out.println("distance to grapph = "+distancetoGraph+" "+pf.getCost(destination));
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		featureBuilder.add(routeGeometry);
		
		featureBuilder.add(pf.getCost(destination)+distancetoGraph);
		featureBuilder.add(objectId);
		
	    SimpleFeature feature = featureBuilder.buildFeature(null);
	  

		return feature;
		
	}

public SimpleFeatureCollection getRouteFeatures() {
		return routeFeatures;
	}

	
}
