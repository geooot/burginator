import java.awt.*;

/**
 * Created by 164543 on 8/30/2017.
 */
public class BoundingBox {
    private int xPos;
    private int yPos;
    private int xSize;
    private int ySize;
    private Rectangle r;

    public BoundingBox(int xPos, int yPos, int xSize, int ySize) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;

        r = new Rectangle(xPos, yPos, xSize, ySize);
    }

    public int doesItCollide(int x, int y, int width, int height){
        boolean xCollision = r.intersects(new Rectangle(x,y,width,1));
        boolean yCollision = r.intersects(new Rectangle(x,y,1,height));
        if(!xCollision && !yCollision){
            return 0;
        }else if(xCollision && !yCollision){
            return 1;
        }else if(!xCollision){
            return 2;
        }else{
            return 3;
        }
    }
}
