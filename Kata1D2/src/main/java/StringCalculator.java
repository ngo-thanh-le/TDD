import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: lent
 * Date: 7/4/13
 */
public class StringCalculator
{
    public int add(String numbers) throws Exception
    {
        if ("".equals(numbers.trim()))
        {
            return 0;
        }
        else
        {
            String delimiter = ",|\\n";
            // Detect new delimiter
            Pattern regex = Pattern.compile("//(.*)\\n");
            Matcher matcher = regex.matcher(numbers);
            if (matcher.find(0))
            {
                delimiter = matcher.group(1);
                // delimiter = numbers.substring(2, 3); // Support delimiter one char
                String endingDelimiter = numbers.substring(3, 4);
                if (!endingDelimiter.equals("\n"))
                {
                    throw new InvalidParameterException();
                }
                numbers = numbers.substring(4);
            }
            // Should be a number or numbers
            // C1: Using regex to parse a number
            String[] arrayNumbers = numbers.split(delimiter);
            // C2: Using regex to parse numbers
            int totalValue = 0;
            List<String> invalidNumbers = new ArrayList<String>();
            for (String number : arrayNumbers)
            {
                try
                {
                    Integer value = Integer.parseInt(number);
                    if (value < 0)
                    {
                        invalidNumbers.add(value.toString());
                    }
                    totalValue += value;
                }
                catch (Exception e)
                {
                    throw new InvalidParameterException("Invalid parameter input");
                }
            }
            if (invalidNumbers.size() > 0)
            {
                String message = "Negatives are not allowed. Wrong: ";
                for (String invalidValue : invalidNumbers)
                {
                    message += invalidValue + ",";
                }
                message = message.substring(0, message.length() - 1);
                throw new Exception(message);
            }
            return totalValue;
        }
    }
}
