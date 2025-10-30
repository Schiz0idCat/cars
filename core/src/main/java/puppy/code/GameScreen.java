package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input;

import puppy.code.schema.GameConfig;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Car car;
    private TrafficManager trafficManager;
    private Texture backgroundTexture;

    //boolean activo = true;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        NPCRegister.registerAll();

        //load the image for the background
        this.backgroundTexture = new Texture(Gdx.files.internal("background.png")); 

        // load the images for the droplet and the bucket, 64x64 pixels each
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        this.car = new Car(new Texture(Gdx.files.internal("car.png")),hurtSound);

        // load the drop sound effect and the rain background "music"
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("cash.wav"));

        Music trafficMusic = Gdx.audio.newMusic(Gdx.files.internal("traffic.ogg"));
        this.trafficManager = new TrafficManager(dropSound, trafficMusic);

        // camera
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        this.batch = new SpriteBatch();
        // creacion del car
        this.car.crear();

        // creacion de la lluvia
        this.trafficManager.crear();
    }

    @Override
    public void render(float delta) {
        //actualizar matrices de la cámara
        this.camera.update();
        //actualizar
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();
        //limpia la pantalla con color azul obscuro.
        batch.draw(backgroundTexture, 0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        //dibujar textos
        this.font.draw(batch, "Dinero: " + car.getPuntos(), 5, GameConfig.SCREEN_HEIGHT - 5);
        this.font.draw(batch, "Vidas : " + car.getVidas(), GameConfig.SCREEN_WIDTH - 130, GameConfig.SCREEN_HEIGHT - 5);
        this.font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth/2 - 50, GameConfig.SCREEN_HEIGHT - 5);

        if (!this.car.getHerido()) {
            // movimiento del car
            this.car.update(delta);

            // caída del tráfico
            if (!this.trafficManager.actualizarMovimiento(this.car)) {
                if (this.game.getHigherScore() < this.car.getPuntos()) {
                    this.game.setHigherScore(car.getPuntos());
                }

                this.game.setScreen(new GameOverScreen(game));
                dispose();
            }
        } else {
            this.car.animacionHerido(delta);
        }

        this.car.draw(batch);
        this.trafficManager.actualizarDibujoNpcs(batch);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            pause();
        }

        this.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // continuar con sonido del tráfico
        this.trafficManager.continuar();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        this.trafficManager.pausar();
        this.game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        this.car.destruir();
        this.trafficManager.destruir();
        this.backgroundTexture.dispose();
    }
}
