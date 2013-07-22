import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

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

    @Test
    public void testAddNumbers_ManyNumbers() throws Exception
    {
        int result = stringCalculator.addNumbers("1,2,3,4,5");
        assertEquals(15, result);
    }

    @Test
    public void testAddNumbers_NewLineSeparator() throws Exception
    {
        int result = stringCalculator.addNumbers("1\n2,3,4,5");
        assertEquals(15, result);
    }

    @Test(expected = InvalidParameterException.class)
    public void testAddNumbers_NewLineSeparatorWithAnotherSeparatorCauseException() throws Exception
    {
        int result = stringCalculator.addNumbers("1\n,2,3,4,5");
        fail();
    }

    @Test
    public void testAddNumbers_CustomSeparator() throws Exception
    {
        int result = stringCalculator.addNumbers("//;\n1;2");
        assertEquals(3, result);
    }

    @Test(expected = InvalidParameterException.class)
    public void testAddNumbers_CustomSeparatorWrong() throws Exception
    {
        int result = stringCalculator.addNumbers("//;1;2");
        fail();
    }

}
