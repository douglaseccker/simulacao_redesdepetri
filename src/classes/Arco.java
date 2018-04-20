package classes;

public class Arco {
    private int indice;
    private Lugar lugar;
    private Transicao transicao;
    private int peso;
    private Direcao direcao;

    public enum Direcao { LUGAR_TRANSICAO, TRANSICAO_LUGAR };

    public Arco(int indice, Lugar lugar, int peso) {
        this.indice = indice;
        this.lugar = lugar;
        this.peso = peso;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Transicao getTransicao() {
        return transicao;
    }

    public void setTransicao(Transicao transicao) {
        this.transicao = transicao;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    public void setDirecao(Direcao direcao) {
        this.direcao = direcao;
    }
}
