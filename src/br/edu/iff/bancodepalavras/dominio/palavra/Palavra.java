package br.edu.iff.bancodepalavras.dominio.palavra;
//importações

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//ENTITY

public class Palavra extends ObjetoDominioImpl
{
	
	//ATRIBUTOS
	private Letra[] letras;
	private Letra letraEncoberta;
	public static LetraFactoryImpl factory;
	private Tema tema;
	
	

	//CONSTRUTOR
	private Palavra(long id, String palavra, Tema tema) {
        super(id);
        if (factory != null){
            this.tema = tema;
            this.letraEncoberta = factory.getLetraEncoberta();

            letras = new Letra[palavra.length()];
            for (int i = 0; i < palavra.length(); i++) {
                letras[i] = factory.getLetra(palavra.charAt(i)) ;
            }

        }else{
            throw new IllegalStateException("LetraFactory precisa ser setado para instanciar Palavra");
        }
    }
	
	
	//USAM OS CONSTRUTORES
	public static Palavra criar(long id, String palavra, Tema tema)
	{
		return new Palavra(id, palavra, tema);
		
	}
	
	public Palavra reconstituir(long id, String palavra, Tema tema)
	{
		return new Palavra(id, palavra, tema);
	}
	
	
	// FACTORY
	public void setLetraFactory(LetraFactoryImpl factory)
	{
		this.factory = factory;
	}
	
	public static LetraFactoryImpl getLetraFactory() 
	{
		return Palavra.factory;
	}
	
	
	//MÉTODOS
	public Letra[] getLetras()
	{
		return this.letras;
	}
	
	public Letra getLetra(int posicao)
	{
		return this.letras[posicao];
	}
	
	public void exibir(Object contexto)
	{
		// PARA CADA LETRA DENTRO DO VETOR LETRAS, EXIBIR A LETRA DE ACORDO C O CONTEXTO
		for(Letra letra : letras)
		{
			letra.exibir(contexto);
		}
	}
	

    public void exibir(Object contexto, boolean[] posicoes){
        for (int i = 0; i < posicoes.length; i++) {
            if(posicoes[i]){
                letras[i].exibir(contexto);
            }else{
                letraEncoberta.exibir(contexto);
            }
        }
    }
    
    
    public int[] tentar(char codigo){
        List<Integer> listaPosicoes = new ArrayList<>();

      
        for (int i = 0; i < this.letras.length; i++) {
            if (this.letras[i].getCodigo() == codigo){
                listaPosicoes.add(i);
            	}
        	}
        	return  listaPosicoes.stream().mapToInt(i->i).toArray();    
    }
    
    
    public Tema getTema()
    {
    	return this.tema;
    }
    
    
    
    public boolean comparar(String palavra)
	{
		if (palavra == this.toString())
			return true;
		else return false;
	}
    
    
    public int getTamanho()
    {
        return letras.length;
    }
    
    
    
    public String toString()
	{
		String palavraToString = "";
		int i = 0;
		for (Letra letra : this.letras)
		{	 
			palavraToString = palavraToString + this.letras[i];
			i++;
		}
		
		return palavraToString;
	}
	

	
}