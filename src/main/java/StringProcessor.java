
public class StringProcessor {
    /**
     * Константы помогающие определить тип символа
     */
    public enum Info {
        SIGN,
        NUMBER,
        SPACE,
        VARIABLE
    }

    public static int VariableSkipper(int i, String exp)
    {
        int j=i;
        while(charCheck(exp.charAt(j))== Info.VARIABLE)
            j++;

        return j;
    }

    /**
     * Возвращает тип символа
     * @param sign Символ
     * @return Тип символа
     */
    public static Info charCheck(char sign)
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
     * Возвращает имя переменной начиная с позиции i
     * @param str Математическое выражение
     * @param i Начало отсчета
     * @return Имя переменной
     */
    public static String nextVar(String str,int i)
    {
        String ret = "";
        while (i<str.length() && charCheck(str.charAt(i))== Info.VARIABLE)
        {
            ret += str.charAt(i);
            i++;
        }

        return ret;
    }

    /**
     * Возвращает целое число с конца начиная с позиции i
     * @param exp Математическое выражение
     * @param i Начало отсчета
     * @return Целое число
     */
    private static int nextIntRevers(String exp, int i)
    {
        String num="";

        while(i>=0 && charCheck(exp.charAt(i))== Info.NUMBER)
        {
            num = exp.charAt(i) + num;
            i--;
        }

        return Integer.parseInt(num);
    }

    /**
     * Возвращает приоритет знака
     * @param sign Знак
     * @return Приоритет знака
     */
    public static int signPriority(char sign)
    {
        if(sign=='+'||sign=='-')
            return 1;
        if(sign=='*'||sign=='/')
            return 2;
        if(sign=='^')
            return 3;
        return 0;
    }

    /**
     * Вычисляет количество переменных в математическом выражении
     * @param str Математическое выражение
     * @return Количество переменных
     */
    public static int countVars(String str)
    {
        int count=0;
        for(int i=0;i<str.length()-1;i++)
        {
            if(charCheck(str.charAt(i))== Info.VARIABLE && (charCheck(str.charAt(i+1))!= Info.VARIABLE))
                count++;
        }
        count++;
        return count;
    }

    /**
     * Вычисляет количество чисел в математическом выражении
     * @param str Математическое выражение
     * @return Количество чисел
     */
    public static int countNums(String str)
    {
        int count=0;
        for(int i=0;i<str.length()-1;i++)
        {
            if(charCheck(str.charAt(i))== Info.NUMBER && (charCheck(str.charAt(i+1))!= Info.NUMBER))
                count++;
        }
        count++;
        return count;
    }

    /**
     * Вычисляет число float начиная с позиции i
     * @param exp Математическое выражение
     * @param i Начало отсчета
     * @return float число
     */
    public static float nextFloat(String exp, int i)
    {
        String num="";

        while(i<exp.length() && charCheck(exp.charAt(i))== Info.NUMBER)
        {
            num+=exp.charAt(i);
            i++;
        }

        return Float.parseFloat(num);
    }

    /**
     * Вычисляет количество знаков в математическом выражении
     * @param str Математическое выражение
     * @return Количество знаков
     */
    public static int countSigns(String str)
    {
        int count=0;
        for(int i=0;i<str.length()-1;i++)
        {
            if(charCheck(str.charAt(i))== Info.SIGN)
                count++;
        }
        return count;
    }

    /**
     * Вычисляет положение 2го операнда в Польской записи
     * @param str Польская запись математического выражения
     * @param i Начало отсчета
     * @return Позиция 2го операнда
     */
    private static int positionSkip(String str,int i)
    {
        int kSign =0;
        int kNum =0;

        while(kNum - kSign != 1)
        {
            if(charCheck(str.charAt(i))== Info.NUMBER)
            {
                kNum++;
                while(charCheck(str.charAt(i))== Info.NUMBER)
                {
                    i--;
                }
                i--;
            }
            else if(charCheck(str.charAt(i))== Info.SIGN)
            {
                kSign++;
                i--;
            }
            else
            {
                i--;
            }
        }
        return i;
    }

    /**
     * Рекурсивная функция вычисления подстроки обратной Польской записи заданного математического выражения
     *      Начиная с символа i вычисляет значения выражения
     * @param numbers Массив чисел использующихся в выражении
     * @param countNum Количество чисел в массиве
     * @param polNote Математическое выражение в обратной Польской записи
     * @param i Начало вычислений
     * @return Вычисленное значение подстроки выражения
     */
    public static float calculateReversPolandRecord(float[] numbers,int countNum,String polNote, int i)
    {
        float num[] = new float[2];
        if(charCheck(polNote.charAt(i))== Info.SIGN) {
            char sign = polNote.charAt(i);
            i--;

            for(int k=1;k>=0;k--)
            {
                if (charCheck(polNote.charAt(i)) == Info.SIGN) {
                    num[k] = calculateReversPolandRecord(numbers, countNum, polNote, i);
                    i = positionSkip(polNote, i);
                } else {
                    int ind = nextIntRevers(polNote, i);
                    num[k] = numbers[ind];
                    while(ind>0) {
                        ind/=10;
                        i--;
                    }
                    i--;
                }
            }

            return signCalculation(num[0],num[1],sign);
        }
        else
        {
            return numbers[nextIntRevers(polNote, i)];
        }
    }

    /**
     * Вычисляет математическое выражения из 2х операндов и 1го оператора
     * Доступные операторы:
     * + Сложение
     * - Вычитание
     * / Деление
     * * Умножение
     * ^ Степень
     *
     * @param num1 Левый операнд
     * @param num2 Правый операнд
     * @param sign Математический оператор
     * @return Значение выражения
     */
    private static float signCalculation(float num1, float num2, char sign) {
        switch (sign) {
            case '+':
                return num1 + num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            case '-':
                return num1 - num2;
            case '^':
                return (float) Math.pow(num1, num2);
        }
        return 0.0f;
    }

    /**
     * Проверяет выражение на ошибки записи
     *
     * @param exp математическое выражение
     * @return Список ошибок или OK
     */
    public static String check(String exp) {
        String errorRecord = "";
        if (!braceCheck(exp)) {
            errorRecord += "Incorrect amount of braces" + '\n';
        }

        if (errorRecord != "")
            return errorRecord;
        else
            return "OK";
    }

    /**
     * Проверяет соответствие скобок в математическом выражении
     *
     * @param exp математическое выражение
     * @return результат проверки
     */
    public static boolean braceCheck(String exp) {
        int openBraces = 0;

        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(')
                openBraces++;
            if (exp.charAt(i) == ')')
                openBraces--;

            if (openBraces < 0)
                return false;
        }

        if (openBraces == 0)
            return true;
        else
            return false;
    }

    /**
     * Исправляет неточности пользователя в написании математической формулы:
     * Отрицательные числа в скобках и в начале строки
     * отсутствие знака * перед скобкой и числом и между скобками
     *
     * @param exp математическое выражение
     * @return исправленное выражение
     */
    public static String expRecordFixes(String exp) {
        exp = ' ' + exp + ' ';
        for (int i = 0; i < exp.length() - 1; i++) {
            if ((exp.charAt(i) == ' ' || exp.charAt(i) == '(') && exp.charAt(i + 1) == '-') {
                int j = i + 2;
                while (charCheck(exp.charAt(j)) == Info.NUMBER)
                    j++;
                exp = exp.substring(0, i + 1) + "(0-" + exp.substring(i + 2, j) + ')' + exp.substring(j);
                i = j;
            }

            if (charCheck(exp.charAt(i)) == Info.NUMBER && exp.charAt(i + 1) == '(') {
                exp = exp.substring(0, i + 1) + '*' + exp.substring(i + 1);
            }
            if (exp.charAt(i) == ')' && exp.charAt(i + 1) == '(') {
                exp = exp.substring(0, i + 1) + '*' + exp.substring(i + 1);
            }
        }
        return exp.substring(1, exp.length() - 1);
    }

    /**
     * Удаляет все пробелы для корректной работы программы
     *
     * @param exp математическое выражение
     * @return выражение без пробелов
     */
    public static String deleteSpaces(String exp) {
        String result = "";
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) != ' ') {
                result += exp.charAt(i);
            }
        }
        return result;
    }

    /**
     * Проверяет существует ли переменная в списке
     * Если переменная существует возвращает его индекс, иначе -1
     *
     * @param varNames   Список имен переменных
     * @param countNames Количество существующих имен переменнных
     * @param name       Проверяемое имя переменной
     * @return Если переменная существует возвращает его индекс, иначе -1
     */
    public static int varIsExist(String varNames[], int countNames, String name) {
        for (int k = 0; k < countNames; k++) {
            if (varNames[k].equals(name)) {
                return k;
            }
        }
        return -1;
    }
}
