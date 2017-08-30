import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

/**
 * Created by 164543 on 8/28/2017.
 */
public class Game extends BasicGameState {
    private int stateID;
    private ArrayList<Tile> tiles;
    private ArrayList<BoundingBox> bounds;

    public Game(int stateID){
        this.stateID = stateID;
        tiles = new ArrayList<>();
        bounds = new ArrayList<>();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        for(int x = 0; x < 100; x++){
            for(int y = 0; y < 16; y++){
                Tile t;
                if(y == 12){
                    t = new GrassTop(x * 50,y * 50);
                }else if(y > 12){
                    t = new GrassBottom(x * 50, y * 50);
                }else{
                    t = new TestTile(x * 50, y * 50);
                }

                tiles.add(t);
            }
        }
        
        bounds.add(new BoundingBox(0, 120, 5000, 150));

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        g.setColor(Color.blue);
        for(Tile t : tiles){
            t.render(g);
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return stateID;
    }
}
