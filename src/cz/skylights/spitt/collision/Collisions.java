package cz.skylights.spitt.collision;

import java.util.ArrayList;

import cz.skylights.geometry.Vertex2D;
import cz.skylights.spitt.BitmapTexture;
import cz.skylights.spitt.Enemy;
import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.SpatterEngine;

public class Collisions extends ArrayList<CollisionArray> {
		
	public float Unit;
	public float X, Y;
	public float Width, Height;
	
	public Collisions()
	{
		
	}
	
	public void AddFrame(CollisionArray edge)
	{
		add(edge);
	}
	
	public static boolean CheckCollision(GameObject obj1, boolean begin1,GameObject obj2, boolean begin2) {		   
		   BitmapTexture texture1 = obj1.getTexture(); 
		   BitmapTexture texture2 = obj2.getTexture();
		
		   float X1 = obj1.X;
		   float X1min = obj1.getMinX();
		   float Y1 = obj1.Y;
		   float X1W = obj1.X+obj1.Width;
		   float X1Wmin = X1min+obj1.getMinWidth();
		   float Y1H = obj1.Y+(obj1.Height/SpatterEngine.screen_ratio);
		   ////
		   float X2 = obj2.X;
		   float X2min = obj2.getMinX();
		   float Y2 = obj2.Y;
		   float X2W = obj2.X+obj2.Width;
		   float X2Wmin = obj2.getMinX()+obj2.getMinWidth();
		   float Y2H = obj2.Y+(obj2.Height/SpatterEngine.screen_ratio);		   
		   
		   boolean check = false;
		   
		   if (X1Wmin < X2min || X2Wmin < X1min || obj1.Y+obj1.Height < Y2 || obj2.Y + obj2.Height < Y1)
			    check = false;
			else
			    check = true;

		   if (check == true)
		   {
			   // vrat body, ktere jsou s kolizni oblasti
			   CollisionArray l1 = texture1.getCollision(obj1.getFrame(),X1, Y1, obj1.Width, obj1.Height, X2, Y2, X2W, obj2.Y + obj2.Height);
			   CollisionArray l2 = texture2.getCollision(obj2.getFrame(),X2, Y2, obj2.Width, obj2.Height, X1, Y1, X1W, obj1.Y + obj1.Height);
			   ///
			   float psize = (l1.Unit+l2.Unit)/2;
			   ///			  
			   if(begin1 ==true)
			   {
				   for(int i = 0; i < l1.size();i++)
				   {
					 Vertex2D v1 = l1.get(i);
					 if (begin2 == true)
					 {
					     for(int j = 0; j < l2.size();j++)
					     {
					    	 Vertex2D v2 = l2.get(j);
					    	 float rozdilX = Math.abs(v1.X-v2.X);		    
					    	 float rozdilY = Math.abs(v1.Y-v2.Y);
					    	 
					    	 if (rozdilX < psize && rozdilY < psize)
					    	 {
					    		 return true;
					    	 }
					     }
					 }
					 else
					 {
					     for(int j = l2.size()-1; j >= 0;j--)
					     {
					    	 Vertex2D v2 = l2.get(j);
					    	 float rozdilX = Math.abs(v1.X-v2.X);		    
					    	 float rozdilY = Math.abs(v1.Y-v2.Y);
					    	 
					    	 if (rozdilX < psize && rozdilY < psize)
					    	 {
					    		 return true;
					    	 }
					     }
					 }
				   }
			   }
			   else
			   {
				   for(int i = l1.size()-1; i >= 0;i--)
				   {
					 Vertex2D v1 = l1.get(i);
					 if (begin2 == true)
					 {
					     for(int j = 0; j < l2.size();j++)
					     {
					    	 Vertex2D v2 = l2.get(j);
					    	 float rozdilX = Math.abs(v1.X-v2.X);		    
					    	 float rozdilY = Math.abs(v1.Y-v2.Y);
					    	 
					    	 if (rozdilX < psize && rozdilY < psize)
					    	 {
					    		 return true;
					    	 }
					     }
					 }
					 else
					 {
					     for(int j = l2.size()-1; j >= 0;j--)
					     {
					    	 Vertex2D v2 = l2.get(j);
					    	 float rozdilX = Math.abs(v1.X-v2.X);		    
					    	 float rozdilY = Math.abs(v1.Y-v2.Y);
					    	 
					    	 if (rozdilX < psize && rozdilY < psize)
					    	 {
					    		 return true;
					    	 }
					     }
					 }
				   }				   
			   }
		   }
		    
			return false;
		}
}
