package puppy.code;

public interface Playable {
    int getVidas();

    int getPuntos();

    boolean getHerido();

    void animacionHerido(float delta);

    void herir();

    void sumarPuntos(int pp);
}
