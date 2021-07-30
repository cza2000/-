package Exp1;

import java.util.*;
class Number implements Comparable<Number>
{
    int value;
    double weight;
    public Number(int n, double m) 
    {
        this.value = n;
		this.weight = m;
	}
    public int compareTo(Number s) 
    {
	    return this.value - s.value;
	}
}
public class WeightedMid 
{
    public static void main(String [] args)
    {
        int n;              
        Scanner in = new Scanner(System.in);
        Scanner input = new Scanner(new File("Exp1_in.txt"));
        for (int count = 0; count < 10; count++)
        {
            n = in.nextInt();
            Number [] data = new Number[n];
            int [] value_list = new int[n];
            double [] weight_list = new double[n];
            for(int i = 0; i < n; i++)
                value_list[i] = in.nextInt();            
            for(int i = 0; i < n; i++)
                weight_list[i] = in.nextDouble();            
            for(int i = 0; i < n; i++)
                data[i] = new Number(value_list[i], weight_list[i]);            
            System.out.println(solve(data, 0, n-1, n/2));
        }
        
        in.close();
    }
    static int solve(Number[] data, int start, int end, int k)
    {
        Number mid = select(data, start, end, k);
        double sum_left = 0;
        double sum_right = 0;
        for (int i = 0; i < data.length; i++)
        {
            if (data[i].compareTo(mid) < 0)
                sum_left += data[i].weight;
            else if (data[i].compareTo(mid) > 0)
                sum_right += data[i].weight;
        }
        if (sum_left > 0.5)
            return solve(data, start, (start + end) / 2, k/2);
        else if (sum_right > 0.5)
            return solve(data, (start + end) / 2, end, k/2);
        else
            return mid.value;        
    }
    private static Number select(Number[] data, int start, int end, int k)
    {
        if (end - start < 5)
        {
            bubbleSort(data, start, end);
            return data[start + k -1];
        }
        for (int i = 0; i <= (end - start - 4) / 5; i++)
        {
            int newStart = start + 5 * i;
            int newEnd = newStart + 4;
            for (int j = 0; j < 3; j++)
                bubble(data, newStart, newEnd - j);
            swap(data, start + i, newStart + 2);
        }
        Number x = select(data, start, start + (end - start - 4) / 5, (end - start + 10) / 5);
        int i = partition(data, start, end, x);
        int j = i - start + 1;
        if (k <= j) 
            return select(data, start, i, k);
        else 
            return select(data, i + 1, end, k - j);
    }
    private static int partition(Number[] data, int start, int end, Number x)
    {
        int i = start - 1, j = end + 1;
        while (true)
        {
            while (data[++i].compareTo(x) < 0 && i < end);
            while (data[--j].compareTo(x) > 0);
            if (i >= j)
                break;
            swap(data, i, j);
        } 
        data[start] = data[j];
        data[j] = x;
        return j;
    }
    private static void swap(Number[] data, int i ,int j)
    {
        Number temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
    private static void bubbleSort(Number[] data, int start ,int end)
    {
        int length = end - start + 1;
        for (int i = 0; i < length - 1; i++)
            bubble(data, start, end - i);
    }
    private static void bubble(Number[] data, int start ,int end)
    {
        for (int j = start; j < end; j++)
            if (data[j].compareTo(data[j + 1]) > 0)
                swap(data, j, j + 1);
    }
}