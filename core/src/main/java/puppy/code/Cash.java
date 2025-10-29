package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

@NPCType
public class Cash extends Npc {
    private final Sound cashSound;

    public Cash() {
        super(new Texture("cash.png"));
        this.cashSound = Gdx.audio.newSound(Gdx.files.internal("cash.wav"));
    }

    @Override
    public void interactWith(Playable entity) {
        entity.sumarPuntos(10);
        cashSound.play();
    }
}
