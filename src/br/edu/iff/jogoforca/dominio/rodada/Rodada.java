package br.edu.iff.jogoforca.dominio.rodada;

 


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;


public class Rodada extends ObjetoDominioImpl
{
	
	//ATRIBUTOS
	private static int maxPalavras = 3;
	private static int maxErros = 10;
	private static int pontosQuandoDescobreTodasAsPalavras = 100;
	private static int pontosPorLetraEncoberta = 15;
	
	
	public static BonecoFactory bonecoFactory;
	private Item[] itens;
	private Letra[] letrasErradas;
	private Jogador jogador;
    private Boneco boneco;
	
	//CONSTRUTORES
	  private Rodada(long id, Palavra[] palavras, Jogador jogador) {
	        super(id);
	     
	        if(bonecoFactory != null) {
	            Item[] auxitens = new Item[palavras.length];

	            
	            // CRIANDO UM ITEM PARA CADA PALAVRA, CHECANDO SE SÃO TODAS DO MESMO TEMA E SETTANDO NO VETOR auxitens
	            for (int i = 0; i < palavras.length; i++) {
	                if(palavras[i].getTema().equals(palavras[0].getTema())){
	                    auxitens[i] = Item.criar(new Random().nextLong(), palavras[i]);
	                }else{
	                    throw new IllegalArgumentException("Todos os temas de palavras precisam ser iguais");
	                }
	            }

	            this.itens = auxitens;
	            this.jogador = jogador;
	            this.boneco = bonecoFactory.getBoneco();
	            this.letrasErradas = new Letra[Rodada.maxErros];
	        }
	        else{
	            throw new IllegalStateException("BonecoFactory precisa ser setado para instanciar Rodada");
	        }
	    }

	    private Rodada(long id, Jogador jogador, Boneco boneco, Item[] itens, Letra[] letrasErradas) {
	        super(id);
	        if(bonecoFactory != null) {
	            this.jogador = jogador;
	            this.boneco = boneco;
	            this.itens = itens;
	            this.letrasErradas = letrasErradas;
	        }else{
	            throw new IllegalStateException("BonecoFactory precisa ser setado para instanciar Rodada");
	        }
	    }
	
	//USAM OS CONSTRUTORES	
	public static Rodada Criar(long id, Palavra[] palavras, Jogador jogador)
	{
		return new Rodada(id, palavras, jogador);
	}
	
	public static Rodada Reconstituir(long id, Item[] itens, Letra[] letrasErradas, Jogador jogador, Boneco boneco)
	{
		return new Rodada(id, jogador, boneco, itens, letrasErradas);
	}
	
	
	//MÉTODOS
	public static int getMaxPalavras()
	{
		return maxPalavras;
	}
	
	public static void setMaxPalavras(int max)
	{
		Rodada.maxPalavras = max;
	}
	
	public static int getMaxErros()
	{
		return maxErros;
	}
	
	public static void setMaxErros(int max)
	{
		Rodada.maxErros = max;
	}
	
	public static int getPontosQuandoDescobreTodasAsPalavras()
	{
		return pontosQuandoDescobreTodasAsPalavras;
	}
	
	public static void setPontosQuandoDescobreTodasAsPalavras(int pontos)
	{
		Rodada.pontosQuandoDescobreTodasAsPalavras = pontos;
	}
	
	public static int getPontosPorLetraEncoberta()
	{
		return pontosPorLetraEncoberta;
	}
	
	public static void setPontosPorLetraEncoberta(int pontos)
	{
		Rodada.pontosPorLetraEncoberta = pontos;
	}
	
	public static void setBonecoFactory(BonecoFactory bonecoFactory)
	{
		Rodada.bonecoFactory = bonecoFactory;
	}
	
	public static BonecoFactory getBonecoFactory()
	{
		return bonecoFactory;
	}
	
	public Jogador getJogador()
	{
		return this.jogador;
	}
	
	public Tema getTema()
	{
		// como o tema é da palavra, devemos puxar o array de itens, pegar a palavra da posição que 
		// temos certeza que estará preenchida, e puxar o getPalavra.getTema
		return itens[0].getPalavra().getTema();
	}
	
	public Palavra[] getPalavras()
	{
		Palavra[] palavras = new Palavra[itens.length];
		for (int i = 0; i < this.itens.length; i++)
		{
			palavras[i] = itens[i].getPalavra();
		}
		return palavras;
	}
	
	
	
	public int getNumPalavras(){
        return itens.length;
    }

    public void tentar(char codigo)
    {
        if(!this.encerrou())
        {
            boolean acertou = false;
            for (Item item : itens) 
            {
                if (item.tentar(codigo)) acertou = true;
            }

            if (!acertou)
            {
                for (int i = 0; i < letrasErradas.length; i++)
                {
                    if (letrasErradas[i] == null)
                    {
                        letrasErradas[i] = Palavra.getLetraFactory().getLetra(codigo);
                        break;
                    }
                }
            }
        }
    }

    public void arriscar(String[] palavras)
    {
        if(!this.encerrou())
        {
            for (int i = 0; i < itens.length; i++) 
            {
                itens[i].arriscar(palavras[i]);
            }
        }
    }

    public void exibirItens(Object contexto){
        for (Item item : itens) {
            item.exibir(contexto);
            System.out.println();
        }
    }

    public void exibirBoneco(Object contexto)
    {
        boneco.exibir(contexto, this.getQtdeErros());
    }

    public void exibirLetrasErradas(Object contexto)
    {
        for (Letra letra : letrasErradas) 
        {
            if (letra != null) letra.exibir(contexto);
        }
    }

    public Letra[] getErradas()
    {
        List<Letra> letrasErradasLista = Arrays.stream(letrasErradas).filter(Objects::nonNull).collect(Collectors.toList());
        return letrasErradasLista.toArray(new Letra[letrasErradasLista.size()]);
    }

    public Letra[] getCertas()
    {
        Set<Letra> certas = new HashSet<>();

        for (Item item : itens) {
            for (Letra letraDescoberta : item.getLetrasDescobertas()) 
            {
                certas.add(letraDescoberta);
            }
        }

        return certas.toArray(new Letra[certas.size()]);
    }

    public Letra[] getTentativas()
    {
        List<Letra> tentativas = new ArrayList<>();

        tentativas.addAll(Arrays.stream(this.getErradas()).filter(Objects::nonNull).collect(Collectors.toList()));
        tentativas.addAll(Arrays.stream(this.getCertas()).filter(Objects::nonNull).collect(Collectors.toList()));

        return tentativas.toArray(new Letra[tentativas.size()]);
    }

    public int calcularPontos()
    {
        int pontos = 0;

        if (this.descobriu())
        {
            pontos = 100;
            for (Item item : itens)
            {
            	
            	pontos += item.getLetrasEncobertas().length * 15;
            	
            }
            

        }
        

        
        return pontos;
    }

    public boolean encerrou()
    {
        if(this.arriscou() || this.descobriu() || this.getErradas().length >= maxErros)
        {
            return true;
        }else
        {
            return false;
        }
    }

    public boolean descobriu()
    {
        boolean descobriu = true;

        for (Item item : itens) 
        {
            if (!item.descobriu())
            {
                descobriu = false;
            }
        }

        return descobriu;
    }

    public boolean arriscou()
    {
        for (Item item : itens)
        {
            if (item.arriscou())
            {
                return true;
            }
        }
        return false;
    }

    public int getQtdeTentativasRestantes()
    {
        return maxErros - getQtdeErros();
    }

    public int getQtdeErros()
    {
        return this.getErradas().length;
    }

    public int getQtdeAcertos() 
    {
        return this.getCertas().length;
    }

    public int getQtdeTentativas() 
    {
        return this.getTentativas().length;
    }
}

	
	
	
	
	

