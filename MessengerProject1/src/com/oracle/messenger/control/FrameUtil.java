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
 * 窗口帮助类，提供一些对于窗口的相关操作方法
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
		f.setDialogTitle("选择图片");
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
	 * 设置新的lookandfeel效果
	 * @param f
	 */
	public static void  decorateFrame(JFrame f){
		try {
			// 设置外观
			UIManager
					.setLookAndFeel(new SubstanceCremeLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			// 设置主题
			SubstanceLookAndFeel
					.setCurrentTheme(new SubstanceBottleGreenTheme());
			//SubstanceUltramarineTheme  //深紫
			//SubstanceTerracottaTheme     //浅褐黄
			//SubstanceBottleGreenTheme   //草绿
			//SubstanceAquaTheme       //浅蓝
			//SubstanceBarbyPinkTheme   //浅紫粉
			//SubstanceBrownTheme   //浅土黄
			
			// 设置按钮外观
			SubstanceLookAndFeel
					.setCurrentButtonShaper(new StandardButtonShaper());
			//StandardButtonShaper  //标准外观
			//ClassicButtonShaper    //经典外观
			// 设置水印
			SubstanceLookAndFeel
					.setCurrentWatermark(new SubstanceBubblesWatermark());
			//SubstanceStripeWatermark  //条纹水印
			//SubstanceStripeWatermark  //二进制水印
			//SubstanceBubblesWatermark   //气泡水印
			//SubstanceWoodWatermark    //木纹水印
			//SubstancePlanktonWatermark  //流体水印
			//SubstanceCrosshatchWatermark //交叉阴影水印
			//SubstanceCopperplateEngravingWatermark  //雕刻铜板水印
			//SubstanceFabricWatermark  //织布水印
			//SubstanceGenericNoiseWatermark   //豹纹水印
			//SubstanceImageWatermark   //照片水印 SubstanceImageWatermark(new FileInputStream("resource/images/test.jpg"))
			//SubstanceKatakanaWatermark   //日文水印
			//SubstanceLatchWatermark    //密集恐惧症水印
			//SubstanceMagneticFieldWatermark  //磁场水印
			//SubstanceMarbleVeinWatermark
			//SubstanceMetalWallWatermark
			//SubstanceMazeWatermark
			//SubstanceMosaicWatermark
			
			// 设置边框
			SubstanceLookAndFeel
					.setCurrentBorderPainter(new SimplisticSoftBorderPainter());
			//StandardBorderPainter
			//ClassicBorderPainter
			//FlatBorderPainter
			//GlassBorderPainter
			//SimplisticSoftBorderPainter();
			
			// 设置渐变渲染
			SubstanceLookAndFeel
					.setCurrentGradientPainter(new ClassicGradientPainter());
			//StandardGradientPainter
			//ClassicGradientPainter
			//GlassGradientPainter
			//SubduedGradientPainter
			
			// 设置标题
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
