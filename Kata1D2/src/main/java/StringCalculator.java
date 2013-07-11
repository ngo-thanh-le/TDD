import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
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
            StringBuilder sbNumbers = new StringBuilder(numbers);
            String splitters = processSplitterMark(sbNumbers);
            numbers = sbNumbers.toString();
            String[] arrayNumbers = numbers.split(splitters);
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
                    if (value < 1000)
                    {
                        totalValue += value;
                    }
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

    private String processSplitterMark(StringBuilder numbers)
    {
        List<String> delimiters = new ArrayList<String>();
        delimiters.add(",");
        delimiters.add("\\n");
        // Detect new delimiters
        Pattern regex = Pattern.compile("//((.)|(\\[.*\\])*)\\n");
        String temporaryValue = numbers.toString();
        Matcher matcher = regex.matcher(temporaryValue);
        String additionalDelimiters;
        if (matcher.find(0))
        {
            String fullMark = matcher.group(0);
            numbers.delete(0, numbers.length());
            numbers.append(temporaryValue.replace(fullMark, ""));
            additionalDelimiters = matcher.group(1);
            // Break delimiters(s) if any
            if (additionalDelimiters.length() > 1)
            {
                additionalDelimiters = additionalDelimiters.substring(1, additionalDelimiters.length() - 1);
            }
            String[] arrDelimiters = additionalDelimiters.split("\\]\\[");
            // delimiters = numbers.substring(2, 3); // Support delimiters one char
            delimiters.addAll(Arrays.asList(arrDelimiters));
        }
        return createSplittersPattern(delimiters);
    }

    private String createSplittersPattern(List<String> delimiters)
    {
        String finalSplitters = "";
        for (String splitter : delimiters)
        {
            if (splitter.equals(",") || splitter.equals("\\n"))
            {
                finalSplitters += splitter + "|";
            }
            else
            {
                finalSplitters += Pattern.quote(splitter) + "|";
            }
        }
        finalSplitters = finalSplitters.substring(0, finalSplitters.length() - 1);
        return finalSplitters;
    }
}
