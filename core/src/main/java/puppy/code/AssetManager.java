package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import java.util.HashMap;
import java.util.Map;

public class AssetManager implements Disposable {
    private static final AssetManager instance = new AssetManager();
    
    private Map<String, Texture> textures;
    private Map<String, Sound> sounds;
    private Map<String, Music> musics;

    private AssetManager() {
        textures = new HashMap<>();
        sounds = new HashMap<>();
        musics = new HashMap<>();
    }
    public static AssetManager getInstance() {
        return instance;
    }

    public void load(){
        textures.put("car", new Texture(Gdx.files.internal("car.png")));
        textures.put("police", new Texture(Gdx.files.internal("police.png")));
        textures.put("cash", new Texture(Gdx.files.internal("cash.png")));
        textures.put("background", new Texture(Gdx.files.internal("backGround.png")));
        textures.put("startScreen", new Texture(Gdx.files.internal("startScreen.png")));
        textures.put("gameOver", new Texture(Gdx.files.internal("gameOver.png")));
        textures.put("pause", new Texture(Gdx.files.internal("pauseScreen.png")));

        sounds.put("hurt", Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")));
        sounds.put("cash", Gdx.audio.newSound(Gdx.files.internal("cash.wav")));

        musics.put("traffic", Gdx.audio.newMusic(Gdx.files.internal("traffic.ogg")));
    }
    public Texture getTexture(String key) {
        if(!textures.containsKey(key)){
            throw new IllegalArgumentException("Texture with key " + key + " not found.");
        }
        return textures.get(key);
    }
    public Sound getSound(String key) {
        if(!sounds.containsKey(key)){
            throw new IllegalArgumentException("Sound with key " + key + " not found.");
        }
        return sounds.get(key);
    }
    public Music getMusic(String key) {
        if(!musics.containsKey(key)){
            throw new IllegalArgumentException("Music with key " + key + " not found.");
        }
        return musics.get(key);
    }

    @Override
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        for (Sound sound : sounds.values()) {
            sound.dispose();
        }
        for (Music music : musics.values()) {
            music.dispose();
        }
        textures.clear();
        sounds.clear();
        musics.clear();
    }
}