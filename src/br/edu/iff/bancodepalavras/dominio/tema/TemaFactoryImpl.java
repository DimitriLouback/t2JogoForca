package br.edu.iff.bancodepalavras.dominio.tema;

import br.edu.iff.bancodepalavras.dominio.tema.emmemoria.MemoriaTemaRepository;
import br.edu.iff.factory.EntityFactory;
import br.edu.iff.repository.RepositoryException;

public class TemaFactoryImpl extends EntityFactory implements TemaFactory 
{
	
	
	//SOLEINSTANCE
    
    private static TemaFactoryImpl soleInstance;

    public static void createSoleInstance(TemaRepository temaRepository) 
    {
        if (soleInstance == null) 
        {
            soleInstance = new TemaFactoryImpl(temaRepository);
        }
    }

     public static TemaFactoryImpl getSoleInstance() 
     {
        if(soleInstance == null) 
        {
            throw new RuntimeException("A fabricacao de repositorio de temas não foi iniciada");
        }
        else 
        {
            return soleInstance;
        }

    }

     
     //CONSTRUTOR
    private TemaFactoryImpl(TemaRepository temaRepository)
    {
        super(temaRepository);
    }


    
    //MÉTODOS
    private TemaRepository getTemaRepository() 
    {
        return (TemaRepository) getRepository();
    }

    @Override
    public Tema getTema(String nome)
    {
        
        return Tema.Criar(MemoriaTemaRepository.getSoleInstance().getProximoId(), nome);
    }

}
