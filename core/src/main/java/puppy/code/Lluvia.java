package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import puppy.code.schema.RainConfig;

public class Lluvia {
    private Array<Gota> gotas;
    private Sound dropSound;
    private Music rainMusic;
    private long lastDropTime;

    public Lluvia(Sound dropSound, Music rainMusic) {
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
        Gota nuevaGota = NPCSupplier.createRandom();
        this.gotas.add(nuevaGota);
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
            // Colisión con el playable
            if (gota.colisionaCon(tarro)) {
                gota.interactWith(tarro);
                gotas.removeIndex(i);

                // Verificar game over si el playable quedó sin vidas
                if (tarro.getVidas() <= 0) {
                    return false;
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
