


import java.util.Scanner;

class Main {

    public static String[] calc(String input) {
        String[] calc_input = input.split(" ");

        if (calc_input.length != 3) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(" Неверный формат ввода данных (только ДВА числа (2 + 2)).\n\n Введите выражение, разделяя каждый символ _пробелом_");
            input = scanner.nextLine();
            return calc(input);

        } else {
            return calc_input;
        }
    }

    private static boolean Arabic = true;


    public static void main(String[] args) {
        Scanner input_value = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String input = input_value.nextLine();
        while (!input.isEmpty()) {
            String[] calc_input;
            calc_input = calc(input);
            String operation = calc_input[1];

            Number values;

            int value1 = 0;
            int value2 = 0;



            try {
                value1 = Integer.parseInt(calc_input[0]);
                value2 = Integer.parseInt(calc_input[2]);


            } catch (NumberFormatException e) {
                Arabic = false;
                System.out.println("Введены римские цифры");
            }


            if (Arabic) {
                values = new Arabic(value1, value2);
            } else {
                values = new Romes(calc_input[0], calc_input[2]);
            }
            if (value1 < 0) {
                System.out.println("Ошибка, введено число < 1");
            } else if (10 < value1) {
                System.out.println(" ошибка, введено число > 10");
                //   System.exit(3);
            }

            if (value2 < 0) {
                System.out.println("Ошибка, введено число < 1");
            } else if (10 < value2) {
                System.out.println(" ошибка, введено число > 10 ");

            }

            switch (operation) {
                case "+" -> values.sum();
                case "-" -> values.sub();
                case "/" -> values.div();
                case "*" -> values.mul();
            }



            if (Arabic) {
                System.out.println(values.getResult());
            } else {
                System.out.println(values.getStringResult());
            }
            System.out.println();
            System.out.print("Введите следующее выражение: \n");
            input = input_value.nextLine();


        }
    }


    abstract static class Number {


        public abstract void sum();

        public abstract void sub();

        public abstract void div();

        public abstract void mul();

        public abstract int getResult();

        public abstract String getStringResult();
    }


    static class Romes extends Number {
        private final int romes_value1_int;
        private final int romes_value2_int;
        private int result_int;
        private String sign = "";
        private String result_string;

        private final String[] romes_letters_9 = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};


        Romes(String value1, String value2) {
            this.romes_value1_int = convert_to_int(value1);
            this.romes_value2_int = convert_to_int(value2);
        }

        private String convert_result_to_Romes(int n, int ostatok) {


            if (ostatok != 0) {
                try {
                    return convert_result_to_Romes(n - ostatok, 0) + romes_letters_9[ostatok - 1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    sign = "throws Exception //т.к. в римской системе нет отрицательных чисел";
                    // return convert_result_to_Romes(n - ostatok, 0) + romes_letters_9[(ostatok + 1) * -1];
                }
            }

            return sign;
        }


        @Override
        public void sum() {

            result_int = romes_value1_int + romes_value2_int;

            result_string = convert_result_to_Romes(result_int, result_int);

        }

        @Override
        public void sub() {
            result_int = romes_value1_int - romes_value2_int;

            result_string = convert_result_to_Romes(result_int, result_int);
        }

        @Override
        public void div() {
            result_int = romes_value1_int / romes_value2_int;

            result_string = convert_result_to_Romes(result_int, result_int);

        }

        @Override
        public void mul() {
            result_int = romes_value1_int * romes_value2_int;

            result_string = convert_result_to_Romes(result_int, result_int);
        }

        @Override
        public int getResult() {
            return result_int;
        }

        public String getStringResult() {
            return result_string;
        }

       private int convert_to_int(String romes_value) {

            char[] value_char = romes_value.toCharArray();
            int[] values_int = new int[value_char.length];
            for (int i = 0; i < value_char.length; i++) {
                switch (value_char[i]) {
                    case 'I' -> values_int[i] = 1;
                    case 'V' -> values_int[i] = 5;
                    case 'X' -> values_int[i] = 10;
                    default -> System.out.println("Вероятно введены арабские и римские одновременно.");

                }


            }


            int result = values_int[0];
            for (int i = 0; i < values_int.length && values_int.length > i + 1; i++) {

                if (values_int[i] >= values_int[i + 1]) {
                    result += values_int[i + 1];

                } else if (values_int[i] < values_int[i + 1]) {
                    result = result + values_int[i + 1] - 2;
                    System.out.println("АГА");
                    break;
                }
            }

            return result;
        }

    }

    static class Arabic extends Number {

        private final int value1;
        private final int value2;
        private int result;


        Arabic(int value1, int value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        public void sum() {
            this.result = value1 + value2;
        }

        public void sub() {
            this.result = value1 - value2;
        }

        public void div() {
            try {
                this.result = value1 / value2;
            } catch (ArithmeticException e) {
                System.out.print("Нельзя делить на ");
            }

        }

        public void mul() {
            this.result = value1 * value2;
        }

        @Override
        public int getResult() {
            return result;
        }

        @Override
        public String getStringResult() {
            return String.valueOf(result);
        }


    }


}