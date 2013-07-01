package cz.skylights.spitt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.interfaces.ITrajectory;


public class Enemy extends GameObject {
	float _ratio;		
	//private int _textureID=-1;
	//private BitmapTexture _texture;
	
	private float vertices[] = { 
        0.0f, 0.0f, 0.0f, 
        1.0f, 0.0f, 0.0f, 
        1.0f, 1.0f, 0.0f, 
        0.0f, 1.0f, 0.0f, 
    }; 

    private float texture[] = { 
        0.0f, 1.0f, 
        1.0f, 1.0f, 
        1.0f, 0.0f, 
        0.0f, 0.0f, 
    }; 

    private byte indices[] = { 
        0,1,2, 
        0,2,3, 
    };    
	
	public Enemy(float sx, float sy)	
	{
		super(sx, sy);	
	}
	
	private void refreshArray()
	{
		// 
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
	}
	
	
	public void setTexture(BitmapTexture texture)
	{
	 if (texture.Frames > 1)
		 _animation = true;
	 
	  _texture = texture;
	  float podil = (float)_texture.FrameWidth/(float)_texture.Width;
	  for(int i = 0; i < this.texture.length; i++)
	  {
	    this.texture[i] = this.texture[i]*podil;
	  }
	  
	}
	/*
	public BitmapTexture getTexture()
	{
		return _texture;
	}*/
	
	public void setTrajectory(ITrajectory traject)
	{
		_trajectory = traject;
		//_trajectory.
	}
	
	// kazda kulka by mela mit svuj pomer velikosti ... individualne k bitmape
	public void setSizeRatio(float ratio)
	{
		_ratio = ratio;		
		for(int i = 0; i < vertices.length;i++)
			vertices[i]*=ratio;
		
		Width = 1.0f*ratio;
		Height = 1.0f*ratio;
		Speed *= ratio;
		refreshArray();
	}	
	
	long _nextTime = SpatterEngine.GameTime+(long)AnimationSpeed;
	public void animation()
	{
		
		if (_nextTime > SpatterEngine.GameTime)
		{
			return;
		}
		
		if (_frame < _texture.Frames)
		  _frame++;
		
		if (_animation == true && _frame >= _texture.Frames)
			_frame = 0;
			
		_nextTime = SpatterEngine.GameTime+(long)AnimationSpeed;
	}
	
	public void move()
	{
		this.animation();
		super.move();
	}
	
	// kresleni
	public void draw(GL10 gl)
	{
		gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glMatrixMode(GL10.GL_MODELVIEW); 
        gl.glLoadIdentity(); 
        gl.glPushMatrix(); 
        //gl.glScalef(scaleX, scaleY, 0f);       
        gl.glTranslatef(X, Y, 0f); 

        gl.glMatrixMode(GL10.GL_TEXTURE); 
        gl.glLoadIdentity(); 
        float factor = (float)_texture.Width/(float)_texture.FrameWidth; 
        float frame_width = (1/(float)factor);

        int tx = _frame % (int)factor;
        int ty = _frame / (int)factor;
        gl.glTranslatef(frame_width*tx,frame_width*ty, 0.0f); 
		
        gl.glBindTexture(GL10.GL_TEXTURE_2D, _texture.textureID); 
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
        
        gl.glPopMatrix(); 
        gl.glLoadIdentity();
	}	
	
}
