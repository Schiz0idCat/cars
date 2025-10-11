package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected Texture texture;
    protected Rectangle area;

    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.area = new Rectangle(x, y, width, height);
    }

    public abstract void actualizar(float delta);

    public abstract void dibujar(SpriteBatch batch);

    public void destruir() {
        if (this.texture != null) {
            this.texture.dispose();
        }
    }

    public Rectangle getArea() {
        this.area.setPosition(this.x, this.y);
        return this.area;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
        this.area.setPosition(x, y);
    }

    public boolean colisionaCon(GameObject otro) {
        return this.getArea().overlaps(otro.getArea());
    }
}
