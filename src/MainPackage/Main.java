package MainPackage;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Evan
 */

class dataNode{
    int[] nums;
    int index;
    public dataNode(int[] nums, int index){
        this.index=index;
        this.nums=nums;
    }
}
public class Main {
    private static final int[] test = new int[]{1,2,3,4,5,6};
    private static boolean[] found;
    private static int numFound=0;
    private static int universe;
    private static int minCover= Integer.MAX_VALUE;
    private static ArrayList<Integer> soln = new ArrayList<>();
    private static ArrayList<dataNode> list = new ArrayList<>();
    int count=10;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\erubi\\IdeaProjects\\SetCoverProblem\\Test(rg6325).txt");
      //  System.out.println(file.getName());
        Scanner sc = new Scanner(file);
      //  System.out.println(sc.nextLine());
        universe=Integer.parseInt(sc.nextLine());
        found = new boolean[universe];
        int numSets=Integer.parseInt(sc.nextLine());
   //     System.out.println("universe is " +universe+ " numsets is " + numSets);
        int[][] arr = new int[numSets][];
        for (int i=0; i<arr.length; i++){
            int count=0;
            String s=sc.nextLine();
          //  System.out.println("s is " +s);
            String[] nums = s.split(" ");

            arr[i]=new int[nums.length];
            if (nums.length==1&&nums[0].equals(""))continue;
            for (int j=0; j<nums.length; j++){
              //  System.out.print(nums[j]+" ");
                arr[i][j]=Integer.parseInt(nums[j])-1;
            }
         //   System.out.println();
        }
       /* for (int i=0; i<arr.length; i++){
            for (int j=0; j<arr[i].length; j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }*/
        boolean remove[] = new boolean[arr.length];
        int removeCount=0;
        for (int i=0; i<arr.length; i++){
            for (int j=0; j<arr.length; j++){
                if (i==j)continue;
                if (isSubset(arr[j],arr[i])){
                    if (!remove[j]){
                        remove[j]=true;
                        removeCount++;
                    }
                }
            }
        }
        int[][] newArr = new int[arr.length-removeCount][];
        removeCount=0;
        for (int i=0; i<arr.length; i++){
            if (!remove[i])
                newArr[removeCount++]=arr[i];
        }
        long time = System.currentTimeMillis();
        backtrack(newArr,-1);
        System.out.println("Size of mincover is "+minCover+", with items ");
        for (int i=0; i<minCover; i++){
            System.out.print(soln.get(i)+" ");
        }
        System.out.println("completed in "+(System.currentTimeMillis()-time)/1000+" seconds");
    }
    public static boolean isSubset(int[] arr1, int[]arr2){ //is arr1 a subset of arr2?
        for (int i=0; i<arr1.length; i++){
            if (!contains(arr2,arr1[i])) return false;
        }
        return true;
    }
    public static boolean contains(int[] arr, int i){
        for (int j=0; j<arr.length; j++){
            if (arr[j]==i) return true;
        }return false;
    }
    public static boolean isASolution(){
        return numFound==universe;
    }
    public static void processSolution(){
      /*  System.out.print("Finished solution:  ");
        for (int j=0; j<list.size(); j++){

            System.out.print(list.get(j).index);
        }
        System.out.println();*/
      if (list.size()<minCover){
          minCover=list.size();
          soln.clear();
          for (int i=0; i<minCover; i++){
              soln.add(list.get(i).index);
          }
      }
    }
    public static void backtrack(int[][] arr,int k){

        k++;
        if (k>=minCover) return;
        if (isASolution()){
            processSolution();
            return;
        }
        if (k==arr.length )return;
        dataNode[] candidates = new dataNode[arr.length];
       // System.out.println("arr.length is "+arr.length);
        int nCandidates=0;
        nCandidates= constructCandidates(arr,k,candidates,nCandidates,arr.length);
      //  System.out.println("ncanddiates is " +nCandidates);
        for (int i=0; i<nCandidates; i++){
           list.add(candidates[i]);
           int size=candidates[i].nums.length;
       //    System.out.println("size is "+size);
           ArrayList<Integer> saved = new ArrayList<>();
           for (int j=0; j<size; j++){
              // System.out.println(" candidates[i].nums[j]is " +candidates[i].nums[j]);
               if (!found[candidates[i].nums[j]]){
                   numFound++;
                  // System.out.println("num found is "+numFound+", num was "+candidates[i].nums[j]);
                   found[candidates[i].nums[j]]=true;
                   saved.add(candidates[i].nums[j]);
               }
           }
           //found[candidates[i].index]=true;
           backtrack(arr,k);
           //found[candidates[i].index]=false;
           list.remove(list.size()-1);
            for (int j=0; j<saved.size(); j++){
                found[saved.get(j)]=false;
                numFound--;
            }
        }
        if (isASolution()){
            processSolution();
        }
    }
    public static void print(int[] arr){
        for (int i=0; i<arr.length; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    public static int constructCandidates(int[][] verticeArr, int k, dataNode[] candidates, int nCandidates, int n){

        boolean inPerm[]= new boolean[n];


        for (int i=0; i<list.size(); i++){
            inPerm[list.get(i).index]=true;
        }
        nCandidates=0;
        int starter=0;
        if (list.size()>0)
            starter=list.get(list.size()-1).index;
        for (int i=starter; i<n;i++){
            if (verticeArr[i].length==1&&verticeArr[i][0]==-1){
                continue;
            }
            if (!inPerm[i]){
                candidates[nCandidates]=new dataNode(verticeArr[i],i);
                nCandidates++;
            }
        }

        return nCandidates;
    }


}