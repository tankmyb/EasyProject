package com.socket.bio.udp.multicast.v3;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JImagePanel extends JScrollPane{

	JImagePane imagepane = null;

	public JImagePanel() {
	    imagepane = new JImagePane();
	    this.getViewport().add(imagepane);
	}

	public synchronized void setImage(Image image) {
	    imagepane.setImage(image);
	}

	class JImagePane
	      extends JPanel {
	    Image img;
	    Insets insets;
	    Dimension d;

	    public JImagePane() {
	      insets = this.getInsets();
	    }

	    public synchronized void setImage(Image image) {
	      if (img != null) {
	        img.flush();
	        img = null;
	      }
	      this.img = image;
	      if (img != null)
	        setSize(image.getWidth(this), image.getHeight(this));
	      else
	        setSize(0, 0);
	      this.repaint();
	    }


	    public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      if (img != null)
	        g.drawImage(img, insets.left, insets.top, this);
	    }

	    public Dimension getPreferredSize() {
	      if (img != null) {
	        return new Dimension(img.getWidth(this), img.getHeight(this));
	      }
	      else {
	        return new Dimension(320, 160);
	      }
	    }

	    public Dimension getMinimumSize() {
	      if (img != null) {
	        return new Dimension(img.getWidth(this), img.getHeight(this));
	      }
	      else {
	        return new Dimension(320, 160);
	      }
	    }

	    public Dimension getMaximumSize() {
	      if (img != null) {
	        return new Dimension(img.getWidth(this), img.getHeight(this));
	      }
	      else {
	        return new Dimension(320, 160);
	      }
	    }

	    public Dimension getSize() {
	      if (img != null) {
	        return new Dimension(img.getWidth(this), img.getHeight(this));
	      }
	      else {
	        return new Dimension(320, 160);
	      }
	    }

	    private void setSiz() {
	      this.setPreferredSize(d);
	      this.setMinimumSize(d);
	      this.setMaximumSize(d);
	      this.setSize(d);
	    }
	}



}
