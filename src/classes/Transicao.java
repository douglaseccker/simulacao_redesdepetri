package classes;

import java.util.ArrayList;
import java.util.List;

public class Transicao {
    private int indice;
    private List<Arco> entrada = new ArrayList<Arco>();
    private List<Arco> saida = new ArrayList<Arco>();
    private boolean habilitada;

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

    public List<Arco> getSaida() {
        return saida;
    }

    public String getNome() {
        return "T" + this.indice;
    }

    public boolean isHabilitada() {
        return habilitada;
    }

    public void run() {
        if (!this.habilitada) {
            return;
        }

        for (Arco arco : this.entrada) {
            arco.getLugar().removeMarcas(arco.getPeso());
        }

        for (Arco arco : this.saida) {
            arco.getLugar().addMarcas(arco.getPeso());
        }
    }

    public boolean checkHabilitada() {
        boolean hasMarcas = true;

        for (Arco arco : this.entrada) {
            if (!arco.getLugar().hasMarcasSuficientes(arco.getPeso())) {
                hasMarcas = false;
            }
        }

        this.habilitada = hasMarcas;

        return hasMarcas;
    }
}
