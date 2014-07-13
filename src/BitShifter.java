import java.util.Scanner;


public class BitShifter {
	static int startInt;
	static int a;
	static int b;
	static int c;
	static int d;
	static int finalInt;
	static Scanner reader = new Scanner(System.in);
	public static void main(String[] args)
	{
		getInt();
		dissassembleInt();
		reassembleInt();
		printInt();
	}
	public static void getInt()
	{
		System.out.println("Enter a 32 bit number");
		startInt = reader.nextInt();
		reader.close();
	}
	public static void dissassembleInt()
	{
		a = (startInt >> 24) & 255;
		b = (startInt >> 16) & 255;
		c = (startInt >> 8) & 255;
		d = startInt & 255;
	}
	public static void reassembleInt()
	{
		finalInt = (d << 24) | (c << 16) | (b << 8) | a;
	}
	public static void printInt()
	{
		System.out.println(finalInt);
	}
}
