package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Gota “buena”: suma puntos y reproduce un sonido.
 */
public class GoodNPC extends Gota {
    private final Sound dropSound;

    public GoodNPC(Sound dropSound) {
        super(new Texture("drop.png"));
        this.dropSound = dropSound;
    }

    @Override
    public void interactWith(Playable entity) {
        entity.sumarPuntos(10);
        dropSound.play();
    }
}
