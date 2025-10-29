package puppy.code;

import org.reflections.Reflections;
import java.util.Set;

import puppy.code.Gota;
import puppy.code.NPCSupplier;

public class NPCRegister {
    public static void registerAll() {
        // Escanea el paquete "puppy.code" para todas las clases con @NPCType
        Reflections reflections = new Reflections("puppy.code");
        Set<Class<?>> npcClasses = reflections.getTypesAnnotatedWith(NPCType.class);

        for (Class<?> cls : npcClasses) {
            // Solo registrar clases que extienden Gota
            if (Gota.class.isAssignableFrom(cls)) {
                @SuppressWarnings("unchecked")
                Class<? extends Gota> npcClass = (Class<? extends Gota>) cls;
                NPCSupplier.register(npcClass);
            }
        }
    }
}
