package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.schema.GameConfig;
import com.badlogic.gdx.graphics.Texture;

public class PausaScreen implements Screen {
	private final GameLluviaMenu game;
	private GameScreen juego;
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
    private Texture pauseTexture;

	public PausaScreen (final GameLluviaMenu game, GameScreen juego) {
		this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
        this.font = game.getFont();
		camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        pauseTexture = AssetManager.getInstance().getTexture("pauseScreen");
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
        batch.draw(pauseTexture, 0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        font.getData().setScale(2f, 2f); // si quieres mantener
        font.draw(batch, "Juego en Pausa ", 100, GameConfig.SCREEN_HEIGHT - 150);
        font.draw(batch, "Presiona Enter o toca en cualquier lado para continuar !!!", 100, GameConfig.SCREEN_HEIGHT - 200);
        batch.end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.setScreen(juego);
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
