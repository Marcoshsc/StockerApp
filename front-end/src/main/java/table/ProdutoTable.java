//package table;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.table.AbstractTableModel;
//
//import br.ufop.stocker.model.Produto;
//import br.ufop.stocker.repository.exception.RepositoryActionException;
//import br.ufop.stocker.repository.factory.RepositoryFactory;
//
//public class ProdutoTable extends AbstractTableModel{
//	
//	
//	
//
//	public ProdutoTable() {  
//		
//	}
//	
//	  @Override
//	    public int getRowCount() {
//	        return listProduto.size();
//	    }
//
//	    @Override
//	    public int getColumnCount() {
//	        return colunas.length;
//	    }
//
//	    @Override
//	    public String getColumnName(int columnIndex) {
//	        return colunas[columnIndex];
//	    }
//
//	 
//
//	    @Override
//	    public Object getValueAt(int rowIndex, int columnIndex) {
//	        Produto p = getProduto(rowIndex);
//
//	        if(p != null) {
//	            switch (columnIndex) {
//	                case 0:
//	                    return p.getNome();
//	                case 1:
//	                    return p.getPreco();
//	                case 2:
//	                    return p.getEstoque();
//	                case 3:
//	                    return p.getDescricao();
//	            }
//	        }
//	        return "";
//	    }
//	    
//	    public Produto getProduto(int rowIndex) {
//	        if (getRowCount() > rowIndex && rowIndex >= 0) {
//	            return listProduto.get(rowIndex);
//	        }
//	        return null;
//	    }
//}
