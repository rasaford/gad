package blatt8;

import blatt8.BinomialHeap;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		BinomialHeap bh = new BinomialHeap();
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextInt()) {
			bh.insert(scanner.nextInt());
		}

		try {
			while (true)
				System.out.println(bh.deleteMin());
		} catch (RuntimeException ex) {
			// heap is empty, just quit program
			return;
		}
	}

}
