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

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;

    //boolean activo = true;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        // load the images for the droplet and the bucket, 64x64 pixels each
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        this.tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")),hurtSound);

        // load the drop sound effect and the rain background "music"
        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        this.lluvia = new Lluvia(gota, gotaMala, dropSound, rainMusic);

        // camera
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        this.batch = new SpriteBatch();
        // creacion del tarro
        this.tarro.crear();

        // creacion de la lluvia
        this.lluvia.crear();
    }

    @Override
    public void render(float delta) {
        //limpia la pantalla con color azul obscuro.
        ScreenUtils.clear(0, 0, 0.2f, 1);
        //actualizar matrices de la cámara
        this.camera.update();
        //actualizar
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();
        //dibujar textos
        this.font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        this.font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        this.font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth/2-50, 475);

        if (!this.tarro.getHerido()) {
            // movimiento del tarro desde teclado
            this.tarro.actualizarMovimiento();

            // caida de la lluvia
            if (!this.lluvia.actualizarMovimiento(this.tarro)) {
                //actualizar HigherScore
                if (this.game.getHigherScore() < this.tarro.getPuntos()) {
                    this.game.setHigherScore(tarro.getPuntos());
                }

                //ir a la ventana de finde juego y destruir la actual
                this.game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        this.tarro.dibujar(batch);
        this.lluvia.actualizarDibujoLluvia(batch);

        this.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // continuar con sonido de lluvia
        this.lluvia.continuar();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        this.lluvia.pausar();
        this.game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        this.tarro.destruir();
        this.lluvia.destruir();
    }
}
