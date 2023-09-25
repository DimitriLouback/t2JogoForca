package br.edu.iff.dominio;

// entity // layer supertype

public abstract class ObjetoDominioImpl implements ObjetoDominio
{
	
	
	//ATRIBUTOS
	long id;
	

	
	// CONSTRUTOR
	public ObjetoDominioImpl(long id) 
	{
		this.id = id;
	}
	
	
	public long getId()
	{
		return this.id;
	}

}