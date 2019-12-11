/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crosszero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CrossZero {

    char[][] field;
    //int x;
    //int y;

    public static void main(String[] args) {
        CrossZero cz = new CrossZero();

        //cz.print();
        cz.run();

    }
//заполнение поля

    void init(char c) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = c;
            }
        }
    }
//распечатка поля

    void print() {

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    //проверка на выигрыш
 //проверка горизонтальных строк
boolean comparison(char c) { //запускать дважды - отдельно не выиграл ли 'X' и отдельно, не выиграл ли 'O'
        //horizontal               
        for (int i = 0; i < field.length; i++) {
            boolean res = false;
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != c) {
                    res = true;
                    break;
                }
            }
            if (res == false) { // нашлась строчка, в которой полностью стоят только c
                System.out.println("horiz");
                return false; // игрок c выиграл и мы сигнализируем именно об этом                    
            }
        }

        //vertical        
        for (int j = 0; j < field[0].length; j++) {
            boolean res = false;
            for (int i = 0; i < field.length; i++) {
                if (field[i][j] != c) {
                    res = true;
                    break;
                }
            }
            if (res == false) // нашелся столбик, в котором полностью стоят только c
            {
                System.out.println("vert");
                return false; // игрок c выиграл и мы сигнализируем именно об этом                    
            }
        }

        //diagonal
        boolean res = false;
        for (int i = 0; i < field.length; i++) {
            if (field[i][i] != c) {
                res = true;
                break;
            }
        }
        if (res == false) // на диагонали полностью стоят только с
        {
            System.out.println("diag1");
            return false; // игрок с выиграл и мы сигнализируем именно об этом                    
        }
        //diagonal
        res = false;
        int N = field.length;

        for (int i = 0; i < field.length; i++) {
            if (field[N - i - 1][i] != c) { //считаем поле квадратным, иначе понятие диагонали размывается
                res = true;
                break;
            }
        }
        if (res == false) { // на диагонали полностью стоят только с
            System.out.println("diag2");
            return false; // игрок с выиграл и мы сигнализируем именно об этом                    
        }

        return true; //игрок c не выиграл.
    }
//игра

    void run() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Ход 1 игрока");
            while (true) {

                int i1 = sc.nextInt();
                int j1 = sc.nextInt();

                if (i1 >= field.length && j1 >= field[0].length) {
                    expand(i1, j1);//расширение массива

                }
                if (field[i1][j1] == '_')//проверка на занятость
                {
                    field[i1][j1] = 'X';
                    break;
                }
                System.out.println("Поле занято");

            }
            print();

            if (comparison('X')== false) {
                System.out.println("Первый игрок победил");
                break;
            }

            System.out.println("Ход 2 игрока");
            while (true) {

                int i2 = (int) (Math.random() * field.length);
                int j2 = (int) (Math.random() * field.length);
                if (i2 > field.length && j2 > field[0].length) {
                    expand(i2, j2);//расширение массива

                }
                if (field[i2][j2] == '_') {
                    field[i2][j2] = 'O';
                    break;
                }
                System.out.println("Поле занято");

            }
            print();
            if (comparison('O')== false) {
                System.out.println("Второй игрок победил");
                break;
            }
        }

    }
//расширение игрового поля, если введенные данные больше нынешних размеров поля
    void expand(int n, int k) {

        if (n < field.length && k < field[0].length)//если введенные данные меньше нынешних размеров, выйти из функции
        {
            return;
        }
        char[][] field2 = new char[n + 1][k + 1];
        for (int i = 0; i < field2.length; i++) {
            for (int j = 0; j < field2[i].length; j++) {
                field2[i][j] = '_';
                System.out.print(field2[i][j]);
            }
            System.out.println();
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field2[i][j] = field[i][j];
            }

        }
        field = field2;
    }
//загрузка из файла
    void load_from_file(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner sc = new Scanner(f);
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();
        field = new char[num1][num2];

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                String str = sc.next();
                field[i][j] = str.charAt(0);
            }
        }
    }
//сохранение в файл
    void save_to_file(String filename) throws FileNotFoundException {
        File f = new File(filename);
        PrintWriter pw = new PrintWriter(f);
        pw.println(field.length);
        pw.println(field[0].length);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                pw.print(field[i][j]);
                pw.print(" ");
            }
            pw.println();
        }
        pw.flush();
    }

    public CrossZero() {
        field = new char[3][3];
        init('_');

    }

}
