package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Entity {
    void draw(SpriteBatch batch);
    void update(float delta);
    boolean isOutOfScreen();
    void interactWith(Playable entity);
    boolean colisionaCon(GameObject entity);
}
