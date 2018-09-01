package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ItemValidador;
import br.com.caelum.estoque.modelo.item.ListaItens;
import br.com.caelum.estoque.modelo.usuario.AutorizacaoException;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

@WebService
public class EstoqueComFiltroWS {
	
	private ItemDao dao = new ItemDao();

	 @WebMethod(operationName="TodosOsItens")
	 @WebResult(name="itens")
	    public ListaItens getItens(@WebParam(name="tokenUsuario", header=true)TokenUsuario token, @WebParam(name="filtros") Filtros filtros) throws AutorizacaoException { //cuidado, plural
	        System.out.println("Chamando getItens()");
	        boolean valido = new TokenDao().ehValido(token);
	        if(!valido) {
	        	throw new AutorizacaoException("Autorização falhou"); 
	        }
	        List<Filtro> lista = filtros.getLista();
	        System.out.println("Filtro: " + lista.get(0).getNome());
	        List<Item> itensResultado = dao.todosItens(lista);
	        return new ListaItens(itensResultado);
	    }
	
	 @WebMethod(operationName="CadastrandoItem")
	 @WebResult(name="item")
	 public Item cadastrarItem(
			 @WebParam(name="tokenUsuario", header=true)TokenUsuario token, 
			 @WebParam(name="item")Item item) 
					 throws AutorizacaoException {
		 
		 System.out.println("Cadastrando item " + item + ", Token " + token);
		 
		 boolean valido =new TokenDao().ehValido(token);
		 
		 if(!valido) {
			 throw new AutorizacaoException("Autorização falhou");
		 }
		 new ItemValidador(item).validate();	
		 
		 this.dao.cadastrar(item);
		 return item;
	 }
}
