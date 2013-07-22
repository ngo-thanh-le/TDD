import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: lent
 * Date: 7/22/13
 */
public class StringCalculatorRemake
{
    public Integer addNumbers(String s)
    {
        if (StringUtils.isEmpty(s))
        {
            return 0;
        }
        // Detect custom delimiter
        String customerDelimiter = null;
        Matcher matcher = matchRegex(s);
        if (matcher != null)
        {
            customerDelimiter = matcher.group(1);
            // Cut the string
            s = matcher.group(2);
        }

        String[] numbers = s.split(createSplitterPatterns(",", "\n", customerDelimiter));
        Integer result = 0;
        for (String number : numbers)
        {
            isNumberOnly(number);
            result += Integer.parseInt(number);
        }
        return result;
    }

    public String createSplitterPatterns(String... patterns)
    {
        String finalPattern = "";
        for (String pattern : patterns)
        {
            finalPattern += pattern + "|";
        }
        finalPattern = finalPattern.substring(0, finalPattern.length() - 1);
        return finalPattern;
    }

    private Matcher matchRegex(String possibleInput)
    {
        Pattern regex = Pattern.compile("//(.)\\n(.*)");
        Matcher matcher = regex.matcher(possibleInput);
        if (matcher.find(0))
        {
            return matcher;
        }
        return null;
    }

    private boolean isNumberOnly(String input) throws InvalidParameterException
    {
        try
        {
            Integer.parseInt(input);
        }
        catch (Exception e)
        {
            throw new InvalidParameterException();
        }
        return true;
    }
}
