package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.schema.GameConfig;
import puppy.code.schema.RainConfig;

public abstract class Gota extends GameObject implements Entity {
    public Gota(Texture texture) {
        super(
            MathUtils.random(0, GameConfig.SCREEN_WIDTH - RainConfig.DROP_WIDTH),
            GameConfig.SCREEN_HEIGHT,
            RainConfig.DROP_WIDTH,
            RainConfig.DROP_HEIGHT,
            0,
            RainConfig.DROP_SPEED,
            texture
        );
    }

    @Override
    public void update(float delta) {
        this.y -= velY * delta;
        this.area.setPosition(this.x, this.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    @Override
    public boolean isOutOfScreen() {
        return this.y + this.height < 0;
    }

    @Override
    public boolean colisionaCon(GameObject otro) {
        return this.getArea().overlaps(otro.getArea());
    }
}
