package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.strategies.Damage;

@NPCType
public class Police extends Npc {
    public Police() {
        super(
            new Texture("police.png"),
            new Damage()
        );
    }

    @Override
    public void interactWith(Playable entity) {
        super.interactionStrategy.interact(entity);
    }
}
