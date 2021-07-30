package Exp3_1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class MultiDispatch {
    public static void main(String[] args) throws IOException
    {
        Scanner input = new Scanner(new File("exp3(1)_in.txt"));
        File file =new File("exp3(1)_myout.txt");
        if (!file.exists())
            file.createNewFile();
        FileWriter fileWritter = new FileWriter(file.getName(),true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

        int n, m;//n：作业总数 m：机器总数        
        for (int count = 0; count < 50; count++)
        {
            n = input.nextInt();
            m = input.nextInt();
            int [] t = new int[n];//作业加工时间
            for (int i = 0; i < n; i++)
                t[i] = input.nextInt();
            if (n <= m)
            {
                System.out.println("0"); 
                bufferWritter.write(String.valueOf(0) + "\n");
                continue;
            }
            Arrays.sort(t);
            int TotalWaitingTime = 0;
            Queue<Integer> q = new PriorityQueue<>();//优先队列中的元素为已安排将在该机器上执行的作业加工时间之和
            for (int i = 0; i < m; i++)
                q.add(0);
            for (int i = 0; i < n; i++)
            {
                int temp = q.poll();
                TotalWaitingTime += temp;
                temp += t[i];
                q.add(temp);
            }
            System.out.println(TotalWaitingTime);      
            bufferWritter.write(String.valueOf(TotalWaitingTime) + "\n");
        }
        bufferWritter.close();
    }
}