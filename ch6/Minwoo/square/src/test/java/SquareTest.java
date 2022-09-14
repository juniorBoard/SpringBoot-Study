import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
    @Test
    public void areaTest() {
        //given
        final int X = 5;
        final int Y = 10;
        final int EXPECTED = 50;

        //when
        int result = Square.area(X, Y);

        //then
        assertEquals(EXPECTED, result);
    }

    @Test
    public void areaThrowIllegalArgumentExceptionTest() {
        //given
        final int X = 0;
        final int Y = 10;

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> Square.area(X, Y));
    }
}

