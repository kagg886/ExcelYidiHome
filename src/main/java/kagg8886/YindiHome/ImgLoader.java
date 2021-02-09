package kagg8886.YindiHome;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import org.bytedeco.javacpp.RealSense.float2;
import org.bytedeco.javacpp.RealSense.intrinsics;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class ImgLoader {

	public static void main(String[] args) throws Exception {
		File file = new File("E:\\video.mp4");
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
		ff.start();
		long frameAll = ff.getLengthInTime();
		Frame frame = null;
		for (int i = 0; i < frameAll; i++) {
			frame = ff.grabFrame();
			File picFile = new File("E:\\img\\" + i + ".jpg");
			Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcImage = converter.getBufferedImage(frame);
            if (srcImage == null) {
				continue;
			}
            
            float rate = 0.1F;
            
            //int width = (int) (srcImage.getWidth() * rate);
            //int height = (int) (srcImage.getHeight() * rate);
            int width = 255;
            int height = 180;
            BufferedImage zipImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            zipImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
			ImageIO.write(zipImage, "jpg", picFile);
			System.out.println("已完成第:" + i + "/" + frameAll + "张图片的写入！");
		}
		ff.stop();
	}
}
