package puppy.code;

import com.badlogic.gdx.graphics.Texture;

@NPCType
public class EvilNPC extends Gota {
    public EvilNPC() {
        super(new Texture("dropBad.png"));
    }

    @Override
    public void interactWith(Playable entity) {
        entity.herir();
    }
}
