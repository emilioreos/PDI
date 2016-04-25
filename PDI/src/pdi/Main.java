package pdi;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static File getFile(){
		JFileChooser fs=new JFileChooser();
		javax.swing.filechooser.FileFilter ff=new javax.swing.filechooser.FileFilter() {
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean accept(File p) {
				// TODO Auto-generated method stub
				boolean jpg=p.getName().endsWith(".jpg"),bmp=p.getName().endsWith(".bmp"),gif=p.getName().endsWith(".gif");
				boolean png=p.getName().endsWith(".png");
				if(p.isDirectory()||jpg||bmp||gif||png){
					return true;
				}
				return false;
			}
		};
		fs.setFileFilter(ff);
		int cosa=fs.showOpenDialog(null);
		if(cosa==JFileChooser.APPROVE_OPTION){
			return fs.getSelectedFile();
		}
		return null;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//for(int i=0;i<Cadenas.letras.length;i++){
		/*File f=Main.getFile(),f2=Main.getFile();
		Imagen ima=new Imagen(f);
		Imagen ima2=new Imagen(f2);
		BufferedImage bn=(BufferedImage)ima.aBN(0.5f);
		BufferedImage bn2=(BufferedImage)ima2.aBN(0.5f);
		
		double fi1,fi2,fi3,fi4,fi5;
		float[] centro=Imagen.getCentroide(bn, Imagen.FONDO_BLANCO);
		fi1=Imagen.F1(bn, Imagen.FONDO_BLANCO, centro);
		fi2=Imagen.F2(bn, Imagen.FONDO_BLANCO, centro);
		fi3=Imagen.F3(bn, Imagen.FONDO_BLANCO, centro);
		fi4=Imagen.F4(bn, Imagen.FONDO_BLANCO, centro);
		fi5=Imagen.F5(bn, Imagen.FONDO_BLANCO, centro);
		
		double fi12,fi22,fi32,fi42,fi52;
		float[] centro2=Imagen.getCentroide(bn2, Imagen.FONDO_BLANCO);
		fi12=Imagen.F1(bn2, Imagen.FONDO_BLANCO, centro2);
		fi22=Imagen.F2(bn2, Imagen.FONDO_BLANCO, centro2);
		fi32=Imagen.F3(bn2, Imagen.FONDO_BLANCO, centro2);
		fi42=Imagen.F4(bn2, Imagen.FONDO_BLANCO, centro2);
		fi52=Imagen.F5(bn2, Imagen.FONDO_BLANCO, centro2);
		
		System.out.println(Cadenas.momentos[0][4]);
		System.out.println(fi1);
		System.out.println(fi2);
		System.out.println(fi3);
		System.out.println(fi4);
		System.out.println(fi5);
		System.out.println("************");
		
		JLabel l=new JLabel(new ImageIcon(bn));
		JOptionPane.showMessageDialog(null,l);*/
		//}
		/*
		System.out.println(fi12);
		System.out.println(fi22);
		System.out.println(fi32);
		System.out.println(fi42);
		System.out.println(fi52);
		
		
		double c1=(fi1-fi12)/fi1,c2=(fi2-fi22)/fi2,c3=(fi3-fi32)/fi3,c4=(fi4-fi42)/fi4,c5=(fi5-fi52)/fi5;
		
		System.out.println("************");
		System.out.println(""+c1*100+"%");
		System.out.println(""+c2*100+"%");
		System.out.println(""+c3*100+"%");
		System.out.println(""+c4*100+"%");
		System.out.println(""+c5*100+"%");
		
		Imagen im=new Imagen(new File(Cadenas.letras[15]));
		BufferedImage in=(BufferedImage)im.aBN(0.4f);
		JLabel ll=new JLabel(new ImageIcon(in));
		JOptionPane.showMessageDialog(null,ll);
		*/
		while(true){
			File f=getFile();
			Imagen ima=new Imagen(f);
			BufferedImage bn=ima.filtrarBN();
			
			JLabel l=new JLabel(new ImageIcon(bn));
			JOptionPane.showMessageDialog(null,l);
			/*
			LinkedList<Integer> lis=Imagen.finales(bn);
			BufferedImage[] letras=new BufferedImage[lis.size()/2];
			for(int i=0;i<letras.length;i++){
				letras[i]=Imagen.recortar(bn, lis.removeFirst(), lis.removeFirst());
			}
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<letras.length;i++){
				int cuenta=0,cosa=0;
				for(int j=0;j<Cadenas.Strings.length;j++){
					Imagen im=new Imagen(new File(Cadenas.letras[j]));
					BufferedImage im1=(BufferedImage)im.aBN(0.4f);
					
				}
			}*/
			
			
			
			System.out.println(OCR(getLetras(bn)));
		}
	}
	public static BufferedImage[] getLetras(BufferedImage bn){
		LinkedList<Integer> lis=Imagen.finales(bn);
		BufferedImage[] letras=new BufferedImage[lis.size()/2];
		for(int i=0;i<letras.length;i++){
			letras[i]=Imagen.recortar(bn, lis.removeFirst(), lis.removeFirst());
		}
		return letras;
	}
	public static String OCR(BufferedImage[] letras){
		double[] momentos=new double[5];
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<letras.length;i++){
			float[] centro=Imagen.getCentroide(letras[i], Imagen.FONDO_BLANCO);
			momentos[0]=Imagen.F1(letras[i], Imagen.FONDO_BLANCO, centro);
			momentos[1]=Imagen.F2(letras[i],Imagen.FONDO_BLANCO, centro);
			momentos[2]=Imagen.F3(letras[i], Imagen.FONDO_BLANCO, centro);
			momentos[3]=Imagen.F4(letras[i], Imagen.FONDO_BLANCO, centro);
			momentos[4]=Imagen.F5(letras[i], Imagen.FONDO_BLANCO, centro);
			LinkedList<Integer> l = agregarCaracter(momentos, sb,0);
			//LinkedList<Integer> l2 = agregarCaracter(momentos, sb,1);
			//LinkedList<Integer> l3 = agregarCaracter(momentos, sb,2);
			System.out.println(l);
			//System.out.println(l2);
			//System.out.println(l3);
			/*JLabel l=new JLabel(new ImageIcon(letras[i]));
			JOptionPane.showMessageDialog(null, l);*/
		}
		return sb.toString();
	}
	
	public static int directo(BufferedImage im1,BufferedImage im2){
		int contador = 0;
		int ancho=(im1.getWidth()<im2.getWidth())?im1.getWidth():im2.getWidth(),alto=(im1.getHeight()<im2.getHeight())?im1.getHeight():im2.getHeight();
		for(int i=0;i<ancho;i++){
			for(int j=0;j<alto;j++){
				if(im1.getRGB(i, j)==im2.getRGB(i, j)&&im1.getRGB(i, j)!=0xffffffff){
					contador++;
				}
			}
		}
		return contador;
	}
	
	public static LinkedList<Integer> agregarCaracter(double[] momentos,StringBuilder cadena,int momento){
		/*int pos=-1;
		LinkedList<Integer> aceptados=new LinkedList<Integer>();
		double e=8000;*/
		/*for(int i=0;i<Cadenas.momentos.length;i++){
			double error=0;//(momentos[momento]-Cadenas.momentos[i][momento])/momentos[momento];
			for(int j=0;j<momentos.length;j++){
				error+=Math.abs((momentos[j]-Cadenas.momentos[i][j])/momentos[j]);
			}
			if(Math.abs(error)<e&&(momentos[momento]>Cadenas.momentos[i][momento]*(1-((momento+1)*0.1))&&momentos[momento]<Cadenas.momentos[i][momento]*(1+((momento+1)*0.1)))){
				e=Math.abs(error);
				pos=i;
				aceptados.add(i);
			}
			//System.out.println(error);
		}
		if(pos>-1){
			cadena.append(Cadenas.Strings[pos]);
		}*/
		LinkedList<Integer> l=new LinkedList<Integer>();
		LinkedList<Integer> l2=new LinkedList<Integer>();
		LinkedList<Integer> l3=new LinkedList<Integer>();
		LinkedList<Integer> l4=new LinkedList<Integer>();
		LinkedList<Integer> l5=new LinkedList<Integer>();
		
		
		for(int i=0;i<Cadenas.momentos.length;i++){
			if(Math.abs((momentos[0]-Cadenas.momentos[i][0])/momentos[0])<0.2){
				l.add(i);
			}
		}
		for(int i=0;i<l.size();i++){
			if(Math.abs((momentos[1]-Cadenas.momentos[l.get(i)][1])/momentos[1])<0.25){
				l2.add(l.get(i));
			}
		}
		for(int i=0;i<l.size();i++){
			if(Math.abs((momentos[2]-Cadenas.momentos[l.get(i)][2])/momentos[2])<0.30){
				l3.add(l.get(i));
			}
		}
		for(int i=0;i<l.size();i++){
			if(Math.abs((momentos[3]-Cadenas.momentos[l.get(i)][3])/momentos[3])<0.4){
				l4.add(l.get(i));
			}
		}
		for(int i=0;i<l.size();i++){
			if(Math.abs((momentos[4]-Cadenas.momentos[l.get(i)][4])/momentos[4])<0.6){
				l5.add(l.get(i));
			}
		}
		
		int pos=-1;
		int votos=1;
		if(l.contains(33)){
			cadena.append(Cadenas.Strings[33]);
			return l;
		}
		for(int i=0;i<l.size();i++){
			int votos2=0;
			if(l2.contains(l.get(i))){
				votos2++;
			}
			if(l3.contains(l.get(i))){
				votos2++;
			}
			if(l4.contains(l.get(i))){
				votos2++;
			}
			if(l5.contains(l.get(i))){
				votos2++;
			}
			if(votos2>=votos){
				votos=votos2;
				pos=i;
				
			}
		}
		
		
		if(pos>-1){
			cadena.append(Cadenas.Strings[l.get(pos)]);
		}
		return l;
	}

}
