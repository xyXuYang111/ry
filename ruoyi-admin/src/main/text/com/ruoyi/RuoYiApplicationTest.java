package com.ruoyi;

public class RuoYiApplicationTest {

    public static void main(String[] args) {
        System.out.println("加法：");
        for(int i = 0; i<10; i++){
            for(int y = 0; y<10; y++){
                int sum = y + i;
                System.out.print(i + "+" + y + "=" + sum);
                System.out.print("  ");
            }
            System.out.println(" ");
        }

        System.out.println("减法：");
        for(int i = 0; i<10; i++){
            for(int y = 0; y<10; y++){
                if(y > i){
                    int sum = y - i;
                    System.out.print(y + "-" + i + "=" + sum);
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }
    }
}
