import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PDFExample {

    public static final String DATA = "src/main/resources/data/ufo.csv";
    public static final String DEST = "results/hello.pdf";

    static PdfFont helvetica = null;
    static PdfFont helveticaBold = null;

    public static void main(String[] args) throws Exception {
        // 生产pdf基础对象
        PdfWriter writer = new PdfWriter(DEST);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Create a PdfFont
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H");
        // 居中加粗中文
        document.add(new Paragraph("采购询盘")
                .setFont(font)
                .setFontSize(20F)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
        );

        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{2, 3, 3, 2, 3}));

        infoTable.addCell(tableCell("产品名称：", font, 15).setBorder(Border.NO_BORDER));
        infoTable.addCell(tableCell("得力钢卷尺", font, 15).setBorder(Border.NO_BORDER));
        infoTable.addCell(tableCell("", font, 15).setBorder(Border.NO_BORDER));
        infoTable.addCell(tableCell("产品品类：", font, 15).setBorder(Border.NO_BORDER));
        infoTable.addCell(tableCell("雨具", font, 15).setBorder(Border.NO_BORDER));

        document.add(infoTable);

        // 空一行
        document.add(new Paragraph());

        document.add(new Paragraph("询价产品").setFont(font).setFontSize(18).setBold());

        Table productTable = new Table(UnitValue.createPercentArray(new float[]{2, 14, 2, 2, 8}));

        List<String> headerList = new ArrayList<>();
        headerList.add("序号");
        headerList.add("商品名");
        headerList.add("单位");
        headerList.add("数量");
        headerList.add("备注");

        // 表头
        for (String header : headerList) {
            productTable.addHeaderCell(new Cell().add(new Paragraph(header)
                    .setFont(font))
                    .setFontSize(15)
                    .setBold()
                    .setBackgroundColor(Color.GRAY, 0.3F));
        }

        for (int i = 0; i < 5; i++) {
            productTable.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1)).setFont(font)).setFontSize(15));
            productTable.addCell(new Cell().add(new Paragraph("得力钢卷尺").setFont(font)).setFontSize(15));
            productTable.addCell(new Cell().add(new Paragraph("件").setFont(font)).setFontSize(15));
            productTable.addCell(new Cell().add(new Paragraph("100").setFont(font)).setFontSize(15));
            productTable.addCell(new Cell().add(new Paragraph("采购").setFont(font)).setFontSize(15));
        }

        productTable.addCell(new Cell().add(new Paragraph("6").setFont(font)).setFontSize(15));
        productTable.addCell(new Cell().add(new Paragraph("得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺得力钢卷尺").setFont(font)).setFontSize(15));
        productTable.addCell(new Cell().add(new Paragraph("件").setFont(font)).setFontSize(15));
        productTable.addCell(new Cell().add(new Paragraph("100").setFont(font)).setFontSize(15));
        productTable.addCell(new Cell().add(new Paragraph("采购").setFont(font)).setFontSize(15));

        document.add(productTable);
        // 空一行
        document.add(new Paragraph());
        document.add(new Paragraph());

        document.add(new Paragraph("询价要求").setFont(font).setFontSize(18).setBold());

        Table requireTable = new Table(UnitValue.createPercentArray(new float[]{2, 8}));

        requireTable.addCell(tableCell("发票要求", font, 15));
        requireTable.addCell(tableCell("专票", font, 15));
        requireTable.addCell(tableCell("供应商要求", font, 15));
        requireTable.addCell(tableCell("实名认证商家", font, 15));
        requireTable.addCell(tableCell("收货地址", font, 15));
        requireTable.addCell(tableCell("广州市海珠区华州路", font, 15));


        document.add(requireTable);

        document.close();
    }

    private static Cell tableCell(String content, PdfFont font, float fontSize) {
        return new Cell().add(new Paragraph(content).setFont(font)).setFontSize(fontSize);
    }


//    public static void main(String[] args) throws Exception {
//        helvetica = PdfFontFactory.createFont(FontConstants.HELVETICA);
//        helveticaBold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new PDFExample().createPdf(DEST);
//    }

    protected void createPdf(String dest) throws Exception {

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));

        // Initialize document
        Document document = new Document(pdf);

        Paragraph p = new Paragraph("List of reported UFO sightings in 20th century")
                .setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(14);
        document.add(p);

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 5, 7, 4}));

        BufferedReader br = new BufferedReader(new FileReader(DATA));
        String line = br.readLine();
        process(table, line, helveticaBold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, helvetica, false);
        }
        br.close();

        document.add(table);

        document.close();
    }

    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)).setFontSize(9).setBorder(new SolidBorder(Color.BLACK, 0.5f)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)).setFontSize(9).setBorder(new SolidBorder(Color.BLACK, 0.5f)));
            }
        }
    }

}
