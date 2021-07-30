import java.io.*;

import java.util.*;

public class BackTrack {
    public static Set<Integer> BestA;//最优解A
    public static Set<Integer> BestB;//最优解B
    public static Set<Integer> NonZero;//含有1的行序号
    public static int [][] matrix;//数据矩阵
    public static int TotalNum;//AB总元素个数
    public static int SubNum;//AB元素个数之差
    public static int NumA;//A的元素个数
    public static int SumA;//A的元素之和
    public static int [][] NonMutex;//互斥判定矩阵
    public static void main(String [] args) throws IOException {
        Scanner input = new Scanner(new File("exp6_in.txt"));
        
        int n = input.nextInt();
        for (int count = 0; count < n; count++) {
            matrix = new int[1000][20];
            NonZero = new TreeSet<>();
            NonMutex = new int[20][20];//赋初值          

            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 20; j++) {
                    matrix[i][j] = input.nextInt();
                    if(matrix[i][j] == 1)
                    NonZero.add(i); 
                }
            }            
            for (int i = 0; i < 20; i++) {
                for (int j = i; j < 20; j++) {
                    for (int k : NonZero) {
                        if (matrix[k][i] == 1 && matrix[k][j] == 1) {
                            NonMutex[i][j] = 1;
                            NonMutex[j][i] = 1;
                        }
                    }
                }
            }
            boolean judge = false;
            for (int i = 0; i < 20; i++) {
                for (int j = i; j < 20; j++) {
                    if (matrix[i][j] == 0) {
                        judge = true;
                        break;
                    }
                }
                if (judge)
                    break;
            }

            if (judge) {
                Set<Integer> A = new TreeSet<>();
                Set<Integer> B = new TreeSet<>();
            
                BestA = new TreeSet<>();
                BestB = new TreeSet<>();
                TotalNum = 0;
                SubNum = 50;
                NumA = 0;
                SumA = 1000;
                dfs(0, A, B);
                for (Integer i : BestA)
                    System.out.print(Integer.toString(i) + " ");
                System.out.println("");
                for (Integer i : BestB) 
                    System.out.print(Integer.toString(i) + " ");     
                System.out.println(""); 
            }
            else {
                System.out.println("");
                System.out.println("");
            }                   
        }
    }
    
    static void dfs(int i, Set<Integer> A, Set<Integer> B) {
        if (!check(A, B))
            return;        
        if (!A.isEmpty() && !B.isEmpty() && A.size() + B.size() >= TotalNum && A.size() >= B.size()) {//AB均不为空 且 AB总元素个数和不小于TotalNum 且 A的元素个数不小于B的元素个数
            int sum = 0;
            for (Integer item : A)
                sum += item;
            if (A.size() + B.size() > TotalNum)
                update(A, B, sum);
            else if (A.size() + B.size() == TotalNum)
                if (A.size() - B.size() < SubNum)
                    update(A, B, sum);
                else if (A.size() - B.size() == SubNum)
                    if (A.size() > NumA)
                        update(A, B, sum);
                    else if (A.size() == NumA)
                        if (sum < SumA)
                            update(A, B, sum);
        }

        if (i < 20 && B.size() - A.size() < 21 - i && A.size() + B.size() > TotalNum + i - 21) {//A的元素个数有可能大于B 且 AB的元素个数和有可能大于当前最优值
            A.add(i);
            dfs(i + 1, A, B);
            A.remove(i);
            B.add(i); 
            dfs(i + 1, A, B);
            B.remove(i);
            dfs(i + 1, A, B);//遍历3个子树
        }
    }

    static void update(Set<Integer> A, Set<Integer> B, int sum) {//更新
        BestA.clear();
        BestB.clear();
        for (Integer integer : A) 
            BestA.add(integer);
        for (Integer integer : B) 
            BestB.add(integer);
        TotalNum = A.size() + B.size();
        SubNum = A.size() - B.size();
        NumA = A.size();
        SumA = sum;
    }

    static boolean check(Set<Integer> A, Set<Integer> B) {
        if (A.isEmpty() || B.isEmpty())
            return true;
        for (Integer i : A) 
            for (Integer j : B) 
                if (NonMutex[i][j] == 1)
                    return false;
        return true;
    }
} 