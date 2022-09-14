public class Square {
    public static int area(int x, int y) {
        valid(x, y);
        return x * y;
    }

    private static void valid(int x, int y) {
        if (x <= 0 || y <=0) {
            throw new IllegalArgumentException("가로 세로 길이는 1이상의 정수만 입력해주세요.");
        }
    }
}

