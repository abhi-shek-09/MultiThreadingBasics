import java.util.List;

public class Merger {
    public void mergeArrays(int[] ar1, int[] ar2, int[] ar3) {
        int i = 0, j = 0, k = 0;
        int n1 = ar1.length;
        int n2 = ar2.length;

        while (i < n1 && j < n2) {
            if (ar1[i] < ar2[j])
                ar3[k++] = ar1[i++];
            else
                ar3[k++] = ar2[j++];
        }

        while (i < n1)
            ar3[k++] = ar1[i++];

        while (j < n2)
            ar3[k++] = ar2[j++];
    }

    public int[] merge(List<int[]> chunkList) {
        if (chunkList.size() == 1) {
            return chunkList.get(0);
        }
        while (chunkList.size() > 1) {
            int[] a = chunkList.get(0);
            int[] b = chunkList.get(1);
            int[] c = new int[a.length + b.length];
            mergeArrays(a, b, c);

            chunkList.remove(0);
            chunkList.remove(0);
            chunkList.add(c);
        }

        return chunkList.get(0);
    }
}
