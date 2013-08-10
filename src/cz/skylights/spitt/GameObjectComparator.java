package cz.skylights.spitt;

import java.util.Comparator;

public class GameObjectComparator implements Comparator<GameObject> {

	public int compare(GameObject arg0, GameObject arg1) {
		// TODO Auto-generated method stub
		return arg0.StartTime-arg1.StartTime;
	}


}

