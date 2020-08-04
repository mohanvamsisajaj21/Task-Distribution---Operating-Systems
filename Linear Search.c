#include <stdio.h>
#include <stdlib.h>

int search(int arr[], int n, int x)
{
    int i;
    for (i = 0; i < n; i++)
        if (arr[i] == x)
            return i;
    return -1;
}

int main(int argc, char *argv[])
{
    int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 30, 40, 50, 75, 100, 150, 200, 300, 400, 500, 1000};
    int x = atoi(argv[1]);
    int n = sizeof(arr) / sizeof(arr[0]);
    int result = search(arr, n, x);
    (result == -1) ? printf("Not Found") : printf("Found");
    return 0;
}
