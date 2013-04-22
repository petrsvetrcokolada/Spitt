package cz.skylights.spitt;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import cz.skylights.spitt.shape.Sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

// scrolovaci layer	
public class ScrollLayer {

    float _speed=0;
    TextureManager _textures;
    ArrayList<Sprite> _layer=new ArrayList<Sprite>();

    public ScrollLayer(TextureManager textures, float speed) 
    { 
    	_textures = textures;
    	_speed = speed;
        
    }
    
    public void scrollBackground(GL10 gl)
	{
	}

    
    public void draw(GL10 gl) 
    { 
    }     

}
