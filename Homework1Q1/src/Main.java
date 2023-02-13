import java.util.Arrays;

public class Main {
    class Solution{
        int numberOfPeople = 5; // number of philosophers
        private int[] chopstick = new int[numberOfPeople]; // The state of chopsticks with 1 = chopstick used, 0 = chopstick available
        private Thread[] listOfPeople = new Thread[numberOfPeople]; // Store the threads for ID
        // create an array of Threads
        public void createListOfPeople(){
            for(int i = 0; i< numberOfPeople;i++){
                listOfPeople[i] = new Thread();
            }
        }
        public void Lock(){
            Thread currentThread = Thread.currentThread(); // get thread
            // Waiting Section
            // If this is the last thread
            if (listOfPeople[numberOfPeople-1] == currentThread){
                // when the left chopstick is available pick it up
                if ( chopstick[Arrays.asList(chopstick).indexOf(currentThread) ] == 0){
                    chopstick[Arrays.asList(chopstick).indexOf(currentThread)] = 1;
                    // wait when the right one is used by someone else
                    while (chopstick[0] == 1){}
                    // Use the chopstick to the right and eat
                    chopstick[0] = 1;
                }
                // when the left chopstick is used by the person to the left
                else {
                    // wait until the left chopstick is available
                    while (chopstick[Arrays.asList(chopstick).indexOf(currentThread)] == 1) {
                    }
                    //pick up the left chopstick
                    chopstick[numberOfPeople] = 1;
                    //wait until the right chopstick is not used by the one to the right
                    while(chopstick[0] == 1){}
                    chopstick[0] = 1;
                }
            }
            // For other threads
            else{
                //if the right one is available then pick it up to
                if ( chopstick[Arrays.asList(chopstick).indexOf(currentThread) + 1] == 0){
                    chopstick[Arrays.asList(chopstick).indexOf(currentThread) + 1] = 1;
                    // wait until the left chopstick is available
                    while (chopstick[Arrays.asList(chopstick).indexOf(currentThread)] == 1){}
                    // pick up the left chopstick
                    chopstick[Arrays.asList(chopstick).indexOf(currentThread)] = 1;
                }
                //if the right one is used
                else{
                    // wait until the right one is available
                    while (chopstick[Arrays.asList(chopstick).indexOf(currentThread) + 1] == 1){}
                    // pick up the right one
                    chopstick[Arrays.asList(chopstick).indexOf(currentThread)+1] = 1;
                    // wait until the left one is available
                    while (chopstick[Arrays.asList(chopstick).indexOf(currentThread) ] == 1){}
                    // pick up the left one
                    chopstick[Arrays.asList(chopstick).indexOf(currentThread)] = 1;
                }

            }
            {} // Critical Section
        }
        public void unlock(){
            Thread currentThread = Thread.currentThread();
            // Dropping Chopsticks
            if (listOfPeople[numberOfPeople-1] == currentThread){
                chopstick[numberOfPeople] = 0;
                chopstick[0] = 0;
            }
            else{
                chopstick[Arrays.asList(chopstick).indexOf(currentThread)] = 0;
                chopstick[Arrays.asList(chopstick).indexOf(currentThread)+1] = 0;
            }
        }

    }

    public static void main(String[] args) {
        System.out.println("No compile error!");
    }
}