package br.edu.iff.jogoforca.dominio.jogador.emmemoria;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;

public class MemoriaJogadorRepository implements JogadorRepository
{

	
	
	//SOLE INSTANCE
	private static MemoriaJogadorRepository soleInstance = null;

    public static MemoriaJogadorRepository getSoleInstance() 
    {
        if(MemoriaJogadorRepository.soleInstance == null)
        {
            MemoriaJogadorRepository.soleInstance = new MemoriaJogadorRepository();
        }
        return MemoriaJogadorRepository.soleInstance;
    }
    
    //ATRIBUTOS

    private List<Jogador> pool;
    
    //CONSTRUTOR
    private MemoriaJogadorRepository() 
    {
    	pool = new ArrayList<>(); 
    }

    
    //MÉTODOS

   
    @Override
    public long getProximoId() 
    {
        return pool.size() + 1;
    }


    @Override
    public Jogador getPorId(Long id)
    {
        for(Jogador jogadorBuscado: pool) 
        {
            if(jogadorBuscado.getId() == (id))
            {
                return jogadorBuscado;
            }
        }
        throw new RuntimeException("Jogador não encontrado pelo id: " + id);
    }


    @Override
    public Jogador getPorNome(String nome)
    {
        for(Jogador jogadorBuscado: pool) 
        {
            if(jogadorBuscado.getNome().equals(nome)) 
            {
                return jogadorBuscado;
            }
        }
        throw new RuntimeException("Jogador não encontrado pelo nome: " + nome);
    }


    @Override
    public void inserir( Jogador jogador) throws RepositoryException 
    {
        if(pool.contains(jogador))  { 
            throw new RepositoryException("Jogador já existe");
        }
        else {
            pool.add(jogador);
        }
    }

    @Override
    public void atualizar(Jogador jogador) throws RepositoryException
    {
    	long id = jogador.getId();

		for(int i = 0; i < pool.size(); i++)
		{
			Jogador jogadorExistente = pool.get(i);
			if(jogadorExistente.getId() == id)
			{
				pool.set(i, jogador);
				return;
			}
		}
		throw new RepositoryException("Tema nao encontrado para atualizacao com ID: " + id);
    }

    @Override
    public void remover(Jogador jogador) throws RepositoryException 
    {
        if(pool.contains(jogador)) {
            pool.remove(jogador);
        }
        else {
            throw new RepositoryException("Jogador " + jogador + "nao encontrado");
        }
    }
}