package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import puppy.code.strategies.Reward;

@NPCType
public class Cash extends Npc {
    private final Sound cashSound;

    public Cash() {
        super(
            AssetManager.getInstance().getTexture("cash"),
            new Reward()
        );
        this.cashSound = AssetManager.getInstance().getSound("cash");
    }

    @Override
    public void interactWith(Playable entity) {
        super.interactionStrategy.interact(entity);
        cashSound.play();
    }
}
