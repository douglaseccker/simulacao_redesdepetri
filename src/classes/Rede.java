package classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.String;

public class Rede {
    private List<Lugar> lugares = new ArrayList<Lugar>();
    private List<Transicao> transicoes = new ArrayList<Transicao>();
    private int lugaresNum;
    private int transicoesNum;
    private int currentCicle;
    private int tempo;
    private FileHandler file;

    public Rede() {
        this.currentCicle = 1;
        this.file = new FileHandler();
    }

    public void run() {
        Scanner keyIn = null;

        try {
            keyIn = new Scanner(System.in);

            System.out.print("Pressione enter para rodar o " + this.currentCicle + "º ciclo. ");

            keyIn.nextLine();

            boolean canRun = false;

            for (Transicao transicao : this.getTransicoes()) {
                transicao.run();
            }

            for (Transicao transicao : this.getTransicoes()) {
                canRun |= transicao.checkHabilitada();
            }

            this.printRede();

            if (canRun) {
                this.currentCicle++;
                this.run();
            } else {
                System.out.println("Final");
            }
        } finally {
            if (keyIn != null) {
                keyIn.close();
            }
        }
    }

    public void montaRedeArquivo(Scanner scan) throws Exception {
        System.out.println("Informe o nome do arquivo de leitura da rede: ");
        String fileName = scan.next();
        String content = "";

        try {
            content = file.read(fileName);

            if (content.equals("")) {
                throw new Exception("Arquivo vazio.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            this.montaRedeArquivo(scan);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.montaRedeArquivo(scan);
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

            if (numbers.length > this.lugares.size()) {
                throw new Exception("N�mero de lugares informado para a " + transicao.getNome() +
                         " maior do que o n�mero de lugares total.");
            }

            for (int i = 1; i <= numbers.length; i++) {
                int index = Integer.parseInt(numbers[i - 1].trim()) - 1;

                if (index < 0 || index > this.lugares.size()) {
                    throw new Exception("Lugar " + index + " informado para a " + transicao.getNome() + " inv�lido");
                }

                Arco arco = new Arco(i, this.lugares.get(index));

                transicao.addEntrada(arco);
            }

            currentLine++;
        }

        //Adicionada vari�vel indexArco exclusivamente para �ndices dos arcos n�o repetirem
        int indexArco = 1;

        for (Transicao transicao : this.transicoes) {
            String numberStr = lines[currentLine];
            String[] numbers = numberStr.trim().split(",");

            //Verifica se os �ndices s�o v�lidos
            if (numbers.length > this.lugares.size()) {
                throw new Exception("N�mero de lugares informado para a " + transicao.getNome() +
                         " maior do que o n�mero de lugares total.");
            }

            for (int i = 1; i <= numbers.length; i++) {
                //Verifica se o �ndice � v�lido
                int index = Integer.parseInt(numbers[i - 1].trim()) - 1;

                if (index < 0 || index > this.lugares.size()) {
                    throw new Exception("Lugar " + index + " informado para a " + transicao.getNome() + " inv�lido");
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

        System.out.println("Rede inicial:");
        this.printRede();
    }

    public void montaRedeInterativa(Scanner scan) throws Exception {
        System.out.print("Quantos lugares? ");
        this.lugaresNum = scan.nextInt();
        file.addLine(this.lugaresNum);

        System.out.print("Quantas transicoes? ");
        this.transicoesNum = scan.nextInt();
        file.addLine(this.transicoesNum);

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
            file.addLine(numberStr);

            if (numbers.length > this.lugares.size()) {
                throw new Exception("N�mero de lugares informado para a " + transicao.getNome() +
                         " maior do que o n�mero de lugares total.");
            }

            for (int i = 1; i <= numbers.length; i++) {
                int index = Integer.parseInt(numbers[i - 1].trim()) - 1;

                if (index < 0 || index > this.lugares.size()) {
                    throw new Exception("Lugar " + index + " informado para a " + transicao.getNome() + " inv�lido");
                }

                Arco arco = new Arco(i, this.lugares.get(index));

                transicao.addEntrada(arco);
            }
        }

        int indexArco = 1;

        for (Transicao transicao : this.transicoes) {
            System.out.printf("Quais os lugares de saida de %s? ", transicao.getNome());

            String numberStr = scan.nextLine();
            String[] numbers = numberStr.trim().split(",");
            file.addLine(numberStr);

            if (numbers.length > this.lugares.size()) {
                throw new Exception("N�mero de lugares informado para a " + transicao.getNome() +
                         " maior do que o n�mero de lugares total.");
            }

            for (int i = 1; i <= numbers.length; i++) {
                int index = Integer.parseInt(numbers[i - 1].trim()) - 1;

                if (index < 0 || index > this.lugares.size()) {
                    throw new Exception("Lugar " + index + " informado para a " + transicao.getNome() + " inv�lido");
                }

                Arco arco = new Arco(indexArco++, this.lugares.get(index));

                transicao.addSaida(arco);
            }
        }

        for (Lugar lugar : this.lugares) {
            System.out.printf("Quantas marcas em %s? ", lugar.getNome());

            int marcasLugar = scan.nextInt();

            lugar.setMarcas(marcasLugar);
            file.addLine(marcasLugar);
        }

        for (Transicao transicao : this.transicoes) {
            for (Arco arco : transicao.getEntrada()) {
                System.out.printf("Qual o peso do arco de %s para %s? ", arco.getLugar().getNome(),
                        transicao.getNome());

                int pesoArco = scan.nextInt();

                arco.setPeso(pesoArco);
                file.addLine(pesoArco);
            }

            for (Arco arco : transicao.getSaida()) {
                System.out.printf("Qual o peso do arco de %s para %s? ", transicao.getNome(),
                        arco.getLugar().getNome());

                int pesoArco = scan.nextInt();

                arco.setPeso(pesoArco);
                file.addLine(pesoArco);
            }

            transicao.checkHabilitada();
        }

        System.out.print("Digite o nome do arquivo para salvar a rede: ");
        String fileName = scan.next();

        while (fileName == "") {
            System.out.print("Digite um nome de arquivo válido: ");
            fileName = scan.next();
        }

        file.save(fileName);

        System.out.println();
        System.out.println("Rede inicial:");
        this.printRede();
    }

    /*private String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }*/

    private String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public void printLugar(List<Lugar> lugares) {
        String borderTable = "+----------+";
        String lugarLine = "| Lugar    |";
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

    public void printTransicao(List<Transicao> transicoes) {
        String borderTable = "+------------+";
        String transicaoLine = "| Transicao  |";
        String habilitadaLine = "| Habilitada |";

        for (Transicao transicao : transicoes) {
            borderTable += "----+";
            transicaoLine += padLeft(transicao.getNome(), 3) + " |";

            if (transicao.isHabilitada()) {
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

    public void printRede() {
        System.out.println();
        this.printLugar(this.getLugares());
        this.printTransicao(this.getTransicoes());
        System.out.println();
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

    public int getTempo() {
        return this.tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
}
