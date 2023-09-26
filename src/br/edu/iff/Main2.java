package br.edu.iff;

import java.util.Scanner;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.JogadorNaoEncontradoException;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;

public class Main2 {

	public static void main(String[] args) throws RepositoryException, JogadorNaoEncontradoException {
		
		Aplicacao.getSoleInstance().configurar();
		
		String[] temas = {"Cores","Roupas","Animais","Comida"};
		for(String tema : temas) {			
			Aplicacao.getSoleInstance().getRepositoryFactory().getTemaRepository().inserir(Aplicacao.getSoleInstance().getTemaFactory().getTema(tema));
		}
		
		String[] palavrasCores = {"azul","vermelho","verde","branco","roxo","laranja","amarelo"};
		for(String palavra : palavrasCores) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra, Aplicacao.getSoleInstance().getRepositoryFactory().getTemaRepository().getPorNome(temas[0])[0].getId());
		}
		String[] palavrasRoupas = {"camisa","jaleco","moletom","vestido","sobretudo"};
		for(String palavra : palavrasRoupas) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra, Aplicacao.getSoleInstance().getRepositoryFactory().getTemaRepository().getPorNome(temas[1])[0].getId());
		}
		String[] palavrasAnimais = {"cachorro","gato","peixe","coelho","passaro","cavalo"};
		for(String palavra : palavrasAnimais) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra, Aplicacao.getSoleInstance().getRepositoryFactory().getTemaRepository().getPorNome(temas[2])[0].getId());
		}
		String[] palavrasComida = {"arroz","feijao","macarrao","batata","alface"};
		for(String palavra : palavrasComida) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra, Aplicacao.getSoleInstance().getRepositoryFactory().getTemaRepository().getPorNome(temas[3])[0].getId());
		}
		
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("Informe seu nome: ");
        String nomeJogador = entrada.nextLine();
        System.out.println();
        
        Aplicacao.getSoleInstance().getRepositoryFactory().getJogadorRepository().inserir(Aplicacao.getSoleInstance().getJogadorFactory().getJogador(nomeJogador));
        Rodada partida = RodadaAppService.getSoleInstance().novaRodada(nomeJogador);
        
        Object contexto = null;
        String escolha;
        char letraTentada;
        String[] palavrasArriscadas = new String[partida.getPalavra().length];
        do {

        	System.out.println("Tema: "+partida.getTema().getNome());
        	System.out.println("Palavras: ");
        	partida.exibirItens(contexto);
        	System.out.print("Letras erradas: ");
        	partida.exibirLetrasErradas(contexto);
        	System.out.println("\nBoneco: ");
        	partida.exibirBoneco(contexto);
        	
        	System.out.print("\nDigite 1 para tentar e 2 para arriscar:");
        	escolha =  entrada.nextLine();
        	switch(escolha) {
        	case "1":
        		System.out.println("## Tentar ##");
        		System.out.println("N�mero de tentativas restantes: "+partida.getQtdeTentativasRestantes());
        		System.out.print("Digite a letra:");
        		letraTentada = entrada.nextLine().charAt(0);
        		if(!(letraTentada >= 'a' && letraTentada <= 'z')) {
        			System.out.println("<<Letra inv�lida>>");
        		}else {
        			partida.tentar(letraTentada);        			
        		}
        		break;
        	case "2":
        		System.out.println("## Arriscar ##");
        		System.out.println("Digite as palavras:");
        		for(int posicaoAtual = 0; posicaoAtual < palavrasArriscadas.length; posicaoAtual++) {
        			System.out.print((posicaoAtual+1)+"� palavra"+":");
        			palavrasArriscadas[posicaoAtual] = entrada.nextLine();
        		}
        		partida.arriscar(palavrasArriscadas);
        		break;
        	default:
        		System.out.println("<<Escolha inv�lida>>");
        		break;
        	}
        	System.out.println("===============================");
        }while(!partida.encerrou());
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        if(partida.descobriu()) {
        	System.out.println("O Jogador "+partida.getJogador().getNome()+" venceu!");
        	System.out.println("N�mero de tentativas: "+partida.getQtdeTentativas());
        	System.out.println("N�mero de acertos: "+partida.getQtdeAcertos());
        	System.out.println("N�mero de erros: "+partida.getQtdeErros());
        	System.out.println("Pontos obtidos: "+partida.calcularPontos());
        }else {
        	System.out.println("O Jogador "+partida.getJogador().getNome()+" perdeu!");
        	System.out.println("N�mero de tentativas: "+partida.getQtdeTentativas());
        	System.out.println("N�mero de acertos: "+partida.getQtdeAcertos());
        	System.out.println("Palavras:");
        	partida.exibirPalavras(contexto);
        }
        System.out.println();
        RodadaAppService.getSoleInstance().salvarRodada(partida);
	}

}