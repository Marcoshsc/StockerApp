package utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class CustomTextField {
	public static JTextField decimal () {  
		JTextField decimalField = new JTextField();
		decimalField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == '.' && decimalField.getText().length() > 0 && !decimalField.getText().contains("."))
					return;
				if(!(decimalField.getText() + e.getKeyChar()).matches("[0-9]+(\\.[0-9][0-9]?)?"))
					e.consume();
			}
		});
		return decimalField;
	}
	
	public static JTextField integer () {  
		JTextField integerField = new JTextField();
		integerField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {			
				if(!(integerField.getText() + e.getKeyChar()).matches("[0-9]{0,}"))
					e.consume();
			}
		});
		return integerField;
	}
}
