import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {
    private int stateID;
    private Image gameOverImage;

    public GameOver(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        gameOverImage = new Image("images/GameOver.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        g.drawImage(gameOverImage, 440,160, 0,0, 400, 400);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyPressed(Input.KEY_R)) {
            stateBasedGame.getState(Main.GAME_STATE).init(gameContainer, stateBasedGame);
            stateBasedGame.enterState(Main.GAME_STATE);
        }
    }

    @Override
    public int getID() {
        return stateID;
    }

}
