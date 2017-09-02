/**
 * Created by George Thayamkery on 8/24/2017.
 */

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

public class Main extends StateBasedGame {

    public static final int MENU_STATE = 0;
    public static final int GAME_STATE = 1;
    public static final int GAME_OVER_STATE = 2;
    public static final int WIN_STATE = 3;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public Main(String title) throws IOException {
        super(title);
        //add states
        addState(new Game(GAME_STATE));
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        enterState(GAME_STATE, new EmptyTransition(), new FadeInTransition(Color.black, 500));
    }

    public static void main(String[] argv) throws IOException {
        try {
            AppGameContainer container = new AppGameContainer(new Main("Burginator"));
            container.setDisplayMode(WIDTH, HEIGHT, false);
            container.setTargetFrameRate(60);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}