package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.schema.GameConfig;
import puppy.code.schema.RainConfig;

public class Gota extends GameObject {
    private int tipo; // RainConfig.TYPE_FRIENDLY o TYPE_EVIL

    public Gota(Texture texturaBuena, Texture texturaMala) {
        super(
            MathUtils.random(0, GameConfig.SCREEN_WIDTH - RainConfig.DROP_WIDTH),
            GameConfig.SCREEN_HEIGHT,
            RainConfig.DROP_WIDTH,
            RainConfig.DROP_HEIGHT,
            0,
            RainConfig.DROP_SPEED
        );

        // Determinar tipo de gota
        this.tipo = MathUtils.random(1, 10) < 5
            ? RainConfig.TYPE_EVIL
            : RainConfig.TYPE_FRIENDLY;

        // Asignar textura según tipo
        this.texture = (this.tipo == RainConfig.TYPE_EVIL)
            ? texturaMala
            : texturaBuena;
    }

    @Override
    public void actualizar(float delta) {
        this.y -= super.velY * delta;
        this.area.setPosition(this.x, this.y);
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(this.texture, this.x, this.y);
    }

    public boolean fueraDePantalla() {
        return this.y + this.height < 0;
    }

    public boolean esMala() {
        return this.tipo == RainConfig.TYPE_EVIL;
    }

    public boolean esBuena() {
        return this.tipo == RainConfig.TYPE_FRIENDLY;
    }
}
