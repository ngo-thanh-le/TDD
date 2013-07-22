import org.apache.commons.lang3.StringUtils;

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
        return Integer.parseInt(s);
    }
}
