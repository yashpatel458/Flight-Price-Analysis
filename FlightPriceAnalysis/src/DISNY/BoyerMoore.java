package DISNY;

public class BoyerMoore {
    private final int R;     // the radix
    private int[] right;     // the bad-character skip array
    private String pat;      // or as a string

    // pattern provided as a string
    public BoyerMoore(String pat) {
        this.R = 256;
        this.pat = pat;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
    }

    // return offset of first match; N if no match
    public int search(String txt, Integer newIndex) {
        int M = pat.length();
        int N = txt.length();
        int skip;
        for (int i = 0 + newIndex; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, (j - right[txt.charAt(i+j)]));
                    break;
                }
            }
            if (skip == 0)
            	{
            		return i;
            	}    // found
        }
        return N;                       // not found
    }

//     test Boyer Moore
    public static void main(String[] args) {

        String pat = "ab";
        String txt = "abhhhhahabbaaabaa";

        System.out.println(txt);
        int M = pat.length();
        int N = txt.length();
        int freq = 0;
        int newIndex = 0;
        BoyerMoore boyermoore = new BoyerMoore(pat);
		int offset = boyermoore.search(txt, newIndex);
		System.out.println("\n");
        while( newIndex < (N-M) )
        {
    		
        	boyermoore = new BoyerMoore(pat);
    		offset = boyermoore.search(txt, newIndex);
    		newIndex = 1 + offset;
    		if(offset != N)
    		{
    			System.out.println("Position : " + offset);
    			freq += 1;
    		}
        }
        System.out.println("Frequency : " + freq);
}
}
