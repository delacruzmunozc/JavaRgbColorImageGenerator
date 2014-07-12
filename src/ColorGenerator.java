import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
public class ColorGenerator {
	static int[] colors = new int[16777217];
	static int xAmount;
	static int yAmount;
	static Random generator = new Random();
	static int x;
	static int y;
	static int z;
	static int blocks = 2;
	static int method = 2;
	public static BufferedImage bufferImage;
	public static void main(String[] args)
	{
		recieveAspectRatio();
		createImage();
		switch (method)
		{
		case 1:
			fillPixels();
			break;
		}
		fillRandomPixels();
		saveImage();
	}
	public static void recieveAspectRatio()
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the width");
		xAmount = reader.nextInt();
		System.out.println("Enter the height");
		yAmount = reader.nextInt();
		System.out.println("How would you like to fill the image?");
		System.out.println("<1> Fill by pattern");
		System.out.println("<2> Fill randomly");
		method = reader.nextInt();
		reader.close();
	}
	public static void createImage()
	{
		bufferImage = new BufferedImage(xAmount, yAmount, BufferedImage.TYPE_INT_RGB);
		System.out.println("Image Created");
	}
	public static void fillPixels()
	{
		for (int i = 0; i < 16777216; i++)
		{
			colors[i] = i;
		}
		colors[16777216] = 0;
		for (int y = 0; y < yAmount; y++)
		{
			for (int x = 0; x < xAmount; x++)
			{
				int color = colors[x+y*xAmount];
				bufferImage.setRGB(x, y, color);
			}
		}
	}
	public static void fillRandomPixels()
	{
		System.out.println("Filling Image");
		z = -16777213/2;
		switch(blocks)
		{
		case 1:
			while (z < 16777215)
			{
				x = generator.nextInt(xAmount);
				y = generator.nextInt(yAmount);
				if (bufferImage.getRGB(x, y) == -16777216)
				{
					bufferImage.setRGB(x, y, colors[z]);
					z++;
					System.out.println(z);
				}
			}
			break;
		case 2:
			while (z < 16777213/2)
			{
				x = generator.nextInt(xAmount/2);
				y = generator.nextInt(yAmount/2);
				if (bufferImage.getRGB(x, y) == -16777216)
				{
					bufferImage.setRGB(2*x, 2*y, z/2);
					bufferImage.setRGB(2*x+1, 2*y, z/2+1);
					bufferImage.setRGB(2*x, 2*y+1, z/2+2);
					bufferImage.setRGB(2*x+1, 2*y+1, z/2+3);
					//System.out.println(z);
					//if ((bufferImage.getRGB(2*x, 2*y) == -16777216
					//	|| bufferImage.getRGB(2*x+1, 2*y) == -16777216
					//	|| bufferImage.getRGB(2*x, 2*y+1) == -16777216
					//	|| bufferImage.getRGB(2*x+1, 2*y+1) == -16777216)
					//	&& z != -7)
					//{
					//	System.out.println("For some reason, the pixels didn't write.");
					//	System.exit(1);
					//}
					z++;
				}
			}
		break;
		case 4:
			while (z < 16777215)
			{
				x = generator.nextInt(xAmount/4);
				y = generator.nextInt(yAmount/4);
				if (bufferImage.getRGB(x, y) == -16777216)
				{
					bufferImage.setRGB(4*x, 4*y, colors[z]);
					bufferImage.setRGB(4*x+1, 4*y, colors[z+1]);
					bufferImage.setRGB(4*x+2, 4*y, colors[z+2]);
					bufferImage.setRGB(4*x+3, 4*y, colors[z+3]);
					bufferImage.setRGB(4*x, 4*y+1, colors[z+4]);
					bufferImage.setRGB(4*x+1, 4*y+1, colors[z+5]);
					bufferImage.setRGB(4*x+2, 4*y+1, colors[z+6]);
					bufferImage.setRGB(4*x+3, 4*y+1, colors[z+7]);
					bufferImage.setRGB(4*x, 4*y+2, colors[z+8]);
					bufferImage.setRGB(4*x+1, 4*y+2, colors[z+9]);
					bufferImage.setRGB(4*x+2, 4*y+2, colors[z+10]);
					bufferImage.setRGB(4*x+3, 4*y+2, colors[z+11]);
					bufferImage.setRGB(4*x, 4*y+3, colors[z+12]);
					bufferImage.setRGB(4*x+1, 4*y+3, colors[z+13]);
					bufferImage.setRGB(4*x+2, 4*y+3, colors[z+14]);
					bufferImage.setRGB(4*x+3, 4*y+3, colors[z+15]);
					z = z + 16;
					System.out.println(z);
				}
			}
		break;
		}
	}
	public static void saveImage()
	{
		try
		{
			System.out.println("Saving");
			File outputfile = new File("RandomRGB4x4block.png");
			ImageIO.write(bufferImage, "png", outputfile);
			System.out.println("Saved");
		}
		catch (IOException e)
		{
			
		}
	}
}
