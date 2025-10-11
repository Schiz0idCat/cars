package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {
    protected float x, y;
    protected int width, height;

    public GameObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void actualizar(float delta);

    public abstract void dibujar(SpriteBatch batch);
}
