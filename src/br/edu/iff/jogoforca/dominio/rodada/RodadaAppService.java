package br.edu.iff.jogoforca.dominio.rodada;

 

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;

public class RodadaAppService {

	private static RodadaAppService soleInstance;
	private RodadaRepository rodadaRepository;
	private RodadaFactory rodadaFactory;
	private JogadorRepository jogadorRepository;
	
	
	public static void createSoleInstance(RodadaRepository rodadaRepository, JogadorRepository jogadorRepository, RodadaFactory rodadaFactory){
        soleInstance = new RodadaAppService(rodadaRepository, jogadorRepository, rodadaFactory);
    }

    public static RodadaAppService getSoleInstance(){
        if (soleInstance == null){
            throw new IllegalStateException("RodadaAppService nao pode ser null");
        }
        return soleInstance;
    }

    private RodadaAppService(RodadaRepository rodadaRepository, JogadorRepository jogadorRepository, RodadaFactory rodadaFactory) {
        this.rodadaRepository = rodadaRepository;
        this.jogadorRepository = jogadorRepository;
        this.rodadaFactory = rodadaFactory;
    }

    public Rodada novaRodada(long idJogador){
        if (jogadorRepository.getPorId(idJogador) == null){
            throw new IllegalArgumentException("Jogador não encontrado");
        }

        return rodadaFactory.getRodada(jogadorRepository.getPorId(idJogador));
    }

    public Rodada novaRodada(String nomeJogador) throws Exception {
        if (jogadorRepository.getPorNome(nomeJogador) == null){
            throw new Exception("O " + nomeJogador + " não foi encontrado.");
        }

        return rodadaFactory.getRodada(jogadorRepository.getPorNome(nomeJogador));
    }

    public Rodada novaRodada(Jogador jogador){
        return rodadaFactory.getRodada(jogador);
    }

    public boolean salvarRodada(Rodada rodada){
        try {
            rodadaRepository.inserir(rodada);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}

