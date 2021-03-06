package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;


@WebService
public class EstoqueWS {

	private ItemDao dao = new ItemDao();
	
	@WebMethod(operationName="todosOsItens")
	@RequestWrapper(localName="listaItens")
	@ResponseWrapper(localName="itens")
	@WebResult(name="item")
	public List<Item> getItens(){
		
		System.out.println("Chamando getItens");
		return dao.todosItens();
	}
	
}
