package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Gota “mala”: quita vida al tarro.
 */
public class EvilNPC extends Gota {
    public EvilNPC() {
        super(new Texture("dropBad.png"));
    }

    @Override
    public void interactWith(Playable entity) {
        entity.herir();
    }
}
