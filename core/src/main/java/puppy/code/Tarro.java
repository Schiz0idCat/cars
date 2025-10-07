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

public class Tarro {
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
        this.bucketImage = tex;
        this.sonidoHerido = ss;
        this.vidas = BucketConfig.START_LIVES;
        this.puntos = 0;
        this.velx = BucketConfig.SPEED;
        this.herido = false;
        this.tiempoHeridoMax = BucketConfig.DAMAGE_ANIMATION_TICKS;
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

    public void dibujar(SpriteBatch batch) {
        if (!this.herido) {
            batch.draw(this.bucketImage, this.bucket.x, this.bucket.y);

            return;
        }

        batch.draw(this.bucketImage, this.bucket.x, this.bucket.y + MathUtils.random(-5,5));
        this.tiempoHerido--;

        if (this.tiempoHerido <= 0) {
            this.herido = false;
        }
    }

    public void actualizarMovimiento() {
        // movimiento desde mouse/touch
        /*if(Gdx.input.isTouched()) {
                  Vector3 touchPos = new Vector3();
                  touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                  camera.unproject(touchPos);
                  bucket.x = touchPos.x - 64 / 2;
            }*/

        //movimiento desde teclado
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.bucket.x -= this.velx * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.bucket.x += this.velx * Gdx.graphics.getDeltaTime();
        }

        // que no se salga de los bordes izq y der
        if (this.bucket.x < 0) {
            bucket.x = 0;
        }

        if (this.bucket.x > GameConfig.SCREEN_WIDTH - BucketConfig.WIDTH) {
            this.bucket.x = GameConfig.SCREEN_WIDTH - BucketConfig.WIDTH;
        }
    }

    public void destruir() {
        this.bucketImage.dispose();
    }
}
