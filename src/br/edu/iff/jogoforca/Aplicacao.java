package br.edu.iff.jogoforca;

import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactoryImpl;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactory;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.jogoforca.dominio.rodada.sorteio.RodadaSorteioFactory;
import br.edu.iff.jogoforca.embdr.BDRRepositoryFactory;
import br.edu.iff.jogoforca.emmemoria.MemoriaRepositoryFactory;
import br.edu.iff.jogoforca.imagem.ElementoGraficoImagemFactory;
import br.edu.iff.jogoforca.texto.ElementoGraficoTextoFactory;
import br.edu.iff.repository.Repository;

public class Aplicacao 
{
	
	
	//ATRIBUTOS
	private static final String[] TIPOS_REPOSITORY_FACTORY = {"memoria", "relacional"};
	private static final String[] TIPOS_ELEMENTO_GRAFICO_FACTORY = {"texto", "imagem"};
	private static final String[] TIPOS_RODADA_FACTORY = {"sorteio"};
	private static String tipoRepositoryFactory = TIPOS_REPOSITORY_FACTORY[0];	
	private static String tipoElementoGraficoFactory = TIPOS_ELEMENTO_GRAFICO_FACTORY[0];
	private static String tipoRodadaFactory = TIPOS_RODADA_FACTORY[0];
	
	
	//SOLE INSTANCE
	private static Aplicacao soleInstance;
	public static Aplicacao getSoleInstance()
	{
		 if (Aplicacao.soleInstance == null) {
	           return  Aplicacao.soleInstance = new Aplicacao();
	        }
		 else {
			 return Aplicacao.soleInstance;
		 }
		 
	}
	
	//CONSTRUTOR
	private Aplicacao()
	{
		
	}

	
	//MÉTODOS
	public void configurar()
	{
		RepositoryFactory repositoryFactory = getRepositoryFactory();

        TemaRepository temaRepository = repositoryFactory.getTemaRepository();
        RodadaRepository rodadaRepository = repositoryFactory.getRodadaRepository();
        PalavraRepository palavraRepository = repositoryFactory.getPalavraRepository();
        JogadorRepository jogadorRepository = repositoryFactory.getJogadorRepository();

        TemaFactoryImpl.createSoleInstance(temaRepository);
        PalavraFactoryImpl.createSoleInstance(palavraRepository);
        JogadorFactoryImpl.createSoleInstance(jogadorRepository);
        RodadaSorteioFactory.createSoleInstance(rodadaRepository, temaRepository, palavraRepository);

        RodadaFactory rodadaFactory = getRodadaFactory();
        PalavraAppService.createSoleInstance(temaRepository, palavraRepository, getPalavraFactory());
        RodadaAppService.createSoleInstance(rodadaFactory, rodadaRepository, jogadorRepository);

        Palavra.setLetraFactory(getLetraFactory());
        Rodada.setBonecoFactory(getBonecoFactory());
		
	}
	
	
	public String[] getTiposRepositoryFactory() 
	{
		return this.TIPOS_REPOSITORY_FACTORY;
	}
	
	public void setTipoRepositoryFactory(String tipo)
	{
		this.tipoRepositoryFactory = tipo;
	}
	
	public RepositoryFactory getRepositoryFactory() {
        switch (tipoRepositoryFactory) {
            case "memoria":
                return MemoriaRepositoryFactory.getSoleInstance();
            case "relacional":
                return BDRRepositoryFactory.getSoleInstance();
            default:
                throw new IllegalStateException("tipoRepositoryFactory não possui um valor válido");
        }
    }

    public ElementoGraficoFactory getElementoGraficoFactory() {
        switch (tipoElementoGraficoFactory) {
            case "texto":
                return ElementoGraficoTextoFactory.getSoleInstance();
            case "imagem":
                return ElementoGraficoImagemFactory.getSoleInstance();
            default:
                throw new IllegalStateException("tipoElementoGraficoFactory não possui um valor válido");
        }
    }

    public RodadaFactory getRodadaFactory() {
        if (tipoRodadaFactory.equals("sorteio")) {
            return RodadaSorteioFactory.getSoleInstance();
        }
        throw new IllegalStateException("tipoRodadaFactory não possui um valor válido");
    }

    public BonecoFactory getBonecoFactory() {
        return this.getElementoGraficoFactory();
    }

    public LetraFactory getLetraFactory() {
        return this.getElementoGraficoFactory();
    }

    public TemaFactory getTemaFactory() {
        return TemaFactoryImpl.getSoleInstance();
    }

    public PalavraFactory getPalavraFactory() {
        return PalavraFactoryImpl.getSoleInstance();
    }

    public JogadorFactory getJogadorFactory() {
        return JogadorFactoryImpl.getSoleInstance();
    }
}
	

