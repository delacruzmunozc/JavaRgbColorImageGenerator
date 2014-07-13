import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
public class ColorGenerator {
	static int[] red = new int[16777216];
	static int[] green = new int[16777216];
	static int[] blue = new int[16777216];
	static int r;
	static int g;
	static int b;
	static int xAmount;
	static int yAmount;
	static Random generator = new Random();
	static int x;
	static int y;
	static int z;
	static int blocks;
	static int method;
	static int[] color = new int[16777216];
	public static BufferedImage bufferImage;
	static int blackPixelCounter = 0;
	static String fileName;
	static Scanner reader = new Scanner(System.in);
	static String channelOrder;
	static int[] digits = new int[500];
	public static void main(String[] args)
	{
		recieveInput();
		createImage();
		switch (method)
		{
		case 1:
			fillPixels();
			break;
		case 2:
			fillRandomPixels();
			countBlackPixels();
			break;
		}
		saveImage();
	}
	public static void recieveInput()
	{
		System.out.println("Enter the width");
		xAmount = reader.nextInt();
		System.out.println("Enter the height");
		yAmount = reader.nextInt();
		System.out.println("How would you like to fill the image?");
		System.out.println("<1> Fill by pattern");
		System.out.println("<2> Fill randomly");
		method = reader.nextInt();
		if (method == 2)
		{
			System.out.println("How large on each side do you want the random blocks to be?");
			blocks = reader.nextInt();
		}
		System.out.println("What would you like to name the file?");
		fileName = reader.next();
		System.out.println("What order would you like the color channels to be in?");
		channelOrder = reader.next();
		reader.close();
	}
	public static void createImage()
	{
		bufferImage = new BufferedImage(xAmount, yAmount, BufferedImage.TYPE_INT_ARGB);
		System.out.println("Image Created");
	}
	public static void fillPixels()
	{
		int[] assembledColors = new int[16777216];
		for (int v = 0; v < 16777216; v++)
		{
			assembledColors[v] = (255 << 24);
			for (int j = 2; j >= 0; j--)
			{
				red[v%256] = (v >> 16) & 255;
				green[v%256] = (v >> 8) & 255;
				blue[v%256] = v & 255;
				if (channelOrder.charAt(2-j) == 'R')
				{
					assembledColors[v] = assembledColors[v] + (red[v%256] << (j*8));
				}
				if (channelOrder.charAt(2-j) == 'G')
				{
					assembledColors[v] = assembledColors[v] + (green[v%256] << (j*8));
				}
				if (channelOrder.charAt(2-j) == 'B')
				{
					assembledColors[v] = assembledColors[v] + (blue[v%256] << (j*8));
				}
			}
		}
		for (int x = 0; x < xAmount; x++)
		{
			for (int y = 0; y < yAmount; y++)
			{
				 bufferImage.setRGB(x, y, assembledColors[x+y*xAmount]);
			}
		}
	}
	public static void fillRandomPixels()
	{
		System.out.println("Filling Image");
		z = -16777216;
		while (z < -1)
		{
			x = generator.nextInt(xAmount/blocks);
			y = generator.nextInt(yAmount/blocks);
			if (bufferImage.getRGB(blocks*x, blocks*y) == 0)
			{
				for (int j = 0; j < blocks; j++)
				{
					for (int i = 0; i < blocks; i++)
					{
						bufferImage.setRGB(blocks*x+i, blocks*y+j, z+i+j*blocks);
					}
				}
				z = z + blocks * blocks;
				System.out.println(z);
			}
		}
	}
	public static void countBlackPixels()
	{
		blackPixelCounter = 0;
		for (int y = 0; y < yAmount; y++)
		{
			for (int x = 0; x < xAmount; x++)
			{
				if (bufferImage.getRGB(x, y) == 0)
				{
					blackPixelCounter++;
				}
			}
		}
		System.out.println(blackPixelCounter);
	}
	public static void saveImage()
	{
		try
		{
			System.out.println("Saving");
			File outputfile = new File(fileName + ".png");
			ImageIO.write(bufferImage, "png", outputfile);
			System.out.println("Saved");
		}
		catch (IOException e)
		{
			
		}
	}
}
