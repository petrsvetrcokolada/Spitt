package cz.skylights.spitt;

import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.*;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

/// Class player
public class Player extends GameObject {
	// sprites
	private TextureManager _textures;
    
    public int playerXState=0;
    public int playerYState=0;
    private int _playerFrame=0;
    private float _moveToX = 0;
    private float _moveToY = 0;
    
    //
    BitmapTexture _texturePlayer;
    // 
    private float _incrementX=SpatterEngine.PLAYER_BANK_SPEED;
    private float _incrementY=SpatterEngine.PLAYER_BANK_SPEED;
    // weapon
	ArrayList<WeaponFire> _playerFire = new ArrayList<WeaponFire>();
	ArrayList<WeaponFire> _fired = new ArrayList<WeaponFire>();
	
	
    private float vertices[] = 
    { 
        0.0f, 0.0f, 0.0f, 
        0.25f, 0.0f, 0.0f, 
        0.25f, 0.25f, 0.0f, 
        0.0f, 0.25f, 0.0f, 
    }; 

    private float texture[] = 
    { 
        0.0f, 0.0f, 
        0.25f, 0.0f, 
        0.25f, 0.25f, 
        0.0f, 0.25f, 
    }; 

    private byte indices[] =
    { 
        0,1,2, 
        0,2,3, 
    }; 

    ///
    public Player(TextureManager text_manager) 
    {         
    	X = 0.375f;
    	Width = 0.25f;
    	Height = 0.25f;
    	_lastFire = System.currentTimeMillis();
    	_textures = text_manager;
    	_texturePlayer = _textures.AddTexture("sm", 1,128,128,true);
    	BitmapTexture shoot = _textures.AddTexture("shoot4a", true);
    	
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4); 
        byteBuf.order(ByteOrder.nativeOrder()); 
        vertexBuffer = byteBuf.asFloatBuffer(); 
        vertexBuffer.put(vertices); 
        vertexBuffer.position(0); 

        byteBuf = ByteBuffer.allocateDirect(texture.length * 4); 
        byteBuf.order(ByteOrder.nativeOrder()); 
        textureBuffer = byteBuf.asFloatBuffer(); 
        textureBuffer.put(texture); 
        textureBuffer.position(0); 

        indexBuffer = ByteBuffer.allocateDirect(indices.length); 
        indexBuffer.put(indices); 
        indexBuffer.position(0);
        
		/// inicializace
		initializePrimaryWeapons(shoot);
    } 
    
    public ArrayList<WeaponFire> GetFiredWeapon()
    {
    	return _fired;
    }
	
    ///
    public void draw(GL10 gl) { 
    	gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
        gl.glBindTexture(GL10.GL_TEXTURE_2D, _texturePlayer.textureID); 
        gl.glFrontFace(GL10.GL_CCW); 
        gl.glEnable(GL10.GL_CULL_FACE); 
        gl.glCullFace(GL10.GL_BACK); 

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); 
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); 
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer); 

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, 
                          GL10.GL_UNSIGNED_BYTE, indexBuffer); 

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); 
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
        gl.glDisable(GL10.GL_CULL_FACE);
                
    }
    
    ///
    public void moveTo(float movex, float movey)    
    {
    	_moveToX = movex;    	    	    	
    	_moveToY = movey;
    	//Log.w("Spatter","MoveTo: X="+String.valueOf(movex)+";Y="+String.valueOf(movey));
    	Float fl_playerx = new Float(X+SpatterEngine.scaleX);
    	Float fl_playery = new Float(Y);
    	
    	double dx = Math.abs(X-movex);
    	double dy = Math.abs(Y-movey);
    	double dl = Math.sqrt(dx*dx+dy*dy);
    	double sin = dy / dl;
    	double cos = dx / dl;
    	    	    
    	
    	_incrementX = (float) (cos*SpatterEngine.PLAYER_BANK_SPEED);
    	_incrementY = (float) (sin*SpatterEngine.PLAYER_BANK_SPEED);
    	//Log.w("Spatter","inX="+String.valueOf(_incrementX)+";incY="+String.valueOf(_incrementY));
    	
		if (_moveToX < fl_playerx)
		{
			SpatterEngine.Player.playerXState = SpatterEngine.PLAYER_LEFT;
		}
		else if (_moveToX > fl_playerx)
		{
			SpatterEngine.Player.playerXState = SpatterEngine.PLAYER_RIGHT;
		}
		else
		{
			SpatterEngine.Player.playerXState = SpatterEngine.PLAYER_RELEASE;    			
		}
		////
		if (_moveToY < fl_playery)
		{
			SpatterEngine.Player.playerYState = SpatterEngine.PLAYER_DW;
		}
		else if (_moveToY > fl_playery)
		{
			SpatterEngine.Player.playerYState = SpatterEngine.PLAYER_UP;
		}
		else
		{
			SpatterEngine.Player.playerYState = SpatterEngine.PLAYER_RELEASE;    			
		}
    }
    
    ///
    public void movePlayer(GL10 gl) { 
    	Float fl_playerx = new Float(X+SpatterEngine.scaleX);
    	Float fl_playery = new Float(Y+SpatterEngine.scaleY);
    	
    	switch (playerYState)
    	{
    	case SpatterEngine.PLAYER_UP:
        	if (fl_playery < _moveToY)
        		Y += _incrementY;
        	else
        		playerYState = SpatterEngine.PLAYER_RELEASE;
    		break;
    	case SpatterEngine.PLAYER_DW:
        	if (fl_playery > _moveToY)
        		Y -= _incrementY;
        	else
        		playerYState = SpatterEngine.PLAYER_RELEASE;
    		break;
    	default:
    		break;
    	}
    	
        switch (playerXState) { 
        case SpatterEngine.PLAYER_LEFT: 
            gl.glMatrixMode(GL10.GL_MODELVIEW); 
            gl.glLoadIdentity();
            gl.glPushMatrix(); 
            //gl.glScalef(SpatterEngine.scaleX, SpatterEngine.scaleY, 1f); 

            if (_playerFrame < SpatterEngine.PLAYER_FRAMES_BETWEEN_ANI && X > 0) {
            	
            	if (fl_playerx > _moveToX)
            		X -= _incrementX;
            	else
            		playerXState = SpatterEngine.PLAYER_RELEASE;
            	
                gl.glTranslatef(X, Y, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity();  
                gl.glTranslatef(0.25f,0.0f, 0.0f);
                _playerFrame += 1;
            } 
            else if (_playerFrame >= SpatterEngine.PLAYER_FRAMES_BETWEEN_ANI && X > 0) { 
            	if (fl_playerx > _moveToX)
            		X -= _incrementX;
            	else
            		playerXState = SpatterEngine.PLAYER_RELEASE;
            	
                gl.glTranslatef(X, Y, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                //gl.glTranslatef(0.0f,0.25f, 0.0f);                
                gl.glTranslatef(0.50f,0.0f, 0.0f);
            }
            else  { 
                gl.glTranslatef(X, Y, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                gl.glTranslatef(0.0f, 0.0f, 0.0f); 
            } 

            draw(gl); 
            gl.glPopMatrix(); 
            gl.glLoadIdentity(); 
            break;                          
        case SpatterEngine.PLAYER_RIGHT: 
            gl.glMatrixMode(GL10.GL_MODELVIEW); 
            gl.glLoadIdentity(); 
            gl.glPushMatrix(); 
            //gl.glScalef(SpatterEngine.scaleX, SpatterEngine.scaleY, 1f); 

            if (_playerFrame < SpatterEngine.PLAYER_FRAMES_BETWEEN_ANI && SpatterEngine.Player.X < 1) {
            	
            	if (fl_playerx < _moveToX)
            		X += _incrementX;
            	else
            		playerXState = SpatterEngine.PLAYER_RELEASE;
            	
                gl.glTranslatef(X, Y, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                //gl.glTranslatef(0.25f,0.0f, 0.0f); 
                gl.glTranslatef(0.75f, 0.0f, 0.0f);
                _playerFrame += 1;             	
            }
            else if (_playerFrame >= SpatterEngine.PLAYER_FRAMES_BETWEEN_ANI && SpatterEngine.Player.X < 1) {

            	if (fl_playerx < _moveToX)
            		X += _incrementX;
            	else
            		playerXState = SpatterEngine.PLAYER_RELEASE;
            	
                gl.glTranslatef(X, Y, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                //gl.glTranslatef(0.50f,0.0f, 0.0f);
                gl.glTranslatef(0.0f,0.25f, 0.0f);
            } 
            else { 
                gl.glTranslatef(X, Y, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                gl.glTranslatef(0.0f,0.0f, 0.0f); 
            } 

            draw(gl); 
            gl.glPopMatrix(); 
            gl.glLoadIdentity(); 
            break; 
        case SpatterEngine.PLAYER_RELEASE: 
            gl.glMatrixMode(GL10.GL_MODELVIEW); 
            gl.glLoadIdentity(); 
            gl.glPushMatrix(); 
            //gl.glScalef(SpatterEngine.scaleX, SpatterEngine.scaleY, 1f); 
            gl.glTranslatef(X, Y, 0f); 
            gl.glMatrixMode(GL10.GL_TEXTURE); 
            gl.glLoadIdentity(); 
            gl.glTranslatef(0.0f,0.0f, 0.0f); 
            draw(gl); 
            gl.glPopMatrix(); 
            gl.glLoadIdentity(); 
            _playerFrame += 1; 
            break; 
        default: 
            gl.glMatrixMode(GL10.GL_MODELVIEW); 
            gl.glLoadIdentity(); 
            gl.glPushMatrix(); 
            //gl.glScalef(SpatterEngine.scaleX, SpatterEngine.scaleY, 1f); 
            gl.glTranslatef(X, Y, 0f); 
            gl.glMatrixMode(GL10.GL_TEXTURE); 
            gl.glLoadIdentity(); 
            gl.glTranslatef(0.0f,0.0f, 0.0f); 
            draw(gl); 
            gl.glPopMatrix(); 
            gl.glLoadIdentity(); 
            break; 
        }     
        
        movePlayerWeapon(gl);
    }  
    
    ///
    private void initializePrimaryWeapons(BitmapTexture texture) { 
        for (int x = 0; x < SpatterEngine.fire_count; x++) { 
        	WeaponFire weapon = new WeaponFire(0.02f, this.X, this.Y); 
            weapon.shotFired = false;           
            weapon.setTexture(texture);
            weapon.X = this.X; 
            weapon.Y = this.Y;
            _playerFire.add(weapon);
        }            
        
        //_lastFire = System.currentTimeMillis();
        _lastFire = SpatterEngine.GameTime;
        fireWeapon(); 
    } 
    
    
    private long _lastFire=0;
    public void fireWeapon()
    {		
    	
    	long now = SpatterEngine.GameTime;
    	long rozdil = now - _lastFire;
    	if (rozdil < SpatterEngine.bullet_timeout)
    	{
    		return;
    	}
    	
    	for (int x = 0; x < _playerFire.size(); x++) {
    		WeaponFire weapon = _playerFire.get(x);
            if (weapon.shotFired == false) { 
                weapon.shotFired = true; 
                weapon.X = X+(Width/2)-weapon.Width/2; // pulka rakety 
                weapon.Y = Y+Height; // vyska ... ta je nasobena pomerem obrazovky
                weapon.Strength = 25;
                _fired.add(weapon);
                break;
            } 	       
    	}
    	
    	_lastFire = SpatterEngine.GameTime;
    }
    
    // posun vystrelenych kulek
    private void movePlayerWeapon(GL10 gl) { 
        if (SpatterEngine.onTouch == true)
        {
        	fireWeapon();        	
        }
    	
    	for (int x = 0; x < _playerFire.size(); x++) { 
            WeaponFire weapon = _playerFire.get(x);
    		if (weapon.shotFired==true) {             	
                if (weapon.Y > 1.1*SpatterEngine.screen_ratio) 
                { 
                    weapon.shotFired = false;
                    _fired.remove(weapon);
                }
                else 
                {                  	
                    weapon.Y += SpatterEngine.bullet_speed; 
                    weapon.draw(gl);  
                }             	
            } 
        } 
    }
}

