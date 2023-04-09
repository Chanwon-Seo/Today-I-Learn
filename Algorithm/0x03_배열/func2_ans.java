package baaarkingdog;


public class Main {
    static int solution(int arr[], int N) {
        int[] occur = new int[101];
        for (int i = 0; i < N; i++) {
            if (occur[100 - arr[i]] == 1) {
                return 1;
            }
            occur[arr[i]] = 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 52, 48}, 3));
        System.out.println(solution(new int[]{50, 42}, 2));
        System.out.println(solution(new int[]{4, 13, 63, 87}, 4));
    }
}