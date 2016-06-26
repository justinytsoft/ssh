package com.goldCityWeb.util;

import java.math.BigDecimal;

public class HongBaoUtils {  
    
    /** 
     *  
     * @param total 
     *            红包总额 
     */  
    public static float[] generateHongbao(int total) {  
    	int totalPepoleCount=total*2;
        float[] result = new float[totalPepoleCount];  
        
         //第一个大红包 唯一一个人获取8%的大红包
        final int SAVE_ORIGINAL=1;
         result[0]=total/100*8;
          //6%的人获取金额的24%
           //第二批次的总人数
        int secondPeopleCount=(total/100*2*6);
            //第二批次的人的总共获取的金额
        int secondGoldCount=total/100*24;
            //第二批次的人的总共获取的平均值
        float secondGoldAverageGoldCount= (float) 24/(2*6);
         //第二批次红包浮动范围为 平均值+-80%;
        float secondFloat=(float) 0.8;
         //产生的浮动率数组为
        float[] flyArray={ (float)1.8,(float)1.79,(float)1.78,(float)1.77,(float)1.76,(float)1.75,(float)1.74,(float)1.73,(float)1.72,(float)1.71,(float)1.7,(float)1.69,(float)1.68,(float)1.67,(float)1.66,(float)1.65,(float)1.64,(float)1.63,(float)1.62,(float)1.61,(float)1.6,(float)1.59,(float)1.58,(float)1.57,(float)1.56,(float)1.55,(float)1.54,(float)1.53,(float)1.52,(float)1.51,(float)1.5,(float)1.49,(float)1.48,(float)1.47,(float)1.46,(float)1.45,(float)1.44,(float)1.43,(float)1.42,(float)1.41,(float)1.4,(float)1.39,(float)1.38,(float)1.37,(float)1.36,(float)1.35,(float)1.34,(float)1.33,(float)1.32,(float)1.31,(float)1.3,(float)1.29,(float)1.28,(float)1.27,(float)1.26,(float)1.25,(float)1.24,(float)1.23,(float)1.22,(float)1.21,(float)1.2,(float)1.19,(float)1.18,(float)1.17,(float)1.16,(float)1.15,(float)1.14,(float)1.13,(float)1.12,(float)1.11,(float)1.1,(float)1.09,(float)1.08,(float)1.07,(float)1.06,(float)1.05,(float)1.04,(float)1.03,(float)1.02,(float)1.01,(float)0.99,(float)0.98,(float)0.97,(float)0.96,(float)0.95,(float)0.94,(float)0.93,(float)0.92,(float)0.91,(float)0.9,(float)0.89,(float)0.88,(float)0.87,(float)0.86,(float)0.85,(float)0.84,(float)0.83,(float)0.82,(float)0.81,(float)0.8,(float)0.79,(float)0.78,(float)0.77,(float)0.76,(float)0.75,(float)0.74,(float)0.73,(float)0.72,(float)0.71,(float)0.7,(float)0.69,(float)0.68,(float)0.67,(float)0.66,(float)0.65,(float)0.64,(float)0.63,(float)0.62,(float)0.61,(float)0.6,(float)0.59,(float)0.58,(float)0.57,(float)0.56,(float)0.55,(float)0.54,(float)0.53,(float)0.52,(float)0.51,(float)0.5,(float)0.49,(float)0.48,(float)0.47,(float)0.46,(float)0.45,(float)0.44,(float)0.43,(float)0.42,(float)0.41,(float)0.4,(float)0.39,(float)0.38,(float)0.37,(float)0.36,(float)0.35,(float)0.34,(float)0.33,(float)0.32,(float)0.31,(float)0.3,(float)0.29,(float)0.28,(float)0.27,(float)0.26,(float)0.25,(float)0.24,(float)0.23,(float)0.22,(float)0.21,(float)0.2};
         //最大被整除的数
         int  maxDevide=secondPeopleCount/160;
         //最大的被除不尽的数
         int  maxGet=secondPeopleCount%160;
          //循环获取数组的最大数量为
         int maxLoopNumber=maxDevide*160;
        
        for(int i=0;i<maxLoopNumber;i++){
        	result[i+SAVE_ORIGINAL]= secondGoldAverageGoldCount*flyArray[i%160];	
        	
        }
          //循环处理除不尽的那一部分
        for(int i=0;i<maxGet;i++){
        	result[maxLoopNumber+i+SAVE_ORIGINAL]=secondGoldAverageGoldCount*flyArray[80-maxGet/2+i];	
        }
        
        
        
        double k=0;
        for(int i=SAVE_ORIGINAL;i<totalPepoleCount;i++){
     	   BigDecimal    b  =   new BigDecimal(result[i]);  
     	   result[i]=b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();  
        }
       
          ////////////////////第三批次
        //6%的人获取金额的24%
        //第二批次的总人数
     int thirdPeopleCount=(total/100*2*12);
         //第二批次的人的总共获取的金额
     int thirdGoldCount=total/100*24;
         //第二批次的人的总共获取的平均值
     float thirdGoldAverageGoldCount= (float)24/(2*12);
      //第二批次红包浮动范围为 平均值+-80%;
     float thirdFloat=(float) 0.8;
      //产生的浮动率数组为
     float[] thridFlyArray={ (float)1.8,(float)1.79,(float)1.78,(float)1.77,(float)1.76,(float)1.75,(float)1.74,(float)1.73,(float)1.72,(float)1.71,(float)1.7,(float)1.69,(float)1.68,(float)1.67,(float)1.66,(float)1.65,(float)1.64,(float)1.63,(float)1.62,(float)1.61,(float)1.6,(float)1.59,(float)1.58,(float)1.57,(float)1.56,(float)1.55,(float)1.54,(float)1.53,(float)1.52,(float)1.51,(float)1.5,(float)1.49,(float)1.48,(float)1.47,(float)1.46,(float)1.45,(float)1.44,(float)1.43,(float)1.42,(float)1.41,(float)1.4,(float)1.39,(float)1.38,(float)1.37,(float)1.36,(float)1.35,(float)1.34,(float)1.33,(float)1.32,(float)1.31,(float)1.3,(float)1.29,(float)1.28,(float)1.27,(float)1.26,(float)1.25,(float)1.24,(float)1.23,(float)1.22,(float)1.21,(float)1.2,(float)1.19,(float)1.18,(float)1.17,(float)1.16,(float)1.15,(float)1.14,(float)1.13,(float)1.12,(float)1.11,(float)1.1,(float)1.09,(float)1.08,(float)1.07,(float)1.06,(float)1.05,(float)1.04,(float)1.03,(float)1.02,(float)1.01,(float)0.99,(float)0.98,(float)0.97,(float)0.96,(float)0.95,(float)0.94,(float)0.93,(float)0.92,(float)0.91,(float)0.9,(float)0.89,(float)0.88,(float)0.87,(float)0.86,(float)0.85,(float)0.84,(float)0.83,(float)0.82,(float)0.81,(float)0.8,(float)0.79,(float)0.78,(float)0.77,(float)0.76,(float)0.75,(float)0.74,(float)0.73,(float)0.72,(float)0.71,(float)0.7,(float)0.69,(float)0.68,(float)0.67,(float)0.66,(float)0.65,(float)0.64,(float)0.63,(float)0.62,(float)0.61,(float)0.6,(float)0.59,(float)0.58,(float)0.57,(float)0.56,(float)0.55,(float)0.54,(float)0.53,(float)0.52,(float)0.51,(float)0.5,(float)0.49,(float)0.48,(float)0.47,(float)0.46,(float)0.45,(float)0.44,(float)0.43,(float)0.42,(float)0.41,(float)0.4,(float)0.39,(float)0.38,(float)0.37,(float)0.36,(float)0.35,(float)0.34,(float)0.33,(float)0.32,(float)0.31,(float)0.3,(float)0.29,(float)0.28,(float)0.27,(float)0.26,(float)0.25,(float)0.24,(float)0.23,(float)0.22,(float)0.21,(float)0.2};
      //最大被整除的数
      int  thirdMaxDevide=thirdPeopleCount/160;
      //最大的被除不尽的数
      int  thirdMaxGet=thirdPeopleCount%160;
       //循环获取数组的最大数量为
      int thirdMaxLoopNumber=thirdMaxDevide*160;
     
     for(int i=0;i<thirdMaxLoopNumber;i++){
     	result[i+SAVE_ORIGINAL+secondPeopleCount]= thirdGoldAverageGoldCount*thridFlyArray[i%160];	
     	
     }
       //循环处理除不尽的那一部分
     for(int i=0;i<thirdMaxGet;i++){
     	result[thirdMaxLoopNumber+i+SAVE_ORIGINAL+secondPeopleCount]= thirdGoldAverageGoldCount*thridFlyArray[80-thirdMaxGet/2+i];	
     }
     
      k=0;
     for(int i=SAVE_ORIGINAL+secondPeopleCount;i<totalPepoleCount;i++){
  	   BigDecimal    b  =   new BigDecimal(result[i]);  
  	   result[i]=b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();  
     }
     
     
        
     ////////////////////第四批次
     //6%的人获取金额的24%
     //第4批次的总人数
  int fourthPeopleCount=(total/100*2*62);
      //第二批次的人的总共获取的金额
  int fourthGoldCount=total/100*40;
      //第二批次的人的总共获取的平均值
  float fourthGoldAverageGoldCount=(float)40/(2*62);
   
   //第二批次红包浮动范围为 平均值+-80%;
  float fourthFloat=(float) 0.8;
   //产生的浮动率数组为
  float[] fourthFlyArray={ (float)1.8,(float)1.79,(float)1.78,(float)1.77,(float)1.76,(float)1.75,(float)1.74,(float)1.73,(float)1.72,(float)1.71,(float)1.7,(float)1.69,(float)1.68,(float)1.67,(float)1.66,(float)1.65,(float)1.64,(float)1.63,(float)1.62,(float)1.61,(float)1.6,(float)1.59,(float)1.58,(float)1.57,(float)1.56,(float)1.55,(float)1.54,(float)1.53,(float)1.52,(float)1.51,(float)1.5,(float)1.49,(float)1.48,(float)1.47,(float)1.46,(float)1.45,(float)1.44,(float)1.43,(float)1.42,(float)1.41,(float)1.4,(float)1.39,(float)1.38,(float)1.37,(float)1.36,(float)1.35,(float)1.34,(float)1.33,(float)1.32,(float)1.31,(float)1.3,(float)1.29,(float)1.28,(float)1.27,(float)1.26,(float)1.25,(float)1.24,(float)1.23,(float)1.22,(float)1.21,(float)1.2,(float)1.19,(float)1.18,(float)1.17,(float)1.16,(float)1.15,(float)1.14,(float)1.13,(float)1.12,(float)1.11,(float)1.1,(float)1.09,(float)1.08,(float)1.07,(float)1.06,(float)1.05,(float)1.04,(float)1.03,(float)1.02,(float)1.01,(float)0.99,(float)0.98,(float)0.97,(float)0.96,(float)0.95,(float)0.94,(float)0.93,(float)0.92,(float)0.91,(float)0.9,(float)0.89,(float)0.88,(float)0.87,(float)0.86,(float)0.85,(float)0.84,(float)0.83,(float)0.82,(float)0.81,(float)0.8,(float)0.79,(float)0.78,(float)0.77,(float)0.76,(float)0.75,(float)0.74,(float)0.73,(float)0.72,(float)0.71,(float)0.7,(float)0.69,(float)0.68,(float)0.67,(float)0.66,(float)0.65,(float)0.64,(float)0.63,(float)0.62,(float)0.61,(float)0.6,(float)0.59,(float)0.58,(float)0.57,(float)0.56,(float)0.55,(float)0.54,(float)0.53,(float)0.52,(float)0.51,(float)0.5,(float)0.49,(float)0.48,(float)0.47,(float)0.46,(float)0.45,(float)0.44,(float)0.43,(float)0.42,(float)0.41,(float)0.4,(float)0.39,(float)0.38,(float)0.37,(float)0.36,(float)0.35,(float)0.34,(float)0.33,(float)0.32,(float)0.31,(float)0.3,(float)0.29,(float)0.28,(float)0.27,(float)0.26,(float)0.25,(float)0.24,(float)0.23,(float)0.22,(float)0.21,(float)0.2};
   //最大被整除的数
   int  fourthMaxDevide=fourthPeopleCount/160;
   //最大的被除不尽的数
   int  fourthMaxGet=fourthPeopleCount%160;
    //循环获取数组的最大数量为
   int fourthMaxLoopNumber=fourthMaxDevide*160;
  
  for(int i=0;i<fourthMaxLoopNumber;i++){
  	result[i+SAVE_ORIGINAL+secondPeopleCount+thirdPeopleCount]=fourthGoldAverageGoldCount* fourthFlyArray[i%160];	
  	
  }
    //循环处理除不尽的那一部分
  for(int i=0;i<fourthMaxGet;i++){
  	result[fourthMaxLoopNumber+i+SAVE_ORIGINAL+secondPeopleCount+thirdPeopleCount]= fourthGoldAverageGoldCount*fourthFlyArray[80-fourthMaxGet/2+i];	
  }
  
  
  k=0;
  for(int i=SAVE_ORIGINAL+secondPeopleCount+thirdPeopleCount;i<totalPepoleCount;i++){
	   k+=result[i];   
  }
       
  /////产生最后一批次红包      
 //循环处理除不尽的那一部分
  
  int lastBegin=(SAVE_ORIGINAL+secondPeopleCount+thirdPeopleCount+fourthPeopleCount);
  int fithPeopleCount=(total/100*2*20);
  for(int i=0;i<(fithPeopleCount-1);i++){
	    result[i+lastBegin]=  0.1f;	
}

k=0;
for(int i=0;i<totalPepoleCount;i++){
   k+=result[i];   
}

//如果需要算法更高效率，可以取消下面的

//System.out.println("k->"+k);
double dd=total-k+result[0];
result[0]=(float) dd;

return result;
}  
    
public static void main(String[] args) {  
    generateHongbao(1000000);
    	
}  
      
   
} 
