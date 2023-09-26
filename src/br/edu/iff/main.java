package br.edu.iff;


import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.palavra.emmemoria.MemoriaPalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.emmemoria.MemoriaTemaRepository;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;

import java.util.Scanner;

public class main {
    public static void main(String[] args) throws RepositoryException {
       

    	
    	Aplicacao aplicacao = Aplicacao.getSoleInstance();
        aplicacao.configurar();
        inserirTemasEPalavras();



        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe seu nome: ");
        String nomeJogador = scanner.nextLine();
        System.out.println();

        Jogador jogador = Aplicacao.getSoleInstance().getJogadorFactory().getJogador(nomeJogador);
        Rodada rodada = RodadaAppService.getSoleInstance().novaRodada(jogador);

        do {
            printStatus(rodada);

            printOpcoes();
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    System.out.println("Informe uma letra: ");
                    char codigo = scanner.nextLine().charAt(0);
                    System.out.println();

                    rodada.tentar(codigo);
                    break;
                case 2:
                    String[] palavras = new String[rodada.getNumPalavras()];
                    for (int i = 0; i < rodada.getNumPalavras(); i++) {
                        System.out.println("Informe a Palavra " + (i + 1) + ": ");
                        palavras[i] = scanner.nextLine();
                    }
                    System.out.println();
                    rodada.arriscar(palavras);
                    break;
                default:
                    System.out.println("Opção Inválida");
            }
        } while (!rodada.encerrou());

        printResultadoFinal(rodada);
    }

    private static void inserirTemasEPalavras() throws RepositoryException {
        MemoriaTemaRepository.getSoleInstance().inserir(TemaFactoryImpl.getSoleInstance().getTema("asd"));
        MemoriaTemaRepository.getSoleInstance().inserir(TemaFactoryImpl.getSoleInstance().getTema("esporte"));
        MemoriaTemaRepository.getSoleInstance().inserir(TemaFactoryImpl.getSoleInstance().getTema("casa"));
        

        
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("arroz", MemoriaTemaRepository.getSoleInstance().getPorId(1) ));        
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("feijao", MemoriaTemaRepository.getSoleInstance().getPorId(1) ));  
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("carne", MemoriaTemaRepository.getSoleInstance().getPorId(1) ));  
        
        
        /*
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("futebol", MemoriaTemaRepository.getSoleInstance().getPorId(2) ));        
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("volei", MemoriaTemaRepository.getSoleInstance().getPorId(2) ));  
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("basquete", MemoriaTemaRepository.getSoleInstance().getPorId(2) ));
        
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("quarto", MemoriaTemaRepository.getSoleInstance().getPorId(3) ));        
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("banheiro", MemoriaTemaRepository.getSoleInstance().getPorId(3) ));  
        MemoriaPalavraRepository.getSoleInstance().inserir(PalavraFactoryImpl.getSoleInstance().getPalavra("sala", MemoriaTemaRepository.getSoleInstance().getPorId(3) ));


        /*
        PalavraAppService palavraService = PalavraAppService.getSoleInstance();

        palavraService.novaPalavra("arroz", 1L);
        palavraService.novaPalavra("feijao", 1L);
        palavraService.novaPalavra("carne", 1L);

        palavraService.novaPalavra("futebol", 2L);
        palavraService.novaPalavra("volei", 2L);
        palavraService.novaPalavra("basquete", 2L);

        palavraService.novaPalavra("quarto", 3L);
        palavraService.novaPalavra("banheiro", 3L);
        palavraService.novaPalavra("sala", 3L);
    }
    
    */
    }
    private static void printStatus(Rodada rodada) {
        System.out.println("Tema: " + rodada.getTema().getNome());
        System.out.println();

        System.out.println("Palavras:");
        rodada.exibirItens(null);
        System.out.println();

        System.out.println("Letras Erradas:");
        rodada.exibirLetrasErradas(null);
        System.out.println();

        System.out.println("Boneco:");
        rodada.exibirBoneco(null);
        System.out.println();
    }

    private static void printOpcoes() {
        System.out.println("[1] Tentar");
        System.out.println("[2] Arriscar");
    }

    private static void printResultadoFinal(Rodada rodada) {
        System.out.println("Resultado Final: " + (rodada.descobriu() ? "venceu" : "perdeu"));

        System.out.println("Total Tentativas: " + rodada.getQtdeTentativas());
        System.out.println("Tentativas Erradas: " + rodada.getQtdeErros());
        System.out.println("Tentativas Certas: " + rodada.getQtdeTentativasRestantes());
        System.out.println("Arriscou: " + (rodada.arriscou() ? "sim" : "não"));
        System.out.println("Pontos: " + rodada.calcularPontos());
    }
}