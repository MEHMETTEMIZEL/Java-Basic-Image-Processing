package assignment3;
import java.util.*;
import java.io.*;
public class myCode {
	// Size of the maze 
	static int N; 		// This number is used in some methods to take border of an array 
	public static void main(String[] args) throws FileNotFoundException{
		int mode = Integer.parseInt(args[0]);
		//String takeInput= args[1];
		//File input = new File(args[1]);
		if(mode==0) {
			Scanner input = new Scanner(new File(args[1]));
			File f=new File("output.ppm");
			PrintStream writeOut=new PrintStream(f);
			writeOutput(input,writeOut);
		}
		if(mode==1) {
			Scanner input2 = new Scanner(new File(args[1]));
			File f2=new File("black-and-white.ppm");
			PrintStream writeOut2=new PrintStream(f2);
			writeBlackAndWhite(input2,writeOut2);
		}if(mode==2) {
			Scanner input3 = new Scanner(new File(args[1]));
			File f3=new File("convolutionBeforeWhiteAndBlack.ppm");
			PrintStream writeOut3=new PrintStream(f3);
			Scanner filter=new Scanner(new File(args[2]));
			writeConvolution(input3,writeOut3,filter);

			Scanner input4 = new Scanner(new File("convolutionBeforeWhiteAndBlack.ppm"));
			File f4=new File("convolution.ppm");
			PrintStream writeOut4=new PrintStream(f4);
			writeBlackAndWhite(input4,writeOut4);
		}
		if(mode==3) {
			Scanner input5 = new Scanner(new File(args[1]));
			File f5=new File("quantized.ppm");
			PrintStream writeOut5=new PrintStream(f5);
			int range=Integer.parseInt(args[2]);
			writeQuantized(input5,writeOut5,range);
		}
	}
	//This reads the input file and write it to an output file.(When mode is zero this function handle the task.)
	public static void writeOutput(Scanner input, PrintStream writeOut) {
		String text=input.next();	// Take first word as a string it is not very important
		int row=input.nextInt();	//Take the row as an integer
		int column=input.nextInt();		//Take the column as an integer
		int maxNum=input.nextInt();		//Take the number that give us the max number in rgb values
		int [][][] arr= new int[row][column][3];
		//This nested for loop is used to generate an 3D array for rgb values
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				for(int k=0;k<3;k++) {
					arr[i][j][k]=input.nextInt();
				}	
			}	
		}		     	
		writeOut.println(text);								//Write first line to the output file	
		writeOut.print(row + " " + column+"\n");			//Write row and column size the output file
		writeOut.println(maxNum);							//Write the max number in rgb values	
		//This nested for loop is used to write the 3D array to the output file.
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				for(int k=0;k<3;k++) {
					writeOut.print((arr[i][j][k])+" ");
				}
				writeOut.print("\t");
			}
			writeOut.println();
		}
	}
	//This reads the input file and convert it to black-and-white form 
	// then write it to an output file.(When mode is one this function handle the task.)
	public static void writeBlackAndWhite(Scanner input, PrintStream writeOut) {
		String text=input.next();
		int row=input.nextInt();
		int column=input.nextInt();
		int maxNum=input.nextInt();

		int [][][] arr= new int[row][column][3];
		//This nested for loop is used to generate an 3D array for rgb values
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				for(int k=0;k<3;k++) {
					arr[i][j][k]=input.nextInt();
				}	
			}	
		}
		int avg=0, sum=0;
		//This nested for loop is used to calculate the average of rgb values
		//and change the rgb values with this average value.
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				for(int k=0;k<3;k++) {
					sum+=arr[i][j][k];
				}
				avg=sum/3;
				for(int k=0;k<3;k++) {
					arr[i][j][k]=avg;

				}
				sum=0;
				avg=0;
			}
		}
		writeOut.println(text);
		writeOut.print(row + " " + column+"\n");
		writeOut.println(maxNum);
		//This nested for loop is used to write the 3D array to the output file.
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				for(int k=0;k<3;k++) {
					writeOut.print((arr[i][j][k])+" ");
				}
				writeOut.print("\t");
			}
			writeOut.println();
		}	
	}
	//This reads the input file and a filter file then convert this input file to the filtered form 
	// then write it to an output file.(When mode is two this function handle the task.)
	public static void writeConvolution(Scanner input, PrintStream writeOut,Scanner input2) {
		String text=input.next();
		int row=input.nextInt();
		int column=input.nextInt();
		int maxNum=input.nextInt();

		//This area is for taking filter file and generate an 2D array for filter 
		String text2=input2.next();
		int indexOfX=text2.indexOf('x');
		int rowFilter=Integer.parseInt(text2.substring(0, indexOfX));	    //Take the row number of filter
		int columnFilter=Integer.parseInt(text2.substring(indexOfX+1));		//Take the column number of the filter
		int [][] arrFilter= new int[rowFilter][columnFilter];
		//Generate the filter array
		for(int i=0;i<rowFilter;i++) {
			for(int j=0;j<columnFilter;j++) {
				arrFilter[i][j]=input2.nextInt();
			}	
		}
		//This nested for loop is used to generate the array
		int [][][] arr= new int[row][column][3];
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				for(int k=0;k<3;k++) {
					arr[i][j][k]=input.nextInt();
				}	
			}	
		}

		// End of the generation 2D filter array 
		int sum=0;
		//This is the main part of this mode. There are 5 nested for loops in here
		//The outer 3 for loops are used to generate new convoluted array.
		//The inner two for loops are used to calculate each index's value in convoluted array.
		int [][][] convolutionArray =new int[row-(rowFilter-1)][column-(columnFilter-1)][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<row-(rowFilter-1);j++) {
				for(int k=0;k<column-(columnFilter-1);k++) {
					for(int m=0;m<rowFilter;m++) {
						for(int n=0;n<columnFilter;n++) {
							sum+=arr[j+m][k+n][i]*arrFilter[m][n];
						}							
					}
					if(sum<0) sum=0;
					else if(sum>255) sum=255;
					convolutionArray[j][k][i]=sum;
					sum=0;
				}	
			}	
		}
		writeOut.println(text);
		writeOut.print((row-(rowFilter-1)) + " " + (column-(columnFilter-1)+"\n"));
		writeOut.println(maxNum);

		//This nested for loop is used to write the new convoluted 3D array to the output file.
		for(int i=0;i<row-(rowFilter-1);i++) {
			for(int j=0;j<column-(columnFilter-1);j++) {
				for(int k=0;k<3;k++) {
					writeOut.print((convolutionArray[i][j][k])+" ");
				}
				writeOut.print("   ");
			}
			writeOut.println();
		}	
	}

	//This method reads the input file and convert it to quantized form 
	// then write it to an output file.(When mode is three this function handles the task.)
	public static void writeQuantized(Scanner input,PrintStream writeOut,int range) {
		String text=input.next();
		int row=input.nextInt();
		int column=input.nextInt();
		int maxNum=input.nextInt();
		writeOut.println(text);
		writeOut.print(row + " " + column+"\n");
		writeOut.println(maxNum);
		int [][][] arr= new int[row][column][3];
		//This nested for loop is used to generate an 3D array for rgb values
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				for(int k=0;k<3;k++) {
					arr[i][j][k]=input.nextInt();
				}	
			}	
		}
		boolean [][][] marked= new boolean[row][column][3];
		N=row;													//This number is used  to find border of an array
		//The most important part of this function is following.
		//This nested for loop is used to find quantization output 
		for(int k=0;k<3;k++) {
			for(int i=0;i<row;i++) {
				for(int j=0;j<column;j++) {
					recursionForQuantization(arr,marked,range,i,j,k,N);
				}	
			}	
		}
		//This nested for loop is used to write the quantized 3D array to the output file.
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				for(int k=0;k<3;k++) {
					writeOut.print((arr[i][j][k])+" ");
				}
				writeOut.print("\t");
			}
			writeOut.println();
		}
	}

	//This method is used to check whether array indexes are inside the border or not 
	public static boolean isSafe(int array[][][],int x, int y, int z,int border) 
	{ 
		N=border;
		return (x >= 0 && x < N && y >= 0 && y < N && z>=0 && z<3 && array[x][y][z] != -1); 
	} 
	//This method is used to check whether indexes are inside the border or not
	public static boolean isSafeBool( int x, int y,int z,int border) 
	{ 
		N=border; 
		return (x >= 0 && x < N && y >= 0 && y < N && z>=0 && z<3); 
	}
	// This function is recursion part and takes an integer array,an boolean array, and
	// x,y,z values for arrays and  an integer for border
	public static void recursionForQuantization(int[][][] arr,boolean [][][] marked,int range, int i, int j, int k,int border) {
		if(isSafe(arr,i,j,k,border) && isSafeBool(i,j,k,border)) {
			if(isSafe(arr,i+1,j,k,border) && isSafeBool(i+1,j,k,border)) {
				if(Math.abs(arr[i][j][k]-arr[i+1][j][k])<=range && marked[i+1][j][k]==false) {		// Enter for x+1 part
					arr[i+1][j][k]=arr[i][j][k];										//If neighbor's value is inside the range change it
					marked[i+1][j][k]=true;												// Change boolean array's index to true 
					recursionForQuantization(arr,marked,range,i+1,j,k,border);
				}
			}
			if(isSafe(arr,i-1,j,k,border) && isSafeBool(i-1,j,k,border)) {		
				if(Math.abs(arr[i][j][k]-arr[i-1][j][k])<=range && marked[i-1][j][k]==false) {		// Enter for x-1 part
					arr[i-1][j][k]=arr[i][j][k];										//If neighbor's value is inside the range change it
					marked[i-1][j][k]=true;												// Change boolean array's index to true
					recursionForQuantization(arr,marked,range,i-1,j,k,border);
				}
			}
			if(isSafe(arr,i,j+1,k,border) && isSafeBool(i,j+1,k,border)) {
				if(Math.abs(arr[i][j][k]-arr[i][j+1][k])<=range && marked[i][j+1][k]==false) {		// Enter for y+1 part
					arr[i][j+1][k]=arr[i][j][k];										//If neighbor's value is inside the range change it
					marked[i][j+1][k]=true;												// Change boolean array's index to true
					recursionForQuantization(arr,marked,range,i,j+1,k,border);
				}
			}
			if(isSafe(arr,i,j-1,k,border) && isSafeBool(i,j-1,k,border)) {
				if(Math.abs(arr[i][j][k]-arr[i][j-1][k])<=range && marked[i][j-1][k]==false) {		// Enter for y-1 part
					arr[i][j-1][k]=arr[i][j][k];										//If neighbor's value is inside the range change it
					marked[i][j-1][k]=true;												// Change boolean array's index to true
					recursionForQuantization(arr,marked,range,i,j-1,k,border);
				}
			}
			if(isSafe(arr,i,j,k+1,border) && isSafeBool(i,j,k+1,border)) {
				if(Math.abs(arr[i][j][k]-arr[i][j][k+1])<=range && marked[i][j][k+1]==false) {		// Enter for k+1 part
					arr[i][j][k+1]=arr[i][j][k];										//If neighbor's value is inside the range change it
					marked[i][j][k+1]=true;												// Change boolean array's index to true
					recursionForQuantization(arr,marked,range,i,j,k+1,border);
				}
			}
			if(isSafe(arr,i,j,k-1,border) && isSafeBool(i,j,k-1,border)) {
				if(Math.abs(arr[i][j][k]-arr[i][j][k-1])<=range && marked[i][j][k-1]==false) {		// Enter for k-1 part
					arr[i][j][k-1]=arr[i][j][k];										//If neighbor's value is inside the range change it 
					marked[i][j][k-1]=true;												// Change boolean array's index to true
					recursionForQuantization(arr,marked,range,i,j,k-1,border);
				}
			}
		}
	}
}
