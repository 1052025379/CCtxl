package com.oracle.messenger.control;

import java.applet.AudioClip;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.SimplisticSoftBorderPainter;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.ClassicGradientPainter;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;
import org.jvnet.substance.theme.SubstanceBottleGreenTheme;
import org.jvnet.substance.title.ArcHeaderPainter;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import com.oracle.messenger.view.MainFrame;
import com.sun.awt.AWTUtilities;
/**
 * ���ڰ����࣬�ṩһЩ���ڴ��ڵ���ز�������
 * @author TengSir
 *
 */
public class FrameUtil {
	private static AudioClip  player,msgPlayer;
	private static File  sound,message;
	static {
		sound=new File("resource/music/shake.au");
		message=new File("resource/music/folder.au");
		try {
			player=JApplet.newAudioClip(sound.toURL());
			msgPlayer=JApplet.newAudioClip(message.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	public static void messageNotice(){
		msgPlayer.play();
	}
	public static void   shakeWindow(final JFrame f)
	{
		player.play();
		new  Thread(){
			@Override
			public void run() {
				int  nowXPoint=f.getX();
				int nowYPoint=f.getY();
				for(int n=0;n<=20;n++)
				{
					f.setLocation(nowXPoint-2, nowYPoint);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					f.setLocation(nowXPoint, nowYPoint-2);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					f.setLocation(nowXPoint+2, nowYPoint);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					f.setLocation(nowXPoint, nowYPoint+2);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				f.setLocation(nowXPoint, nowYPoint);
			}
		}.start();
		
	}
	public static String selectImage(JFrame parent){
		JFileChooser  f=new JFileChooser();
		f.setFileSelectionMode(JFileChooser.FILES_ONLY);
		f.setDialogTitle("ѡ��ͼƬ");
		f.setControlButtonsAreShown(true);
	/*	f.setAcceptAllFileFilterUsed(true);
		f.setFileHidingEnabled(true);
		f.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return null;
			}
			
			@Override
			public boolean accept(File f) {
				String[]  exts={".jpg",".png",".gif",".gif",".jpeg"};
				boolean isImage=false;
				for (String ext:exts) {
					if(f.getName().endsWith(ext))isImage=true;
				}
				return isImage;
			}
		});*/
		int n=f.showOpenDialog(parent);
		if(n==0)
		{
			if(f.getSelectedFile().isFile())
				return  f.getSelectedFile().getAbsolutePath();
			else
				return null;
		}else
		{
			return null;
		}
	}
	/**
	 * �����µ�lookandfeelЧ��
	 * @param f
	 */
	public static void  decorateFrame(JFrame f){
		try {
			// �������
			UIManager
					.setLookAndFeel(new SubstanceCremeLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			// ��������
			SubstanceLookAndFeel
					.setCurrentTheme(new SubstanceBottleGreenTheme());
			//SubstanceUltramarineTheme  //����
			//SubstanceTerracottaTheme     //ǳ�ֻ�
			//SubstanceBottleGreenTheme   //����
			//SubstanceAquaTheme       //ǳ��
			//SubstanceBarbyPinkTheme   //ǳ�Ϸ�
			//SubstanceBrownTheme   //ǳ����
			
			// ���ð�ť���
			SubstanceLookAndFeel
					.setCurrentButtonShaper(new StandardButtonShaper());
			//StandardButtonShaper  //��׼���
			//ClassicButtonShaper    //�������
			// ����ˮӡ
			SubstanceLookAndFeel
					.setCurrentWatermark(new SubstanceBubblesWatermark());
			//SubstanceStripeWatermark  //����ˮӡ
			//SubstanceStripeWatermark  //������ˮӡ
			//SubstanceBubblesWatermark   //����ˮӡ
			//SubstanceWoodWatermark    //ľ��ˮӡ
			//SubstancePlanktonWatermark  //����ˮӡ
			//SubstanceCrosshatchWatermark //������Ӱˮӡ
			//SubstanceCopperplateEngravingWatermark  //���ͭ��ˮӡ
			//SubstanceFabricWatermark  //֯��ˮӡ
			//SubstanceGenericNoiseWatermark   //����ˮӡ
			//SubstanceImageWatermark   //��Ƭˮӡ SubstanceImageWatermark(new FileInputStream("resource/images/test.jpg"))
			//SubstanceKatakanaWatermark   //����ˮӡ
			//SubstanceLatchWatermark    //�ܼ��־�֢ˮӡ
			//SubstanceMagneticFieldWatermark  //�ų�ˮӡ
			//SubstanceMarbleVeinWatermark
			//SubstanceMetalWallWatermark
			//SubstanceMazeWatermark
			//SubstanceMosaicWatermark
			
			// ���ñ߿�
			SubstanceLookAndFeel
					.setCurrentBorderPainter(new SimplisticSoftBorderPainter());
			//StandardBorderPainter
			//ClassicBorderPainter
			//FlatBorderPainter
			//GlassBorderPainter
			//SimplisticSoftBorderPainter();
			
			// ���ý�����Ⱦ
			SubstanceLookAndFeel
					.setCurrentGradientPainter(new ClassicGradientPainter());
			//StandardGradientPainter
			//ClassicGradientPainter
			//GlassGradientPainter
			//SubduedGradientPainter
			
			// ���ñ���
			SubstanceLookAndFeel
					.setCurrentTitlePainter(new ArcHeaderPainter(true, true));
			//MatteHeaderPainter
			//ArcHeaderPainter
			//BrushedMetalHeaderPainter
			//MarbleNoiseHeaderPainter
			f.setVisible(true);
			AWTUtilities.setWindowShape(f,new RoundRectangle2D.Double(0,0,MainFrame.width, MainFrame.height,20,20));
			AWTUtilities.setWindowOpacity(f, 0.9f);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
	}
}
