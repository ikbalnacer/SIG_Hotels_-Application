package org.geotools.interf;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.geotools.map.MapContent;
import org.geotools.swing.JMapPane;

	public class Interface_Carte extends JFrame {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private JMapPane mapPane;
	

	public Interface_Carte() {
	this(null);
	}
	
	public Interface_Carte(MapContent content) {
	super(content == null ? "" : content.getTitle());
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	// the map pane is the one element that is always displayed
	System.out.println("yeah");
	mapPane = new JMapPane(content);
	System.out.println("yeah");

	mapPane.setBackground(Color.WHITE);
	mapPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	
	// give keyboard focus to the map pane
	addWindowFocusListener(new WindowAdapter() {
	@Override
	public void windowGainedFocus(WindowEvent e) {
	mapPane.requestFocusInWindow();
	}
	});
	
	mapPane.addFocusListener(new FocusAdapter() {
	
	@Override
	public void focusGained(FocusEvent e) {
	mapPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	@Override
	public void focusLost(FocusEvent e) {
	mapPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	}
	});
	
	mapPane.addMouseListener(new MouseAdapter() {
	
	@Override
	public void mousePressed(MouseEvent e) {
	mapPane.requestFocusInWindow();
	}
	});
	}
	
	public MapContent getMapContent() {
	return mapPane.getMapContent();
	}
	
	public void setMapContent(MapContent content) {
	if (content == null) {
	throw new IllegalArgumentException("map content must not be null");
	}
	
	mapPane.setMapContent(content);
	}

	public JMapPane getMapPane() {
	return mapPane;
	}
	
 }
	
