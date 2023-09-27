package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.factory.EntityFactory;
import br.edu.iff.repository.RepositoryException;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory {

    private static JogadorFactoryImpl soleInstance;

    public static void createSoleInstance(JogadorRepository jogadorRepository) {
        if (soleInstance == null) {
            soleInstance = new JogadorFactoryImpl(jogadorRepository);
        }
    }

    public static JogadorFactoryImpl getSoleInstance() {
        if(soleInstance == null) {
            throw new RuntimeException(" ");
        }
        return soleInstance;
    }
    
    private JogadorFactoryImpl(JogadorRepository jogadorRepository) {
        super(jogadorRepository);
    }

    private JogadorRepository getJogadorRepository() {
        return (JogadorRepository) getRepository();
    }

    @Override
    public Jogador getJogador(String nome) {
        Jogador jogador = Jogador.Criar(getProximoId(), nome);
        

        

        return jogador;
    }

}
