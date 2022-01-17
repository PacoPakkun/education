import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    Lock lock = new ReentrantLock();
    private int id;
    private double amount;

    BankAccount(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public boolean withdraw(double sum) {
        this.lock.lock();
        try {
            if (amount < sum) return false;
            amount -= sum;
            return true;
        }
        finally {
            this.lock.unlock();
        }

    }

    public void deposit(double sum) {
        this.lock.lock();
        try {
            amount += sum;
        }
        finally {
            this.lock.unlock();
        }

    }

    public boolean transfer(double sum, BankAccount receiver) {
        BankAccount first;
        BankAccount second;
        if (this.id > receiver.getId()) {
            first = this;
            second = receiver;
        } else {
            first = receiver;
            second = this;
        }
        first.lock.lock();
        try {
            second.lock.lock();
            try {
                if (amount < sum) return false;
                amount -= sum;
                return true;
            }
            finally {
                second.lock.unlock();
            }
        }
        finally {
            first.lock.unlock();
        }
    }

    public static boolean transferBetween(double sum, BankAccount from, BankAccount to) {
        BankAccount first;
        BankAccount second;
        if (from.id > to.id) {
            first = from;
            second = to;
        } else {
            first = to;
            second = from;
        }
        first.lock.lock();
        try {
            second.lock.lock();
            try {
                if (from.amount < sum) return false;
                from.amount -= sum;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                to.amount += sum;
            }
            finally {
                second.lock.unlock();
            }
            return true;
            }
            finally {
            first.lock.unlock();
            }
        }
    }

public class Main {

    public static void main(String[] args) {
        var acc1 = new BankAccount(1, 1200.0);
        var acc2 = new BankAccount(2, 1500.0);

        var t1 = new Thread(() -> {
            acc1.deposit(100);
        });
        var t2 = new Thread(() -> {
            acc2.withdraw(100);
        });
        var t3 = new Thread(() -> {
            acc1.transfer(100, acc2);
        });

        var t4 = new Thread(() -> {
            acc2.transfer(100, acc1);
        });
        var t5 = new Thread(() -> {
            BankAccount.transferBetween(100, acc1, acc2);
        });

        var t6 = new Thread(() -> {
            BankAccount.transferBetween(100, acc2, acc1);
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        System.out.println(acc1.getAmount() + " " + acc2.getAmount());

    }
}