package cz.skylights.spitt.layer;

public class ScrollTile {
	
	private int _txID;
	private int _loop;

	public float Offset=0.0f;
	
	public ScrollTile(int txID, int loop)
	{
		_txID = txID;
		_loop = loop;
	}
	
	public int Texture()
	{
    	return _txID;
	}
	
	public int Loop()
	{
		return _loop;
	}
	
}
