package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

@NPCType
public class GoodNPC extends Gota {
    private final Sound dropSound;

    public GoodNPC() {
        super(new Texture("drop.png"));
        this.dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    }

    @Override
    public void interactWith(Playable entity) {
        entity.sumarPuntos(10);
        dropSound.play();
    }
}
