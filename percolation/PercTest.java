public class PercTest {

    public static void main(String[] args) {
	Percolation perc= new Percolation(10);
	perc.open(1,1);
	perc.open(1,2);
	perc.open(2,2);
	StdOut.println(perc.isFull(2, 2));

    }


}
