package cz.skylights.spitt.collision;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import cz.skylights.spitt.Bonus;
import cz.skylights.spitt.Enemy;
import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.OptionsEngine;
import cz.skylights.spitt.Player;
import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.WeaponFire;
import cz.skylights.spitt.shape.ExplosionEmitter;
import android.os.AsyncTask;

public class AsyncCollision extends AsyncTask<Void,Void, Void> {

	ArrayList<SimpleEntry<GameObject,GameObject>> queue = new ArrayList();
	ExplosionEmitter _explosions;
	ArrayList<GameObject> _remove = null;
	
	public void setExplosions(ExplosionEmitter emitter)
	{
		_explosions = emitter;
	}
	
	public void setEnemies(ArrayList<GameObject> list)
	{
		_remove = list;
	}	
	
	public void Add(GameObject obj1, GameObject obj2)
	{
		SimpleEntry<GameObject,GameObject> entry = new SimpleEntry(obj1, obj2);
		queue.add(entry);	
	}
	
	@Override
	protected Void doInBackground(Void... arg0) 
	{
		while(true)
		{
			try {
				for(int i = 0; i < queue.size();i++)
				{
					SimpleEntry<GameObject,GameObject> entry = queue.get(i);
					GameObject obj1 = entry.getKey(); // enemy
					GameObject obj2 = entry.getValue(); // player, wf
					if (obj2.Live > 0 && obj1.Live > 0 && Collisions.CheckCollision(obj1, false, obj2, true) == true)
					{																
						if (obj1 instanceof Enemy && obj2 instanceof Player)
						{
							if (_remove!= null)
								_remove.remove(obj1);
							
							_explosions.setExplosion(obj1);
							if (obj2.Live > 0)
							{
								obj2.Live -= obj1.Strength;	
								obj1.Live = 0;
							}
						}
						else if (obj1 instanceof Enemy && obj2 instanceof WeaponFire)
						{
							WeaponFire wf = (WeaponFire)obj2;
							if (wf.shotFired == true)
							{
								wf.shotFired = false;
								obj1.Live -= obj2.Strength;
								if(obj1.Live <=0)
								{							  
									  Player pl = (Player)wf.Parent;
									  pl.Score += obj1.Strength;
									  //_score.BuildCharacters("Score:"+String.valueOf(Score), 0.9f*16, 0.95f);
									  //vybuch
									  obj1.Adorner = _explosions.setExplosion(obj1);
								}
																							
							}
						}
						else if (obj1 instanceof Bonus && obj2 instanceof Player)
						{
							obj2.Live = 100;
							obj1.Live = 0;
						}
						
					}						
					else if (obj2.Live <= 0)
					{						
						_explosions.setExplosion(obj2);
					}
				}				
				queue.clear();
				
				Thread.sleep(20);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}					
	}
	
    protected void onProgressUpdate(Void v) {
        
    }

    protected void onPostExecute(Void v) {
        
    }
}
