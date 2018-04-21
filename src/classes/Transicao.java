package classes;

import java.util.ArrayList;
import java.util.List;

public class Transicao {
    private int indice;
    private List<Arco> entrada = new ArrayList<Arco>();
    private List<Arco> saida = new ArrayList<Arco>();
    //private boolean habilitada;

    public Transicao(int indice) {
        this.indice = indice;
        this.entrada = new ArrayList<Arco>();
        this.saida = new ArrayList<Arco>();
    }

    public void addEntrada(Arco arco) {
        this.entrada.add(arco);
    }

    public void addSaida(Arco arco) {
        this.saida.add(arco);
    }

    public List<Arco> getEntrada() {
        return entrada;
    }

    public void run()
    {
        boolean tem = true;

        System.out.println("entrada");

        for (Arco arco : entrada) {
            //if (arco.getDirecao() == Arco.Direcao.LUGAR_TRANSICAO) {
                System.out.println(arco.getLugar().toString());

                if (!arco.getLugar().hasMarcasSuficientes(arco.getPeso()))
                {
                    tem = false;
                }
            //}
        }

        if (!tem) return;

        System.out.println("saida");

        for (Arco arco : saida) {
            System.out.println(arco.getLugar().toString());

            //if (arco.getDirecao() == Arco.Direcao.LUGAR_TRANSICAO) {
            arco.getLugar().addMarcas(arco.getPeso());
            //}

            System.out.println(arco.getLugar().toString());
        }

        System.out.println("dps de tirar marcar");
        for (Arco arco : entrada) {
            arco.getLugar().removeMarcas(arco.getPeso());

            System.out.println(arco.getLugar().toString());
        }
    }

	public String getNome() {
		return "T" + this.indice;
	}
}
