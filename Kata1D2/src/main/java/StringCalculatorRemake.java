import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: lent
 * Date: 7/22/13
 */
public class StringCalculatorRemake
{
    public Integer addNumbers(String s) throws Exception
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
        List<String> negativeNumbers = new ArrayList<String>();
        for (String number : numbers)
        {
            if (!isPositiveNumber(number))
            {
                negativeNumbers.add(number);
            }
            result += customParseNumber(number);
        }
        checkNegativeNumbers(negativeNumbers);
        return result;
    }

    private int customParseNumber(String number)
    {
        int result = Integer.parseInt(number);
        return result > 1000 ? 0 : result;
    }

    private void checkNegativeNumbers(List<String> negativeNumbers) throws Exception
    {
        if (negativeNumbers.size() > 0)
        {
            String message = "Negatives are not allowed. Wrong: ";
            for (String negativeNumber : negativeNumbers)
            {
                message += negativeNumber + ",";
            }
            message = message.substring(0, message.length() - 1);
            throw new Exception(message);
        }
    }

    public String createSplitterPatterns(String... patterns)
    {
        String finalPattern = "";
        for (String pattern : patterns)
        {
            if (pattern == null)
            {
                continue;
            }
            if (pattern.matches("\\[.*\\]"))
            {
                finalPattern = parseCustomPatterns(finalPattern, pattern);
                continue;
            }
            finalPattern += Pattern.quote(pattern) + "|";
        }
        finalPattern = finalPattern.substring(0, finalPattern.length() - 1);
        return finalPattern;
    }

    private String parseCustomPatterns(String finalPattern, String pattern)
    {
        pattern = pattern.substring(1, pattern.length() - 1);
        String[] multiPatternParsed = pattern.split("\\]\\[");
        if (multiPatternParsed.length >= 1)
        {
            // Should be multi pattern
            for (String subPattern : multiPatternParsed)
            {
                finalPattern += Pattern.quote(subPattern) + "|";
            }

        }
        return finalPattern;
    }

    private Matcher matchRegex(String possibleInput)
    {
        Pattern regex = Pattern.compile("//(\\[.*\\]|.)*\\n(.*)");
        Matcher matcher = regex.matcher(possibleInput);
        if (matcher.find(0))
        {
            return matcher;
        }
        return null;
    }

    private boolean isPositiveNumber(String input) throws Exception
    {
        try
        {
            int result = Integer.parseInt(input);
            return result >= 0;
        }
        catch (NumberFormatException e)
        {
            throw new InvalidParameterException();
        }
    }
}
