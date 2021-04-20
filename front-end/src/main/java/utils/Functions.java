package utils;

import java.sql.Date;
import java.util.Calendar;

import produto.ProductList;
import venda.VendaRelatorio;

public class Functions {
	
	public static void abrirProximaPagina(String proximaPagina) {  
		switch (proximaPagina) {  
		case "LISTA_PRODUTOS": new ProductList().frame.setVisible(true); break;
		case "LISTA_VENDA": new VendaRelatorio().setVisible(true); break;
		default: return;
		}
	}
	

	public static String doubleParaDinheiro(double valor) { 
		String string =  String.format("%.2f", valor);
		string.replace(".", ",");
		return "R$ " + string;
	}

	public static String formatData(String dataHora) { 
		String data =  dataHora.substring(0, 10);
		data = data.substring(8,10) + 
				"/" + data.substring(5,7) + "/" 
				+ data.substring(0,4);
		return data + dataHora.substring(10, 19);
	}
	
	public static Date sqlDateAddMonth(Date date, int number) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, number);
		Date result = new Date(calendar.getTimeInMillis());
	    return result;
	}
}
