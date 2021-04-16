package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;


public class DateLabelFormatter extends AbstractFormatter {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String datePattern = "dd-MM-yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}
