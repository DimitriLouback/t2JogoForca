package br.edu.iff.bancodepalavras.dominio.tema;

import br.edu.iff.dominio.ObjetoDominioImpl;

//ENTITY

public class Tema extends ObjetoDominioImpl 
{
	//ATRIBUTOS
	private String nome;
	
	//CONSTRUTORES
	private Tema(long id, String nome)
	{
		super(id);
		this.setNome(nome);
	}
	
	//USAM OS CONSTRUTORES
	public static Tema Criar(long id, String nome)
	{
		return new Tema(id, nome);
	}
	
	public static Tema Reconstituir(long id, String nome)
	{
		return new Tema(id, nome);
	}
	
	
	//MÉTODOS
	public String getNome()
	{
		return this.nome;
	}
	
	public final void setNome(String nome)
	{
		this.nome = nome;
	}

	
}