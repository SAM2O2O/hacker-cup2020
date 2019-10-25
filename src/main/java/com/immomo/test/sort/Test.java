package com.immomo.test.sort;

import java.util.Arrays;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.sort
 * @date 2019-10-25 15:26
 */
public class Test {

    public static void adjustHeap(int[] arr, int pos, int len){
        //保存当前要调整的节点，保持不变
        int temp = arr[pos];
        int child;
        // 沿关键字较大的孩子结点向下筛选，将当前节点放置到合适的位置
        for(; pos*2+1 <= len; pos = child){
            child = pos*2 + 1;
            if( child < len && arr[child] > arr[child+1]){
                // child为关键字中较小记录的下标
                child++;
            }

            if(temp <= arr[child]){
                break;
            }
            //若子节点小，则交换
            arr[pos] = arr[child];
        }

        //通过上面的for循环找到了当前要调整的节点要存放的位置
        arr[pos] = temp;
    }

    //时间复杂度O(n)
    public static void heapSort(int [] arr){
        //创建最大堆
        for (int i = arr.length/2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length-1);
        }

        //交换堆顶元素和最后一个元素并调整堆
        for (int i = arr.length - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            adjustHeap(arr, 0, i-1);
        }
    }
    public static void main(String[] args) {
        int a[] = {5,4,9,8,7,6,0,1,3,2};
        heapSort(a);
        System.out.println(Arrays.toString(a));
    }
}
