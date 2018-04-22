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
        Scanner scan = new Scanner(System.in);

        System.out.print("Selecione o método de leitura da rede: 1 - interativo, 2 - arquivo: ");
        int option = scan.nextInt();

        try {
	        if(option == 1) {
		        rede.montaRedeInterativa(scan);
	        }else {
	        	rede.montaRedeArquivo(scan);
	        }

	        rede.run();
        }catch(Exception e) {
        	e.printStackTrace();
        	System.out.println(e.getMessage());
        }
    }   
}
