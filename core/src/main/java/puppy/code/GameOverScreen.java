package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.schema.GameConfig;

public class GameOverScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    public GameOverScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);

        this.batch.begin();
        this.camera.setToOrtho(false, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        this.font.draw(this.batch, "Chocaste, Suerte para la proxima!!!!", 100, GameConfig.SCREEN_HEIGHT / 2 + 50);
        this.font.draw(this.batch, "Toca en cualquier lado para volver a intentarlo", 100, GameConfig.SCREEN_HEIGHT / 2 - 50);
        this.batch.end();

        if (Gdx.input.isTouched()) {
            this.game.setScreen(new GameScreen(this.game));
            dispose();
        }
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
