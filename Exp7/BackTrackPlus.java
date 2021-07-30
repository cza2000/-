

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class BackTrackPlus {
    public static Set<Integer> BestA;// 最优解A
    public static Set<Integer> BestB;// 最优解B
    public static Set<Integer> BestC;// 最优解C
    public static Set<Integer> NonZero;// 含有1的行序号
    public static int[][] matrix;// 数据矩阵
    public static int TotalNum;// ABC总元素个数
    public static int SubNum;// AB元素个数之差+BC个数之差
    public static int NumA;// A的元素个数
    public static int SumA;// A的元素之和
    public static int SumB;// B的元素之和
    public static int SumC;// C的元素之和
    public static int[][] NonMutex;// 互斥判定矩阵

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("exp7_in.txt"));
        int n = input.nextInt();
        for (int count = 0; count < n; count++) 
        {
            matrix = new int[1000][20];
            NonZero = new TreeSet<>();
            NonMutex = new int[20][20];//赋初值          

            for (int i = 0; i < 1000; i++)
            {
                for (int j = 0; j < 20; j++)
                {
                    matrix[i][j] = input.nextInt();
                    if(matrix[i][j] == 1)
                    NonZero.add(i); 
                }
            }            
            for (int i = 0; i < 20; i++)
            {
                for (int j = i; j < 20; j++)
                {
                    for (int k : NonZero) 
                    {
                        if (matrix[k][i] == 1 && matrix[k][j] == 1)
                        {
                            NonMutex[i][j] = 1;
                            NonMutex[j][i] = 1;
                        }
                    }
                }
            }
            boolean judge = false;
            for (int i = 0; i < 20; i++)
            {
                for (int j = i; j < 20; j++)
                {
                    if (matrix[i][j] == 0)
                    {
                        judge = true;
                        break;
                    }
                }
                if (judge)
                    break;
            }

            if (judge)
            {
                Set<Integer> A = new TreeSet<>();
                Set<Integer> B = new TreeSet<>();
                Set<Integer> C = new TreeSet<>();
                BestA = new TreeSet<>();
                BestB = new TreeSet<>();
                BestC = new TreeSet<>();
                TotalNum = 0;
                SubNum = 50;
                NumA = 0;
                SumA = 1000;
                SumB = 1000;
                SumC = 1000;
                dfs(0, A, B, C);
                for (Integer i : BestA)
                    System.out.print(Integer.toString(i) + " ");
                System.out.println("");
                for (Integer i : BestB) 
                    System.out.print(Integer.toString(i) + " ");     
                System.out.println(""); 
                for (Integer i : BestC) 
                    System.out.print(Integer.toString(i) + " ");     
                System.out.println(""); 
            }
            else
            {
                System.out.println("");
                System.out.println("");
                System.out.println("");
            }                   
        }
    }
    static void dfs(int i, Set<Integer> A, Set<Integer> B, Set<Integer> C)
    {
        if (!check(A, B) || !check(B, C) || !check(A, C))
            return;        
        if (!A.isEmpty() && !B.isEmpty() && !C.isEmpty() && A.size() + B.size() + C.size() >= TotalNum)
        {//AB均不为空 且 AB总元素个数和不小于TotalNum 且 A的元素个数不小于B的元素个数
            int sumA = 0, sumB = 0, sumC = 0;
            for (Integer integer : A) 
                sumA += integer;
            for (Integer integer : B) 
                sumB += integer;
            for (Integer integer : C) 
                sumC += integer;
            if (A.size() + B.size() + C.size() > TotalNum)
                update(A, B, C, sumA, sumB, sumC);
            else if (A.size() + B.size() + C.size() == TotalNum)
                if (Math.abs(A.size() - B.size()) + Math.abs(B.size() - C.size()) < SubNum)
                    update(A, B, C, sumA, sumB, sumC);
                else if (Math.abs(A.size() - B.size()) + Math.abs(B.size() - C.size()) == SubNum)
                    if (sumA < SumA)
                        update(A, B, C, sumA, sumB, sumC);
                    else if (sumA == SumA)
                        if (sumB < SumB)
                            update(A, B, C, sumA, sumB, sumC);
                            else if (sumB == SumB)
                                if (sumC < SumC)
                                    update(A, B, C, sumA, sumB, sumC);
        }
        if (i < 20 && A.size() + B.size() + C.size() > TotalNum + i - 21)
        {//A的元素个数有可能大于B 且 AB的元素个数和有可能大于当前最优值
            A.add(i);
            dfs(i + 1, A, B, C);
            A.remove(i);
            B.add(i); 
            dfs(i + 1, A, B, C);
            B.remove(i);
            C.add(i); 
            dfs(i + 1, A, B, C);
            C.remove(i);
            dfs(i + 1, A, B, C);//遍历3个子树
        }
    }
    static void update(Set<Integer> A, Set<Integer> B, Set<Integer> C, int sumA, int sumB, int sumC)
    {//更新
        BestA.clear();
        BestB.clear();
        BestC.clear();
        for (Integer integer : A) 
            BestA.add(integer);
        for (Integer integer : B) 
            BestB.add(integer);
        for (Integer integer : C) 
            BestC.add(integer);
        TotalNum = A.size() + B.size() + C.size();
        SubNum = Math.abs(A.size() - B.size()) + Math.abs(B.size() - C.size());
        SumA = sumA;
        SumB = sumB;
        SumC = sumC;
    }
    static boolean check(Set<Integer> A, Set<Integer> B)
    {
        if (A.isEmpty() || B.isEmpty())
            return true;
        for (Integer i : A) 
            for (Integer j : B) 
                if (NonMutex[i][j] == 1)
                    return false;
        return true;
    }
}
