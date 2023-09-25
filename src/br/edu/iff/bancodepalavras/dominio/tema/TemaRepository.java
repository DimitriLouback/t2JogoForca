package br.edu.iff.bancodepalavras.dominio.tema;

import java.util.List;

import br.edu.iff.repository.Repository;
import br.edu.iff.repository.RepositoryException;

public interface TemaRepository extends Repository
{
	//ATRIBUTOS
	public Tema getPorId(long id);
	public List<Tema> getPorNome(String nome);
	public List<Tema> getTodos();

	
	//MÉTODOS
	public void inserir(Tema tema) throws RepositoryException;
	public void atualizar(Tema tema) throws RepositoryException;
	public void remover(Tema tema) throws RepositoryException;
} 