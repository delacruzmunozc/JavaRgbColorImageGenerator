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
		if (method == 1)
		{
			System.out.println("What order would you like the color channels to be in?");
			channelOrder = reader.next();
		}
		else
		{
			System.out.println("How large on each side do you want the random blocks to be?");
			blocks = reader.nextInt();
		}
		System.out.println("What would you like to name the file?");
		fileName = reader.next();
		reader.close();
	}
	public static void createImage()
	{
		bufferImage = new BufferedImage(xAmount, yAmount, BufferedImage.TYPE_INT_ARGB);
		System.out.println("Image Created");
	}
	public static void fillPixels()
	{
		for (int i = 0; i < 16777216; i++)
		{
			red[i] = i%256;
			green[i] = i%256;
			blue[i] = i%256;
			if (channelOrder == "RGB");
			{
				r = red[i/65536];
				g = green[i/256];
				b = blue[i];
				bufferImage.setRGB(i%xAmount, (i/yAmount)%yAmount, (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
			if (channelOrder == "GBR")
			{
				r = red[i];
				g = green[i/65536];
				b = blue[i/256];
				bufferImage.setRGB(i%xAmount, (i%4096)/4095, (0xFF << 24) | ((i%256)/65536 << 16) | ((i%256)/256 << 8) | i%256);
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
