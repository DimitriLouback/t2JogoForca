package br.edu.iff.jogoforca.dominio.rodada;

 

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;

public class RodadaAppService 
{

	
	//ATRIBUTOS
	
	private RodadaRepository rodadaRepository;
	private RodadaFactory rodadaFactory;
	private JogadorRepository jogadorRepository;
	
	
	//SOLE INSTANCE
	private static RodadaAppService soleInstance;
	public static void createSoleInstance(RodadaFactory rodadaFactory, RodadaRepository rodadaRepository, JogadorRepository jogadorRepository){
        soleInstance = new RodadaAppService(rodadaRepository, jogadorRepository, rodadaFactory);
    }

    public static RodadaAppService getSoleInstance(){
        if (soleInstance == null){
            throw new IllegalStateException("RodadaAppService nao pode ser null");
        }
        return soleInstance;
    }
    
    
    //CONSTRUTOR

    private RodadaAppService(RodadaRepository rodadaRepository, JogadorRepository jogadorRepository, RodadaFactory rodadaFactory) {
        this.rodadaRepository = rodadaRepository;
        this.jogadorRepository = jogadorRepository;
        this.rodadaFactory = rodadaFactory;
    }

    
    //MÉTODOS DE SERVIÇO
    public Rodada novaRodada(long idJogador)
    {
        if (jogadorRepository.getPorId(idJogador) == null)
        {
            throw new IllegalArgumentException("Jogador não encontrado");
        }

        return rodadaFactory.getRodada(jogadorRepository.getPorId(idJogador));
    }

    public Rodada novaRodada(String nomeJogador) throws Exception
    {
        if (jogadorRepository.getPorNome(nomeJogador) == null)
        {
            throw new Exception("O " + nomeJogador + " não foi encontrado.");
        }

        return rodadaFactory.getRodada(jogadorRepository.getPorNome(nomeJogador));
    }

    public Rodada novaRodada(Jogador jogador)
    {
        return rodadaFactory.getRodada(jogador);
    }

    public boolean salvarRodada(Rodada rodada)
    {
        try {
            rodadaRepository.inserir(rodada);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}

