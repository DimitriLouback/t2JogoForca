package br.edu.iff;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Aplicacao aplicacao = Aplicacao.getSoleInstance();

    public static void main(String[] args) {
        aplicacao.configurar();

        PalavraAppService palavraAppService = PalavraAppService.getSoleInstance();

        TemaRepository temaRepository = aplicacao.getRepositoryFactory().getTemaRepository();
        TemaFactory temaFactory = aplicacao.getTemaFactory();

        try {
            temaRepository.inserir(temaFactory.getTema("Carro"));
            temaRepository.inserir(temaFactory.getTema("Nome"));
            temaRepository.inserir(temaFactory.getTema("Casa"));
            temaRepository.inserir(temaFactory.getTema("Comida"));
            temaRepository.inserir(temaFactory.getTema("Jogos"));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }	

        palavraAppService.novaPalavra("fusca", 1);
        palavraAppService.novaPalavra("palio", 1);
        palavraAppService.novaPalavra("corsa", 1);
        palavraAppService.novaPalavra("felipe", 2);
        palavraAppService.novaPalavra("ana", 2);
        palavraAppService.novaPalavra("jorge", 2);
        palavraAppService.novaPalavra("sofa", 3);
        palavraAppService.novaPalavra("fogao", 3);
        palavraAppService.novaPalavra("armario", 3);
        palavraAppService.novaPalavra("fifa", 4);
        palavraAppService.novaPalavra("mario", 4);
        palavraAppService.novaPalavra("sonic", 4);

        System.out.println("Digite seu nome: ");
        String nomeJogador = scanner.next();

        Jogador jogador = aplicacao.getJogadorFactory().getJogador(nomeJogador);
        try {
            aplicacao.getRepositoryFactory().getJogadorRepository().inserir(jogador);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

        jogarRodada(jogador);
    }

    private static void jogarRodada(Jogador jogador) {
        RodadaAppService rodadaAppService = RodadaAppService.getSoleInstance();

        Rodada rodada = rodadaAppService.novaRodada(jogador);
        System.out.println("Tema das palavras: " + rodada.getTema());

        do {
            System.out.println("Tentativas restantes: " + rodada.getQtdeTentativasRestantes());
            System.out.println("Tentativas anteriores: ");
            for (Letra tentativa : rodada.getTentativas()) {
                tentativa.exibir(null);
                System.out.print(" ");
            }
            System.out.println();

            System.out.println("Palavras:");
            rodada.exibirItens(null);
            System.out.println();
            System.out.println("Corpo: ");
            rodada.exibirBoneco(null);
            System.out.println();


            System.out.println("(1) Tentar letra");
            System.out.println("(2) Arriscar");
            String escolha = scanner.next();
            switch (escolha){
                case "1":
                    System.out.print("Digite a letra: ");
                    rodada.tentar(scanner.next().charAt(0));
                    break;
                case "2":
                    String[] palavrasArriscadas = new String[rodada.getNumPalavras()];
                    for (int i = 0; i < palavrasArriscadas.length; i++) {
                        System.out.print("Chute a palavra " + (i + 1)  + " :");
                        palavrasArriscadas[i] = scanner.next();
                    }
                    rodada.arriscar(palavrasArriscadas);
                    break;
            }

            if (rodada.descobriu()) System.out.println("Descobriu");


        }while (!rodada.encerrou());
    }
}