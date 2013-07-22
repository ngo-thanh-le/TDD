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
        if (s.startsWith("//"))
        {
            customerDelimiter = matchRegex(s).group(1);
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

    private Matcher matchRegex(String possibleInput)
    {
        Pattern regex = Pattern.compile("//(.)\\n");
        Matcher matcher = regex.matcher(possibleInput);
        if (matcher.find(0))
        {
            return matcher;
        }
        return null;
    }
}
