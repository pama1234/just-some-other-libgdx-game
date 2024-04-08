package hhs.game.sort;

import hhs.game.sort.games.ViewableArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class SortingAlgorithm implements Runnable{

  int arr[];
  ViewableArray array;

  public static int st=10;

  public void set(ViewableArray array) {
    this.arr=array.arr;
    this.array=array;
  }

  public void bubbleSort() {
    int j,i;
    for(i=0;i<arr.length-1;i++) {
      for(j=0;j<arr.length-1-i;j++) {
        if(arr[j]>arr[j+1]) {
          array.swap(j,j+1);
        }
        try {
          Thread.sleep(st);
        }catch(InterruptedException e) {
          return;
        }
      }
    }
  }
  public void cocatailSort() {
    for(int i=0;i<arr.length/2;i++) {

      boolean isSorted=true;

      for(int j=i;j<arr.length-i-1;j++) {
        if(arr[j]>arr[j+1]) {
          array.swap(j,j+1);
          try {
            Thread.sleep(st);
          }catch(InterruptedException e) {
            return;
          }

          isSorted=false;
        }
      }
      if(isSorted) {

        break;
      }

      isSorted=true;

      for(int j=arr.length-i-1;j>i;j--) {
        if(arr[j]<arr[j-1]) {
          array.swap(j,j-1);
          try {
            Thread.sleep(st);
          }catch(InterruptedException e) {
            return;
          }

          isSorted=false;
        }
      }
      if(isSorted) {

        break;
      }
    }
  }
  public void insertSort() {

    for(int i=1;i<arr.length;i++) {
      for(int j=i;j>0;j--) {
        if(arr[j]<arr[j-1]) {
          array.swap(j,j-1);
          try {
            Thread.sleep(st);
          }catch(InterruptedException e) {
            return;
          }
        }
      }
    }
  }
  public void selectionSort() {
    int i,j,max=0,tmp=0;
    for(i=arr.length-1;i>=0;i--) {
      for(j=i-1;j>=0;j--) {
        if(arr[j]>max) {
          max=arr[j];
          tmp=j;
        }
        array.ct(i,j);
        try {
          Thread.sleep(st);
        }catch(InterruptedException e) {
          return;
        }
      }
      array.swap(i,tmp);
      max=0;
    }
  }

  public void quickSort() {
    qSort(0,arr.length-1);
  }
  public void qSort(int left,int right) {
    if(Thread.currentThread().isInterrupted()) return;
    int i,j,middle;
    i=left;
    j=right;
    middle=arr[(left+right)/2];
    while(i<=j) {
      while(arr[i]<middle) i++;
      while(arr[j]>middle) j--;
      if(i<=j) {
        array.swap(i,j);
        try {
          Thread.sleep(st);
        }catch(InterruptedException e) {
          return;
        }
        i++;
        j--;
      }
    }
    if(!Thread.currentThread().isInterrupted()) {
      if(left<j) qSort(left,j);
      if(right>i) qSort(i,right);
    }
  }

  public void shellSort() {
    int gap,i,j,temp;
    for(gap=arr.length/2;gap>0;gap/=2) {
      for(i=gap;i<arr.length;i+=1) {
        temp=arr[i];
        for(j=i;j>=gap&&arr[j-gap]>temp;j-=gap) {
          arr[j]=arr[j-gap];
          array.arrayChange();
          array.ct(i,j);
          try {
            Thread.sleep(st);
          }catch(InterruptedException e) {
            return;
          }
        }
        arr[j]=temp;
      }
    }
  }

  public void mergeSort() {
    merging(0,arr.length-1,new int[arr.length]);
  }

  public void merging(int left,int right,int temp[]) {
    if(Thread.currentThread().isInterrupted()) return;
    if(left<right) {
      int mid=(left+right)/2;
      if(Thread.currentThread().isInterrupted()) return;
      merging(left,mid,temp);
      if(Thread.currentThread().isInterrupted()) return;
      merging(mid+1,right,temp);
      if(Thread.currentThread().isInterrupted()) return;
      merge(arr,left,mid,right,temp);
    }
  }
  public void merge(int[] arr,int left,int mid,int right,int temp[]) {
    if(Thread.currentThread().isInterrupted()) return;
    int i=left;
    int j=mid+1;
    int t=0;
    while(i<=mid&&j<=right) {
      if(arr[i]<=arr[j]) {
        temp[t]=arr[i];
        t++;
        i++;
      }else {

        temp[t]=arr[j];
        t++;
        j++;
      }
    }
    while(i<=mid) {
      temp[t]=arr[i];
      t++;
      i++;
    }
    while(j<=right) {
      temp[t]=arr[j];
      t++;
      j++;
    }
    t=0;
    int tempLeft=left;
    while(tempLeft<=right) {
      arr[tempLeft]=temp[t];
      array.ct(tempLeft,i);
      t++;
      tempLeft++;
      array.arrayChange();
      try {
        Thread.sleep(st);
      }catch(InterruptedException e) {
        return;
      }
    }
  }
  public void heapSort() {

    for(int i=arr.length/2-1;i>=0;i--) {
      if(Thread.currentThread().isInterrupted()) return;
      adjustHeap(i,arr.length);
    }
    for(int j=arr.length-1;j>0;j--) {
      if(Thread.currentThread().isInterrupted()) return;
      int temp=arr[j];
      arr[j]=arr[0];
      arr[0]=temp;
      adjustHeap(0,j);
    }
  }

  public void adjustHeap(int i,int length) {
    if(Thread.currentThread().isInterrupted()) return;
    int temp=arr[i];

    for(int j=i*2+1;j<length;j=j*2+1) {
      array.ct(i,j);
      array.arrayChange();
      try {
        Thread.sleep(st);
      }catch(InterruptedException e) {
        return;
      }
      if(j+1<length&&arr[j]<arr[j+1]) {
        j++;
      }
      if(arr[j]>temp) {
        arr[i]=arr[j];

        i=j;
      }else {

        break;
      }
    }
    arr[i]=temp;
  }
  public void countingSort() {
    if(arr.length==0) {
      return;
    }
    int[] copyArray=Arrays.copyOf(arr,arr.length);

    int max=Integer.MIN_VALUE;
    int min=Integer.MAX_VALUE;
    int test=0;

    for(int num:copyArray) {
      max=Math.max(max,num);
      min=Math.min(min,num);
      array.one(test++);
      try {
        Thread.sleep(st);
      }catch(InterruptedException e) {
        return;
      }
    }
    int[] countArray=new int[max-min+1];
    for(int num:copyArray) {
      countArray[num-min]++;
    }
    int length=countArray.length;
    for(int i=1;i<length;i++) {
      countArray[i]=countArray[i]+countArray[i-1];
    }
    for(int j=copyArray.length-1;j>=0;j--) {
      int countIndex=copyArray[j]-min;
      int index=countArray[countIndex]-1;
      arr[index]=copyArray[j];
      countArray[countIndex]--;
      array.one(j);
      array.arrayChange();
      try {
        Thread.sleep(st);
      }catch(InterruptedException e) {
        return;
      }
    }
  }
  public void radixSort() {
    int max=Integer.MIN_VALUE;
    for(int num:arr) {
      max=Math.max(max,num);
    }
    LinkedList<Integer>[] list=new LinkedList[10];
    for(int i=0;i<10;i++) {
      list[i]=new LinkedList<>();
    }
    for(int divider=1;divider<=max;divider*=10) {
      for(int num:arr) {
        int no=num/divider%10;
        list[no].offer(num);
      }
      int index=0;
      for(LinkedList<Integer> linkedList:list) {
        while(!linkedList.isEmpty()) {
          arr[index++]=linkedList.poll();
          array.one(index-1);
          array.arrayChange();
          try {
            Thread.sleep(st);
          }catch(InterruptedException e) {
            return;
          }
        }
      }
    }
  }
  public void bucketSort() {
    bucket(5);
  }
  public void bucket(int bucketSize) {
    // 初始化最大最小值
    int max=Integer.MIN_VALUE;
    int min=Integer.MAX_VALUE;
    // 找出最小值和最大值
    for(int num:arr) {
      max=Math.max(max,num);
      min=Math.min(min,num);
    }
    // 创建bucketSize个桶
    ArrayList<ArrayList<Integer>> bucketList=new ArrayList<>();// 声明五个桶
    for(int i=0;i<bucketSize;i++) {
      bucketList.add(new ArrayList<>());// 确定桶的格式为ArrayList
    }
    // 将数据放入桶中
    int index=0;
    for(int num:arr) {
      // 确定元素存放的桶号
      int bucketIndex=(num-min)*(bucketSize-1)/(max-min);//重点
      List<Integer> list=bucketList.get(bucketIndex);
      list.add(num);// 将元素存入对应的桶中

      array.one(index++);
      try {
        Thread.sleep(st);
      }catch(InterruptedException e) {
        return;
      }
    }
    // 遍历每一个桶
    for(int i=0,arrIndex=0;i<bucketList.size();i++) {
      List<Integer> list=bucketList.get(i);
      list.sort(null);// 对每一个桶排序
      for(int value:list) {
        array.one(arrIndex);
        array.arrayChange();
        try {
          Thread.sleep(st);
        }catch(InterruptedException e) {
          return;
        }
        arr[arrIndex++]=value;
      }
    }
  }
}
