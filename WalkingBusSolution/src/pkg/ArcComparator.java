package pkg;

import java.util.Comparator;

public class ArcComparator implements Comparator<Arc>{
	@Override
	public int compare(Arc o1, Arc o2) {
		int a = new Double(o1.distance).compareTo(new Double(o2.distance));
		int b = new Double(o1.danger).compareTo(new Double(o2.danger));
		if(a == 0 && b == 0)
			return -1;
		else if(a == 0)
			return new Double(o1.danger).compareTo(new Double(o2.danger));
		else return a;
	}
}
