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

}
