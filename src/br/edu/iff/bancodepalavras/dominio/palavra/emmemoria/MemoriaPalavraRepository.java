package br.edu.iff.bancodepalavras.dominio.palavra.emmemoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.repository.RepositoryException;

public class MemoriaPalavraRepository implements PalavraRepository
{

	
	//SOLE INSTANCE 
	private static MemoriaPalavraRepository soleInstance = null;
	
	public static  MemoriaPalavraRepository getSoleInstance()
	{
		if(MemoriaPalavraRepository.soleInstance == null)
		{
			 MemoriaPalavraRepository.soleInstance = new MemoriaPalavraRepository();
		}
		return MemoriaPalavraRepository.soleInstance;
	}
	
	
	
	
	
	//ATRIBUTOS
	private List<Palavra> pool;
	
	
	
	
	//CONSTRUTOR
	private MemoriaPalavraRepository()
	{
		pool = new ArrayList<>();
	}
	
	
	//MÉTODOS

	@Override
	public long getProximoId() {
		return pool.size() + 1;
	}

	@Override
	public Palavra getPorId(long id) 
	{
		if(this.pool.size() > 0)
		{
			for(Palavra palavraBuscada : pool)
			{
				if(palavraBuscada.getId() == id)
				{
					return palavraBuscada;
				}
			}
		}
		throw new RuntimeException("Palavra nao encontrada no ID: " + id);
	}

	@Override
	public List<Palavra> getPorTema(Tema tema) {
		List <Palavra> palavrasEncontradasNoTema = new ArrayList<>();
		
		for(Palavra palavrasBuscadas: pool)
		{
			if(palavrasBuscadas.getTema() == tema)
			{
				palavrasEncontradasNoTema.add(palavrasBuscadas);
			}
		}
		return palavrasEncontradasNoTema;
	}

	@Override
	public List<Palavra> getTodas() {
		return Collections.unmodifiableList(pool);
	}

	@Override
	public Palavra getPalavra(String palavra) {
		for(Palavra palavraBuscada: pool)
		{
			if(palavraBuscada.comparar(palavra))
			{
				return palavraBuscada;
			}
		}
		return null;
	}

	@Override
	public void inserir(Palavra palavra) throws RepositoryException {
		if(pool.contains(palavra))
		{
			throw new RepositoryException("A palavra ja se encontra no repositorio");
		} else {
			pool.add(palavra);
		}
	}

	@Override
	public void atualizar(Palavra palavra) throws RepositoryException {
		long id = palavra.getId();
		
		for(int i = 0; i < pool.size(); i++)
		{
			Palavra palavraExistente = pool.get(i);
			if(palavraExistente.getId() == id)
			{
				pool.set(i, palavra);
				return;
			}
		}
		throw new RepositoryException("Palavra nao encontrada para atualizacao com ID: " + id);
	}

	@Override
	public void remover(Palavra palavra) throws RepositoryException {
		if(pool.contains(palavra))
		{
			pool.remove(palavra);
		} else {
			throw new RepositoryException("Palavra nao localizada no repositorio");
		}
	}
}