package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Drawable {
    void draw(SpriteBatch batch);

    void update(float delta);

    boolean isOutOfScreen();
}
