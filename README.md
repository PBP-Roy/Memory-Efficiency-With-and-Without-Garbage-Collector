# Memory-Efficiency-With-and-Without-Garbage-Collector
Studi Perbandingan Efisiensi Penggunaan Memori pada Bahasa dengan dan Tanpa Garbage Collector
# Studi Perbandingan Efisiensi Penggunaan Memori pada Bahasa dengan dan Tanpa Garbage Collector

## 1. Identifikasi Problem
Efisiensi penggunaan memori dapat bervariasi antara bahasa pemrograman yang menggunakan garbage collector (GC) dan yang tidak menggunakan GC. Penggunaan memori dan waktu eksekusi program dapat menunjukkan perbedaan dalam manajemen memori antara kedua jenis bahasa ini.

## 2. Deskripsi Problem
Bahasa pemrograman dengan garbage collector (seperti Java) menawarkan kemudahan dalam pengelolaan memori otomatis, tetapi mungkin memerlukan lebih banyak memori dibandingkan dengan bahasa pemrograman yang tidak menggunakan GC (seperti C), yang memerlukan pengelolaan memori manual. Tujuan eksperimen ini adalah untuk membandingkan penggunaan memori dan waktu eksekusi dari kedua bahasa.

## 3. Metodologi Experiment
Eksperimen dilakukan dengan:
1. Menulis program identik dalam dua bahasa: Java (dengan GC) dan C (tanpa GC).
2. Mengukur waktu eksekusi dan penggunaan memori dari kedua program.
3. Menganalisis hasil untuk membandingkan efisiensi memori dan waktu eksekusi antara kedua bahasa.

## 4. Pelaksanaan Experiment

### Program Java (dengan GC)

```java
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
            Thread.sleep(5000); // Menunggu 5 detik
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

```

### Program C (Tanpa GC)
```C
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

void print_memory_usage() {
    PROCESS_MEMORY_COUNTERS pmc;
    if (GetProcessMemoryInfo(GetCurrentProcess(), &pmc, sizeof(pmc))) {
        printf("Memory Used without GC (C): %lu KB\n", pmc.WorkingSetSize / 1024);
    } else {
        printf("Failed to get memory usage\n");
    }
}

int main() {
    int size = 10000000; // Ukuran array besar
    int *array = (int *)malloc(size * sizeof(int));
    if (array == NULL) {
        printf("Memory allocation failed!\n");
        return 1;
    }

    srand(time(0));
    clock_t start = clock();
    for (int i = 0; i < size; i++) {
        array[i] = rand();
    }
    clock_t end = clock();

    printf("Execution Time without GC (C): %lf ms\n", ((double)(end - start) / CLOCKS_PER_SEC) * 1000);
    
    // Cetak penggunaan memori
    print_memory_usage();

    free(array); // Mengelola memori secara manual
    return 0;
}
```
## 5. Analisis Hasil Experiment
Hasil
### Program Java:
- Execution Time with GC (Java): 199 ms
- Memory Used with GC (Java): 42 MB
### Program C:
- Execution Time without GC (C): 249 ms
- Memory Used without GC (C): 41,856 KB (sekitar 41 MB)
### Analisis
- Waktu Eksekusi:
Program Java memiliki waktu eksekusi lebih cepat (199 ms) dibandingkan dengan program C (249 ms). Ini menunjukkan bahwa program Java dengan GC dapat menyelesaikan tugas sedikit lebih cepat dibandingkan dengan program C dalam kasus ini.

- Penggunaan Memori:
Program Java menggunakan sekitar 42 MB memori, sedangkan program C menggunakan sekitar 41 MB. Meskipun hasilnya sangat mendekati, Java menggunakan sedikit lebih banyak memori dibandingkan dengan C.

- Trade-off:
Kemudahan Penggunaan: Java menawarkan kemudahan penggunaan memori dengan GC, meskipun ini sedikit meningkatkan penggunaan memori.
Efisiensi Memori: C, yang mengelola memori secara manual, menunjukkan penggunaan memori yang sedikit lebih rendah, tetapi dengan waktu eksekusi yang sedikit lebih lama.

## Kesimpulan
Eksperimen menunjukkan bahwa meskipun Java dengan GC dapat menyelesaikan tugas sedikit lebih cepat, ia menggunakan sedikit lebih banyak memori dibandingkan dengan C tanpa GC. Trade-off antara kemudahan penggunaan dan efisiensi memori tergantung pada kebutuhan spesifik aplikasi dan konteks penggunaannya.
