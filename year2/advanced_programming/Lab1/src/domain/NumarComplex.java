package domain;

public class NumarComplex {

    private float re, im;

    public NumarComplex(float re, float im) {
        this.re = re;
        this.im = im;
    }

    public float getRe() {
        return re;
    }

    public void setRe(float re) {
        this.re = re;
    }

    public float getIm() {
        return im;
    }

    public void setIm(float im) {
        this.im = im;
    }

    public NumarComplex adunare(NumarComplex nr) {
        return new NumarComplex(this.re + nr.getRe(), this.im + nr.getIm());
    }

    public NumarComplex scadere(NumarComplex nr) {
        return new NumarComplex(this.re - nr.getRe(), this.im - nr.getIm());
    }

    public NumarComplex inmultire(NumarComplex nr) {
        return new NumarComplex(this.re * nr.getRe() - this.im * nr.getIm(), this.re * nr.getIm() + this.im * nr.getRe());
    }

    public NumarComplex conjugatul(NumarComplex nr) {
        return new NumarComplex(nr.getRe(), -nr.getIm());
    }

    public NumarComplex impartire(NumarComplex nr) {
        return new NumarComplex((this.re * nr.getRe() + this.im * nr.getIm()) / (nr.getRe() * nr.getRe() + nr.getIm() * nr.getIm()), (this.im * nr.getRe() - this.re * nr.getIm()) / (nr.getRe() * nr.getRe() + nr.getIm() * nr.getIm()));
    }

    @Override
    public String toString() {
        if (im > 0)
            return "" + re + '+' + im + 'i';
        if (im < 0)
            return "" + re + im + 'i';
        else
            return "" + re;
    }
}
