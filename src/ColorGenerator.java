import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ColorGenerator {
	
	static int xAmount;
	static int yAmount;
	static int blocks;
	static int method;
	public static BufferedImage bufferImage;
	static int blackPixelCounter = 0;
	static String fileName;
	static Scanner reader = new Scanner(System.in);
	static String channelOrder;
	
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
				if (channelOrder.charAt(2-j) == 'R')
				{
					assembledColors[v] = assembledColors[v] + (((v >> 16) & 255) << (j*8));
				}
				if (channelOrder.charAt(2-j) == 'G')
				{
					assembledColors[v] = assembledColors[v] + (((v >> 8) & 255) << (j*8));
				}
				if (channelOrder.charAt(2-j) == 'B')
				{
					assembledColors[v] = assembledColors[v] + ((v & 255) << (j*8));
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
		int z = -16777216;
		Random generator = new Random();
		int[] assembledColors = new int[16777216];
		int count = 0;
		while (z < -1)
		{
			int x = generator.nextInt(xAmount/blocks);
			int y = generator.nextInt(yAmount/blocks);
			if (bufferImage.getRGB(blocks*x, blocks*y) == 0)
			{
				for (int j = 0; j < blocks; j++)
				{
					for (int i = 0; i < blocks; i++)
					{
						assembledColors[count+i+j*blocks] = (255 << 24);
						for (int h = 2; h >= 0; h--)
						{
							if (channelOrder.charAt(2-h) == 'R')
							{
								assembledColors[count+i+j*blocks] = assembledColors[count+i+j*blocks] + (((count+i+j*blocks >> 16) & 255) << (h*8));
							}
							if (channelOrder.charAt(2-h) == 'G')
							{
								assembledColors[count+i+j*blocks] = assembledColors[count+i+j*blocks] + (((count+i+j*blocks >> 8) & 255) << (h*8));
							}
							if (channelOrder.charAt(2-h) == 'B')
							{
								assembledColors[count+i+j*blocks] = assembledColors[count+i+j*blocks] + ((count+i+j*blocks & 255) << (h*8));
							}
						}
						bufferImage.setRGB(blocks*x+i, blocks*y+j, assembledColors[count+i+j*blocks]);
					}
				}
				z = z + blocks * blocks;
				count = count + blocks * blocks;
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
