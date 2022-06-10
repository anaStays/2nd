import java.lang.String;
import java.util.Scanner;

public class Main {
    public  static void main(String args[])
    {
        Scanner scanner = new Scanner(System.in);
        String example=scanner.nextLine();
        Calculations calc= new Calculations();
        String errors=StringProcessor.check(example);
        if(errors=="OK")
        {
            float ans = calc.calculation(example,scanner);
            System.out.println(ans);
        }
        else
        {
            System.out.println(errors);
        }
    }
}
