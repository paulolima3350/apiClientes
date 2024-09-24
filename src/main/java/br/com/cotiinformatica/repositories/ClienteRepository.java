package br.com.cotiinformatica.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ClienteRepository {

	//método para gravar um registro de cliente no banco
	public void create(Cliente cliente) throws Exception {

		//abrindo conexão com o banco de dados
		var connection = ConnectionFactory.getConnection();
		
		//escrever o comando SQL (query) para executar uma gravação no banco de dados
		var statement = connection.prepareStatement("INSERT INTO cliente(id, nome, cpf, telefone, email) VALUES(?,?,?,?,?)");
		statement.setString(1, cliente.getId().toString());
		statement.setString(2, cliente.getNome());
		statement.setString(3, cliente.getCpf());
		statement.setString(4, cliente.getTelefone());
		statement.setString(5, cliente.getEmail());
		statement.execute();
		
		//fechando a conexão com o banco de dados
		connection.close();
	}
	
	public void update(Cliente cliente) throws Exception {

		//abrindo conexão com o banco de dados
		var connection = ConnectionFactory.getConnection();
		
		//escrever o comando SQL (query) para executar uma atualização no banco de dados
		var statement = connection.prepareStatement("UPDATE cliente SET nome=?, cpf=?, telefone=?, email=? WHERE id=?");
		statement.setString(1, cliente.getNome());
		statement.setString(2, cliente.getCpf());
		statement.setString(3, cliente.getTelefone());
		statement.setString(4, cliente.getEmail());
		statement.setString(5, cliente.getId().toString());
		statement.execute();
		
		//fechando a conexão com o banco de dados
		connection.close();
	}
		
	public void delete(UUID id) throws Exception {

		//abrindo conexão com o banco de dados
		var connection = ConnectionFactory.getConnection();
		
		//escrever o comando SQL (query) para executar uma exclusão no banco de dados
		var statement = connection.prepareStatement("DELETE FROM cliente WHERE id=?");
		statement.setString(1, id.toString());
		statement.execute();
		
		//fechando a conexão com o banco de dados
		connection.close();
	}
	
	public List<Cliente> getAll() throws Exception {

		//abrindo conexão com o banco de dados
		var connection = ConnectionFactory.getConnection();
		
		//escrever o comando SQL (query) para executar uma consulta no banco de dados
		var statement = connection.prepareStatement("SELECT id, nome, cpf, telefone, email FROM cliente ORDER BY nome");
		var resultSet = statement.executeQuery();
		
		//criando uma variável para armazenar uma lista de clientes
		var lista = new ArrayList<Cliente>();
		
		//percorrer cada registro obtido do banco de dados
		while(resultSet.next()) {
			
			var cliente = new Cliente();			
			cliente.setId(UUID.fromString(resultSet.getString("id")));
			cliente.setNome(resultSet.getString("nome"));
			cliente.setCpf(resultSet.getString("cpf"));
			cliente.setTelefone(resultSet.getString("telefone"));
			cliente.setEmail(resultSet.getString("email"));
			
			lista.add(cliente);
		}
		
		connection.close(); //fechando a conexão
		
		//retornando a lista
		return lista;
	}
	
	public Cliente getById(UUID id) throws Exception {

		var connection = ConnectionFactory.getConnection();
		
		var statement = connection.prepareStatement("SELECT id, nome, cpf, telefone, email FROM cliente WHERE id=?");
		statement.setString(1, id.toString());
		var resultSet = statement.executeQuery();
		
		Cliente cliente = null;
		
		if(resultSet.next()) {
			
			cliente = new Cliente();			
			cliente.setId(UUID.fromString(resultSet.getString("id")));
			cliente.setNome(resultSet.getString("nome"));
			cliente.setCpf(resultSet.getString("cpf"));
			cliente.setTelefone(resultSet.getString("telefone"));
			cliente.setEmail(resultSet.getString("email"));
		}
		
		connection.close(); 
		
		return cliente;
	}
}

