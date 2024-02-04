public class TestExpressLinkedList {
    
	public static void main(String[] args){
		ExpressLinkedList<Integer> ll = new ExpressLinkedList<>(-1);

		for(int i = 1500000; i >=0 ; i--) {
			ll.add(0, i);
		}

        long startTime = System.nanoTime();
        //put code here to measure performance
        ll.get(1000000);
        ll.add(100000, 20);
        long endTime = System.nanoTime();

        long runtime = endTime - startTime;

        // Convert nanoseconds to milliseconds for a more readable output
        long runtimeInMilliseconds = runtime / 1000000;

        // Print the runtime
        System.out.println("Runtime: " + runtimeInMilliseconds + " milliseconds");
	}
}
