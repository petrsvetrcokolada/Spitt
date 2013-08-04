package cz.skylights.spitt.collision;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import cz.skylights.spitt.Enemy;
import cz.skylights.spitt.GameObject;
import cz.skylights.spitt.OptionsEngine;
import cz.skylights.spitt.SpatterEngine;
import cz.skylights.spitt.shape.ExplosionEmitter;
import android.os.AsyncTask;

public class AsyncCollision extends AsyncTask<Void,Void, Void> {

	ArrayList<SimpleEntry<GameObject,GameObject>> queue = new ArrayList();
	ExplosionEmitter _explosions;
	ArrayList<Enemy> _enemies = null;
	
	public void setExplosions(ExplosionEmitter emitter)
	{
		_explosions = emitter;
	}
	
	public void setEnemies(ArrayList<Enemy> list)
	{
		_enemies = list;
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
					if (entry.getValue().Live > 0 && entry.getValue().Live > 0 && Collisions.CheckCollision(entry.getKey(),false, entry.getValue(), true) == true)
					{
						if (_enemies!= null)
							_enemies.remove(entry.getKey());
						
						_explosions.setExplosion(entry.getKey());
						if (entry.getValue().Live >= 0)
						{
							entry.getValue().Live -= entry.getKey().Strength;	
							entry.getKey().Live = 0;
						}
						else
						{							
							SpatterEngine.game_state = OptionsEngine.gameState.gameOver;
							_explosions.setExplosion(entry.getValue());						
						}
					}
				}				
				queue.clear();
				
				Thread.sleep(20);
			} catch (InterruptedException e) {
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
