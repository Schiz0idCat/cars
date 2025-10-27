package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import puppy.code.schema.RainConfig;

public class Lluvia {
    private Array<Gota> gotas;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Sound dropSound;
    private Music rainMusic;
    private long lastDropTime;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Sound dropSound, Music rainMusic) {
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
    }

    public void crear() {
        this.gotas = new Array<>();
        crearGotaDeLluvia();

        this.rainMusic.setLooping(true);
        this.rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        this.gotas.add(new Gota(gotaBuena, gotaMala));
        this.lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Tarro tarro) {
        float delta = Gdx.graphics.getDeltaTime();

        // Generar gotas nuevas
        if (TimeUtils.nanoTime() - lastDropTime > RainConfig.DROP_GENERATION_INTERVAL_NS) {
            crearGotaDeLluvia();
        }

        // Recorrer gotas (de atrás hacia adelante por si se eliminan)
        for (int i = gotas.size - 1; i >= 0; i--) {
            Gota gota = gotas.get(i);
            gota.update(delta);

            // Si sale de pantalla, eliminarla
            if (gota.isOutOfScreen()) {
                gotas.removeIndex(i);
                continue;
            }

            // Si colisiona con el tarro
            if (gota.colisionaCon(tarro)) {
                if (gota.esMala()) {
                    tarro.herir();
                    gotas.removeIndex(i);

                    if (tarro.getVidas() <= 0) {
                        return false; // Game over
                    }
                } else {
                    tarro.sumarPuntos(10);
                    dropSound.play();
                    gotas.removeIndex(i);
                }
            }
        }

        return true;
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Gota gota : gotas) {
            gota.draw(batch);
        }
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();

        for (Gota gota : gotas) {
            gota.destruir();
        }
    }
}
