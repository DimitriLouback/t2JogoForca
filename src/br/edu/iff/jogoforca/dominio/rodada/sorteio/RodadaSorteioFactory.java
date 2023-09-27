package br.edu.iff.jogoforca.dominio.rodada.sorteio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public class RodadaSorteioFactory extends RodadaFactoryImpl 
{

	
	
	//SOLE INSTANCE
    private static RodadaSorteioFactory soleInstance;

    public static void createSoleInstance(RodadaRepository rodadaRepository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        if (soleInstance == null) {
            soleInstance = new RodadaSorteioFactory(rodadaRepository, temaRepository, palavraRepository);
        }
    }
   
    public static RodadaSorteioFactory getSoleInstance() {
        return soleInstance;
    }
   
   
    
    //CONSTRUTOR
    private RodadaSorteioFactory(RodadaRepository rodadaRepository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        super(rodadaRepository, temaRepository, palavraRepository);
    }


    
    
    //MÉTODOS
    @Override
    public Rodada getRodada(Jogador jogador) {
    	
        Random rodadaAleatoria = new Random();
        int quantidadePalavras = rodadaAleatoria.nextInt(3)+1;
        
        List<Tema> temas = getTemaRepository().getTodos();
        int posicao = rodadaAleatoria.nextInt(temas.size());

        Tema tema = temas.get(posicao);
        
        List<Palavra> palavrasDoTema = getPalavraRepository().getPorTema(tema);

        
        List<Palavra> palavras = new ArrayList<>();
        for (int i = 0; i < quantidadePalavras; i++)
        {	
   
        	
        	palavras.add(i, (Palavra) palavrasDoTema.toArray()[rodadaAleatoria.nextInt(temas.size() - 1)]);
        }
        
        
        

        long id = getRodadaRepository().getProximoId();
        return Rodada.Criar(id,palavras.toArray(palavras.toArray(new Palavra[0])), jogador);
    }
    
}
