package puppy.code;

import com.badlogic.gdx.graphics.Texture;

@NPCType
public class Police extends Npc {
    public Police() {
        super(new Texture("police.png"));
    }

    @Override
    public void interactWith(Playable entity) {
        entity.herir();
    }
}
