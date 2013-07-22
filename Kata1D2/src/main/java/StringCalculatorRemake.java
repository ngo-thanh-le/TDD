import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;

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
        if (s.startsWith("//"))
        {
            customerDelimiter = s.substring(s.indexOf("//") + "//".length(), s.indexOf("\n"));
            // Cut the string
            s = s.substring(s.indexOf("\n") + 1);
        }

        String[] numbers = s.split(createSplitterPatterns(",", "\n", customerDelimiter));
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
