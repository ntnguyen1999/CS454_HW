import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    class Solution{
        private double balance = 0;
        private Lock withdrawAndDepositLock = new ReentrantLock();
        private Condition enoughBalance = withdrawAndDepositLock.newCondition(); // Condition when the ordinary withdraw has a valid balance
        private Condition enoughBalancePrefered = withdrawAndDepositLock.newCondition(); // Condition when the preferred withdraw has a valid balance
        private int preferredWidthdraw; // number of waiting priority withdraw

        /**
         * Deposit then notify all waiting threads
         * @param k the deposit amount
         */
        void deposit(double k){
            withdrawAndDepositLock.lock();
            try{
                balance = balance + k;
                callThreads();
            }
            finally {
                withdrawAndDepositLock.unlock();
            }
        }

        /**
         * withdraw preferred and non preferred
         * @param preferred boolean if the withdraw is preferred or not
         * @param k the withdrawn amount
         * @throws InterruptedException
         */
        void withdraw(boolean preferred, double k) throws  InterruptedException{
            withdrawAndDepositLock.lock();
            try{
                if (preferred == true) {
                    preferredWidthdraw++; // add another withdraw to the waiting list
                    while (balance <= k) {
                        enoughBalancePrefered.await();
                    }
                    preferredWidthdraw--; // remove a withdraw from the waiting list
                    balance = balance - k;
                    callThreads();
                }
                else{

                    while (balance <= k && preferredWidthdraw > 0) { // wait until the balance is valid and when there is no preferred withdrawn
                        enoughBalance.await();
                    }
                    preferredWidthdraw--;
                    balance = balance - k;
                    callThreads();
                }
            }
            finally {
               withdrawAndDepositLock.unlock();
            }
        }
        void callThreads(){
            if (preferredWidthdraw == 0){
                enoughBalance.signalAll();
            }
            else{
                enoughBalancePrefered.signalAll();
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}