import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Random;

public class MemoryTest {
    public static void main(String[] args) {
        int size = 10000000; // Ukuran array besar
        int[] array = new int[size];
        Random random = new Random();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        long endTime = System.currentTimeMillis();

        // Menunggu agar GC dapat membersihkan memori yang tidak digunakan
        try {
            Thread.sleep(500); // Menunggu 5 detik
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Menghitung penggunaan memori menggunakan JMX
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

        long heapMemoryUsed = heapMemoryUsage.getUsed();

        System.out.println("Execution Time with GC (Java): " + (endTime - startTime) + " ms");
        System.out.println("Memory Used with GC (Java): " + (heapMemoryUsed / 1024 / 1024) + " MB");
    }
}
