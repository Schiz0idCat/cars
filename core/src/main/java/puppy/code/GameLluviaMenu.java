package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameLluviaMenu extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private int higherScore;

    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont(); // use libGDX's default Arial font
        AssetManager.getInstance().load();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        AssetManager.getInstance().dispose();
        this.batch.dispose();
        this.font.dispose();
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }

    public BitmapFont getFont() {
        return this.font;
    }

    public int getHigherScore() {
        return this.higherScore;
    }

    public void setHigherScore(int higherScore) {
        this.higherScore = higherScore;
    }
}
