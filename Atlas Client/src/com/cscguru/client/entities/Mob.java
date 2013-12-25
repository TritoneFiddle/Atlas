package com.cscguru.client.entities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import com.cscguru.client.interfaces.IClickable;
import com.cscguru.client.map.Spawn;
import com.cscguru.client.ui.Log;
import com.cscguru.client.ui.Settings;
import com.cscguru.com.balance.Balance;
public class Mob extends Agent implements IClickable {
	private static final long serialVersionUID = 1L;
	private MobStat ms;
	private Spawn s;
	private Log log;
	private DecimalFormat df;
	
	//movement
	private boolean hasBounced;
	private boolean isMoving;
	private float targetX;
	private float targetY;
	private int targetTileX;
	private int targetTileY;
	private float movingValueX;
	private float movingValueY;
	private boolean isIdle = true;
	private int tilesToMove;
	private Rectangle boundingRect;
	private Vector2f center;
	private float normSpeed = .1f;
	private float runSpeed = .15f;
	
	//behavior
	private boolean isHostile;
	private boolean isHooked;
	private Player hookedPlayer;
	private int attackTime = 1000;
	private int despawnTime = 30000;
	private int attackSpeed = 1000;
	private boolean isDead;
	private boolean isDespawned;
	private int sight;
	
	//drawing
	private boolean onScreen;
	private boolean isListed;
	
	
	public Mob(float x, float y, int tileX, int tileY, Actor actor, MobStat ms, Spawn s) {
		super(x, y, tileX, tileY, actor);
		this.ms = ms;
		sight = ms.getSight();
		df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		log = s.getLog();
		isHostile = ms.isHostile();
		anim = actor.getAnimation(this);
		this.s = s;
		targetTileX = tileX;
		targetTileY = tileY;
		boundingRect = new Rectangle(x - 16, y - 16, actor.getSpriteWidth() * 16 + 32, actor.getSpriteHeight() * 16 + 32);
		center = new Vector2f (x + boundingRect.getWidth()/2, y + boundingRect.getHeight()/2);
	}

	@Override
	public void update(int delta) {
		float speed = normSpeed;
		if (isHooked){
			speed = runSpeed;
			attackTime += delta;
			if (attackTime >= despawnTime){
				isDespawned = true;
				return;
			}
		}
		//randomly determines the next move to make if idling.
		if (isIdle && !isMoving && tilesToMove <= 0){
			Random r = new Random();
			int k = r.nextInt(200);
			if (k == 0){
				tilesToMove = r.nextInt(12) + 1;
				setFacing(Facing.EAST);
				move(Facing.EAST);
			}
			else if (k == 1){
				tilesToMove = r.nextInt(12) + 1;
				setFacing(Facing.WEST);
				move(Facing.WEST);
			}
			else if (k == 2){
				tilesToMove = r.nextInt(12) + 1;
				setFacing(Facing.SOUTH);
				move(Facing.SOUTH);
			}
			else if(k == 3){
				tilesToMove = r.nextInt(12) + 1;
				setFacing(Facing.NORTH);
				move(Facing.NORTH);
			}
		}
		if (!isIdle && isHooked && !isMoving){
			Vector2f v = hookedPlayer.getLocation();
			Vector2f m = getLocation();
			float dx = Math.abs(v.x - m.x);
			float dy = Math.abs(v.y - m.y);
			if (boundingRect.intersects(hookedPlayer)){
				float x = v.x + 16;
				float y = v.y + 16;
				dx = Math.abs(x - center.x);
				dy = Math.abs(y - center.y);
				
				if (dx > dy){
					if (center.x < v.x){
						setFacing(Facing.EAST);
					}
					else{
						setFacing(Facing.WEST);
					}
				}
				else{
					if (center.y < v.y){
						setFacing(Facing.SOUTH);
					}
					else{
						setFacing(Facing.NORTH);
					}
				}
				attack();
				return;
			}
			
			if (dx >= dy){
				if (m.x < v.x){
					setFacing(Facing.EAST);
					move(Facing.EAST);
				}
				else{
					setFacing(Facing.WEST);
					move(Facing.WEST);
				}
			}
			else{
				if (m.y < v.y){
					setFacing(Facing.SOUTH);
					move(Facing.SOUTH);
				}
				else{
					setFacing(Facing.NORTH);
					move(Facing.NORTH);
				}
			}
		}
		if (isMoving){
			anim.update(delta);
			Facing face = getFacing();
			if (face == Facing.EAST){
				movingValueX += speed * delta;
				if (movingValueX >= targetX){
					isMoving = false;
					setX(getX() + targetX);
				}
			}
			else if (face == Facing.WEST){
				movingValueX -= speed * delta;
				if (movingValueX <= targetX){
					isMoving = false;
					setX(getX() - targetX);
				}
			}
			else if (face == Facing.SOUTH){
				movingValueY += speed * delta;
				if (movingValueY >= targetY){
					isMoving = false;
					setY(getY() + targetY);
				}
			}
			else{
				movingValueY -= speed * delta;
				if (movingValueY <= targetY){
					isMoving = false;
					setY(getY() - targetY);
				}
			}
			if (!isMoving){
				movingValueX = 0;
				movingValueY = 0;
				setTileX(targetTileX);
				setTileY(targetTileY);
			}
		}
		else if (!isMoving && tilesToMove > 0){
			tilesToMove -= 1;
			move(getFacing());
		}
	}
	@Override
	public void draw(Graphics g, int offsetX, int offsetY){
		int x = tileX - offsetX;
		int y = tileY - offsetY;
		if (x < -2 || x > 40 || y < -2 || y > 40){
			onScreen = false;
			return;
		}
		onScreen = true;
		float tX = x * 16 + Settings.CAM_X + movingValueX;
		float tY = y * 16 + Settings.CAM_Y + movingValueY;
		boundingRect.setX(tX - 16);
		boundingRect.setY(tY - 16);
		setX(tX);
		setY(tY);
		center.x = getX() + actor.getSpriteWidth() * 8;
		center.y = getY() + actor.getSpriteHeight() * 8;
		anim.draw(tX, tY);
		if (isHooked){
			g.setColor(Color.red);
			g.fillRect(tX, tY - 2, actor.getSpriteWidth() * 16, 2);
			g.setColor(Color.green);
			float ratio = (float) (ms.getHp() / (ms.getMaxHP() * 1.0));
			g.fillRect(tX, tY - 2, actor.getSpriteWidth() * ratio * 16, 2);
		}
	}
	private void move(Facing face){
		if (s.canMove()){
			switch (face){
			case EAST:
				targetTileX += 1;
				targetX = 16;
				break;
			case NORTH:
				targetTileY -= 1;
				targetY = -16;
				break;
			case SOUTH:
				targetTileY += 1;
				targetY = 16;
				break;
			case WEST:
				targetTileX -= 1;
				targetX = -16;
				break;
			default:
				break;
			}
			if (isHooked){
				isMoving = true;
				movingValueX = 0;
				movingValueY = 0;
			}
			if (s.checkRange(targetTileX, targetTileY, face) && isIdle){
				isMoving = true;
				movingValueX = 0;
				movingValueY = 0;
			}
			else if (!s.checkRange(targetTileX, targetTileY, face) && isIdle && !hasBounced){
				tilesToMove = 15;
				switch (face){
				case EAST:
					hasBounced = true;
					setFacing(Facing.WEST);
					move(Facing.WEST);
					break;
				case NORTH:
					hasBounced = true;
					setFacing(Facing.SOUTH);
					move(Facing.SOUTH);
					break;
				case SOUTH:
					hasBounced = true;
					setFacing(Facing.NORTH);
					move(Facing.NORTH);
					break;
				case WEST:
					hasBounced = true;
					setFacing(Facing.EAST);
					move(Facing.EAST);
					break;
				default:
					break;
				}
			}
			hasBounced = false;
		}
		else if (!s.canMove() && isHooked && !intersects(hookedPlayer)){
			if (face == Facing.NORTH){
				setFacing(Facing.EAST);
				move(Facing.EAST);
			}
			else if (face == Facing.EAST){
				setFacing(Facing.SOUTH);
				move(Facing.SOUTH);
			}
			else if (face == Facing.WEST){
				setFacing(Facing.NORTH);
				move(Facing.NORTH);
			}
			else{
				setFacing(Facing.WEST);
				move(Facing.WEST);
			}
		}
		else{
			tilesToMove = 0;
		}
	}
	public boolean isOnScreen(){
		return onScreen;
	}
	public boolean isListed(){
		return isListed;
	}
	public void setListed(boolean val){
		isListed = val;
	}
	public boolean withinAggroDistance(Player p){
		Vector2f pv = p.getLocation();
		Vector2f mv = getLocation();
		if (pv.distance(mv) <= sight){
			return true;
		}
		return false;
	}
	public boolean isHooked(){
		return isHooked;
	}
	public void setHooked(boolean val, Player p){
		isHooked = val;
		hookedPlayer = p;
		if (val){
			isIdle = false;
		}
		else{
			isIdle = true;
		}
	}
	public void setHooked(boolean val){
		isHooked = val;
		if (val){
			isIdle = false;
		}
		else{
			isIdle = true;
		}
	}
	public boolean isHostile(){
		return isHostile;
	}
	public void takeDamage(Player p){
		if (!isHooked){
			setHooked(true, p);
		}
		int k = p.getInfo().getDamage();
		log.writeToLog(p.getInfo().getName() + " hit " + ms.getName() + " for " + k, Color.green);
		ms.takeDamage(k);
		if  (ms.getHp() <= 0){
			isDead = true;
		}
	}
	private void attack(){
		if (attackTime >= attackSpeed && isHooked){
			attackTime = 0;
			int k = ms.getDmg();
			int levelP = hookedPlayer.getInfo().getLevel();
			int levelM = ms.getLevel();
			float pierce = (levelM - levelP) * Balance.PIERCE_REDUCT;
			if (pierce < 0){
				pierce = 0;
			}
			float reduct = hookedPlayer.getInfo().getDmgReduct() - pierce;
			float dmg = k - (k * reduct);
			String s = df.format(dmg);
			dmg = Float.parseFloat(s);
			if (!hookedPlayer.isDead()){
				log.writeToLog(ms.getName() + " hit " + hookedPlayer.getInfo().getName() + " for " + dmg, new Color(100,0,0));
				hookedPlayer.takeDamage(dmg);
			}
			if (hookedPlayer.isDead()){
				isHooked = false;
				isDespawned = true;
			}
		}
	}
	public boolean isDead(){
		return isDead;
	}
	public MobStat getMobStat(){
		return ms;
	}

	@Override
	public boolean checkMouseHover(Vector2f v) {
		return false;
	}

	@Override
	public boolean checkMouseClick(Vector2f v, int button) {
		if (this.contains(v.x, v.y)){
			log.writeToLog("You see a level " + ms.getName() + "(" + ms.getLevel() + ").");
		}
		return false;
	}

	public boolean isDespawned() {
		return isDespawned;
	}

	public void setDespawned(boolean isDespawned) {
		this.isDespawned = isDespawned;
	}
}
