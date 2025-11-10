package puppy.code.strategies;

import puppy.code.Playable;

public class Damage implements Interaction {
    @Override
    public void interact(Playable entity) {
        entity.herir();
    }
}
