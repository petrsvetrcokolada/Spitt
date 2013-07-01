package cz.skylights.spitt.collision;

import java.util.ArrayList;

import cz.skylights.geometry.Vertex2D;
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
	
	public static boolean CheckCollision(GameObject obj1, GameObject obj2) {
		   float X1 = obj1.X;
		   float Y1 = obj1.Y;
		   float X1W = obj1.X+obj1.Width;
		   float Y1H = obj1.Y+(obj1.Height/SpatterEngine.screen_ratio);

		   float X2 = obj2.X;
		   float Y2 = obj2.Y;
		   float X2W = obj2.X+obj2.Width;
		   float Y2H = obj2.Y+(obj2.Height/SpatterEngine.screen_ratio);
		   
		   boolean check = false;
		   
		   if (X1W < X2 || X2W < X1 || Y1H < Y2 || Y2H < Y1)
			    check = false;
			else
			    check = true;

		   if (check == true)
		   {
			   // vrat body, ktere jsou s kolizni oblasti
			   CollisionArray l1 = obj1.getTexture().getCollision(0,X1, Y1, obj1.Width, obj1.Height/SpatterEngine.screen_ratio, X2, Y2, X2W, Y2H);
			   CollisionArray l2 = obj2.getTexture().getCollision(obj2.getFrame(),X2, Y2, obj2.Width, obj2.Height/SpatterEngine.screen_ratio, X1, Y1, X1W, Y1H);
			   ///
			   float psize = (l1.Unit+l2.Unit)/2;
			   ///
			   for(int i = 0; i < l1.size();i++)
			   {
				 Vertex2D v1 = l1.get(i);
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
		   }
		    
			return false;
		}
}
