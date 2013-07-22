import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;

/**
 * User: lent
 * Date: 7/22/13
 */
public class StringCalculatorRemake
{
    // No blue possible by now
    public Integer addNumbers(String s)
    {
        // Nothing to blue
        if (StringUtils.isEmpty(s))
        {
            return 0;
        }
        String[] numbers = s.split(createSplitterPatterns(",", "\n"));
        Integer result = 0;
        for (String number : numbers)
        {
            if (StringUtils.isEmpty(number))
            {
                throw new InvalidParameterException();
            }
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
}
