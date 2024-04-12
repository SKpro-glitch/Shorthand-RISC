import java.util.*;
class ShortHandRISC 
{
    static String code[][] = new String[100][4];
    static String line[] = new String[4];
    static int address = 0; double acc; boolean flag;
    HashMap<String, Integer> ndata = new HashMap<>();
    HashMap<String, String> sdata = new HashMap<>();

    void error(int x, int y)
    {
        String e = "";
        switch(x)
        {
            case 0:
                e = "Invalid command";
                break;
            case 1:
                e = "Variable not declared";
                break;
            case 2:
                e = "Value must be numeric";
                break;
            case 3:
                e = "Value too large";
                break;
            case 4:
                e = "Invalid variable name";
                break;
        }
        System.out.println("Line " + address + ": " + line[y] + ": " + e);
        address = 100;
    }

    void add(String a, String b)
    {
        if(ndata.containsKey(a))
            if(ndata.containsKey(b))
                ndata.put(line[1], ndata.get(a) + ndata.get(b));
            else
                error(1, 2);
        else
            error(1, 1);
    }

    void sub(String a, String b)
    {
        if(ndata.containsKey(a))
            if(ndata.containsKey(b))
                ndata.put(line[1], ndata.get(a) - ndata.get(b));
            else
                error(1, 2);
        else
            error(1, 1);
    }

    void mul(String a, String b)
    {
        if(ndata.containsKey(a))
            if(ndata.containsKey(b))
                ndata.put(line[1], ndata.get(a) * ndata.get(b));
            else
                error(1, 2);
        else
            error(1, 1);
    }

    void div(String a, String b)
    {
        if(ndata.containsKey(a))
            if(ndata.containsKey(b))
                ndata.put(line[1], ndata.get(a) / ndata.get(b));
            else
                error(1, 2);
        else
            error(1, 1);
    }

    void exp(String a, String b)
    {
        if(ndata.containsKey(a))
            if(ndata.containsKey(b))
                ndata.put(line[1], (int) Math.pow(ndata.get(a), ndata.get(b)));
            else
                error(1, 2);
        else
            error(1, 1);
    }

    void log(String a, String b)
    {
        if(ndata.containsKey(a))
            if(ndata.containsKey(b))
                ndata.put(line[1], (int) (Math.log(ndata.get(a)) / Math.log(ndata.get(b))));
            else
                error(1, 2);
        else
            error(1, 1);
    }
    
    void assign(int x)
    {
        if(Character.isLetter(line[1].charAt(0)))
            ndata.put(line[1], x);
        else
            error(4, 1);
    }

    void Maths()
    {
        switch(line[0].charAt(1))
        {
            case '+': add(line[2], line[3]); break;
            case '-': sub(line[2], line[3]); break;
            case '*': mul(line[2], line[3]); break;
            case '/': div(line[2], line[3]); break;
            case '^': exp(line[2], line[3]); break;
            case '~': log(line[2], line[3]); break;
            case '=':
                try
                {
                    int x = Integer.parseInt(line[2]);
                    assign(x);
                }
                catch(Exception e)
                {
                    error(2, 2);
                }
                break;
            default: error(0, 0);
        }
    }

    void concat(String a, String b)
    {
        if(sdata.containsKey(a))
            if(b.startsWith("\"") && b.endsWith("\""))
                sdata.put(a, sdata.get(a) + b.substring(1, b.length()-1));
            else if(sdata.containsKey(b))
                sdata.put(a, sdata.get(a) + sdata.get(b));
            else
                error(1, 2);
        else
            error(1, 1);
    }

    void delto(String a, String b)
    {
        if(sdata.containsKey(a))

            try
            {
                int x = Integer.parseInt(b); 
                String s = sdata.get(a);
                if(x<s.length())
                    sdata.put(a, s.substring(x));
                else
                    error(3, 2);
            }
            catch(Exception e)
            {
                error(2, 2);
            }
        else
            error(1, 1);
    }

    void repeat(String a, String b)
    {
        if(sdata.containsKey(a))
            try
            {
                int x = Integer.parseInt(b);
                String s = sdata.get(a);
                sdata.put(a, s.repeat(x));
            }
            catch(Exception e)
            {
                error(2, 2);
            }
        else
            error(1, 1);        
    }

    void delfrom(String a, String b)
    {
        if(sdata.containsKey(a))

            try
            {
                int x = Integer.parseInt(b); 
                String s = sdata.get(a);
                if(x<s.length())
                    sdata.put(a, s.substring(0, x));
                else
                    error(3, 2);
            }
            catch(Exception e)
            {
                error(2, 2);
            }
        else
            error(1, 1);
    }

    void Char(String a, String b)
    {
        if(sdata.containsKey(a))

            try
            {
                int x = Integer.parseInt(b); 
                String s = sdata.get(a);
                if(x<s.length())
                    sdata.put(a, "" + s.charAt(x));
                else
                    error(3, 2);
            }
            catch(Exception e)
            {
                error(2, 2);
            }
        else
            error(1, 1);        
    }

    void compare(String a, String b)
    {
        if(sdata.containsKey(a))
            a = sdata.get(a);
        else if(a.startsWith("\"") && a.endsWith("\""))
            a = a.substring(1, a.length()-1);
        else
        {
            error(1, 1);
            return;
        }
        if(sdata.containsKey(b))
            b = sdata.get(b);
        else if(a.startsWith("\"") && a.endsWith("\""))
            b = b.substring(1, b.length()-1);
        else
        {
            error(1, 1);
            return;
        }
        int x = a.compareTo(b);
        if(x<0)
            flag = true;
    }

    void Strings()
    {
        switch(line[0].charAt(1))
        {
            case '+': concat(line[1], line[2]); break;
            case '-': delto(line[1], line[2]); break;
            case '*': repeat(line[1], line[2]); break;
            case '/': delfrom(line[1], line[2]); break;
            case '^': Char(line[1], line[2]); break;
            case '~': compare(line[1], line[2]); break;
            case '=': sdata.put(line[1], line[2]); break;
            default: error(0, 0);
        }
    }

    void Register()
    {
        switch(line[0].charAt(1))
        {
            case '+':
                break;
            case '-':
                break;
            case '*':
                break;
            case '/':
                break;
            case '^':
                break;
            case '~':
                break;
            case '=':
                break;
            default:
                error(0, 0);
        }
    }

    void Logical()
    {
        switch(line[0].charAt(1))
        {
            case '+':
                break;
            case '-':
                break;
            case '*':
                break;
            case '/':
                break;
            case '^':
                break;
            case '~':
                break;
            case '=':
                break;
            default:
                error(0, 0);
        }
    }

    void Jump()
    {
        switch(line[0].charAt(1))
        {
            case '+':
                break;
            case '-':
                break;
            case '*':
                break;
            case '/':
                break;
            case '^':
                break;
            case '~':
                break;
            case '=':
                break;
            default:
                error(0, 0);
        }
    }

    void Condition()
    {
        switch(line[0].charAt(1))
        {
            case '+':
                break;
            case '-':
                break;
            case '*':
                break;
            case '/':
                break;
            case '^':
                break;
            case '~':
                break;
            case '=':
                break;
            default:
                error(0, 0);
        }
    }

    void Print()
    {
        if(ndata.containsKey(line[1]))
            System.out.println(ndata.get(line[1]));
        else if(sdata.containsKey(line[1]))
            System.out.println(ndata.get(line[1]));
        else
            error(1, 1);
    }

    void compiler()
    {
        switch(line[0].charAt(0))
        {
            case 'M': case 'm': Maths(); break;
            case 'S': case 's': Strings(); break;
            case 'R': case 'r': Register(); break;
            case 'L': case 'l': Logical(); break;
            case 'J': case 'j': Jump(); break;
            case 'C': case 'c': Condition(); break;
            case 'P': case 'p': Print(); break;
            default:
                if(line[0].equalsIgnoreCase("end"))
                    break;
                error(0, 0);
        }
    }

    public static void main() 
    {
        Scanner sc = new Scanner(System.in);
        ShortHandRISC ob = new ShortHandRISC();
        System.out.println("Write code here:");
        for(address=0; address<100; address++)
        {
            code[address] = sc.nextLine().split(" ");
            if(code[address][0].equalsIgnoreCase("end"))
                break;
        }
        System.out.println("\nOutput:\n");
        address = 0; line = code[0];
        while(!line[0].equalsIgnoreCase("end"))
        {
            line = code[address];
            ob.compiler();
            address++;
        }
        System.out.println(ob.ndata);
    }
}