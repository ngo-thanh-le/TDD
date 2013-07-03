package StringCalculator;

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
            // Should be a number or numbers
            // C1: Using regex to parse a number
            String[] arrayNumbers = numbers.split(",|\\n");
            // C2: Using regex to parse numbers
            int totalValue = 0;
            for (String number : arrayNumbers)
            {
                totalValue += Integer.parseInt(number);
            }
            return totalValue;
        }
    }
}
