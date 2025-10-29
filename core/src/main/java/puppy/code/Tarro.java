package puppy.code;

import puppy.code.schema.BucketConfig;
import puppy.code.schema.GameConfig;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Representa el tarro controlado por el jugador.
 * Puede moverse, recibir daño y acumular puntos.
 */
public class Tarro extends GameObject implements Playable, Entity {
    private final Sound sonidoHerido;
    private int vidas;
    private int puntos;
    private boolean herido;
    private int tiempoHeridoMax;
    private float tiempoHeridoActual;

    public Tarro(Texture tex, Sound ss) {
        super(
            GameConfig.SCREEN_WIDTH / 2f - BucketConfig.WIDTH / 2f, // posición x
            BucketConfig.START_Y_POSITION,                          // posición y
            BucketConfig.WIDTH,                                     // ancho
            BucketConfig.HEIGHT,                                    // alto
            BucketConfig.SPEED,
            0,
            tex
        );

        this.sonidoHerido = ss;
        this.vidas = BucketConfig.START_LIVES;
        this.puntos = 0;
        this.herido = false;
        this.tiempoHeridoMax = BucketConfig.DAMAGE_ANIMATION_TICKS;
        this.tiempoHeridoActual = 0;
    }

    // === Getters ===
    @Override
    public int getVidas() { return this.vidas; }

    @Override
    public int getPuntos() { return this.puntos; }

    @Override
    public boolean getHerido() { return this.herido; }

    // === Lógica principal ===
    @Override
    public void sumarPuntos(int pp) {
        this.puntos += pp;
    }

    public void crear() {
        this.setPos(GameConfig.SCREEN_WIDTH / 2f - this.width / 2f, BucketConfig.START_Y_POSITION);
    }

    @Override
    public void herir() {
        if (this.herido) return; // evita reactivar daño si ya está en animación
        this.vidas--;
        this.herido = true;
        this.tiempoHeridoActual = this.tiempoHeridoMax;
        this.sonidoHerido.play();
    }

    @Override
    public void animacionHerido(float delta) {
        if (!this.herido) return;

        this.tiempoHeridoActual -= delta * GameConfig.FPS; // si DAMAGE_ANIMATION_TICKS son frames
        if (this.tiempoHeridoActual <= 0) {
            this.herido = false;
            this.tiempoHeridoActual = 0;
        }
    }

    @Override
    public void interactWith(Playable entity) {
        this.herir();
    }

    @Override
    public void update(float delta) {
        // Movimiento
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= this.velX * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += this.velX * delta;
        }

        // Limitar dentro de pantalla

        if (this.isOutOfScreen()) {
            // Si se sale por la izquierda o derecha, se corrige la posición
            if (this.x < 0) {
                this.x = 0;
            }
            else if (this.x + this.width > GameConfig.SCREEN_WIDTH) {
                this.x = GameConfig.SCREEN_WIDTH - this.width;
            }
        }
        if (this.x < 0) {
            this.x = 0;
        }

        if (this.x > GameConfig.SCREEN_WIDTH - this.width) {
            this.x = GameConfig.SCREEN_WIDTH - this.width;
        }

        // Actualizar rectángulo de colisión
        this.area.setPosition(this.x, this.y);

    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!this.herido) {
            batch.draw(this.texture, this.x, this.y);
        } else {
            float offsetY = MathUtils.random(-5, 5);
            batch.draw(this.texture, this.x, this.y + offsetY);
        }
    }

    @Override
    public boolean isOutOfScreen() {
        return this.x < 0
        || this.x + this.width > GameConfig.SCREEN_WIDTH
        || this.y + this.height < 0
        || this.y > GameConfig.SCREEN_HEIGHT;
    }

    @Override
    public void destruir() {
        super.destruir(); // libera textura
    }
}
