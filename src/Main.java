import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.Arco;
import classes.Lugar;
import classes.Rede;
import classes.Transicao;

public class Main {
    public static final int ITERATION = 10;

	public static void main(String[] args) {
		Rede rede = new Rede();
//        List<Lugar> lugares = new ArrayList<Lugar>();
//        List<Transicao> transicoes = new ArrayList<Transicao>();
//        int lugaresNum;
//        int transicoesNum;
        Scanner scan = new Scanner(System.in);

        System.out.print("Selecione o método de leitura da rede: 1 - interativo, 2 - arquivo: ");
        int option = scan.nextInt();

        if(option == 1) {
	        rede.montaRedeInterativa(scan);
        }else {
        	try {
				rede.montaRedeArquivo(scan);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }

        System.out.println();
        printLugar(rede.getLugares());
        printTransicao(rede.getTransicoes());
        System.out.println();
        
        System.out.println("Pressione enter para rodar a primeira interacao...");
        System.out.println(scan.nextLine());
        
        System.exit(0);

        for (int i = 1; i <= ITERATION; i++) {
            boolean canRun = false;

            for (Transicao transicao : rede.getTransicoes()) {
                transicao.run();
            }

            for (Transicao transicao : rede.getTransicoes()) {
                canRun |= transicao.checkHabilitada();
            }

            System.out.println();
            printLugar(rede.getLugares());
            printTransicao(rede.getTransicoes());
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
