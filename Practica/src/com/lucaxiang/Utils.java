package com.lucaxiang;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utils Class
 *
 * @version 1.0.0
 * @author luca xiang
 */
public class Utils
{
    /**
     * Safe Input Text
     * @return String User Imputed Text
     */
    public static String input()
    {
        Scanner in = new Scanner(System.in);
        String result;
        do {
            try
            {
                System.out.print(">> ");
                result = in.nextLine();
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                in = new Scanner(System.in);
                continue;
            }
            if(result.length() == 0)
            {
                in = new Scanner(System.in);
                continue;
            }
            break;
        }while(true);
        return result;
    }
    public static String inputAllowEmpty()
    {
        Scanner in = new Scanner(System.in);
        String result;
        do {
            try
            {
                System.out.print(">> ");
                result = in.nextLine();
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                in = new Scanner(System.in);
                continue;
            }
            break;
        }while(true);
        return result;
    }


    public static String input(int maxlength)
    {
        Scanner in = new Scanner(System.in);
        String result;
        do {
            try
            {
                System.out.print(">> ");
                result = in.nextLine();
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                in = new Scanner(System.in);
                continue;
            }
            if(result.length() == 0)
            {
                in = new Scanner(System.in);
                continue;
            }
            else if(result.length() > maxlength)
            {
                System.out.println("Max Length is " + maxlength);
                in = new Scanner(System.in);
                continue;
            }
            break;
        }while(true);
        return result;
    }

    /**
     * Safe Input Number
     * @return int User Imputed Number
     */
    public static int inputNumber()
    {
        int result;
        do{
            try
            {
                result = Integer.parseInt(input());
            }
            catch (Exception e)
            {
                System.err.println("You Must Input Number");
                continue;
            }
            break;
        }while (true);
        return result;
    }
    /**
     * Safe Input Number With Range
     * @param min int Minimum Value
     * @param max int Maximum Value
     * @return String User Imputed Text
     */
    public static int inputNumber(int min,int max)  {
        return inputNumber(min,max,String.format("Option Does Not Existe, A Range is %d - %d",min,max));
    }
    /**
     * Safe Input Number With Range
     * @param min int Minimum Value
     * @param max int Maximum Value
     * @param msg String Index Out Of Bounds Message
     * @return String User Imputed Text
     */
    public static int inputNumber(int min,int max,String msg)  {
        int result;
        do{
            result = inputNumber();
            if(result < min || result > max)
            {
                pressKeyToContinue(msg);
                continue;
            }
            break;
        }while (true);
        return result;
    }
    /**
     * Safe Input Decimal
     * @return double User inputed Decimal
     */
    public static double inputDecimal()
    {
        double result;
        do{
            try
            {
                result = Double.parseDouble(input());
            }
            catch (Exception e)
            {
                System.err.println("You Must Input Decimal Number");
                continue;
            }
            break;
        }while (true);
        return result;
    }
    /**
     * Safe Input Number With Range
     * @param min int Minimum Value
     * @param max int Maximum Value
     * @return String User Imputed Text
     */
    public static double inputDecimal(double min,double max)  {
        return inputDecimal(min,max,String.format("Not Valid Decimal Number %.2f -> %.2f",min,max));
    }
    /**
     * Safe Input Number With Range
     * @param min int Minimum Value
     * @param max int Maximum Value
     * @param msg String Index Out Of Bounds Message
     * @return String User Imputed Text
     */
    public static double inputDecimal(double min,double max,String msg)  {
        double result;
        do{
            result = inputDecimal();
            if(result < min || result > max)
            {
                pressKeyToContinue(msg);
                continue;
            }
            break;
        }while (true);
        return result;
    }

    /**
     * Generate Random Number from 0 - 2147483647
     * @return int Random Number
     */
    public static int randomNumber()
    {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
    /**
     * Generate Random Number With Range
     * @param min int Minimum Value
     * @param max int Maximum Value
     * @return int Random Number
     */
    public static int randomNumber(int min,int max)
    {
        return (int)(Math.random() * (max - min ) + min);
    }

    /**
     * Get Current Date
     * @return String Current Date
     */
    public static String nowDateString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String nowDateTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Wait User Press Enter To Continue
     */
    public static void pressKeyToContinue()
    {
        Scanner in = new Scanner(System.in);
        try
        {
            in.nextLine();
        }
        catch(Exception e)
        {
            System.out.print(e.toString());
        }
    }
    /**
     * Wait User Press Enter To Continue With Message
     * @param message String Message Before  Block
     */
    public static void pressKeyToContinue(String message)
    {
        System.out.print(message);
        pressKeyToContinue();
    }

    /**
     * Wait Some Second
     * @param times int
     */
    public  static  void wait(int times)
    {
        for(int i = 0; i < times; i++)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }
            System.out.print(".");
        }
        System.out.println(".");
    }

    /**
     * Ask To User Confirm Process
     * @param msg message
     * @return Boolean
     */
    public static boolean confirm(String msg)
    {
        System.out.println(msg + " (yes/no)");
        String text;
        do {
            text = input().toLowerCase();
        }while(!text.equals("yes") && !text.equals("no"));
        return text.equals("yes");
    }

    /**
     * Create a BufferReader With Filename
     * @param fileName fileName
     * @return BufferedReader
     */
    public static BufferedReader FileBufferedReader(String fileName)
    {
        BufferedReader result = null;
        do {
            try
            {
                File file = new File(fileName);
                FileReader fileReader = new FileReader(file);
                result = new BufferedReader(fileReader);

            }catch (FileNotFoundException e)
            {
                break;
            }
        }while (false);
        return result;

    }
    /**
     * Create a BufferWritter With Filename
     * @param fileName filename
     * @return BufferWritter
     */
    public static BufferedWriter FileBufferedWriter(String fileName,boolean append )
    {
        BufferedWriter result = null;
        do {
            try
            {
                File file = new File(fileName);
                FileWriter fileWriter = new FileWriter(file,append);
                result = new BufferedWriter(fileWriter);
            }catch (Exception e)
            {
                break;
            }
        }while (false);
        return result;
    }

    /**
     * Try to Create  File With Name  Default No  Create Parent Directory
     * @param fileName filename
     * @return
     */
    public static boolean tryToCreateFile(String fileName)
    {
        return tryToCreateFile(fileName,false);
    }
    /**
     * Try to Create  File With Name
     * @param fileName
     * @param createParentDirectory  Create Parent Directory
     * @return
     */
    public static boolean tryToCreateFile(String fileName,boolean createParentDirectory) {
        boolean result = true;
        File file;
        try {
            file = new File(fileName);
            if(createParentDirectory)
            {
                result = file.getParentFile().mkdirs();
            }
            if(result && !file.exists())
            {
               result = file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Fatal Error: try to create file " + fileName);
            result = false;
        }
        return result;
    }

    public static  <T>  boolean existsInArray(T[] arrays, T target)
    {
        boolean exists = false;
        for(T item : arrays)
        {
            if(item.equals(target))
            {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public static  <T> boolean existsInArray(ArrayList<T> arrays, T target)
    {
        return existsInArray(arrays.toArray(),target);
    }



    public static class SimpleMenu
    {
        private String title;
        private Object[] options;
        private String lastOption;
        private String menuStr;
        private int optionCount;

        public  SimpleMenu(String title,Object[] options,String lastOption)
        {
            this.title = title;
            this.options = options;
            this.lastOption = lastOption;
            this.optionCount = options.length;
            this.buildMenu();
        }
        public  SimpleMenu(String title,Object[] options)
        {
            this(title,options,null);
        }
        public  <T> SimpleMenu(String title, ArrayList<T> options)
        {
            this(title,options.toArray(),null);
        }
        public  <T> SimpleMenu(String title, ArrayList<T> options, String lastOption)
        {
            this(title,options.toArray(),lastOption);
        }

        private void buildMenu()
        {
            int index =0;
            int maxLength = 0;
            int paddingSize;
            StringBuilder body = new StringBuilder();
            String padding;
            char[] character;
            for(Object option : this.options)
            {
                index++;
                String current = option.toString();
                int currentLength = current.length();
                if(currentLength > maxLength)
                {
                    maxLength = currentLength;
                }
                body.append(String.format("%2d. %s\n", index, current));
            }
            if(this.lastOption != null)
            {
                body.append(String.format("%2d. %s\n", 0, this.lastOption));
            }
            paddingSize =  (maxLength / 2 -  this.title.length() / 2);
            if(paddingSize < 3)
            {
                paddingSize = 3;
            }
            character = new char[paddingSize];
            Arrays.fill(character,'-');
            padding = new String(character);
            this.menuStr = String.format("|%s %s %s|\n%s ",padding,this.title,padding, body.toString());
        }
        public void displayMenu()
        {
            System.out.println(this.menuStr);
        }
        public int select()
        {
            return select("Option Does Not Exist");
        }
        public int select(String msg)
        {
            int min = this.lastOption == null ? 1:0;
            this.displayMenu();
            return  Utils.inputNumber(min,this.optionCount,msg);
        }
        public String getOptionString(int index)
        {
            return this.options[index].toString();
        }

    }

    public static class SimpleTable
    {

        private String[]   tableHead = null;
        private String[][] tableBody = null;
        private String tableStr = "";

        public SimpleTable(String[] head,Object body)
        {
            this.tableHead = head;
            this.tableBody = new String[1][head.length];
            for(int i = 0; i < tableBody.length; i++)
            {
                String current = body.toString();
                current = current.replaceAll(" {2,}", " ");
                tableBody[i] = current.split(" ");
            }
            this.buildTable();
        }

        public SimpleTable(String[] head,Object[] body)
        {
            this(head,SimpleTable.buildOptions(body));
        }
        public  <T>SimpleTable(String[] head, List<T> body)
        {
            this(head,SimpleTable.buildOptions(body));
        }

        public SimpleTable(String[] head,String[] body)
        {
            this.tableHead = head;
            this.tableBody = new String[body.length][head.length];
            for(int i = 0; i < tableBody.length; i++)
            {
                tableBody[i] = body[i].split(" ");
            }
            this.buildTable();
        }


        private void buildTable()
        {
            StringBuilder head = new StringBuilder();
            String[] body = new String[tableBody.length];
            Arrays.fill(body,"");
            for(int i = 0; i < tableHead.length; i++)
            {
                int headLength = tableHead[i].length();
                int bodyLength = 0;
                int maxLength=  headLength;
                for (String[] strings : tableBody) {
                    bodyLength = strings[i].length();
                    if (bodyLength > maxLength) {
                        maxLength = bodyLength;
                    }
                }
                head.append(String.format("%-" + maxLength + "s  ", tableHead[i]));
                for(int j = 0; j < tableBody.length; j++)
                {
                    body[j] += String.format("%-"+ maxLength +"s  ",tableBody[j][i]);
                }
            }
            StringBuilder tableStrBuilder = new StringBuilder();
            if(tableBody.length == 0)
            {
                tableStr = "Empty\n";
            }
            else
            {
                tableStrBuilder.append(String.format("%-5s", "No.")).append(head).append("\n");
                for(int o = 0 ; o < tableBody.length;o++)
                {
                    tableStrBuilder.append(String.format("%-5d", o+1));
                    tableStrBuilder.append(body[o]).append('\n');
                }
                tableStr = tableStrBuilder.toString();
            }

        }
        public void display()
        {
            System.out.println(this.tableStr);
        }
        public static <T> String[]  buildOptions(List<T> objects)
        {
            return buildOptions(objects.toArray());
        }
        public static String[] buildOptions(Object[] objects)
        {
            String[] result =  new String[objects.length];
            int index =0;
            for (Object obj : objects)
            {
                String current = obj.toString();
                result[index] = current;
                index++;
            }
            return result;
        }

        public String getTableStr() {
            return tableStr;
        }
    }
    public static class SimpleSelect
    {
        private  String   title = null;
        private  String[] options = null;
        private  Integer  length = null;
        private  String   strSelect = "";
        private  String   indexSelect = "";

        public SimpleSelect(String title, Object[] options)
        {
            this(title,SimpleTable.buildOptions(options));
        }
        public <T>SimpleSelect(String title, List<T> options)
        {
            this(title,SimpleTable.buildOptions(options));
        }

        public SimpleSelect(String title, String[] options)
        {
            this.title   = title;
            this.options = new String[options.length];
            for (int i = 0; i < options.length; i++)
            {
                this.options[i] = options[i].toUpperCase();
            }
            length = options.length;
            this.buildStrSelect();
            this.buildIndexSelect();
        }

        private void buildStrSelect() {

            StringBuilder strOptionsBuilder = new StringBuilder();
            strOptionsBuilder.append(" (");
            for(int index = 0 ; index < options.length ; index++)
            {
                strOptionsBuilder.append(options[index]);
                if(index != options.length -1)
                {
                    strOptionsBuilder.append(",");
                }
            }
            strOptionsBuilder.append(")\n");

            strSelect = title + strOptionsBuilder.toString();
        }

        private void buildIndexSelect() {
            int index = 0;
            StringBuilder indexSelectBuilder = new StringBuilder();
            indexSelectBuilder.append(title).append("\n");
            for(String option : options)
            {
                index++;
                indexSelectBuilder.append(String.format("%2d. %s \n", index, option));
            }
            indexSelect = indexSelectBuilder.toString();
        }
        public Integer indexSelect()
        {
            System.out.print(indexSelect);
            return Utils.inputNumber(1,length) - 1;
        }
        public String stringSelect()
        {

            String str = null;
            System.out.print(strSelect);
            do {
                str =  Utils.input().toUpperCase();
                if(Utils.existsInArray(options,str))
                {
                    break;
                }
                System.out.println("Option Not Exist " + strSelect);
            }while (true);
            return  str;
        }
    }
    public static class StorageManager
    {
        private String  fileName  =null;
        private Boolean append    =null;

        public StorageManager(String fileName, boolean append)
        {
            this.fileName =  fileName;
            this.append = append;
        }
        public StorageManager(String fileName)
        {
            this(fileName,false);
        }

        public String load()
        {
            StringBuilder result = null;
            String line = null;
            BufferedReader reader = null;
            try {
                reader = FileBufferedReader(this.fileName);
                result = new StringBuilder();
                while((line = reader.readLine()) != null)
                {
                    result.append(String.format("%s\n", line));
                }
                reader.close();
            }
            catch (IOException e)
            {
                result = null;
                System.err.println(e.getMessage());
            }
            return result.toString();
        }
        public boolean save(String content)
        {
            boolean result = true;
            BufferedWriter writter = null;
            try {
                writter = FileBufferedWriter(this.fileName,this.append);
                writter.write(content);
                writter.close();
            }
            catch (IOException e)
            {
                result =false;
                System.err.println(e.getMessage());
            }
            return  result;
        }

    }

    public static class Timer
    {
        long now = 0;
        public  Timer()
        {
            now = System.currentTimeMillis();
        }

        public  void reStart()
        {
            now = System.currentTimeMillis();
        }

        public long elapsed()
        {
            long result = System.currentTimeMillis() - now;
            now = System.currentTimeMillis();
            return  result;
        }
    }

}

