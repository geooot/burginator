import org.newdawn.slick.*;
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
    private int x =0;
    private BurgerCharacter mainChar;

    public Game(int stateID) throws SlickException {
        this.stateID = stateID;
        tiles = new ArrayList<>();
        bounds = new ArrayList<>();
        mainChar = new BurgerCharacter();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        mainChar.init();
        for(int x = 0; x < 40; x++){
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
        
        bounds.add(new BoundingBox(0, 600, 2000, 150));
        bounds.add(new BoundingBox(300,400,100,200));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        if(mainChar.getXPos() >= 550){
            g.translate(-(mainChar.getXPos() - 550), 0);
        }

        g.setColor(Color.blue);
        for(Tile t : tiles){
            t.render(g);
        }
        for(BoundingBox b : bounds){
            b.render(g);
        }


        mainChar.render(g, bounds);


    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
            mainChar.goLeft();
        }
        if(gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)){
            mainChar.goRight();
        }
        if(gameContainer.getInput().isKeyDown(Input.KEY_UP)){
            mainChar.jump();
        }

    }


    @Override
    public int getID() {
        return stateID;
    }
}
