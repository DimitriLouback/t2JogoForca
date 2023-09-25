package br.edu.iff.jogoforca.dominio.rodada.emmemoria;

 

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.repository.RepositoryException;

public class MemoriaRodadaRepository implements RodadaRepository
{
	
	
	//SOLE INSTANCE
	private static MemoriaRodadaRepository soleInstance = null;
  
    public static MemoriaRodadaRepository getSoleInstance() 
    {
        if(MemoriaRodadaRepository.soleInstance == null) 
        {
            MemoriaRodadaRepository.soleInstance = new MemoriaRodadaRepository();
        }
        return MemoriaRodadaRepository.soleInstance;
    }

    
    //ATRIBUTOS
    private List<Rodada> pool;

    
    //CONSTRUTOR
    private MemoriaRodadaRepository() 
    {
        pool = new ArrayList<>();
    }

    
    //MÉTODOS
    public long getProximoId()
    {
        return pool.size() + 1;
    }

    public Rodada getPorId(Long id)
    {
        for(Rodada rodadaBuscada: pool)
        {
            if(rodadaBuscada.getId() == (id))
            {
                return rodadaBuscada;
            }
        }
        throw new RuntimeException("Rodada nao encontrada no ID: " + id);
    }

    public List<Rodada> getPorJogador(Jogador jogador)
    {
        List<Rodada> rodadasDoJogador = new ArrayList<Rodada>();

        for(Rodada rodadasBuscadaDoJogador: pool) {
            if(rodadasBuscadaDoJogador.getJogador() == jogador) 
            {
                rodadasDoJogador.add(rodadasBuscadaDoJogador);
            }
        }
        return rodadasDoJogador;
    }

    public void inserir(Rodada rodada) throws RepositoryException
    {
        if(pool.contains(rodada))
        {
            throw new RepositoryException("Rodada ja existe");
        }
        else 
        {
            pool.add(rodada);
        }
    }

    public void atualizar(Rodada rodada) throws RepositoryException
    {
    	long id = rodada.getId();

		for(int i = 0; i < pool.size(); i++)
		{
			Rodada rodadaExistente = pool.get(i);
			if(rodadaExistente.getId() == id)
			{
				pool.set(i, rodada);
				return;
			}
		}
		throw new RepositoryException("Rodada nao encontrada para atualizacao com ID: " + id);
    }

    public void remover(Rodada rodada) throws RepositoryException
    {
        if(pool.contains(rodada)) {
            pool.remove(rodada);
        } else {
            throw new RepositoryException("rodada não encontrada, rodada: " + rodada);
        }
    }

}