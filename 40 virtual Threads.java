
public class VirtualThreadsDemo {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100_000; i++) {
            Thread.startVirtualThread(() -> {
                System.out.println("Running in virtual thread: " + Thread.currentThread());
            });
        }

        Thread.sleep(2000);
    }
}
