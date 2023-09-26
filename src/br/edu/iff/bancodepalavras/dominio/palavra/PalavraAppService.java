package br.edu.iff.bancodepalavras.dominio.palavra;

 

import  br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import  br.edu.iff.repository.RepositoryException;

public class PalavraAppService 
{

	//SOLE INSTANCE
	private static PalavraAppService soleInstance;
	TemaRepository temaRepository;
	PalavraRepository palavraRepository;
	PalavraFactory palavraFactory;

	public static void createSoleInstance(TemaRepository temaRepository, PalavraRepository palavraRepository,PalavraFactory palavraFactory)
	{
		soleInstance = new PalavraAppService(temaRepository, palavraRepository, palavraFactory);
	}

	public static PalavraAppService getSoleInstance()
	{
		if(soleInstance == null)
		{
			throw new IllegalStateException("PalavraAppService nao pode ser null");
		}
		return soleInstance;
	}

	
	//CONSTRUTOR
	private PalavraAppService(TemaRepository temaRepository, PalavraRepository palavraRepository, PalavraFactory palavraFactory)
	{
		this.temaRepository = temaRepository;
		this.palavraRepository = palavraRepository;
		this.palavraFactory = palavraFactory;
	}

	
	//MÉTODOS
	public boolean novaPalavra(String palavra, long idTema)
	{
		if(temaRepository.getPorId(idTema) == null)
		{
			throw new IllegalArgumentException("Tema nao encontrado");
		}
		if(palavraRepository.getPalavra(palavra) != null) 
		{
			return true;
		}
		try {
			palavraRepository.inserir(palavraFactory.getPalavra(palavra, temaRepository.getPorId(idTema)));
			return true;
		} catch (RepositoryException e)
		{
			return false;
		}
	}
}