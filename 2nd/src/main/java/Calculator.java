import java.lang.String;
import java.util.Scanner;

public class Calculator {
    /**
     * Константы помогающие определить тип символа
     */
    private enum Info {
        SIGN,
        NUMBER,
        SPACE,
        VARIABLE
    }

    /**
     * Проверяет выражение на ошибки записи
     * @param exp математическое выражение
     * @return Список ошибок или OK
     */
    public String check(String exp)
    {
        String errorRecord = "";
        if (!braceCheck(exp))
        {
            errorRecord+="Incorrect amount of braces"+'\n';
        }

        if(errorRecord!="")
            return errorRecord;
        else
            return "OK";
    }

    /**
     * Проверяет соответствие скобок в математическом выражении
     * @param exp математическое выражение
     * @return результат проверки
     */
    public boolean braceCheck(String exp)
    {
        int openBraces = 0;

        for(int i=0;i<exp.length();i++)
        {
            if (exp.charAt(i)=='(')
                openBraces++;
            if (exp.charAt(i)==')')
                openBraces--;

            if(openBraces<0)
                return false;
        }

        if(openBraces==0)
            return true;
        else
            return false;
    }

    private String deleteSpaces(String exp)
    {
        String result="";
        for(int i=0;i<exp.length();i++)
        {
            if(exp.charAt(i)!=' ')
            {
                result+=exp.charAt(i);
            }
        }
        return  result;
    }

    /**
     * Исправляет неточности пользователя в написании математической формулы:
     *      Отрицательные числа в скобках и в начале строки
     *      отсутствие знака * перед скобкой и числом и между скобками
     * @param exp математическое выражение
     * @return исправленное выражение
     */
    private String expRecordFixes(String exp)
    {
        exp = ' ' + exp + ' ';
        for(int i=0;i<exp.length()-1;i++)
        {
            if((exp.charAt(i)==' ' || exp.charAt(i)=='(')&&exp.charAt(i+1)=='-')
            {
                int j=i+2;
                while (charCheck(exp.charAt(j))==Info.NUMBER)
                    j++;
                exp=exp.substring(0,i+1)+ "(0-" + exp.substring(i+2,j) + ')' + exp.substring(j);
                i=j;
            }

            if(charCheck(exp.charAt(i))==Info.NUMBER && exp.charAt(i+1)=='(')
            {
                exp=exp.substring(0,i+1) + '*' + exp.substring(i+1);
            }
            if(exp.charAt(i)==')' && exp.charAt(i+1)=='(')
            {
                exp=exp.substring(0,i+1) + '*' + exp.substring(i+1);
            }
        }
        return exp.substring(1,exp.length()-1);
    }

    /**
     * Вычисляет количество переменных в математическом выражении
     * @param str Математическое выражение
     * @return Количество переменных
     */
    private int countVars(String str)
    {
        int count=0;
        for(int i=0;i<str.length()-1;i++)
        {
            if(charCheck(str.charAt(i))==Info.VARIABLE && (charCheck(str.charAt(i+1))!=Info.VARIABLE))
                count++;
        }
        count++;
        return count;
    }

    /**
     * Возвращает тип символа
     * @param sign Символ
     * @return Тип символа
     */
    private Info charCheck(char sign)
    {
        if(sign=='+' || sign=='-' || sign=='*'|| sign=='/'|| sign=='('|| sign==')' || sign=='^')
            return Info.SIGN;
        if(sign>='0' && sign<='9' || sign=='.')
            return Info.NUMBER;
        if(sign>='a' && sign<='z' || sign>='A' && sign<='Z')
            return Info.VARIABLE;
        return Info.SPACE;
    }

    /**
     * Подготавливает выражение к вычислению, ищет переменные и запрашивает их у пользователя
     * @param exp Математическое выражение
     * @param scan Объект сканнера
     * @return Вычисленное выражение
     */
    public float calculation(String exp,Scanner scan)
    {
        //Prepare for processing
        exp = deleteSpaces(exp);
        exp = ' ' + expRecordFixes(exp) + ' ';

        String Names[] = new String[countVars(exp)];
        int countNames = 0;
        float Num[] = new float[countVars(exp)];

        for(int i=0; i < exp.length(); ++i){
            if(charCheck(exp.charAt(i))==Info.VARIABLE)
            {
                VariableSkipper(i, exp);
            }
        }

        return 0.0f;
    }

    int VariableSkipper(int i, String exp){
        int j=i;
        while(charCheck(exp.charAt(j))==Info.VARIABLE)
            j++;

        return j;
    }
}
