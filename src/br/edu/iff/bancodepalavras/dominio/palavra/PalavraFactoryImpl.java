package br.edu.iff.bancodepalavras.dominio.palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.factory.EntityFactory;
import br.edu.iff.repository.RepositoryException;

public class PalavraFactoryImpl extends EntityFactory implements PalavraFactory {

    private static PalavraFactoryImpl soleInstance = null;

    public static void createSoleInstance(PalavraRepository palavraRepository) {
        if (soleInstance == null) {
            soleInstance = new PalavraFactoryImpl(palavraRepository);
        }
    }
    public static PalavraFactoryImpl getSoleInstance() {
        if(soleInstance == null) {
            throw new RuntimeException("");
        }
        else {
            return soleInstance;
        }
    }

    private PalavraFactoryImpl(PalavraRepository repository) {
        super(repository);
    }

    private PalavraRepository getPalavraRepository() {
        return (PalavraRepository) getRepository();
    }

    @Override
    public Palavra getPalavra(String palavra, Tema tema) {
        return Palavra.criar(getProximoId(), palavra, tema);
    }

}
    