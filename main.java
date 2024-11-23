import java.util.Random;
import java.util.Scanner;

public class Main {
    static int vidasJogador1 = 3;
    static int vidasJogador2 = 3;
    static int balasTambor = 1; 
    static boolean[] funcoesUsadas = {false, false, false}; 
    static int turnoAtual = 1; 
    static boolean foiceAtiva = false; 
    static int tirosExtras = 0; 
    static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Bem-vindo ao jogo de Roleta Russa! ===");
        System.out.println("\nRegras:");
        System.out.println("1. Cada jogador começa com 3 vidas.");
        System.out.println("2. O tambor tem 6 câmaras, inicialmente com 1 bala.");
        System.out.println("3. Funções disponíveis na mesa:");
        System.out.println("   - Suprimentos Médicos: Adiciona 1 vida ao jogador.");
        System.out.println("   - Novas Balas: Adiciona 2 balas ao tambor.");
        System.out.println("   - Foice: Permite atirar no adversário, mas no próximo turno você atira duas vezes em si mesmo.");
        System.out.println("4. Vence o jogador que sobreviver.");
        System.out.println();

        while (vidasJogador1 > 0 && vidasJogador2 > 0) {
            executarTurno(scanner);
        }

        if (vidasJogador1 <= 0) {
            exibirCaveira("Jogador 1");
            System.out.println("Jogador 1 perdeu todas as vidas! Jogador 2 venceu!");
        } else {
            exibirCaveira("Jogador 2");
            System.out.println("Jogador 2 perdeu todas as vidas! Jogador 1 venceu!");
        }

        scanner.close();
    }

    private static void executarTurno(Scanner scanner) {
        System.out.println("\n=== Turno do Jogador " + turnoAtual+ " ===");
        System.out.println("Vidas: Jogador 1 = " + vidasJogador1 + " | Jogador 2 = " + vidasJogador2);
        System.out.println("Balas no tambor: " + balasTambor);
        System.out.println("Escolha sua ação:");
        System.out.println("1. Apertar o gatilho");
        System.out.println("2. Usar Suprimentos Médicos");
        System.out.println("3. Usar Novas Balas");
        System.out.println("4. Usar a Foice");

        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Escolha inválida. Tente novamente.");
            return;
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
            default:
                System.out.println("Escolha inválida. Tente novamente.");
        }

        if (tirosExtras > 0) {
            System.out.println("Por causa da Foice, o Jogador " + turnoAtual + " precisa atirar novamente!");
            apertarGatilho();
            tirosExtras--;
        }

        trocarTurno();
    }

    private static void apertarGatilho() {
        boolean balaDisparada = random.nextInt(6) < balasTambor;

        if (balaDisparada) {
            if (foiceAtiva) {
                System.out.println("BANG! O jogador adversário foi atingido!");
                if (turnoAtual == 1) {
                    vidasJogador2--;
                } else {
                    vidasJogador1--;
                }
                foiceAtiva = false;
            } else {
                System.out.println("BANG! O jogador " + turnoAtual + " foi atingido!");
                if (turnoAtual == 1) {
                    vidasJogador1--;
                } else {
                    vidasJogador2--;
                }
            }
        } else {
            System.out.println("BANG! Nenhuma bala foi disparada.");
        }
    }

    private static void usarSuprimentosMedicos() {
        if (funcoesUsadas[0]) {
            System.out.println("Suprimentos Médicos já foram usados!");
            return;
        }

        if (turnoAtual == 1) {
            vidasJogador1 = Math.min(vidasJogador1 + 1, 3);
        } else {
            vidasJogador2 = Math.min(vidasJogador2 + 1, 3);
        }
        funcoesUsadas[0] = true;
        System.out.println("Jogador " + turnoAtual + " usou Suprimentos Médicos e ganhou 1 vida!");
    }

    private static void adicionarBalas() {
        if (funcoesUsadas[1]) {
            System.out.println("Novas Balas já foram usadas!");
            return;
        }

        balasTambor = Math.min(balasTambor + 2, 6);
        funcoesUsadas[1] = true;
        System.out.println("Jogador " + turnoAtual + " adicionou 2 balas ao tambor!");
    }

    private static void usarFoice() {
        if (funcoesUsadas[2]) {
            System.out.println("A Foice já foi usada!");
            return;
        }

        System.out.println("Jogador " + turnoAtual + " usou a Foice! O próximo tiro será no adversário.");
        foiceAtiva = true;
        tirosExtras = 2; 
        funcoesUsadas[2] = true;
    }

    private static void trocarTurno() {
        turnoAtual = turnoAtual == 1 ? 2 : 1;
    }

    private static void exibirCaveira(String jogador) {
        System.out.println("\n=== O " + jogador + " morreu! ===");
        System.out.println("     _____");
        System.out.println("    /     \\");
        System.out.println("   | () () |");
        System.out.println("    \\  ^  /");
        System.out.println("     |||||");
        System.out.println("     |||||");
    }
}
