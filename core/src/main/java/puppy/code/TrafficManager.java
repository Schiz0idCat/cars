package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import puppy.code.schema.NpcConfig;

public class TrafficManager {
    private Array<Npc> npcs;
    private Sound cashSound;
    private Music trafficMusic;
    private long lastDropTime;

    public TrafficManager(Sound cashSound, Music trafficMusic) {
        this.cashSound = cashSound;
        this.trafficMusic = trafficMusic;
    }

    public void crear() {
        this.npcs = new Array<>();
        crearNpc();

        this.trafficMusic.setLooping(true);
        this.trafficMusic.setVolume(0.2f);
        this.trafficMusic.play();
    }

    private void crearNpc() {
        Npc nuevoNpc = NPCSupplier.createRandom();
        this.npcs.add(nuevoNpc);
        this.lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(GameObject car) {
        float delta = Gdx.graphics.getDeltaTime();

        // Generar nuevos NPCs
        if (TimeUtils.nanoTime() - lastDropTime > NpcConfig.NPC_GENERATION_INTERVAL_NS) {
            crearNpc();
        }

        // Recorrer NPCs (de atrás hacia adelante por si se eliminan)
        for (int i = npcs.size - 1; i >= 0; i--) {
            Entity npc = npcs.get(i);
            npc.update(delta);

            // Si sale de pantalla, eliminarla
            if (npc.isOutOfScreen()) {
                npcs.removeIndex(i);
                continue;
            }

            // Si colisiona con el tarro
            // Colisión con el playable
            if (npc.colisionaCon(car)) {
                Playable entity = (Playable) car;

                npc.interactWith(entity);
                npcs.removeIndex(i);

                // Verificar game over si el playable quedó sin vidas
                if (entity.getVidas() <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void actualizarDibujoNpcs(SpriteBatch batch) {
        for (Entity npc : npcs) {
            npc.draw(batch);
        }
    }

    public void pausar() {
        trafficMusic.stop();
    }

    public void continuar() {
        trafficMusic.play();
    }

    public void destruir() {
        cashSound.dispose();
        trafficMusic.dispose();

        for (Npc npc : npcs) {
            npc.destruir();
        }
    }
}
