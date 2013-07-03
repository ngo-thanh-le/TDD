import StringCalculator.StringCalculator;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: lent
 * Date: 7/3/13
 */
public class StringCalculatorTest
{
    StringCalculator stringCalculator;

    @Before
    public void setup()
    {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void testAddNumbers_NoParameter()
    {
        int result = stringCalculator.add("");
        assertEquals(0, result);
    }

    @Test
    public void testAddNumbers_OneParameter()
    {
        int result = stringCalculator.add("1");
        assertEquals(1, result);
    }

    @Test
    public void testAddNumbers_TwoParameters()
    {
        int result = stringCalculator.add("1,2");
        assertEquals(3, result);
    }

    @Test
    public void testAddNumbers_UnspecifiedNoOfParameters()
    {
        int result = stringCalculator.add("1,3,4,5");
        assertEquals(13, result);
    }
}
