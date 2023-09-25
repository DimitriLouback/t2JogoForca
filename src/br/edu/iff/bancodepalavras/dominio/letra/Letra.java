package br.edu.iff.bancodepalavras.dominio.letra;

import java.util.Objects;

//VALUE OBJECT
//FLYWEIGHT

public abstract class Letra 
{
	
	//ATRIBUTOS
	private char codigo;
	
	//CONSTRUTOR
	protected Letra(char codigo)
	{
		this.codigo = codigo;
	}
	
	
	//METODOS

	public char getCodigo()
	{
		return this.codigo;
	}
	
	public abstract void exibir(Object contexto);
	
	
	public final String toString()
	{
		return String.valueOf(codigo);
	}
	
	
	
	// OVERRIDE DO HASH CODE E EQUALS
	
	@Override
	public final boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Letra letra = (Letra) o;
		return codigo == letra.codigo;
	}
	
	@Override
	public final int hashCode()
	{
		return Objects.hash(codigo);
	}
	
	
	
	
}