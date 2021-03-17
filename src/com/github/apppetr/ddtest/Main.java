package com.github.apppetr.ddtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*Условие:
        Написать на Java программу распаковывания строки.
        На вход поступает строка вида число[строка], на выход - строка, содержащая повторяющиеся подстроки.

        Пример:
        Вход: 3[xyz]4[xy]z
        Выход: xyzxyzxyzxyxyxyxyz

        Ограничения:
        - одно повторение может содержать другое. Например: 2[3[x]y]  = xxxyxxxy
        - допустимые символы на вход: латинские буквы, числа и скобки []
        - числа означают только число повторений
        - скобки только для обозначения повторяющихся подстрок
        - входная строка всегда валидна.

        Дополнительное задание:
        Проверить входную строку на валидность.*/

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Digital Design Test Project");
        System.out.println("Введите строку вида: 3[xyz]4[xy]z2[3[x]y]");

        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String inputData = reader.readLine();
            String result =  isDataValid(inputData) ? extractString(inputData) : "Введите корректную строку.";
            System.out.println(result);
        }
    }

    public static String extractString(String s) {
        StringBuilder result = new StringBuilder();
        int open = 0;
        int close = 0;
        int startIndex = 0;
        boolean num = false;
            for (int i = 0; i < s.length(); i++) {
                if (isNumeric(String.valueOf(s.charAt(i))) && open == close) {
                    if (!num) startIndex = i;
                    num = true;
                } else {
                    num = false;
                    if (s.charAt(i) == '[') {
                        open++;
                        continue;
                    }
                    if (s.charAt(i) == ']') {
                        close++;
                        if (open == close) {
                            int n = Integer.parseInt(s.substring(startIndex, s.indexOf('[', startIndex)));
                            for (int j = 0; j < n; j++) {
                                result.append(extractString(s.substring(s.indexOf('[', startIndex) + 1, i)));
                            }
                        }
                        continue;
                    }
                    if (close == open) {
                        result.append(s.charAt(i));
                        startIndex = i + 1;
                    }
                }
            }
            return result.toString();
    }

    public static Boolean isNumeric(String s) {
        return s.matches("^[0-9]");
    }

    /*Проверяем равенство октрывающих и закрывающих скобок, а также наличие цифры перед открывающей скобкой*/
    public static Boolean isDataValid(String s) {
        int countRightScope = 0;
        int countLeftScope = 0;
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '[') {
                countLeftScope++;
                if (i > 0) {
                    if (isNumeric(String.valueOf(s.charAt(i - 1)))) {
                        continue;
                    } else return false;
                } else return false;
            }
            if (s.charAt(i) == ']') countRightScope++;
        }
        return s.length() > 1 && countLeftScope == countRightScope;
    }
}
