public class ComplexNumber {

    private double re;
    private double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.re + other.re, this.im + other.im);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.re - other.re, this.im - other.im);
    }

    //(a + bi)(c + di) = (ac - bd) + (ad + bc)i
    public ComplexNumber multiply(ComplexNumber other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new ComplexNumber(newRe, newIm);
    }

    //(a + bi)/(c + di) = (ac + bd)/(c^2 + d^2) + (bc - ad)/(c^2 + d^2) * i
    public ComplexNumber divide(ComplexNumber other) {
        double denominator = other.re * other.re + other.im * other.im;
        if (denominator == 0) {
            throw new ArithmeticException("Деление на нулевое комплексное число");
        }
        double newRe = (this.re * other.re + this.im * other.im) / denominator;
        double newIm = (this.im * other.re - this.re * other.im) / denominator;
        return new ComplexNumber(newRe, newIm);
    }

    public boolean isZero() {
        return re == 0 && im == 0;
    }

    public String toString() {
        if (im == 0) {
            return String.format("%.2f", re);
        }
        if (im > 0) {
            return String.format("%.2f+%.2fi", re, im);
        }
        return String.format("%.2f-%.2fi", re, Math.abs(im));
    }
}