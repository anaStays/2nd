import java.lang.String;
import java.util.Scanner;


public class Calculations {
    /**
     * Подготавливает выражение к вычислению, ищет переменные и запрашивает их у пользователя
     *
     * @param exp  Математическое выражение
     * @param scan Объект сканнера
     * @return Вычисленное выражение
     */
    public float calculation(String exp, Scanner scan) {
        exp = StringProcessor.deleteSpaces(exp);
        exp = ' ' + StringProcessor.expRecordFixes(exp) + ' ';

        String varNames[] = new String[StringProcessor.countVars(exp)];
        int countNames = 0;
        float varNum[] = new float[StringProcessor.countVars(exp)];

        int i = 0;
        while (i < exp.length()) {
            if (StringProcessor.charCheck(exp.charAt(i)) == StringProcessor.Info.VARIABLE) {
                int j = StringProcessor.VariableSkipper(i, exp);

                String name = StringProcessor.nextVar(exp, i);
                float varN = 0.0f;
                int ind = StringProcessor.varIsExist(varNames, countNames, name);

                if (ind == -1) {
                    System.out.print(name + " = ");
                    varN = scan.nextFloat();
                    varNames[countNames] = name;
                    varNum[countNames] = varN;
                    countNames++;
                } else {
                    varN = varNum[ind];
                }

                String strN = Float.toString(varN);

                exp = exp.substring(0, i) + strN + exp.substring(j);
                i = i + strN.length();
            }
            i++;
        }
        return convertToPolandRecord(exp.substring(1, exp.length() - 1));
    }


    /**
     * Конвертирует математическую запись выражения в обратную польскую запись и подготавливает числа для вычисления результата
     *
     * @param exp Математическое выражение
     * @return Вычисленное выражение
     */
    public float convertToPolandRecord(String exp) {
        float result = 0.0f;

        float[] numbers = new float[StringProcessor.countNums(' ' + exp + ' ')];
        int countNum = 0;

        char[] signs = new char[StringProcessor.countSigns(' ' + exp + ' ')];
        int countSign = 0;

        String polNote = "";

        int i = 0;
        while (exp.length() > i) {
            if (StringProcessor.charCheck(exp.charAt(i)) == StringProcessor.Info.SIGN) {
                if (countSign == 0 || exp.charAt(i) == '(') {
                    signs[countSign] = exp.charAt(i);
                    countSign++;
                } else if (StringProcessor.signPriority(signs[countSign - 1]) < StringProcessor.signPriority(exp.charAt(i))) {
                    signs[countSign] = exp.charAt(i);
                    countSign++;
                } else if (exp.charAt(i) == ')') {
                    countSign--;
                    while (countSign >= 0 && signs[countSign] != '(') {
                        polNote += signs[countSign];
                        signs[countSign] = ' ';
                        countSign--;
                    }
                    signs[countSign] = ' ';
                } else {
                    countSign--;
                    while (countSign >= 0 && StringProcessor.signPriority(signs[countSign]) >= StringProcessor.signPriority(exp.charAt(i))) {
                        polNote += signs[countSign];
                        signs[countSign] = ' ';
                        countSign--;
                    }
                    countSign++;
                    signs[countSign] = exp.charAt(i);
                    countSign++;
                }
                i++;
            } else {

                numbers[countNum] = StringProcessor.nextFloat(exp, i);
                polNote += '#' + Integer.toString(countNum);
                countNum++;
                while (i < exp.length() && StringProcessor.charCheck(exp.charAt(i)) == StringProcessor.Info.NUMBER)
                    i++;
            }
        }
        for (int j = countSign - 1; j >= 0; j--) {
            polNote += signs[j];
        }
        return StringProcessor.calculateReversPolandRecord(numbers, countNum, polNote, polNote.length() - 1);
    }





}
