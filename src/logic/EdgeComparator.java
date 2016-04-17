package logic;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge<?>> {
    @Override
    public int compare(Edge<?> edge1, Edge<?> edge2) {
        if (edge1.getWeight() == edge2.getWeight()) {
        	return 0;
        } else if (edge1.getWeight() > edge2.getWeight()) {
        	return 1;
        } else {
        	return -1;
        }
    }
}