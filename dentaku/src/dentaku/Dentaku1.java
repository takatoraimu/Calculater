package dentaku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dentaku1 {

	public static void main(String[] args) throws IOException{


	System.out.println("掛算、割算の演算子はそれぞれ* / を用いてください");
	System.out.println("全て半角で入力してください");
	System.out.println("マイナスを入力するときは必ず(-1)のように入力してください");
	System.out.println("使える文字は数字と +-*/ () のみです");
	System.out.println(" ");
    System.out.println("数式を入力してください");

    /* 作者は初心者のためこれを見てもほとんどの人が理解できないと思われる。
     * そのためコメントで何をしているのか補っていきたい
     * 四則演算を考えるためには演算子と数の区別が必須である。そのため以下の方針を立てた
     * ・入力された文字列を配列するときに配列A,Bを導入する
     * ・Aに代入された文字で判別し、Bに代入された数字で計算をする
     * また演算子や()による計算順序を考慮するために優先度Yというものを考える。
     *
     */

	 BufferedReader br =
     		new BufferedReader (new InputStreamReader(System.in));

     String str = br.readLine();
     System.out.println(str+"が入力されました");

     /* 入力された数式を文字列A[i] と数B[i]に分ける。
     * Aには　数字N、演算子、そのほかにはGを代入する
 	 * 空白はAにG　B=0
 	 * 演算子はAは演算子 B=0
 	 * 数字はAにN　Bの最後のみ数字、それ以外は0である。
 	 * 計算するために数字を探す必要があるが、簡略化のため今回は全ての数字を右づめにする。
 	        */

       char[]A = new char[str.length()];
     for(int i = 0; i<str.length();i++ ){
    	 	 A[i]=str.charAt(i);
     }
     double[]B = new double[str.length()];

	 for(int i=0; i<str.length();i++){
     	if(A[i]=='0' ||
     		A[i]=='1' ||
    		A[i]=='2' ||
    		A[i]=='3' ||
   			A[i]=='4' ||
   			A[i]=='5' ||
   			A[i]=='6' ||
   			A[i]=='7' ||
   			A[i]=='8' ||
    		A[i]=='9' ){

    	B[i]=Integer.valueOf(A[i])-48;
     	}
	 }

    for(int i=0; i<str.length();i++){
    	if(A[i]==' '){
           	A[i]='G';
           	B[i]=0;
        }
    	if(A[i]=='+' ||
          	A[i]=='-' ||
            A[i]=='*' ||
          	A[i]=='/' ||
           	A[i]=='(' ||
           	A[i]==')' ){
    		B[i]=0;
         	}

        if(i!=str.length()-1){
          	if(A[i]=='0' ||
            	A[i]=='1' ||
           		A[i]=='2' ||
           		A[i]=='3' ||
           		A[i]=='4' ||
           		A[i]=='5' ||
           		A[i]=='6' ||
           		A[i]=='7' ||
           		A[i]=='8' ||
           		A[i]=='9' ||
           		A[i]=='N' ){
          		A[i]='N';

        		if(A[i+1]=='0' ||
        			A[i+1]=='1' ||
        			A[i+1]=='2' ||
        			A[i+1]=='3' ||
        			A[i+1]=='4' ||
       				A[i+1]=='5' ||
       				A[i+1]=='6' ||
       				A[i+1]=='7' ||
       				A[i+1]=='8' ||
        			A[i+1]=='9' ||
        			A[i+1]=='N' ){

        			B[i+1]=B[i]*10+B[i+1];
        			B[i]=0;
        			A[i]='G';
       			}
        		if(A[i+1]==' '){
        			B[i+1]=B[i];
        			B[i]=0;
        			A[i]='G';
        			A[i+1]='N';
        		}
       		}
        }
        if(i==str.length()-1){
        	if(A[i]=='0' ||
                A[i]=='1' ||
               	A[i]=='2' ||
              	A[i]=='3' ||
             	A[i]=='4' ||
              	A[i]=='5' ||
               	A[i]=='6' ||
               	A[i]=='7' ||
               	A[i]=='8' ||
               	A[i]=='9' ||
               	A[i]=='N' ){

        		A[i]='N';
        	}
        	else{
        		B[i]=0;
        	}
        }
    }

    for(int i=0; i<str.length();i++){
    //	System.out.println("A[i]"+A[i]);
    //	System.out.println("B[i]"+B[i]);
    }
    /* 入力された負の数を処理するためにMを導入した。
	 * (-N)の形になっていればM=Tとなり、計算の過程で判別できる。
	 */
    char[]M = new char[str.length()];
    for(int i = 0; i<str.length();i++ ){
    	for(int t=i+1; t<str.length(); t++){
    		for(int s=i-1; s>-1; s--){
    			if(A[i]=='-' ){
    				if(A[s]=='+' ||
    					A[s]=='-' ||
    					A[s]=='*' ||
    					A[s]=='/' ||
    					A[s]==')' ||
    					A[s]=='N'){
    					break;
    				}
    				if(A[s]=='('){
    					if(A[t]=='+' ||
    	    				A[t]=='-' ||
    	    				A[t]=='*' ||
    	    				A[t]=='/' ||
    	    				A[t]==')' ){
    	    					break;
    					}
    					if(A[t]=='(' || A[t]==')' || A[t]=='N'){
    						M[i]='T';
    						break;
    					}
    				}
    			}
    		}
    			
    	}
    }
    /*計算の優先度Yを考える
     * 要求として、()の中、*と/、+と-の順で計算できるようにしたい
     * そのために+-にYの初期値を1、* /に2を与え、()に入るたび優先度が＋2されるようにする
     * またKazuは(と)の数を等しいときにループを抜けるようにするための数である。
     */

    int Y[] = new int[str.length()];
   	for(int i=0; i<str.length();i++){
    	Y[i] = 0;
    	if(A[i]=='+' ||
    		A[i]=='-' ){

    		Y[i] = 1;
    	}
    	if(A[i]=='*' ||
    		A[i]=='/' ){

    		Y[i] = 2;
    	}
    }
    int Kazu = 1 ;
    for(int i=0; i<str.length();i++){
    	for(int j=i+1; j<str.length(); j++){
    	if(A[i]=='(' ){
    			if(A[j]=='+' ||
    				A[j]=='-' ||
    				A[j]=='*' ||
   					A[j]=='/' ){
    				Y[j]=Y[j]+2*(Kazu);
    			}
    			if(A[j]=='('){
    				Kazu = Kazu + 1 ;
    				A[j]='G';
    			}
    			if(A[j]==')' && Kazu==1 ){
    				A[j]='G';
    				break;
    			}
    			if(A[j]==')' && Kazu!=1 ){
    				Kazu = Kazu - 1 ;
    				A[j]='G';
    			}
    		}

    	}
    }
    
    if(A[str.length()-1]==')'){
    	A[str.length()-1]='G';
    }

    for(int i=0 ; i<str.length()-1;i++){
    	if(A[i]=='('){
    		A[i]='G';
    	}
    }
    // ()の処理により右づめではなくなった部分が存在するので右づめにする 

    boolean Z=false ; //計算結果が出たかどうかの判別に用いる

    //優先度が高いものから処理するために、大きい数からどんどん小さくして優先度と同じ数になったときを考える
    for(int i=str.length()*3; i>0;i--){
    	for(int s=0; s<str.length()-1;s++){

    		//まずは右づめにする
    		if(A[s]=='N' && A[s+1]=='G'){
	    		for(int t=s+1; t<str.length();t++){
	    			if(A[t]=='+' ||
	    				A[t]=='-' ||
	    				A[t]=='*' ||
	    				A[t]=='/' ){
	    				B[t-1]=B[s];
	    				A[s]='G';
	    				A[t-1]='N';
	    				break;
	    			}
	    		}
	    	}
    		if(Y[s]==i){
    			
    			if(A[s]=='+'){
    				for(int t=s+1; t<str.length(); t++){
    					if(A[t]=='N'){
    						B[t]=B[s-1]+B[t];
  							A[s]='G';
  							A[s-1]='G';

  							break;
    					}
    				}
    			}
    			if(A[s]=='-'){
    				for(int t=s+1; t<str.length(); t++){
    					//まず(-N)の形の処理をする
    					if(M[s]=='T' && A[t]=='N'){
    						B[t]=-B[t];
    						A[s]='G';
    						break;
    					}
    					if(A[t]=='N' && A[s-1]=='N'){
   							B[t]=B[s-1]-B[t];
   							A[s-1]='G';
   							A[s]='G';
   							break;
    					}
    				}
    			}
    			if(A[s]=='*'){
    				for(int t=s+1; t<str.length(); t++){
    					if(A[t]=='N' ){
    						B[t]=B[s-1]*B[t];
    						A[s-1]='G';
    						A[s]='G';
    						break;
    					}
    				}
   				}
    			if(A[s]=='/'){
    				for(int t=s+1; t<str.length(); t++){
    					if(A[t]=='N' && B[t]!=0){
    						B[t]=B[s-1]/B[t];
   							A[s-1]='G';
    						A[s]='G';
    						break;
    					}
    					if(A[t]=='N' && B[t]==0){
    						System.out.println("0徐算は定義されていないため計算できません");
    						Z=true;
    						break;
    					}
    				}
    			}
    			for(int r=0; r<str.length()-1; r++){
    				if(A[r]=='N' && A[r+1]=='G'){
    					for(int t=r+1; t<str.length();t++){
    						if(A[t]=='+' ||
    								A[t]=='-' ||
    								A[t]=='*' ||
    								A[t]=='/' ){
    							B[t-1]=B[r];
    							A[r]='G';
    							A[t-1]='N';
    								break;
    						}
    	    			}
    	    		}
    	    	}
   			}

    	}
   	}

    for(int i=0; i<str.length(); i++){
    	if(i!=str.length()){
    		for(int s=i+1; s<str.length(); s++){
    			if(A[i]=='N' && A[s]=='N'){
    				System.out.println("計算できません。式を確認してください");
    				Z=true;
    			}
    		}
    	}
    }
    
    //計算結果が整数の時は整数で表示するためにint型との値を比較する
    for(int i=0; i<str.length(); i++){
    	if(A[i]=='N' && Z==false){
    		int b = (int)B[i];
    		if(b==B[i]){
    			System.out.print("計算結果は"+b+"です");
    			Z=true;
    			break;
    		}
    		else{
    			System.out.println("計算結果は"+B[i]+"です");
    			Z=true;
    			break;
    		}
    	}
    }
    if(Z==false){
    	System.out.println("計算できません。入力した式を確認してください");
    }
    

	}
}


