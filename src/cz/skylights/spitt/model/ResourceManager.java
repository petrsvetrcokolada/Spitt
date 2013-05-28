package cz.skylights.spitt.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import cz.skylights.spitt.R;
import android.content.Context;

public class ResourceManager {
	
	private static HashMap<String,Integer> resourceMap;
	
	public ResourceManager(){
		/*Class res = R.drawable.class;
		 java.lang.reflect.Field[] pole = res.getDeclaredFields();
		 
		 for(java.lang.reflect.Field f:pole){
			 try {
				int drawableId = f.getInt(null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }*/
	}
	
	public ResourceManager(List<GameObjectModel> objekty,Class<?> c) throws IllegalArgumentException, IllegalAccessException{
		
		for(GameObjectModel objekt:objekty){
			try {
				resourceMap.put(objekt.getId(), c.getDeclaredField(objekt.getId()).getInt(null));
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		 //Class res = R.drawable.class;
		//Field[] pole = c.getDeclaredFields();
		 
		 /*for(java.lang.reflect.Field f:pole){
			 int drawableId = f.getInt(null);
		 }*/
		/*Resources res = c.getResources();
		for(GameObjectModel obj:objekty){
			resourceMap.put(obj.getId(), res.getIdentifier(obj.getId(), type,"cz.skylights.spitt"));
		}*/
	}
	
	public HashMap<String, Integer> getResourceMap() {
		return resourceMap;
	}
	
}
