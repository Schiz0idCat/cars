package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import puppy.code.schema.GameConfig;
import puppy.code.schema.RainConfig;

public class Lluvia {
    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Sound dropSound;
    private Music rainMusic;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm) {
        this.rainMusic = mm;
        this.dropSound = ss;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
    }

    public void crear() {
        this.rainDropsPos = new Array<Rectangle>();
        this.rainDropsType = new Array<Integer>();
        crearGotaDeLluvia();
        // start the playback of the background music immediately
        this.rainMusic.setLooping(true);
        this.rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, GameConfig.SCREEN_WIDTH - RainConfig.DROP_WIDTH);
        raindrop.y = GameConfig.SCREEN_HEIGHT;
        raindrop.width = RainConfig.DROP_WIDTH;
        raindrop.height = RainConfig.DROP_HEIGHT;

        this.rainDropsPos.add(raindrop);

        // Definimos el tipo de gota usando enteros
        int tipo = MathUtils.random(1, 10) < 5 ? RainConfig.TYPE_EVIL : RainConfig.TYPE_FRIENDLY;
        this.rainDropsType.add(tipo);

        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Tarro tarro) {
        // generar gotas de lluvia
        if (TimeUtils.nanoTime() - lastDropTime > RainConfig.DROP_GENERATION_INTERVAL_NS) {
            crearGotaDeLluvia();
        }

        // revisar si las gotas cayeron al suelo o chocaron con el tarro
        for (int i = 0; i < rainDropsPos.size; i++ ) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= RainConfig.DROP_SPEED * Gdx.graphics.getDeltaTime();

            //cae al suelo y se elimina
            if (raindrop.y + RainConfig.DROP_HEIGHT < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
            }
            if (raindrop.overlaps(tarro.getArea())) { //la gota choca con el tarro
                if (rainDropsType.get(i) == 1) { // gota dañina
                    tarro.dañar();

                    if (tarro.getVidas()<=0) {
                        return false; // si se queda sin vidas retorna falso /game over
                    }

                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                }
                else { // gota a recolectar
                    tarro.sumarPuntos(10);
                    dropSound.play();
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                }
            }
        }

        return true;
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++ ) {
            Rectangle raindrop = rainDropsPos.get(i);

            if(rainDropsType.get(i) == 1) { // gota dañina
                batch.draw(gotaMala, raindrop.x, raindrop.y);
            }
            else {
                batch.draw(gotaBuena, raindrop.x, raindrop.y);
            }
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }
}
