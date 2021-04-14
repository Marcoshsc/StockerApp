package utils;

import view.ProductList;

public class Functions {
	
	public static void abrirProximaPagina(String proximaPagina) {  
		switch (proximaPagina) {  
		case "LISTA_PRODUTOS": new ProductList().frame.setVisible(true);
		default: return;
		}
	}

}
