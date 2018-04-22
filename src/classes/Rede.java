package classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rede {
	private List<Lugar> lugares = new ArrayList<Lugar>();
    private List<Transicao> transicoes = new ArrayList<Transicao>();
    private int lugaresNum;
    private int transicoesNum;

	public Rede() {
    }
	
	public void montaRedeArquivo(Scanner scan) throws Exception {
		System.out.println("Informe o nome do arquivo de leitura da rede: ");
    	String fileName = scan.next();
    	String content = "";

    	try {
            content = new FileHandler().read(fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            this.montaRedeArquivo(scan);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.montaRedeArquivo(scan);
        }
    	
    	/*
    	    5 // lugares
			2 // transicoes
			1,2 // entrada em T1
			3,4 // entrada em T2
			2,3 // saida em T1
			5 // saida em T2
			1 // marca L1
			2 // marca L2
			0 // marca L3
			1 // marca L4
			0 // marca L5
			
			//PESOS: OS PESOS TEM QUE ESTAR DE ACORDO COM A QUANTIDADE DE LUGARES E TRANSIÇÕES
			1 // peso do primeiro de entrada de T1
			2 // peso do primeiro de entrada de T1
			2
			1
			1
			1
			1
    	 */
    	
    	if(content.equals("")) {
    		throw new Exception("Arquivo vazio");
    	}

    	String[] lines = content.split("\n");

    	int currentLine = 1;
    	this.lugaresNum = Integer.parseInt(lines[currentLine++]);
    	this.transicoesNum = Integer.parseInt(lines[currentLine++]);
    	
    	for (int i = 1; i <= this.transicoesNum; i++) {
            Transicao transicao = new Transicao(i);
            
            this.transicoes.add(transicao);
        }
        
        for (int i = 1; i <= this.lugaresNum; i++) {
            Lugar lugar = new Lugar(i);

            this.lugares.add(lugar);
        }
        
        for (Transicao transicao : this.transicoes) {
            String numberStr = lines[currentLine];
            String[] numbers = numberStr.trim().split(",");

            for (int i = 1; i <= numbers.length; i++) {
                // TODO: Validacao... Exception: IndexOutOfBoundsException
                Arco arco = new Arco(i, this.lugares.get(Integer.parseInt(numbers[i - 1].trim()) - 1));

                transicao.addEntrada(arco);
            }
            
            currentLine++;
        }

        // TODO: Indice dos arcos irÃ£o se repetir
        for (Transicao transicao : this.transicoes) {
            String numberStr = lines[currentLine];
            String[] numbers = numberStr.trim().split(",");

            for (int i = 1; i <= numbers.length; i++) {
                // TODO: Validacao... Exception: IndexOutOfBoundsException
                Arco arco = new Arco(i, this.lugares.get(Integer.parseInt(numbers[i - 1].trim()) - 1));

                transicao.addSaida(arco);
            }
            
            currentLine++;
        }

        for (Lugar lugar : this.lugares) {
            int marcasLugar = Integer.parseInt(lines[currentLine]);
            lugar.setMarcas(marcasLugar);
            
            currentLine++;
        }
        
        for (Transicao transicao : this.transicoes) {
            for (Arco arco : transicao.getEntrada()) {
                int pesoArco = Integer.parseInt(lines[currentLine]);
                arco.setPeso(pesoArco);
                
                currentLine++;
            }

            for (Arco arco : transicao.getSaida()) {
            	int pesoArco = Integer.parseInt(lines[currentLine]);
                arco.setPeso(pesoArco);
                
                currentLine++;
            }

            transicao.checkHabilitada();
        }
	}

	public void montaRedeInterativa(Scanner scan) {
		System.out.print("Quantos lugares? ");
        this.lugaresNum = scan.nextInt();

        System.out.print("Quantas transicoes? ");
        this.transicoesNum = scan.nextInt();

        for (int i = 1; i <= this.lugaresNum; i++) {
            Lugar lugar = new Lugar(i);

            this.lugares.add(lugar);
        }

        for (int i = 1; i <= this.transicoesNum; i++) {
            Transicao transicao = new Transicao(i);

            this.transicoes.add(transicao);
        }

        scan.nextLine();

        for (Transicao transicao : this.transicoes) {
            System.out.printf("Quais os lugares de entrada de %s? ", transicao.getNome());

            String numberStr = scan.nextLine();
            String[] numbers = numberStr.trim().split(",");

            for (int i = 1; i <= numbers.length; i++) {
                // TODO: Validacao... Exception: IndexOutOfBoundsException
                Arco arco = new Arco(i, this.lugares.get(Integer.parseInt(numbers[i - 1].trim()) - 1));

                transicao.addEntrada(arco);
            }
        }

        // TODO: Indice dos arcos irÃ£o se repetir

        for (Transicao transicao : this.transicoes) {
            System.out.printf("Quais os lugares de saida de %s? ", transicao.getNome());

            String numberStr = scan.nextLine();
            String[] numbers = numberStr.trim().split(",");

            for (int i = 1; i <= numbers.length; i++) {
                // TODO: Validacao... Exception: IndexOutOfBoundsException
                Arco arco = new Arco(i, this.lugares.get(Integer.parseInt(numbers[i - 1].trim()) - 1));

                transicao.addSaida(arco);
            }
        }

        for (Lugar lugar : this.lugares) {
            System.out.printf("Quantas marcas em %s? ", lugar.getNome());

            int marcasLugar = scan.nextInt();
            lugar.setMarcas(marcasLugar);
        }

        for (Transicao transicao : this.transicoes) {
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
	}

	public List<Lugar> getLugares() {
		return this.lugares;
	}

	public void setLugares(List<Lugar> lugares) {
		this.lugares = lugares;
	}

	public List<Transicao> getTransicoes() {
		return this.transicoes;
	}

	public void setTransicoes(List<Transicao> transicoes) {
		this.transicoes = transicoes;
	}

	public int getLugaresNum() {
		return this.lugaresNum;
	}

	public void setLugaresNum(int lugaresNum) {
		this.lugaresNum = lugaresNum;
	}

	public int getTransicoesNum() {
		return this.transicoesNum;
	}

	public void setTransicoesNum(int transicoesNum) {
		this.transicoesNum = transicoesNum;
	}
}
