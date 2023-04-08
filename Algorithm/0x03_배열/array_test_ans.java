package baaarkingdog;


public class Main {
    static int length;

    public static int[] insert(int idx, int num, int[] arr, int len) {
        int[] newArr = new int[len + 1];
        for (int i = 0; i < idx; i++) {
            newArr[i] = arr[i];
        }
        newArr[idx] = num;
        for (int i = idx; i < len; i++) {
            newArr[i + 1] = arr[i];
        }
        length = newArr.length;
        return newArr;
    }

    public static int[] erase(int idx, int[] arr, int len) {
        int[] newArr = new int[len - 1];
        for (int i = 0; i < idx; i++) {
            newArr[i] = arr[i];
        }
        for (int i = idx + 1; i < len; i++) {
            newArr[i - 1] = arr[i];
        }
        length = newArr.length;
        return newArr;
    }

    public static void printArr(int[] arr, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("\n");
    }

    public static void insert_test() {
        System.out.println("***** insert_test *****");
        int[] arr = {10, 20, 30};
        length = arr.length;
        arr = insert(3, 40, arr, length); // 10 20 30 40
        printArr(arr, length);
        arr = insert(1, 50, arr, length); // 10 50 20 30 40
        printArr(arr, length);
        arr = insert(0, 15, arr, length); // 15 10 50 20 30 40
        printArr(arr, length);
    }

    public static void erase_test() {
        System.out.println("***** erase_test *****");
        int[] arr = {10, 50, 40, 30, 70, 20};
        int len = 6;
        arr = erase(4, arr, len); // 10 50 40 30 20
        printArr(arr, len - 1);
        arr = erase(1, arr, len - 1); // 10 40 30 20
        printArr(arr, len - 2);
        arr = erase(3, arr, len - 2); // 10 40 30
        printArr(arr, len - 3);
    }

    public static void main(String[] args) {
        insert_test();
        erase_test();
    }
}