import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by 164543 on 8/28/2017.
 */
public abstract class Tile {
    private int xPos;
    private int yPos;
    private int xSize;
    private int ySize;

    public Tile(int x, int y){
        xPos = x;
        yPos = y;
        xSize = 50;
        ySize = 50;
    }

    abstract void render(Graphics g);

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public int getXSize() {
        return xSize;
    }

    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }
}


class TestTile extends Tile{
    private Image tileImage;

    public TestTile(int x, int y) throws SlickException {
        super(x, y);
        tileImage = new Image("/images/TestTile.png");
    }

    @Override
    void render(Graphics g) {
        g.drawImage(tileImage, getXPos(), getYPos(), 0,0, getXSize(), getXSize());
    }
}

class GrassTop extends Tile{
    private Image tileImage;

    public GrassTop(int x, int y) throws SlickException {
        super(x, y);
        tileImage = new Image("/images/GrassTop.png");
    }

    @Override
    void render(Graphics g) {
        g.drawImage(tileImage, getXPos(), getYPos(), 0,0, getXSize(), getXSize());
    }
}

class GrassBottom extends Tile{
    private Image tileImage;

    public GrassBottom(int x, int y) throws SlickException {
        super(x, y);
        tileImage = new Image("/images/GrassBottom.png");
    }

    @Override
    void render(Graphics g) {
        g.drawImage(tileImage, getXPos(), getYPos(), 0,0, getXSize(), getXSize());
    }
}