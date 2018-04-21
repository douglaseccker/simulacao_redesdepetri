import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.Arco;
import classes.Lugar;
import classes.Transicao;

public class Main {
    public static final int ITERATION = 10;

	public static void main(String[] args) {
        List<Lugar> lugares = new ArrayList<Lugar>();
        List<Transicao> transicoes = new ArrayList<Transicao>();
        int lugaresNum;
        int transicoesNum;
        Scanner scan = new Scanner(System.in);

        System.out.print("Quantos lugares? ");
        lugaresNum = scan.nextInt();

        System.out.print("Quantas transicoes? ");
        transicoesNum = scan.nextInt();

        for (int i = 1; i <= lugaresNum; i++) {
            Lugar lugar = new Lugar(i);

            lugares.add(lugar);
        }

        for (int i = 1; i <= transicoesNum; i++) {
            Transicao transicao = new Transicao(i);

            transicoes.add(transicao);
        }

        scan.nextLine();

        for (Transicao transicao : transicoes) {
            System.out.printf("Quais os lugares de entrada de %s? ", transicao.getNome());

            String numberStr = scan.nextLine();
            String[] numbers = numberStr.trim().split(",");

            for (int i = 1; i <= numbers.length; i++) {
                // TODO: Validacao... Exception: IndexOutOfBoundsException
                Arco arco = new Arco(i, lugares.get(Integer.parseInt(numbers[i - 1].trim()) - 1));

                transicao.addEntrada(arco);
            }
        }

        // TODO: Indice dos arcos irão se repetir

        for (Transicao transicao : transicoes) {
            System.out.printf("Quais os lugares de saida de %s? ", transicao.getNome());

            String numberStr = scan.nextLine();
            String[] numbers = numberStr.trim().split(",");

            for (int i = 1; i <= numbers.length; i++) {
                // TODO: Validacao... Exception: IndexOutOfBoundsException
                Arco arco = new Arco(i, lugares.get(Integer.parseInt(numbers[i - 1].trim()) - 1));

                transicao.addSaida(arco);
            }
        }

        for (Lugar lugar : lugares) {
            System.out.printf("Quantas marcas em %s? ", lugar.getNome());

            int marcasLugar = scan.nextInt();
            lugar.setMarcas(marcasLugar);
        }

        for (Transicao transicao : transicoes) {
            for (Arco arco : transicao.getEntrada()) {
                System.out.printf(
                    "Qual o peso do arco de %s para %s? ",
                    arco.getLugar().getNome(),
                    transicao.getNome()
                );

                int pesoArco = scan.nextInt();
                arco.setPeso(pesoArco);
            }

            for (Arco arco : transicao.getSaida()) {
                System.out.printf(
                    "Qual o peso do arco de %s para %s? ",
                    transicao.getNome(),
                    arco.getLugar().getNome()
                );

                int pesoArco = scan.nextInt();
                arco.setPeso(pesoArco);
            }

            transicao.checkHabilitada();
        }

        System.out.println();
        printLugar(lugares);
        printTransicao(transicoes);
        System.out.println();

        scan.close();

        for (int i = 1; i <= ITERATION; i++) {
            boolean canRun = false;

            System.out.println(i + "a interacao...");

            for (Transicao transicao : transicoes) {
                transicao.run();
            }

            for (Transicao transicao : transicoes) {
                canRun |= transicao.checkHabilitada();
            }

            System.out.println();
            printLugar(lugares);
            printTransicao(transicoes);
            System.out.println();

            if(!canRun) {
                break;
            }
        }
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
   
    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);  
    }
    
    private static void printLugar(List<Lugar> lugares) {
        String borderTable  = "+----------+";
        String lugarLine    = "| Lugar    |";
        String marcacaoLine = "| Marcacao |";

        for (Lugar lugar : lugares) {
            borderTable += "----+";
            lugarLine += padLeft(lugar.getNome(), 3) + " |";
            marcacaoLine += padLeft(Integer.toString(lugar.getMarcas()), 3) + " |";
        }

        System.out.println(borderTable);
        System.out.println(lugarLine);
        System.out.println(borderTable);
        System.out.println(marcacaoLine);
        System.out.println(borderTable);
    }

    private static void printTransicao(List<Transicao> transicoes) {
        String borderTable    = "+------------+";
        String transicaoLine  = "| Transicao  |";
        String habilitadaLine = "| Habilitada |";

        for (Transicao transicao : transicoes) {
            borderTable += "----+";
            transicaoLine += padLeft(transicao.getNome(), 3) + " |";

            if(transicao.isHabilitada()) {
                habilitadaLine += "  S |";
            } else {
                habilitadaLine += "  N |";
            }
        }

        System.out.println(borderTable);
        System.out.println(transicaoLine);
        System.out.println(borderTable);
        System.out.println(habilitadaLine);
        System.out.println(borderTable);
    }    
}
