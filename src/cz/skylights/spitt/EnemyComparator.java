package cz.skylights.spitt;

import java.util.Comparator;

public class EnemyComparator implements Comparator<Enemy> {

	public int compare(Enemy arg0, Enemy arg1) {
		// TODO Auto-generated method stub
		return arg0.StartTime-arg1.StartTime;
	}


}

