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
