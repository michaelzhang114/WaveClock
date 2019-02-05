import java.util.ArrayList;
import java.util.List;

public class SeedTmp {

    private int max;
    private List<Integer> randList;

    SeedTmp(int n) {
        max = n;
    }

    public List<Integer> getRandList(int lengthOfList) {
        randList = new ArrayList<>();
        for (int i = 0; i < lengthOfList; i++) {
            //int r = (int)(Math.random() * max + 1);
            randList.add(3);
        }
        return randList;
    }

    public List<Integer> getNormalized(List<Integer> l) {
        int m = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) >= m) {
                m = l.get(i);
            }
        }
        for (int i = 0; i < l.size(); i++) {
            int tmp = l.get(i) / m;
            l.add(i, tmp);
        }
        return l;
    }
}
