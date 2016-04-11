package pdi;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import java.io.*;

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
		/*File f=Main.getFile(),f2=Main.getFile();
		Imagen ima=new Imagen(f);
		Imagen ima2=new Imagen(f2);
		BufferedImage bn=(BufferedImage)ima.aBN(0.5f);
		BufferedImage bn2=(BufferedImage)ima2.aBN(0.5f);
		
		double fi1,fi2,fi3,fi4,fi5;
		float[] centro=Imagen.getCentroide(bn2, Imagen.FONDO_BLANCO);
		fi1=Imagen.F1(bn, Imagen.FONDO_BLANCO, centro);
		fi2=Imagen.F2(bn, Imagen.FONDO_BLANCO, centro);
		fi3=Imagen.F3(bn, Imagen.FONDO_BLANCO, centro);
		fi4=Imagen.F4(bn, Imagen.FONDO_BLANCO, centro);
		fi5=Imagen.F5(bn, Imagen.FONDO_BLANCO, centro);
		
		double fi12,fi22,fi32,fi42,fi52;
		float[] centro2=Imagen.getCentroide(bn, Imagen.FONDO_BLANCO);
		fi12=Imagen.F1(bn2, Imagen.FONDO_BLANCO, centro2);
		fi22=Imagen.F2(bn2, Imagen.FONDO_BLANCO, centro2);
		fi32=Imagen.F3(bn2, Imagen.FONDO_BLANCO, centro2);
		fi42=Imagen.F4(bn2, Imagen.FONDO_BLANCO, centro2);
		fi52=Imagen.F5(bn2, Imagen.FONDO_BLANCO, centro2);
		
		System.out.println(fi1);
		System.out.println(fi2);
		System.out.println(fi3);
		System.out.println(fi4);
		System.out.println(fi5);
		System.out.println("************");
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
		*/
		
		File f=getFile();
		Imagen ima=new Imagen(f);
		BufferedImage bn=ima.filtrarBN();
		JLabel l=new JLabel(new ImageIcon(bn));
		JOptionPane.showMessageDialog(null,l);
	}

}
