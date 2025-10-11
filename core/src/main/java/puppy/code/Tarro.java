package puppy.code;

import puppy.code.schema.BucketConfig;
import puppy.code.schema.GameConfig;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Tarro extends GameObject {
    private Rectangle bucket;
    private Texture bucketImage;
    private Sound sonidoHerido;
    private int vidas;
    private int puntos;
    private int velx;
    private boolean herido;
    private int tiempoHeridoMax;
    private int tiempoHerido;

    public Tarro(Texture tex, Sound ss) {
        super(
            GameConfig.SCREEN_WIDTH / 2f - BucketConfig.WIDTH / 2f, // posición x
            BucketConfig.START_Y_POSITION,                          // posición y
            BucketConfig.WIDTH,                                     // ancho
            BucketConfig.HEIGHT                                     // alto
        );

        this.bucketImage = tex;
        this.sonidoHerido = ss;
        this.vidas = BucketConfig.START_LIVES;
        this.puntos = 0;
        this.velx = BucketConfig.SPEED;
        this.herido = false;
        this.tiempoHeridoMax = BucketConfig.DAMAGE_ANIMATION_TICKS;

        this.bucket = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public int getVidas() {
        return this.vidas;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public Rectangle getArea() {
        return this.bucket;
    }

    public boolean getHerido() {
        return this.herido;
    }

    public void sumarPuntos(int pp) {
        this.puntos += pp;
    }

    public void crear() {
        this.bucket = new Rectangle();
        this.bucket.x = GameConfig.SCREEN_WIDTH / 2 - BucketConfig.WIDTH / 2;
        this.bucket.y = BucketConfig.START_Y_POSITION;
        this.bucket.width = BucketConfig.WIDTH;
        this.bucket.height = BucketConfig.HEIGHT;
    }

    public void dañar() {
        this.vidas--;
        this.herido = true;
        this.tiempoHerido = this.tiempoHeridoMax;
        this.sonidoHerido.play();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if (!this.herido) {
            batch.draw(this.bucketImage, this.x, this.y);
        }
        else {
            batch.draw(this.bucketImage, this.x, this.y + MathUtils.random(-5, 5));
        }
    }

    @Override
    public void actualizar(float delta) {
        // Movimiento con teclado
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= this.velx * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += this.velx * delta;
        }

        // Limitar movimiento dentro de la pantalla
        if (this.x < 0) {
            this.x = 0;
        }

        if (this.x > GameConfig.SCREEN_WIDTH - this.width) {
            this.x = GameConfig.SCREEN_WIDTH - this.width;
        }

        if (this.herido) {
            this.tiempoHerido--;
            if (this.tiempoHerido <= 0) {
                this.herido = false;
                this.tiempoHerido = 0;
            }
        }
    }

    public void destruir() {
        this.bucketImage.dispose();
    }
}
