package com.immomo.test.sort;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test
 * @date 2019-10-24 21:00
 */
public class Main {


    /**
     * 插入排序
     * 直接插入
     * 希尔排序
     * <p>
     * 选择
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("=========");

    }


    /**
     * 1.1 选择排序的
     * 原理:很简单，就是从需要排序的数据中选择最小的（从小到大排序），然后放在第一个，选择第二小的放在第二个……
     *
     * @param a 进行排序的数组
     */
    public static int[] selectionSort(int a[]) {
        int min;
        for (int i = 0; i < a.length; i++) {
            min = i;
            // 这个for循环是为了找出最小的值
            for (int j = i + 1; j < a.length; j++) {
                if (a[min] > a[j]) {
                    min = j;
                }
            }
            /** 如果第一个取出的元素不是最小值，就进行交换
             * 意思就是：如果取出的元素就是最小值，那么就没有必要进行交换了 		   */
            if (min != i) {
                // 进行交换
                exc(a, i, min);
            }
        }
        return a;
    }


    /**
     * 1.2 插入排序  如果数组进行循环得到a，若a比a前面的一个数小，则a就与前面数交换位置（相当于a向前面移动一位），若移动后a任然比前面一个数小，则再向前移动一位……
     *
     * @param a 进行排序的数组
     * @return 返回排序好的数组
     */
    public static int[] insertSort(int a[]) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            // 如果a[i]比前面的数字小，则a[i]向前挪
            for (int j = i; j > 0 && (a[j - 1] > a[j]); j--) {
                exc(a, j, j - 1);
            }
        }
        return a;
    }

    /**
     * 1.3 冒泡排序
     *
     * @param a
     * @return
     */
    public static int[] bubbleSort(int[] a) {
        int N = a.length;
        for (int i = 0; i < N - 1; i++) {
            // 小的数字向上冒泡
            for (int j = N - 1; j > i; j--) {
                // 交换位置
                if (a[j - 1] > a[j]) {
                    exc(a, j - 1, j);
                }
            }
        }
        return a;
    }

    /**
     * 1.3.2 冒泡排序的优化
     *
     * @param a
     * @return
     */
    public static int[] bubbleSort2(int[] a) {
        int N = a.length;
        boolean flag = true;
        for (int i = 0; i < N - 1 && flag; i++) {
            int j = N - 1;
            for (flag = false; j > i; j--) {
                if (a[j - 1] > a[j]) {
                    flag = true;
                    exc(a, j - 1, j);
                }
            }
        }
        return a;
    }


    //------------------------------------------------

    /**
     * 2.1 梳排序
     *
     * @param a
     * @return
     */
    public static int[] combSort(int[] a) {
        int N = a.length;
        int step = N;
        int k;
        // 第一部分
        while ((step /= 1.3) > 1) {
            for (int i = N - 1; i >= step; i--) {
                k = i - step;
                if (a[k] > a[i]) {
                    // 交换位置
                    exc(a, k, i);
                }
            }
        }
        // 第二部分：进行冒泡排序
        a = bubbleSort2(a);
        return a;
    }


    /**
     * 2.2 shell排序
     *
     * @param a
     * @return
     */
    public static int[] shellSort(int[] a) {
        int N = a.length;
        int h = 1;
        // 增量序列
        while (h < N / 3) {
            // h = 1,4,13,40,……
            h = h * 3 + 1;
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                // 进行插入排序，诺a[j]比a[j-h]小，则向前挪动h
                for (int j = i; j >= h && a[j - h] > a[j]; j -= h) {
                    exc(a, j, j - h);
                }
            }
            h /= 3;
        }
        return a;
    }

    public static int[] shellSort2(int[] ins) {

        int n = ins.length;
        int gap = n / 2;
        while (gap > 0) {
            for (int j = gap; j < n; j++) {
                int i = j;
                while (i >= gap && ins[i - gap] > ins[i]) {
                    int temp = ins[i - gap] + ins[i];
                    ins[i - gap] = temp - ins[i - gap];
                    ins[i] = temp - ins[i - gap];
                    i -= gap;
                }
            }
            gap = gap / 2;
        }
        return ins;
    }


    /**
     * 2.3 调用quickSort函数
     *
     * @param a 数组
     */
    public static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    /**
     * 进行递归的快排
     *
     * @param a
     * @param lo
     * @param h
     */
    private static void quickSort(int[] a, int lo, int h) {
        if (h <= lo) {
            return;
        }
        // j为基准值的位置
        int j = partition(a, lo, h);
        // 进行递归调用，将j前面的进行快排
        quickSort(a, lo, j - 1);
        // 进行递归调用，将j后面的进行快排
        quickSort(a, j + 1, h);
    }


    /**
     * 进行切分，并进行交换
     *
     * @param a  数组
     * @param lo 切分开始的位置
     * @param h  切分结束的位置
     * @return 返回分界点的位置
     */
    private static int partition(int[] a, int lo, int h) {
        // 选取中间的值为基准值
        int middle = (lo + h + 1) / 2;
        int v = a[middle];
        // 将基准值和a[lo]交换位置
        exc(a, lo, middle);

        int i = lo;
        int j = h + 1;
        while (true) {

            // 假如左边的小于基准值，则一直进行循环
            while (a[++i] < v) {
                // 防止越界
                if (i == h) {
                    break;
                }
            }
            // 假如右边的大于基准值，则一直进行循环
            while (a[--j] > v) {
                if (j == lo) {
                    break;
                }
            }

            // 一旦i>=j则代表i前面的除第一个外都比基准值小，j后面的都比基准值大，这时候就可以跳出循环了
            if (i >= j) {
                break;
            }

            // 进行交换(因为a[lo]>v,a[h]<v，所以将两者进行交换)
            exc(a, i, j);
        }
        // 将基准放到分界点
        exc(a, lo, j);
        return j;
    }


    /**
     * 2.4 堆排序
     *
     * @param a
     */
    public static void heepSort(int[] a) {
        int N = a.length;

        // 构造一个堆有序
        for (int i = N / 2; i >= 0; i--) {
            sink(a, i, N - 1);
        }
        N = N - 1;
        // 然后进行下沉排序
        while (N > 0) {
            exc(a, 0, N--);
            sink(a, 0, N);
        }
    }

    /**
     * 小的结点往下移动
     *
     * @param a
     * @param k 开始移动的位置
     * @param N 下沉结束位置
     */
    private static void sink(int[] a, int k, int N) {
        // 满足向下移动的条件
        while (2 * k + 1 <= N) {
            int j = 2 * k + 1;
            // 从 a[j]和a[j+1]中a比较出较大的元素
            if (j < N - 1 && a[j + 1] > a[j]) {
                j++;
            }
            if (a[j] < a[k]) {
                break;
            }
            // 将大的元素移动到上面去
            exc(a, k, j);
            k = j;
        }
    }


    // --------------------------------------------

    /**
     * 3.1 归并排序的核心思想是分治法，是创建在归并操作上面的一种有效的排序算法。
     * <p>
     * 原理：
     * <p>
     * 采用分治法:
     * <p>
     * 分割：递归地把当前序列平均分割成两半。
     * <p>
     * 集成：在保持元素顺序的同时将上一步得到的子序列集成到一起（归并）。
     *
     * @param a
     */
    public static void mergeSort(int[] a) {
        aux = new int[a.length];
        mergeSort(a, 0, a.length - 1);
    }

    public static void mergeSort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int middle = (lo + hi) / 2;
        // 对左半边进行排序
        mergeSort(a, lo, middle);
        // 对右半边进行排序
        mergeSort(a, middle + 1, hi);
        // 进行归并
        merge(a, lo, middle, hi);
    }

    // 临时空间
    public static int[] aux;

    /**
     * 进行归并操作
     *
     * @param a      　数组
     * @param lo     　第一部分数组的开始位置
     * @param middle 　第一部分数组归并的结束位置
     * @param hi     　第二部分数组归并的结束位置
     */
    public static void merge(int[] a, int lo, int middle, int hi) {
        int i = lo;
        // 第二部分数组归并的开始位置
        int j = middle + 1;

        // 将a[lo..hi]的内容复制到aux[lo..hi]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int z = lo; z <= hi; z++) {

            if (i > middle) {
                a[z] = aux[j++];
            } else if (j > hi) {
                a[z] = aux[i++];
            } else if (aux[i] > aux[j]) {
                a[z] = aux[j++];
            } else {
                a[z] = aux[i++];
            }
        }
    }


    /**
     * 3.2 基数排序
     *
     * @param a
     */
    public static void radixSort(int[] a) {
        int max = a[0];
        for (int value : a) {
            if (max < value) {
                max = value;
            }
        }
        // 找出最大位数N
        int N = 0;
        if (max == 0) {
            N = 1;
        } else {
            N = (int) (Math.log10(max) + 1);
        }
        // 进行基数排序
        radixSort(a, N);
    }

    /**
     * 基数排序
     *
     * @param a
     * @param N 最大位数
     */
    public static void radixSort(int[] a, int N) {

        // 相当于博客中表格的编号
        int radix = 10;
        int length = a.length;
        // 代表1，10,100……
        int factor = 1;

        //之所以将二位数组的高度设置为length是为了防止极端情况【即所有数据的最高位数相同】
        int[][] bucket = new int[radix][length];

        // 记录每一个bucket里面有多少个元素
        int[] order = new int[radix];

        for (int i = 0; i < N; i++, factor *= 10) {
            // 将数据放入桶中
            for (int v : a) {
                int digit = (v / factor) % 10;

                bucket[digit][order[digit]] = v;
                order[digit]++;
            }

            int position = 0;
            // 将桶中的数据重新连接放入数组中
            for (int j = 0; j < radix; j++) {
                // 假如里面有数据
                if (order[j] != 0) {
                    // 将数据放入数组中
                    for (int k = 0; k < order[j]; k++) {
                        a[position++] = bucket[j][k];
                    }
                    // 将计数器置零
                    order[j] = 0;
                }
            }
        }
    }


    /**
     * 3.3 计数排序
     * 原理：使用一个额外的数组C，其中C中第i个元素是待排序数组A中值等于i的元素的个数。然后根据数组C来将A中的元素排到正确的位置。
     *
     * @param a
     */
    public static void countSort(int[] a) {

        int max = a[0];
        // 找出最大值
        for (int v : a) {
            if (v > max) {
                max = v;
            }
        }
        // 辅助数组
        int[] count = new int[max + 1];

        // 将数据的个数储存到count数组中
        for (int v : a) {
            count[v]++;
        }

        int indexArray = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                a[indexArray++] = i;
                count[i]--;
            }
        }

    }


    /**
     * 交换a数组中i和j的位置
     *
     * @param a 需要交换的数组
     * @param i 位置
     * @param j 位置
     */
    private static void exc(int a[], int i, int j) {
        // 当他们相等的时候就没必要进行交换
        if (a[i] != a[j]) {
            a[i] ^= a[j];
            a[j] ^= a[i];
            a[i] ^= a[j];
        }
    }
}
