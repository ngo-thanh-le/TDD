package StringCalculator;

import java.security.InvalidParameterException;

/**
 * User: lent
 * Date: 7/3/13
 */
public class StringCalculator
{
    public int add(String numbers)
    {
        if ("".equals(numbers.trim()))
        {
            return 0;
        }
        else
        {
            String delimiter = ",|\\n";
            // Detect new delimiter
            if (numbers.startsWith("//"))
            {
                delimiter = numbers.substring(2, 3); // Support delimiter one char
                numbers = numbers.substring(3);
            }
            // Should be a number or numbers
            // C1: Using regex to parse a number
            String[] arrayNumbers = numbers.split(delimiter);
            // C2: Using regex to parse numbers
            int totalValue = 0;
            for (String number : arrayNumbers)
            {
                try
                {
                    totalValue += Integer.parseInt(number);
                }
                catch (Exception e)
                {
                    throw new InvalidParameterException("Invalid parameter input");
                }
            }
            return totalValue;
        }
    }
}
