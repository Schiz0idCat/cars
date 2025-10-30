package puppy.code;

import org.reflections.Reflections;
import java.util.Set;

import puppy.code.Npc;
import puppy.code.NPCSupplier;

public class NPCRegister {
    public static void registerAll() {
        // Escanea "puppy.code" en busca de @NPCType para cargarlo en memoria
        Reflections reflections = new Reflections("puppy.code");
        Set<Class<?>> npcClasses = reflections.getTypesAnnotatedWith(NPCType.class);

        for (Class<?> cls : npcClasses) {
            // Solo registrar clases que extienden Gota
            if (Npc.class.isAssignableFrom(cls)) {
                @SuppressWarnings("unchecked")
                Class<? extends Npc> npcClass = (Class<? extends Npc>) cls;
                NPCSupplier.register(npcClass);
            }
        }
    }
}
