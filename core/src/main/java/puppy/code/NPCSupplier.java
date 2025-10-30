package puppy.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NPCSupplier {
    private static final List<Class<? extends Npc>> registered = new ArrayList<>();
    private static final Random random = new Random();

    public static void register(Class<? extends Npc> npcClass) {
        if (!registered.contains(npcClass)) {
            registered.add(npcClass);
        }
    }

    public static Npc createRandom() {
        if (registered.isEmpty()) {
            throw new IllegalStateException("No NPCs registered in factory");
        }

        int index = random.nextInt(registered.size());

        try {
            return registered.get(index).getDeclaredConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to instantiate NPC", e);
        }
    }
}
