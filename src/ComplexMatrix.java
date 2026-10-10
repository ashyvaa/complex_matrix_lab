public class ComplexMatrix {

    private ComplexNumber[][] data;
    private int rows;
    private int cols;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new ComplexNumber(0, 0);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ComplexNumber get(int i, int j) {
        return data[i][j];
    }

    public void set(int i, int j, ComplexNumber value) {
        data[i][j] = value;
    }

    public ComplexMatrix add(ComplexMatrix other) throws MatrixException {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new MatrixException(
                    "Нельзя сложить матрицы разных размеров: "
                            + this.rows + "x" + this.cols + " и " + other.rows + "x" + other.cols
            );
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) throws MatrixException {
        if (this.cols != other.rows) {
            throw new MatrixException(
                    "Нельзя перемножить матрицы: число столбцов первой (" + this.cols
                            + ") не равно числу строк второй (" + other.rows + ")"
            );
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].multiply(other.data[k][j]));
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) throws MatrixException {
        if (other.rows != other.cols) {
            throw new MatrixException("Делить можно только на квадратную матрицу");
        }
        if (this.cols != other.rows) {
            throw new MatrixException("Размеры матриц не подходят для деления");
        }
        ComplexMatrix inverse = other.inverse();
        return this.multiply(inverse);
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    public ComplexNumber determinant() throws MatrixException {
        if (rows != cols) {
            throw new MatrixException("Определитель можно вычислить только для квадратной матрицы");
        }
        return determinantRecursive(data);
    }

    private ComplexNumber determinantRecursive(ComplexNumber[][] m) {
        int n = m.length;

        if (n == 1) {
            return m[0][0];
        }
        if (n == 2) {
            return m[0][0].multiply(m[1][1]).subtract(m[0][1].multiply(m[1][0]));
        }

        ComplexNumber result = new ComplexNumber(0, 0);
        for (int col = 0; col < n; col++) {
            ComplexNumber[][] minor = getMinor(m, 0, col);
            ComplexNumber term = m[0][col].multiply(determinantRecursive(minor));
            if (col % 2 == 0) {
                result = result.add(term);
            } else {
                result = result.subtract(term);
            }
        }
        return result;
    }

    private ComplexNumber[][] getMinor(ComplexNumber[][] m, int skipRow, int skipCol) {
        int n = m.length;
        ComplexNumber[][] minor = new ComplexNumber[n - 1][n - 1];
        int mi = 0;
        for (int i = 0; i < n; i++) {
            if (i == skipRow) {
                continue;
            }
            int mj = 0;
            for (int j = 0; j < n; j++) {
                if (j == skipCol) {
                    continue;
                }
                minor[mi][mj] = m[i][j];
                mj++;
            }
            mi++;
        }
        return minor;
    }

    public ComplexMatrix inverse() throws MatrixException {
        if (rows != cols) {
            throw new MatrixException("Обратную матрицу можно найти только для квадратной матрицы");
        }
        ComplexNumber det = determinant();
        if (det.isZero()) {
            throw new MatrixException("Матрица вырождена (определитель равен нулю), обратной не существует");
        }

        int n = rows;
        ComplexNumber[][] cofactors = new ComplexNumber[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ComplexNumber[][] minor = getMinor(data, i, j);
                ComplexNumber minorDet = (n == 1) ? new ComplexNumber(1, 0) : determinantRecursive(minor);
                int sign = ((i + j) % 2 == 0) ? 1 : -1;
                cofactors[i][j] = minorDet.multiply(new ComplexNumber(sign, 0));
            }
        }

        ComplexMatrix result = new ComplexMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ComplexNumber adjElement = cofactors[j][i];
                result.data[i][j] = adjElement.divide(det);
            }
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j].toString());
                if (j < cols - 1) {
                    sb.append("\t");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
