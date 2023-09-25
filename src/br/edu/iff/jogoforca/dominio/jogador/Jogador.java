package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.dominio.ObjetoDominioImpl;

//ENTITY

public class Jogador extends ObjetoDominioImpl 
{
	
	
	//ATRIBUTOS
	private String nome;
	private int pontuacao = 0;
	
	// CONSTRUTORES
	private Jogador(long id, String nome) 
	{
		super(id);
		this.setNome(nome);
	}
	
	private Jogador(long id, String nome, int pontuacao) 
	{
		super(id);
		this.setNome(nome);
		this.pontuacao = pontuacao;
	}
	
	
	// UTILIZAM OS CONSTRUTORES
	public static Jogador Criar(long id, String nome) 
	{ 
		return new Jogador(id, nome);
	}
	
	public static Jogador Reconstituir(long id, String nome, int pontuacao)
	{
		return new Jogador(id, nome, pontuacao);
	}
	
	
	
	// GETTERS AND SETTERS
	public String getNome()
	{
		return this.nome;
	}
	
	public final void setNome(String  nome)
	{
		this.nome = nome;
	}
	
	public final int getPontuacao()
	{
		return this.pontuacao;
	}
	
	public void setPontuacao(int pontuacao)
	{
		this.pontuacao = pontuacao;
	}
	
}
	
	
	