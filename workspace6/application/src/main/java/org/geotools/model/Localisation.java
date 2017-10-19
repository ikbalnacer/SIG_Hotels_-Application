package org.geotools.model;

import com.vividsolutions.jts.geom.Coordinate;

public  class Localisation {
	
private double x;
private double y;

public double getX() {
	return x;
}



public void setX(double x) {
	this.x = x;
}



public double getY() {
	return y;
}



public void setY(double y) {
	this.y = y;
}

public Localisation(Coordinate c){
	this.x=c.x;
	this.y=c.y;
}


}
