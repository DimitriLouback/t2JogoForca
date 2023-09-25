package br.edu.iff.jogoforca.dominio.rodada;

import java.util.ArrayList;
import java.util.List;

import  br.edu.iff.bancodepalavras.dominio.letra.Letra;
import  br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import  br.edu.iff.dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl
{
	//ATRIBUTOS
	public Palavra palavra;
	private boolean[] posicoesDescobertas;
	private String palavraArriscada = null;
	
	//CONSTRUTORES
	private Item(long id, Palavra palavra)
	{
		super(id);
		this.palavra = palavra;
		this.posicoesDescobertas = new boolean[palavra.getTamanho()];
	}
	
	private Item(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada)
	{
		super(id);
		this.palavra = palavra;
		this.palavraArriscada = palavraArriscada;
		for (int i = 0; i < posicoesDescobertas.length; i++ )
		{
			this.posicoesDescobertas[posicoesDescobertas[i]] = true;
		}
	}

	//USAM OS CONSTRUTORES
	static Item criar(long id, Palavra palavra)
	{
		return new Item(id, palavra);
	}
	
	public static Item reconstituir(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada)
	{
		return new Item(id, palavra, posicoesDescobertas, palavraArriscada);
	}
	
	
	//MÉTODOS
	public Palavra getPalavra()
	{
		return this.palavra;
	}
	
	 public Letra[] getLetrasDescobertas()
	 {
	       List<Letra> listaLetras = new ArrayList<>();

	        for (int i = 0; i < posicoesDescobertas.length; i++) {
	            if(posicoesDescobertas[i]){
	                listaLetras.add(palavra.getLetra(i));
	            }
	        }
	        return   (Letra[]) listaLetras.toArray();

	 }
	 
	 

	 public Letra[] getLetrasEncobertas()
	 {
		 List<Letra> listaLetras = new ArrayList<>();
		 
		 for (int i = 0; i < posicoesDescobertas.length; i++) {
			 if(!posicoesDescobertas[i]){
				 listaLetras.add(palavra.getLetra(i));
	            }
	        }

	        return listaLetras.toArray(new Letra[listaLetras.size()]);
	    }
	 
	 public int quantidadeLetrasEncobertas()
	 {
		 int qtd = 0;
		 for (int i = 0; i < posicoesDescobertas.length; i++) {
			 if(!posicoesDescobertas[i]){
				 qtd++;
	            }
	        }
		 return qtd;
	 }
	 
	 
	 public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta)
	 {
	      return this.quantidadeLetrasEncobertas() * valorPorLetraEncoberta;
	 }
	 
	 
	 public boolean descobriu() {
	     return acertou() || quantidadeLetrasEncobertas() == 0;
	 }

	public void exibir(Object contexto)
	 {
		 palavra.exibir(contexto, posicoesDescobertas);
	 }
	 
    boolean tentar(char codigo){
    	
    	// se ele chamar a funcao de tentar de palavra e ela n retornar nenhuma posicao acertada, retorna false
        if (palavra.tentar(codigo).length == 0) return false;
        
        //caso ele encontre posicoes acertadas, ele vai rodar esse 
        // vetor de posicoes setando a posicao acertada como true no vetor posicoesDescobertas;
        for (int posicao : palavra.tentar(codigo)) {
            posicoesDescobertas[posicao] = true	;
        }
        return true;
    }
    
    void arriscar(String palavra)
    {
      	this.palavraArriscada = palavra;
    }
	 

    public String getPalavraArriscada() {
        return palavraArriscada;
    }

    public boolean arriscou(){
        return palavraArriscada != null;
    }

    public boolean acertou(){
        return palavraArriscada.equals(palavra.toString());
    }
}
