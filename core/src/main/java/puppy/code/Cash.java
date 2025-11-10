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
            new Texture("cash.png"),
            new Reward()
        );
        this.cashSound = Gdx.audio.newSound(Gdx.files.internal("cash.wav"));
    }

    @Override
    public void interactWith(Playable entity) {
        super.interactionStrategy.interact(entity);
        cashSound.play();
    }
}
