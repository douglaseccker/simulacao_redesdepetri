package classes;

public class Arco {
    private int indice;
    private Lugar lugar;
    private int peso;

    public Arco(int indice, Lugar lugar) {
        this(indice, lugar, 1);
    }

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

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public String toString() {
        return "A" + this.indice;
    }
}
