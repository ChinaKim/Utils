package com.kim.imageloader.Arithmetic;

/**
 * Created by kim on 16-11-10.
 */
public class Arithmetic {


    /**
     * 题目：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     1.程序分析： 兔子的规律为数列1,1,2,3,5,8,13,21....
     */
    public static int rabbit(int month){
        if (month==1 || month==2){
            return 1;
        }else {
            return rabbit(month - 1) + rabbit(month - 2);
        }
    }


    /**
     * 题目：判断101-200之间有多少个素数，并输出所有素数。(素数定义为在大于1的自然数中，除了1和它本身以外不再有其他因数的数称为素数)。
     * 程序分析:用该数n去除以2-n/2之间数,如果不能被整除,则该数为素数
     * @return
     */
    public static int flywater(){
        int count = 0;
        boolean flag ;
        for (int n= 101;n<=200;n++){
            flag = true;
            for (int j=2;j<=n/2;j++){
                if (n%j==0){//不是素数
                    flag = false;
                    break;
                }
            }
            if (flag){
                count++;
                System.out.print(n+"   ");
            }
        }
        return count;
    }
}
