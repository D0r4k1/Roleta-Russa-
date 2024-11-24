import java.util.Random;
import java.util.Scanner;

public class Main {
    static int vidasJogador1 = 3;
    static int vidasJogador2 = 3;
    static int balasTambor = 1; 
    static boolean[][] funcoesUsadas = { {false, false, false}, {false, false, false} }; 
    static int turnoAtual = 1; 
    static String nomeJogador1;
    static String nomeJogador2;
    static final String VERMELHO = "\u001B[31m";
    static final String AZUL = "\u001B[34m";
    static final String VERDE = "\u001B[32m";
    static final String RESET = "\u001B[0m";
    static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(VERMELHO + "=== Bem-vindo ao jogo de Roleta Russa! ===" + RESET);

        System.out.print("Digite o nome do Jogador 1: ");
        nomeJogador1 = AZUL + scanner.nextLine() + RESET;

        System.out.print("Digite o nome do Jogador 2: ");
        nomeJogador2 = VERDE + scanner.nextLine() + RESET;

        System.out.println("\nRegras:");
        System.out.println("1. Cada jogador começa com 3 vidas.");
        System.out.println("2. O tambor tem 6 câmaras, inicialmente com 1 bala.");
        System.out.println("3. Funções disponíveis na mesa:");
        System.out.println("   - Suprimentos Médicos: Adiciona 1 vida ao jogador.");
        System.out.println("   - Novas Balas: Adiciona 2 balas ao tambor.");
        System.out.println("   - Foice: Atira duas vezes em si mesmo, depois uma vez no adversário.");
        System.out.println("4. Cada jogador pode usar as funções da mesa apenas uma vez.");
        System.out.println("5. Vence o jogador que sobreviver.");
        System.out.println();

        while (vidasJogador1 > 0 && vidasJogador2 > 0) {
            executarTurno(scanner);
        }

        if (vidasJogador1 <= 0) {
            exibirCaveira(nomeJogador1);
            System.out.println(VERMELHO + nomeJogador1 + " perdeu todas as vidas! " + nomeJogador2 + " venceu!" + RESET);
        } else {
            exibirCaveira(nomeJogador2);
            System.out.println(VERMELHO + nomeJogador2 + " perdeu todas as vidas! " + nomeJogador1 + " venceu!" + RESET);
        }

        scanner.close();
    }

    private static void executarTurno(Scanner scanner) {
        String jogadorAtual = turnoAtual == 1 ? nomeJogador1 : nomeJogador2;
        System.out.println("\n=== Turno de " + jogadorAtual + " ===");
        System.out.println("Vidas: " + nomeJogador1 + " = " + vidasJogador1 + " | " + nomeJogador2 + " = " + vidasJogador2);
        System.out.println("Balas no tambor: " + balasTambor);
        System.out.println("Escolha sua ação:");
        System.out.println("1. Apertar o gatilho");
        System.out.println("2. Usar Suprimentos Médicos");
        System.out.println("3. Usar Novas Balas");
        System.out.println("4. Usar a Foice");

        int escolha = -1;
        boolean escolhaValida = false;

        while (!escolhaValida) {
            try {
                escolha = Integer.parseInt(scanner.nextLine());
                if (escolha >= 1 && escolha <= 4) {
                    if (escolha > 1 && funcoesUsadas[turnoAtual - 1][escolha - 2]) {
                        System.out.println("Você já usou essa função! Escolha outra.");
                    } else {
                        escolhaValida = true;
                    }
                } else {
                    System.out.println("Escolha inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Escolha inválida. Tente novamente.");
            }
        }

        switch (escolha) {
            case 1:
                apertarGatilho();
                break;
            case 2:
                usarSuprimentosMedicos();
                break;
            case 3:
                adicionarBalas();
                break;
            case 4:
                usarFoice();
                break;
        }

        trocarTurno();
    }

    private static void apertarGatilho() {
        boolean balaDisparada = random.nextInt(6) < balasTambor;

        if (balaDisparada) {
            System.out.println(VERMELHO + "BANG! Você foi atingido!" + RESET);
            if (turnoAtual == 1) {
                vidasJogador1--;
            } else {
                vidasJogador2--;
            }
        } else {
            System.out.println("Clique! Nenhuma bala foi disparada.");
        }
    }

    private static void usarSuprimentosMedicos() {
        if (turnoAtual == 1) {
            vidasJogador1 = Math.min(vidasJogador1 + 1, 3);
        } else {
            vidasJogador2 = Math.min(vidasJogador2 + 1, 3);
        }
        funcoesUsadas[turnoAtual - 1][0] = true;
        System.out.println("Você usou Suprimentos Médicos e ganhou 1 vida!");
    }

    private static void adicionarBalas() {
        balasTambor = Math.min(balasTambor + 2, 6);
        funcoesUsadas[turnoAtual - 1][1] = true;
        System.out.println("Você adicionou 2 balas ao tambor!");
    }

    private static void usarFoice() {
        funcoesUsadas[turnoAtual - 1][2] = true;
        System.out.println("Você usou a Foice! Atirará duas vezes em si mesmo e depois no adversário.");

        for (int i = 0; i < 2; i++) {
            if (vidasJogador1 <= 0 || vidasJogador2 <= 0) break;
            System.out.println("Atirando em si mesmo...");
            apertarGatilho();
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (vidasJogador1 > 0 && vidasJogador2 > 0) {
            System.out.println("Agora você atira no adversário!");
            if (turnoAtual == 1) {
                vidasJogador2--;
                System.out.println(VERMELHO + "BANG! " + nomeJogador2 + " foi atingido!" + RESET);
            } else {
                vidasJogador1--;
                System.out.println(VERMELHO + "BANG! " + nomeJogador1 + " foi atingido!" + RESET);
            }
        }
    }

    private static void trocarTurno() {
        turnoAtual = turnoAtual == 1 ? 2 : 1;
    }

private static void exibirCaveira(String jogador) {
    System.out.println(VERMELHO + "\n=== " + jogador + VERMELHO + " morreu! ===" + VERMELHO);
    System.out.println(VERMELHO + "     _____");
    System.out.println(VERMELHO + "    /     \\");
    System.out.println(VERMELHO + "   | () () |");
    System.out.println(VERMELHO + "    \\  ^  /");
    System.out.println(VERMELHO + "     |||||");
    System.out.println(VERMELHO + "     |||||" + RESET);
 }
}
