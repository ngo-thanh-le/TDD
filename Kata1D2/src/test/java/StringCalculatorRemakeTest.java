import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: lent
 * Date: 7/22/13
 */
public class StringCalculatorRemakeTest
{
    StringCalculatorRemake stringCalculator;

    @Before
    public void setup()
    {
        stringCalculator = new StringCalculatorRemake();
    }

    @Test
    public void testAddNumbers_EmptyString() throws Exception
    {
        int result = stringCalculator.addNumbers("");
        assertEquals(0, result);
    }

    @Test
    public void testAddNumbers_OneNumber() throws Exception
    {
        int result = stringCalculator.addNumbers("1");
        assertEquals(1, result);
    }

    @Test
    public void testAddNumbers_TwoNumbers() throws Exception
    {
        int result = stringCalculator.addNumbers("1,2");
        assertEquals(3, result);
    }
}
