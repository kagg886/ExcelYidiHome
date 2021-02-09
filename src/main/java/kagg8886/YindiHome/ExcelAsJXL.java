package kagg8886.YindiHome;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import kagg8886.YindiHome.FileSort.listener;

public class ExcelAsJXL {
	public static void main(String[] args) throws Exception {
		File file = new File("E:\\img");
		WritableWorkbook book= Workbook.createWorkbook(new File("E:\\img5.xls"));
		final WritableSheet sheet=book.createSheet("sheet1",0);
		FileSort.sort(new listener() {
			private int width,height,minX,minY,poi;
			private BufferedImage image;
			public void onchoose(File frameFile) throws Exception {
				if (poi > 180 * 250) {
					return;
				} else {
					System.out.println("写入第" + (poi / 180) + "帧");
				}
				image = ImageIO.read(frameFile);
				width = image.getWidth();
				height = image.getHeight();
				minX = image.getMinX();
				minY = image.getMinY();
				for (int x = minX ; x < width; x++) { //绘制区
					sheet.setRowView(x + poi, 670); //行高
					for(int y = minY; y < height; y++) {
						sheet.setColumnView(y, 10); //列宽
					    WritableCellFormat writableCellFormat = new WritableCellFormat();
					    writableCellFormat.setBackground(getNearestColour(image.getRGB(x, y)));
					    sheet.addCell(new Label(x, y + poi, "", writableCellFormat));
					}
				}
				poi = poi + 180;
				frameFile.delete();
				frameFile = null;
			}
		});
		
		
		System.out.println("准备写入...");
		book.write();
		book.close();
		System.out.println("写入完毕！");
	}
	
	public static Colour getNearestColour(int rg) {  
        Color cl = new Color(rg);
        Colour color = null;  
        Colour[] colors = Colour.getAllColours();  
        if ((colors != null) && (colors.length > 0)) {  
           Colour crtColor = null;  
           int[] rgb = null;  
           int diff = 0;  
           int minDiff = 999;  
           for (int i = 0; i < colors.length; i++) {  
                crtColor = colors[i];  
                rgb = new int[3];  
                rgb[0] = crtColor.getDefaultRGB().getRed();  
                rgb[1] = crtColor.getDefaultRGB().getGreen();  
                rgb[2] = crtColor.getDefaultRGB().getBlue();  
      
                diff = Math.abs(rgb[0] - cl.getRed())  
                  + Math.abs(rgb[1] - cl.getGreen())  
                  + Math.abs(rgb[2] - cl.getBlue());  
                if (diff < minDiff) {  
                 minDiff = diff;  
                 color = crtColor;  
                }  
           }  
        }  
        if (color == null)  
           color = Colour.BLACK;  
        return color;  
    }  
}
