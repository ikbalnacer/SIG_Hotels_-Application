package org.geotools.compte;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Confort {
ArrayList<String> Divertissement = new ArrayList<String>();
ArrayList<ImageIcon> list_image = new ArrayList<ImageIcon>();
public ArrayList<String> getDivertissement() {
	return Divertissement;
}
public void setDivertissement(ArrayList<String> divertissement) {
	Divertissement = divertissement;
}
public ArrayList<ImageIcon> getList_image() {
	return list_image;
}
public void setList_image(ArrayList<ImageIcon> list_image) {
	this.list_image = list_image;
}

}
