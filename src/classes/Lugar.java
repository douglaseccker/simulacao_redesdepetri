package classes;

public class Lugar {
    private int indice;
    private int marcas;
    private int tempo;

    public Lugar(int indice) {
        this(indice, 1);
    }

    public Lugar(int indice, int marcas) {
        this.indice = indice;
        this.marcas = marcas;
    }

    public Lugar(int indice, int marcas, int tempo) {
        this.indice = indice;
        this.marcas = marcas;
        this.tempo = tempo;
    }

    public boolean hasMarcasSuficientes(int marcasNecessarias) {
        return (this.marcas >= marcasNecessarias);
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getMarcas() {
        return marcas;
    }

    public void setMarcas(int marcas) {
        this.marcas = marcas;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int getTempo() {
        return this.tempo;
    }

    public void addMarcas(int marcas) {
        this.marcas += marcas;
    }

    public void removeMarcas(int marcas) {
        this.marcas -= marcas;
    }

    @Override
    public String toString() {
        return String.format("Sou o lugar L%d e tenho %d", this.indice, this.marcas);
    }

    public String getNome() {
        return "L" + this.indice;
    }
}
