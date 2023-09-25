package br.edu.iff.bancodepalavras.dominio.letra.texto;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;

public class LetraTexto extends Letra
{
	//CONSTRUTOR
	public LetraTexto(char codigo)
	{
		super(codigo);
	}
	
	//OVERRRIDE
	@Override
	public void exibir(Object contexto)
	{
		System.out.println(this.getCodigo());
	}

}