import org.apache.commons.lang3.StringUtils;

/**
 * User: lent
 * Date: 7/22/13
 */
public class StringCalculatorRemake
{
    public Integer addNumbers(String s)
    {
        // Nothing to blue
        if (StringUtils.isEmpty(s))
        {
            return 0;
        }
        String[] numbers = s.split(",");
        Integer result = 0;
        for (String number : numbers)
        {
            result += Integer.parseInt(number);
        }
        return result;
    }
}
