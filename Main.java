import java.util.List;
import java.util.Scanner;

import impl.Grafo;
import impl.Vertice;

public class Main {
    public static void main(String[] args) {
        Grafo rede = new Grafo();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Adicionar usuário");
            System.out.println("2. Adicionar amizade");
            System.out.println("3. Mostrar rede");
            System.out.println("4. Usuário mais influente");
            System.out.println("5. Verificar conexão entre usuários");
            System.out.println("6. Sugerir amizades");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Nome do usuário: ");
                    rede.adicionarVertice(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Usuário 1: ");
                    String u1 = sc.nextLine();
                    System.out.print("Usuário 2: ");
                    String u2 = sc.nextLine();
                    rede.adicionarAresta(u1, u2);
                    break;
                case 3:
                    rede.mostrarGrafo();
                    break;
                case 4:
                    System.out.println("Mais influente: " + rede.buscaNomeUsuarioMaisInfluente());
                    break;
                case 5:
                    System.out.print("Usuário origem: ");
                    String origem = sc.nextLine();
                    System.out.print("Usuário destino: ");
                    String destino = sc.nextLine();
                    boolean conectado = rede.existeAlgumCaminhoEntreOsUsuarios(origem, destino);
                    System.out.println("Conectados? " + (conectado ? "Sim" : "Não"));
                    break;
                case 6:
                    System.out.print("Usuário para sugestão: ");
                    String nome = sc.nextLine();
                    List<Vertice> sugestoes = rede.sugerirAmizades(nome);
                    System.out.println("Sugestões: ");
                    for (Vertice s : sugestoes) {
                        System.out.println("- " + s.getValor());
                    }
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
}