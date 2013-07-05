import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertTrue;

/**
 * User: lent
 * Date: 7/4/13
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
    public void testAddNumbers_EmptyString() throws Exception
    {
        int result = stringCalculator.add("");
        assertEquals(0, result);
    }

    @Test
    public void testAddNumbers_OneNumber() throws Exception
    {
        int result = stringCalculator.add("1");
        assertEquals(1, result);
    }

    @Test
    public void testAddNumbers_TwoNumbers() throws Exception
    {
        int result = stringCalculator.add("1,2");
        assertEquals(3, result);
    }

    @Test
    public void testAddNumbers_ManyNumbers() throws Exception
    {
        int result = stringCalculator.add("1,2,3,4,5");
        assertEquals(15, result);
    }

    @Test
    public void testAddNumbers_NewLineSeparator() throws Exception
    {
        int result = stringCalculator.add("1\n2,3,4,5");
        assertEquals(15, result);
    }

    @Test(expected = InvalidParameterException.class)
    public void testAddNumbers_NewLineSeparatorWithAnotherSeparatorCauseException() throws Exception
    {
        int result = stringCalculator.add("1\n,2,3,4,5");
        fail();
    }

    @Test
    public void testAddNumbers_CustomSeparator() throws Exception
    {
        int result = stringCalculator.add("//;\n1;2");
        assertEquals(3, result);
    }

    @Test(expected = InvalidParameterException.class)
    public void testAddNumbers_CustomSeparatorWrong() throws Exception
    {
        int result = stringCalculator.add("//;1;2");
        fail();
    }

    @Test
    public void testAddNumbers_OneNegativeNumber()
    {
        try
        {
            int result = stringCalculator.add("-1");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e.getMessage().equals("Negatives are not allowed. Wrong: -1"));
        }
    }

    @Test
    public void testAddNumbers_MultiNegativeNumbers()
    {
        try
        {
            int result = stringCalculator.add("-1,2,-3");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e.getMessage().equals("Negatives are not allowed. Wrong: -1,-3"));
        }
    }

    @Test
    public void testAddNumbers_IgnoreGreaterThan1000() throws Exception
    {
        int result = stringCalculator.add("1001,2");
        assertEquals(2, result);
    }

    @Test
    public void testAddNumbers_CustomDelimitorVeryLong() throws Exception
    {
        int result = stringCalculator.add("//[*****]\n1*****2*****3");
        assertEquals(6, result);
    }

    @Test
    public void testAddNumbers_CustomLongDelimitors() throws Exception
    {
        int result = stringCalculator.add("//[**][%]\n1**2%3");
        assertEquals(6, result);
    }
}
