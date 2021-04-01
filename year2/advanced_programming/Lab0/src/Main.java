import java.util.Scanner;

enum Forma{
    PATRAT,
    CERC
}

interface Shape{
    void display();
}

class Patrat implements Shape{

    @Override
    public void display(){
        System.out.println("patrat\n");
    }
}

class Cerc implements Shape{

    @Override
    public void display(){
        System.out.println("cerc\n");
    }
}

class ShapeFactory{
    public Shape getShape(Forma e){
        if(e==Forma.PATRAT)
            return new Patrat();
        if(e==Forma.CERC)
            return new Cerc();
        return null;
    }
}

public class Main {

    public static void main(String[] args) {

        ShapeFactory fac=new ShapeFactory();
        Shape p=fac.getShape(Forma.PATRAT);
        p.display();

        /*System.out.println("hello world");

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        System.out.println(s);

        int i = in.nextInt();
        System.out.println(i);

        float f = in.nextFloat();
        System.out.println(f);

        int[] a=new int[]{1,2,3,4,5,6,7,8,9,10};
        int max=a[0];
        int min=a[0];
        for(i=1;i<10;i++){
            if(a[i]>max)
                max=a[i];
            if(a[i]<min)
                min=a[i];
        }

        System.out.println("min: "+ min+" max: "+max);*/

    }
}
