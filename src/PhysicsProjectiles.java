import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;

class Projectile extends CharacterWithPhysics{
    Image item;
    public boolean isActive = true;

    public Projectile(Image withImage, float xPos, float yPos, int direction){
        setVelocityY(1.0f);
        setVelocityX(25.0f * direction);
        setGravity(0.01f);
        setYSize(20);
        setXPos(xPos);
        setYPos(yPos);
        item = withImage;
    }

    @Override
    public void step(ArrayList<BoundingBox> arr) {
        super.step(arr);

        if(getXPos() <= 0 || getXPos() >= 1850 || isOnGround){
            isActive = false;
        }
    }

    public void render(Graphics g, ArrayList<BoundingBox> arr, ArrayList<EnemyCharacter> enemies) {
        super.render(g, arr);

        g.drawImage(item, getXPos(), getYPos(), 0, 0, getXSize(), getYSize());
        for(EnemyCharacter e : enemies){
            if(getBoundingBox().doesItIntesect(e.getBoundingBox()) && isActive){
                e.gotHit();
                e.setYPos(e.getYPos() - 10);
                e.setXPos(e.getXPos() + (getVelocityX() / Math.abs(getVelocityX()))*10);
                e.setVelocityX((getVelocityX() / Math.abs(getVelocityX()))*10);
                e.setVelocityY(-15);
                isActive = false;
            }
        }
    }
}