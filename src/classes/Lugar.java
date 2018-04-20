package classes;

public class Lugar {
    private int indice;
    private int marcas;

    public Lugar(int indice) {
        this(indice, 0);
    }

    public Lugar(int indice, int marcas) {
        this.indice = indice;
        this.marcas = marcas;
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
}
