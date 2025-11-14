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
    private Texture pauseTexture;

    //boolean activo = true;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        NPCRegister.registerAll();

        //load the image for the background
        this.backgroundTexture = AssetManager.getInstance().getTexture("background"); 

        // load the car texture and sound, create the car object
        Sound hurtSound = AssetManager.getInstance().getSound("hurt");
        this.car = new Car(AssetManager.getInstance().getTexture("car"), hurtSound);
        
        // load the cash sound effect and the traffic background music
        Sound cashSound = AssetManager.getInstance().getSound("cash");

        Music trafficMusic = AssetManager.getInstance().getMusic("traffic");
        this.trafficManager = new TrafficManager(cashSound, trafficMusic);

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

        if(Gdx.input.isKeyPressed(Input.Keys.P)) {
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
        
    }
}
