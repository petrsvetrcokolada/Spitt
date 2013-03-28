package cz.skylights.spitt.collision;

public class CollisionRectangle {
	public float X;
	public float Y;
	public float Width;
	public float Height;
 // Left, Top, Width, Height
	
	public CollisionRectangle()
	{
		X=0;
		Y=0;
		Width = 1;
		Height = 1;
	}
	
	public boolean CollisionTo(CollisionRectangle rect)
	{
		float x1 = X;
		float y1 = Y;
		// 
		float x2 = X+Width;
		float y2 = Y;
		//
		float x3 = X;
		float y3 = Y+Height;
		//
		float x4 = X+Width;
		float y4 = Y+Height;
		
		if (x1 >= rect.X &&  x1 <= rect.X+rect.Height && y1 >= rect.Y && y1 <= rect.Y + rect.Height)
			return true;
		if (x2 >= rect.X &&  x2 <= rect.X+rect.Height && y2 >= rect.Y && y2 <= rect.Y + rect.Height)
			return true;
		if (x3 >= rect.X &&  x3 <= rect.X+rect.Height && y3 >= rect.Y && y3 <= rect.Y + rect.Height)
			return true;
		if (x4 >= rect.X &&  x4 <= rect.X+rect.Height && y4 >= rect.Y && y4 <= rect.Y + rect.Height)
			return true;
		
		return false;
	}
	
	public boolean CollisionTo(CollisionCircle circle)
	{
		float x1 = X;
		float y1 = Y;
		// 
		float x2 = X+Width;
		float y2 = Y;
		//
		float x3 = X;
		float y3 = Y+Height;
		//
		float x4 = X+Width;
		float y4 = Y+Height;

		// pocitat vzdalenosti od vektoru
		
		return false;
	}
}
