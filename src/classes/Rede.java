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
    private int currentCicle;

	public Rede() {
		this.currentCicle = 1;
    }
	
	public void run() {
		Scanner keyIn = new Scanner(System.in);

        System.out.print("Pressione enter para rodar a " + this.currentCicle + "ª interacao. ");
        
        keyIn.nextLine();
        
        boolean canRun = false;

        for (Transicao transicao : this.getTransicoes()) {
            transicao.run();
        }

        for (Transicao transicao : this.getTransicoes()) {
            canRun |= transicao.checkHabilitada();
        }
        
        System.out.println();
        this.printLugar(this.getLugares());
        this.printTransicao(this.getTransicoes());
        System.out.println();

        if(canRun) {
	        this.currentCicle++;
	        
	        this.run();
        }else {
        	System.out.println("Final");
        }
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
            
            if(numbers.length > this.lugares.size()) {
            	throw new Exception("Número de lugares informado para a " + transicao.getNome() + " maior do que o número de lugares total.");
            }

            for (int i = 1; i <= numbers.length; i++) {
            	int index = Integer.parseInt(numbers[i - 1].trim()) - 1;
                if(index < 0 || index > this.lugares.size()) {
                	throw new Exception("Lugar " + index + " informado para a " + transicao.getNome() + " inválido");
                }
            	
            	Arco arco = new Arco(i, this.lugares.get(index));

                transicao.addEntrada(arco);
            }
            
            currentLine++;
        }

        //Adicionada variável indexArco exclusivamente para índices dos arcos não repetirem
        int indexArco = 1;
        for (Transicao transicao : this.transicoes) {
            String numberStr = lines[currentLine];
            String[] numbers = numberStr.trim().split(",");

            //Verifica se os índices são válidos
        	if(numbers.length > this.lugares.size()) {
            	throw new Exception("Número de lugares informado para a " + transicao.getNome() + " maior do que o número de lugares total.");
            }

            for (int i = 1; i <= numbers.length; i++) {
            	//Verifica se o índice é válido
            	int index = Integer.parseInt(numbers[i - 1].trim()) - 1;
                if(index < 0 || index > this.lugares.size()) {
                	throw new Exception("Lugar " + index + " informado para a " + transicao.getNome() + " inválido");
                }
            	
            	Arco arco = new Arco(indexArco++, this.lugares.get(index));

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

	public void montaRedeInterativa(Scanner scan) throws Exception {
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

            if(numbers.length > this.lugares.size()) {
            	throw new Exception("Número de lugares informado para a " + transicao.getNome() + " maior do que o número de lugares total.");
            }

            for (int i = 1; i <= numbers.length; i++) {
            	int index = Integer.parseInt(numbers[i - 1].trim()) - 1;
                if(index < 0 || index > this.lugares.size()) {
                	throw new Exception("Lugar " + index + " informado para a " + transicao.getNome() + " inválido");
                }
            	
            	Arco arco = new Arco(i, this.lugares.get(index));

                transicao.addEntrada(arco);
            }
        }

        //Adicionada variável indexArco exclusivamente para índices dos arcos não repetirem
        int indexArco = 1;
        for (Transicao transicao : this.transicoes) {
            System.out.printf("Quais os lugares de saida de %s? ", transicao.getNome());

            String numberStr = scan.nextLine();
            String[] numbers = numberStr.trim().split(",");

            if(numbers.length > this.lugares.size()) {
            	throw new Exception("Número de lugares informado para a " + transicao.getNome() + " maior do que o número de lugares total.");
            }

            for (int i = 1; i <= numbers.length; i++) {
            	int index = Integer.parseInt(numbers[i - 1].trim()) - 1;
                if(index < 0 || index > this.lugares.size()) {
                	throw new Exception("Lugar " + index + " informado para a " + transicao.getNome() + " inválido");
                }
            	
            	Arco arco = new Arco(indexArco++, this.lugares.get(index));

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
	
	private static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
   
    private static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);  
    }
    
    public static void printLugar(List<Lugar> lugares) {
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

    public static void printTransicao(List<Transicao> transicoes) {
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
	
	public int getCurrentCicle() {
		return this.currentCicle;
	}

	public void setCurrentCicle(int currentCicle) {
		this.currentCicle = currentCicle;
	}
}
