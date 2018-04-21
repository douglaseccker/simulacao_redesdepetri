import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.Arco;
import classes.Lugar;
import classes.Transicao;

public class Main {
    public static final int ITERATION = 5;

	public static void main(String[] args) {
        List<Lugar> lugares = new ArrayList<Lugar>();
        List<Transicao> transicoes = new ArrayList<Transicao>();
        int lugaresNum;
        int transicoesNum;

        Scanner scan = new Scanner(System.in);

        System.out.print("Quantos lugares? ");
        lugaresNum = scan.nextInt();

        System.out.print("Quantas transições? ");
        transicoesNum = scan.nextInt();

        System.out.println();

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
            System.out.println();

            String numberStr = scan.nextLine();
            String[] numbers = numberStr.trim().split(",");

            for (int i = 1; i <= numbers.length; i++) {
                Arco arco = new Arco(i, lugares.get(Integer.parseInt(numbers[i - 1]) - 1));

                transicao.addEntrada(arco);
            }
        }

        for (Lugar lugar : lugares) {
            System.out.printf("Quantas marcas em %s? ", lugar.getNome());
            System.out.println();

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
                System.out.println();

                int pesoArco = scan.nextInt();
                arco.setPeso(pesoArco);
            }
        }

        scan.close();

        for (int i = 0; i < ITERATION; i++) {
            for (Transicao transicao : transicoes) {
                transicao.run();
            }
        }
	}
}
