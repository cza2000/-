package Exp2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Cut {
    public static void main(String[] args) throws IOException
    {
        Scanner input = new Scanner(new File("exp2_in.txt"));
        
        int length, n;
        for (int count = 0; count < 10; count++)
        {
            length = input.nextInt();

            n = input.nextInt();
            int [] cutPoint = new int[n + 2];
            for (int i = 0; i < n; i++)
                cutPoint[i] = input.nextInt();
            cutPoint[n] = 0;
            cutPoint[n + 1] = length;
            Arrays.sort(cutPoint);
            int [][] dp = new int [n + 2][n + 2];
            System.out.println(solve(cutPoint, dp, 0, n + 1));
            
        }     
    }
    private static int solve(int [] p, int [][] dp, int left, int right)
    {
        int costLeast = p[right] - p[left];
        if (right - left == 1)
            return 0;
        int minCost = 9999999;
        for (int i = left + 1; i < right; i++)
        {
            if (dp[left][i] == 0)
                dp[left][i] = solve(p, dp, left, i);
            if (dp[i][right] == 0)
                dp[i][right] = solve(p, dp, i, right);
            minCost = Math.min(minCost, costLeast + dp[left][i] + dp[i][right]);
        }
        return minCost;
    }
}