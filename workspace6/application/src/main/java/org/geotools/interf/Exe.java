package org.geotools.interf;


import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapContent;
import org.geotools.model.Carte;
import org.geotools.model.Localisation;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Graphic;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLDParser;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.FeatureId;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class Exe
{
	public static MapContent map = new MapContent();
	private static Connection db;
	private static Carte carte =null;
	static StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
	static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();
	public static CoordinateReferenceSystem crs = null;
    public static Style Style_hotel;
    public static Boolean recherche=false;
	public static void init() throws IOException{
	
		BufferedReader sld = new BufferedReader(new InputStreamReader(new FileInputStream("linesld.sld")));

		StyleFactory stylef = CommonFactoryFinder.getStyleFactory();
		SLDParser stylereader = new SLDParser(stylef, sld);
		
		Style styles[] = stylereader.readXML();
		
		map.addLayer(new FeatureLayer(getCarte().getLine_shape(), styles[0]));
	    crs=getCarte().getClosest_Line().getSchema().getCoordinateReferenceSystem();
		map.addLayer(new FeatureLayer(getCarte().getClosest_Line().getFeatures(),createLineStyle() ));
		
		sld = new BufferedReader(new InputStreamReader(new FileInputStream("point.sld")));
	
		stylef = CommonFactoryFinder.getStyleFactory();
		stylereader = new SLDParser(stylef, sld);
	    styles = stylereader.readXML();
	 
		
		map.addLayer(new FeatureLayer(getCarte().getPoint_shape().getFeatures(),styles[0]));
	 
		sld = new BufferedReader(new InputStreamReader(new FileInputStream("Hotels.sld")));

		stylef = CommonFactoryFinder.getStyleFactory();
		stylereader = new SLDParser(stylef, sld);
		
		styles = stylereader.readXML();
		
		map.addLayer(new FeatureLayer(getCarte().getPolygone_shape().getFeatures(), styles[0]));
	    
	}
	
	public  void init_sir() throws Exception 
	{ try{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (InstantiationException e) {}
			catch (ClassNotFoundException e) {}
			catch (UnsupportedLookAndFeelException e) {}
			catch (IllegalAccessException e) {}
		try
		{   connecter();
		    carte = new Carte();
			init();
			new Frame(new org.geotools.interf.Interface_Carte(map));
		}catch(Exception e){}
		SimpleFeatureStore store = (SimpleFeatureStore) getCarte().getClosest_Line();
        init_Shape_File("clo2.1", store);
        store = (SimpleFeatureStore) getCarte().getPoint_shape();
        init_Shape_File("des2.3", store);
	   } 
    catch (Throwable t) {

   }
	}
	public static void connecter () throws ClassNotFoundException{
	
		try {
			 Class.forName("org.postgresql.Driver");
			   String url = "jdbc:postgresql://localhost:5432/projet";
			   String user = "postgres";
			   String passwd = "ikol1945";
			    db = DriverManager.getConnection(url, user,
			   passwd);
			} catch (SQLException e) {
			}
	}
   
	public static Connection getDb() {
		return db;
	}

	public static Carte getCarte() {
		return carte;
	}

	public static void setCarte(Carte carte) {
		Exe.carte = carte;
	}
	
	private  static Style createLineStyle() {
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(Color.red),
                filterFactory.literal(4));
        LineSymbolizer sym = styleFactory.createLineSymbolizer(stroke, null);
        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }
    public static void init_Shape_File(String str,SimpleFeatureStore store){
   	  FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2( GeoTools.getDefaultHints() ); 
  	  Set<FeatureId> fids = new HashSet<FeatureId>();
  	  fids.add( ff.featureId(str) );
  	  Filter filter = ff.id( fids );   	 
  	  Transaction transaction = new DefaultTransaction("removeExample");
      store.setTransaction(transaction);
   try {
       store.removeFeatures(filter);
       transaction.commit();
   } catch (Exception eek) {
  
		try {
			transaction.rollback();
		} catch (IOException e) {
			e.printStackTrace();
		}
   } 
    }
  public static void Create_Style(ArrayList<Localisation> list)  {
	  StyleBuilder builder = new StyleBuilder();
	    FilterFactory2 ff = builder.getFilterFactory();
	    Graphic dotGraphic = builder.createGraphic(null,builder.createMark(StyleBuilder.MARK_STAR, Color.red, Color.orange,1),
	            null);
	    PointSymbolizer dotSymbolize = builder.createPointSymbolizer(dotGraphic);
	    Rule rule2 = builder.createRule(dotSymbolize);
	    GeometryBuilder geom = new GeometryBuilder();

	    for (int i = 0; i<list.size(); i++) {			
	    rule2.setFilter(ff.equals(ff.property("geom"), ff.literal(geom.point(list.get(i).getX(), list.get(i).getY()))));
	    }
	    
	    Rule rules[] = new Rule[] {  rule2 };
	    FeatureTypeStyle featureTypeStyle = builder.createFeatureTypeStyle("Feature", rules);
	    
	    Style style = builder.createStyle();
	    style.setName("style");
	    style.getDescription().setTitle("User Style");
	    style.getDescription().setAbstract("Definition of Style");
	    style.featureTypeStyles().add(featureTypeStyle);

        /*
         * Use the filter to identify the selected features
         */
	    if(list.size()>0){
	    org.geotools.map.Layer layer = map.layers().get(3);
	        ((FeatureLayer) layer).setStyle(style);
	    Frame.paneauMapPane.repaint();
	    recherche=true;
	    }
    }
    public static void first_Style(){

		BufferedReader sld=null;
		try {
			sld = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\Hotels.sld")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StyleFactory	stylef = CommonFactoryFinder.getStyleFactory();
		SLDParser stylereader = new SLDParser(stylef, sld);
		
		Style[] styles = stylereader.readXML();
		 org.geotools.map.Layer layer = map.layers().get(3);
	        ((FeatureLayer) layer).setStyle(styles[0]);
    }
  

  
}
