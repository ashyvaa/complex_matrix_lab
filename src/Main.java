import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Операции над матрицами комплексных чисел");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        doAdd();
                        break;
                    case "2":
                        doMultiply();
                        break;
                    case "3":
                        doDivide();
                        break;
                    case "4":
                        doTranspose();
                        break;
                    case "5":
                        doDeterminant();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Такого пункта меню нет, попробуйте ещё раз");
                }
            } catch (MatrixException ex) {
                System.out.println("Ошибка: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                System.out.println("Введено не число: " + ex.getMessage());
            }
        }

        System.out.println("Завершение работы");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1 - сложить две матрицы");
        System.out.println("2 - перемножить две матрицы");
        System.out.println("3 - разделить две матрицы");
        System.out.println("4 - транспонировать матрицу");
        System.out.println("5 - вычислить определитель");
        System.out.println("0 - выход");
        System.out.print("Выберите пункт: ");
    }

    private static ComplexMatrix readMatrix(String name) {


        System.out.print("Число строк: ");
        int rows = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Число столбцов: ");
        int cols = Integer.parseInt(scanner.nextLine().trim());

        ComplexMatrix matrix = new ComplexMatrix(rows, cols);

        System.out.println("Вводите элементы в формате: действительная часть мнимая часть");
        System.out.println("Например: 3 -2  означает число 3-2i");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(name + "[" + i + "][" + j + "] = ");
                String[] parts = scanner.nextLine().trim().split("\\s+");
                double re = Double.parseDouble(parts[0]);
                double im = Double.parseDouble(parts[1]);
                matrix.set(i, j, new ComplexNumber(re, im));
            }
        }
        return matrix;
    }

    private static void doAdd() throws MatrixException {
        ComplexMatrix a = readMatrix("A");
        ComplexMatrix b = readMatrix("B");
        ComplexMatrix result = a.add(b);
        System.out.println("Результат A + B:");
        System.out.print(result);
    }

    private static void doMultiply() throws MatrixException {
        ComplexMatrix a = readMatrix("A");
        ComplexMatrix b = readMatrix("B");
        ComplexMatrix result = a.multiply(b);
        System.out.println("Результат A * B:");
        System.out.print(result);
    }

    private static void doDivide() throws MatrixException {
        ComplexMatrix a = readMatrix("A");
        ComplexMatrix b = readMatrix("B");
        ComplexMatrix result = a.divide(b);
        System.out.println("Результат A / B:");
        System.out.print(result);
    }

    private static void doTranspose() {
        ComplexMatrix a = readMatrix("A");
        ComplexMatrix result = a.transpose();
        System.out.println("Транспонированная матрица:");
        System.out.print(result);
    }

    private static void doDeterminant() throws MatrixException {
        ComplexMatrix a = readMatrix("A");
        ComplexNumber det = a.determinant();
        System.out.println("Определитель = " + det);
    }
}