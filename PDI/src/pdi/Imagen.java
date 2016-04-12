package pdi;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

public class Imagen {
	public static final int FONDO_BLANCO=0xff000000;
	public static final int FONDO_NEGRO=0xffffffff;
	public static final byte X=0;
	public static final byte Y=1;
	public static final byte N=2;
	
	private BufferedImage imagen;
	
	public Imagen(BufferedImage im){
		imagen=im;
	}
	public Imagen(File imagen) throws IOException{
		this.imagen=ImageIO.read(imagen);
	}
	public Image aGris(){
		int alto=imagen.getHeight(),ancho=imagen.getWidth();
		BufferedImage x=new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<ancho;i++){
			for(int j=0;j<alto;j++){
				int color=imagen.getRGB(i, j);
				int R,G,B;
				B=color&255;
				G=(color>>8)&255;
				R=(color>>16)&255;
				color=(int)(R*0.3+G*0.59+B*0.11);
				x.setRGB(i, j, (color<<16)|(color<<8)|color);
			}
		}
		return x;
	}
	public Image aBN(float nivel){
		int alto=imagen.getHeight(),ancho=imagen.getWidth();
		BufferedImage x=new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<ancho;i++){
			for(int j=0;j<alto;j++){
				int color=imagen.getRGB(i, j);
				int R,G,B;
				B=color&255;
				G=(color>>8)&255;
				R=(color>>16)&255;
				color=(int)(R*0.3+G*0.59+B*0.11);
				color=(color>(255*nivel))?16777215:0;
				x.setRGB(i, j, color);
			}
		}
		return x;
	}
	
	/*
	 * H=0
	 * S=1
	 * V=2
	 * */
	public double[] RGBaHSB(short R,short G, short B){
		double[] HSB=new double[3];
		short M=(short)Math.max(R, Math.max(G, B)),m=(short)Math.min(R, Math.min(G, B)),C;
		C=(short)(M-m);
		if(C==0){
			HSB[0]=0;
		}else if(M==R){
			HSB[0]=60*(((G-B)/(C*1.0))%6);
		}else if(M==G){
			HSB[0]=60*(((B-R)/(C*1.0))+2);
		}else{
			HSB[0]=60*(((R-G)/(C*1.0))+4);
		}
		HSB[1]=(C==0)?0:(C*1.0)/M;
		HSB[2]=M/255.0;
		
		if(HSB[0]<0){
			HSB[0]=360+HSB[0];
		}
		return HSB;
	}
	
	public BufferedImage filtrarBN(){
		int alto=imagen.getHeight(),ancho=imagen.getWidth();
		int nuevoalto=(int)(alto-(alto*0.25+(alto-alto*0.69)));
		BufferedImage x=new BufferedImage(ancho, nuevoalto, BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<ancho;i++){
			for(int j=0;j<nuevoalto;j++){
				int color=imagen.getRGB(i, j+(int)(alto*0.25));
				int R,G,B;
				B=color&255;
				G=(color>>8)&255;
				R=(color>>16)&255;
				double[] hsb=RGBaHSB((short)R, (short)G, (short)B);
				if(hsb[2]<0.4||(hsb[2]>0.2&&hsb[2]<0.6&&hsb[1]<0.1)){
					x.setRGB(i, j, 0);
				}else{
					x.setRGB(i, j, 0xffffffff);
				}
			}
		}
		return x;
	}
	
	
	public static float[] getCentroide(BufferedImage bn,int relebante){
		int alto=bn.getHeight(),ancho=bn.getWidth();
		long contador=0,x=0,y=0;
		float[] centro=new float[3];
		for(int i=0;i<ancho;i++){
			for(int j=0;j<alto;j++){
				int color=bn.getRGB(i, j);
				if(color==relebante){
					contador++;
					x+=i;
					y+=j;
				}
			}
		}
		centro[X]=x*1.0f/contador;
		centro[Y]=y*1.0f/contador;
		centro[N]=contador;
		return centro;
	}
	public static double Muv(BufferedImage bn,int relebante,float u,float v,float[] centro){
		double m=0;
		if(u==0&&v==0){
			return centro[N];
		}
		int alto=bn.getHeight(),ancho=bn.getWidth();
		for(int i=0;i<ancho;i++){
			for(int j=0;j<alto;j++){
				int color=bn.getRGB(i, j);
				if(color==relebante){
					m+=(Math.pow(i-centro[X], u)*Math.pow(j-centro[Y], v));
				}
			}
		}
		return m;
	}
	public static double Vij(BufferedImage bn,int relebante,float i,float j,float[] centro){
		if(i+j<2){
			return Double.NaN;
		}
		double v,k=((i+j)/2)+1;
		v=Muv(bn,relebante,i,j,centro)/Math.pow(centro[N], k);
		return v;
	}
	public static double F1(BufferedImage bn,int relebante,float[] centro){
		double f1;
		f1=Vij(bn, relebante, 2, 0, centro)+Vij(bn,relebante,0,2,centro);
		return f1;
	}
	public static double F2(BufferedImage bn,int relebante,float[] centro){
		double f2;
		f2=Math.pow(Vij(bn, relebante, 2, 0, centro)-Vij(bn,relebante,0,2,centro), 2)
				+4*Math.pow(Vij(bn,relebante,1,1,centro), 2);
		return f2;
	}
	public static double F3(BufferedImage bn,int relebante,float[] centro){
		double f3;
		f3=Math.pow(Vij(bn, relebante, 3, 0, centro)-3*Vij(bn,relebante,1,2,centro), 2)
				+Math.pow(3*Vij(bn, relebante, 2, 1, centro)-Vij(bn,relebante,0,3,centro), 2);
		return f3;
	}
	public static double F4(BufferedImage bn,int relebante,float[] centro){
		double f4;
		f4=Math.pow(Vij(bn, relebante, 3, 0, centro)+Vij(bn,relebante,1,2,centro), 2)
				+Math.pow(Vij(bn, relebante, 2, 1, centro)+Vij(bn,relebante,0,3,centro), 2);
		return f4;
	}
	public static double F5(BufferedImage bn,int relebante,float[] centro){
		double f5;
		f5=(Vij(bn, relebante, 3, 0, centro)-3*Vij(bn,relebante,1,2,centro))*(Vij(bn, relebante, 3, 0, centro)+Vij(bn,relebante,1,2,centro))
				*(Math.pow(Vij(bn, relebante, 3, 0, centro)+Vij(bn,relebante,1,2,centro), 2)-3*Math.pow(Vij(bn, relebante, 2, 1, centro)+Vij(bn,relebante,0,3,centro), 2))
				+(3*Vij(bn, relebante, 2, 1, centro)-Vij(bn,relebante,0,3,centro))*(Vij(bn, relebante, 2, 1, centro)+Vij(bn,relebante,0,3,centro))
				*(3*Math.pow(Vij(bn, relebante, 3, 0, centro)+Vij(bn,relebante,1,2,centro), 2)-Math.pow(Vij(bn, relebante, 2, 1, centro)+Vij(bn,relebante,0,3,centro), 2));
		return f5;
	}
}
