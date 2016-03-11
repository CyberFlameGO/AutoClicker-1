package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class GuiUtil {

	public static void center(JFrame frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		frame.setLocation(x, y);
	}
	
	/**
	 * Sets native look and feel.
	 */
	public static void setNativeLAndF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//do nothing. It will default to normal
		}
	}
	
	/**
	 * Recursively enables components in a component 
	 * @param c
	 */
	public static void setEnabledRecurse(Component component, boolean enabled) {
		if (component instanceof Container) {
			Container container = (Container) component;
			for (Component c : container.getComponents()) setEnabledRecurse(c, enabled);
		}
		component.setEnabled(enabled);
	}
	
	/**
	 * Adds a ChangeListener to a JTextComponent, which receives a single event when the text 
	 * changes in the field.
	 * 
	 * Courtesy of http://stackoverflow.com/a/27190162.
	 * 
	 * @param text
	 * @param changeListener
	 */
	public static void addChangeListener(final JTextComponent text, final ChangeListener changeListener) {
	    Objects.requireNonNull(text);
	    Objects.requireNonNull(changeListener);
	    
	    final DocumentListener dl = new DocumentListener() {
	        private int lastChange = 0, lastNotifiedChange = 0;

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	            changedUpdate(e);
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	            changedUpdate(e);
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	            lastChange++;
	            SwingUtilities.invokeLater(new Runnable() {
	            	public void run() {
	            		
	            	
			            if (lastNotifiedChange != lastChange) {
			                lastNotifiedChange = lastChange;
			                changeListener.stateChanged(new ChangeEvent(text));
			            }
	            	}
	            });
	        }
	    };
	    
	    text.addPropertyChangeListener("document", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
		        Document d1 = (Document)e.getOldValue();
		        Document d2 = (Document)e.getNewValue();
		        if (d1 != null) d1.removeDocumentListener(dl);
		        if (d2 != null) d2.addDocumentListener(dl);
		        dl.changedUpdate(null);
			}
	    });
	    
	    Document d = text.getDocument();
	    if (d != null) d.addDocumentListener(dl);
	}
}
