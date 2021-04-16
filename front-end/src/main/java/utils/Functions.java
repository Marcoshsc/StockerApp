package utils;

import produto.ProductList;

public class Functions {
	
	public static void abrirProximaPagina(String proximaPagina) {  
		switch (proximaPagina) {  
		case "LISTA_PRODUTOS": new ProductList().frame.setVisible(true);
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
		System.out.println(data.substring(8,10));
		System.out.println(data.substring(5,7));
		System.out.println(data.substring(0,4));
		data = data.substring(8,10) + 
				"/" + data.substring(5,7) + "/" 
				+ data.substring(0,4);
		return data + dataHora.substring(10, 19);
	}
}
