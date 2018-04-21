import classes.Arco;
import classes.Lugar;
import classes.Transicao;

public class Main {
	public static void main(String[] args) {
		Lugar lugar1 = new Lugar(1, 2);
		Lugar lugar2 = new Lugar(2, 2);
		Lugar lugar3 = new Lugar(3, 2);

		Arco arco1 = new Arco(1, lugar1, 2);
		Arco arco2 = new Arco(2, lugar2, 1);
		Arco arco3 = new Arco(4, lugar3, 3);

		Transicao transicao = new Transicao(1);

		transicao.addEntrada(arco1);
		transicao.addEntrada(arco2);
		transicao.addSaida(arco3);

		transicao.run();
	}
}
