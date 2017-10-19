package org.geotools.interf;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.geotools.compte.Chamber;

public class Interface_Modif_Chambre extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Chamber chambre;
	private ImageIcon imageCHambre;
	private JTextField PrixField ;
	private JPanel imageChambrePanel = new JPanel();
		
	private void init() 
	{
		//image Panel
		final JButton ajouterImage = new JButton("Image Modifier"); 
		imageCHambre = chambre.getImage();
		imageChambrePanel.setLayout(new FlowLayout());
		
		JLabel label = new JLabel(imageCHambre);
		label.setPreferredSize(new Dimension(100, 100));
		
		imageChambrePanel.add(label);
		imageChambrePanel.add(ajouterImage);


		//prix Type Panel 
		JPanel prixPanel = new JPanel();
		prixPanel.setLayout(new FlowLayout());
		JLabel prix= new JLabel("Prix : ");
		PrixField = new JTextField(5);
		PrixField.setText(chambre.getPrix());
		
		prixPanel.add(prix);
		prixPanel.add(PrixField);

		//control panel
		
		JPanel controlPanle = new JPanel();
		JButton retourBouton = new JButton("Retour");
		JButton ValideBouton = new JButton("Valider Changement");
		controlPanle.add(ValideBouton);
		controlPanle.add(retourBouton);
		
		this.add(imageChambrePanel,BorderLayout.CENTER);
		this.add(prixPanel,BorderLayout.NORTH);
		this.add(controlPanle,BorderLayout.SOUTH);
		
		retourBouton.addActionListener(new ActionListener()
		{
	
			public void actionPerformed(ActionEvent arg0) 
			{
				setVisible(false);
			}
		});
		
		
		ValideBouton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0) 
			{
				chambre.setPrix(PrixField.getText());
				chambre.setImage(imageCHambre);
				setVisible(false);
			}
		});
		
		ajouterImage.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0) 
			{
				String urlImage =recypUrlImage();
				imageCHambre = new ImageIcon(urlImage);
				imageChambrePanel.removeAll();
				imageChambrePanel.add(new JLabel(imageCHambre));
				imageChambrePanel.add(ajouterImage);
				imageChambrePanel.updateUI();
			}
		});		
	}

	private String recypUrlImage()
	{
		JFileChooser fileChooser = new JFileChooser(".");
		FileFilter filter1 = new ExtensionFileFilter("*.JPG et *.JPEG", new String[] { "jpg", "jpeg" });
		fileChooser.setFileFilter(filter1);
		int status = fileChooser.showOpenDialog(null);
		String urlFile = null;
		if (status == JFileChooser.APPROVE_OPTION) 
		{
			File selectedFile = fileChooser.getSelectedFile();
			urlFile=selectedFile.getParent()+"\\"+ selectedFile.getName();
		}else if (status == JFileChooser.CANCEL_OPTION){

		}
		return urlFile;
	}

	public Chamber getChambre()
	{
		
		this.setVisible(true);
		return this.chambre;      
	}

	public Interface_Modif_Chambre  (JFrame parent,Chamber chambre) 
	{
		super(parent, "Chambre "+chambre.getType(), true);
		this.chambre=chambre;
		this.setSize(300, 200);	
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.init();		
	}

}