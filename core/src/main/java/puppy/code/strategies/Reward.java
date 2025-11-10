package puppy.code.strategies;

import puppy.code.Playable;

public class Reward implements Interaction {
    @Override
    public void interact(Playable entity) {
        entity.sumarPuntos(10);
    }
}
