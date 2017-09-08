import org.newdawn.slick.*;
import java.awt.font.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

/**
 * Created by 164543 on 8/28/2017.
 */
public class Game extends BasicGameState {
    private int stateID;
    private ArrayList<Tile> tiles;
    private ArrayList<BoundingBox> bounds;
    private ArrayList<Projectile> projectiles;
    private ArrayList<EnemyCharacter> enemies;
    private int x =0;
    private int requiredHeightToWin = 250;
    private BurgerCharacter mainChar;
    private Image bg;
    java.awt.Font f;
    TrueTypeFont ttf;

    public Game(int stateID) throws SlickException {
        this.stateID = stateID;

    }

    public void startup(){
        tiles = new ArrayList<>();
        bounds = new ArrayList<>();
        mainChar = new BurgerCharacter();
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        startup();
        mainChar.init();
        bg = new Image("images/background.png");

        for(int x = 0; x < 3; x++){
            addRandomItemToMainChar();
        }

        for(int x = 0; x < 40; x++){
            for(int y = 0; y < 16; y++){
                Tile t;
                if(y == 12){
                    t = new GrassTop(x * 50,y * 50);
                    tiles.add(t);
                }else if(y > 12){
                    t = new GrassBottom(x * 50, y * 50);
                    tiles.add(t);
                }

            }
        }
        
        bounds.add(new BoundingBox(0, 600, 2000, 150));
        EnemyCharacter e = new EnemyCharacter(1800,0);
        e.init();
        enemies.add(e);

        f = new java.awt.Font("Verdana", java.awt.Font.BOLD, 40);

        ttf = new TrueTypeFont(f, true);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        float camPos = (mainChar.getXPos() >= 1270 ? 1270 - 550 : mainChar.getXPos() - 550);
        if(mainChar.getXPos() >= 550){
            g.translate(-camPos, 0);
        }

        g.setColor(Color.blue);

        g.drawImage(bg, 0,-120,0,0,2000,720);

        int percentDone = (int)Math.round(100*((double)mainChar.getYSize() / (double)requiredHeightToWin));



        ttf.drawString(mainChar.getXPos() >= 550 ? camPos + 30 : 30,30, percentDone + "% " + (percentDone  == 100 ? "DONE! GO TO THE EXIT!" : "ideal burger"), Color.blue);

        for(Tile t : tiles){
            t.render(g);
        }
        for(BoundingBox b : bounds){
            b.render(g);
        }

        mainChar.render(g, bounds);

        ArrayList<EnemyCharacter> enemiesToBeRemoved = new ArrayList<>();
        for(EnemyCharacter enemy : enemies){
            enemy.render(g, bounds, mainChar);
            if(enemy.getHealth() <= 0){
                enemiesToBeRemoved.add(enemy);
                for(int x = 0; x < enemy.getStartingHealth() + 1; x++){
                    addRandomItemToMainChar();
                }
            }
        }

        enemies.removeAll(enemiesToBeRemoved);

        ArrayList<Projectile> toBeRemoved = new ArrayList<>();
        for(Projectile p : projectiles){
            if(p.isActive){
                p.render(g, bounds, enemies);
            }else{
                toBeRemoved.add(p);
            }
        }

        projectiles.removeAll(toBeRemoved);

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

        if(gameContainer.getInput().isKeyPressed(Input.KEY_Z)){
            addRandomItemToMainChar();
        }
        if(gameContainer.getInput().isKeyPressed(Input.KEY_X)){
            if(mainChar.items.size() > 0) {
                Projectile p = mainChar.shootItem();
                projectiles.add(p);
            }

        }

        if(mainChar.items.size() == 0){
            stateBasedGame.enterState(Main.GAME_OVER_STATE, new FadeOutTransition(Color.white, 500), new FadeInTransition(Color.white, 500));
        }

        if(enemies.size() < 1){
            int ran = (int)(Math.random() * 2) + 1;
            EnemyCharacter e = new EnemyCharacter(ran == 1 ? (int) mainChar.getXPos() - 650: (int) mainChar.getXPos() + 650,0);
            e.init();
            enemies.add(e);
        }

        if(mainChar.getXPos() >= 1700  && mainChar.getYSize() >= requiredHeightToWin){
            stateBasedGame.enterState(Main.WIN_STATE, new FadeOutTransition(Color.white, 500), new FadeInTransition(Color.white, 500));
        }

    }


    @Override
    public int getID() {
        return stateID;
    }

    private void addRandomItemToMainChar(){
        int randomNum = (int)(Math.random() * 3);
        switch (randomNum){
            case 0:
                mainChar.addToStack(mainChar.patty);
                break;
            case 1:
                mainChar.addToStack(mainChar.lettuce);
                break;
            case 2:
                mainChar.addToStack(mainChar.cheese);
                break;
        }
    }
}
