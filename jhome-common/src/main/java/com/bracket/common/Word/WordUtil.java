package com.bracket.common.Word;

import com.bracket.common.ToolKit.StringUtil;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import springfox.documentation.schema.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: jhome-root
 * @description: word操作类
 * @author: Daxv
 * @create: 2021-04-24 12:29
 **/


public class WordUtil {
    private static Logger logger = Logger.getLogger(XWPFHelper.class.toString());

    /**
     * Doc 处理 替换字符
     *
     * @throws IOException
     */
    public void test() throws IOException {
        String path = this.getClass().getClassLoader().getResource("BgTemple.doc").getPath();
        path = path.substring(1);
//        XWPFDocument xwpfDocument = XWPFHelper.openDoc(path);
//        xwpfDocument.getBodyElements();

        InputStream is = new FileInputStream(path);
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();
        //把range范围内的${reportDate}替换为当前的日期
        range.replaceText("${title}", "坦克质量诊断");
        range.replaceText("${result}", "合格");
        range.replaceText("${name}", "答旭");
        range.replaceText("${sex}", "男");
        range.replaceText("${nianling}", "33");
        range.replaceText("${zhiye}", "IT");
        range.replaceText("${jielun}", "质量诊断非常合格！");
        OutputStream os = new FileOutputStream("D:\\write.doc");
        //把doc输出到输出流中
        doc.write(os);
        closeStream(is);
        closeStream(os);
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    private void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    private void closeStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Doc DOCx  处理 替换字符
     *
     * @throws IOException
     */
    public void test1(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        String path = this.getClass().getClassLoader().getResource("BgTemple.docx").getPath();
        path = path.substring(1);
        String filepathString = path;
        String destpathString = "D:/2ttt.docx";
        Map<String, String> map = new HashMap<String, String>();
        map.put("${title}", "坦克质量诊断");
        map.put("${result}", "合格");
        map.put("${name}", "答旭");
        map.put("${name1}", "答旭旭");
        map.put("${sex}", "男");
        map.put("${nianling}", "33");
        map.put("${zhiye}", "IT");
        map.put("${jielun}", "质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格质量诊断非常合格！");
        System.out.println(replaceAndGenerateWord(filepathString,
                destpathString, map));
    }



    // 返回Docx中需要替换的特殊字符，没有重复项
    // 推荐传入正则表达式参数"\\$\\{[^{}]+\\}"
    public ArrayList<String> getReplaceElementsInWord(String filePath,
                                                      String regex) {
        String[] p = filePath.split("\\.");
        if (p.length > 0) {// 判断文件有无扩展名
            // 比较文件扩展名
            if (p[p.length - 1].equalsIgnoreCase("doc")) {
                ArrayList<String> al = new ArrayList<>();
                File file = new File(filePath);
                HWPFDocument document = null;
                try {
                    InputStream is = new FileInputStream(file);
                    document = new HWPFDocument(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Range range = document.getRange();
                String rangeText = range.text();
                CharSequence cs = rangeText.subSequence(0, rangeText.length());
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(cs);
                int startPosition = 0;
                while (matcher.find(startPosition)) {
                    if (!al.contains(matcher.group())) {
                        al.add(matcher.group());
                    }
                    startPosition = matcher.end();
                }
                return al;
            } else if (p[p.length - 1].equalsIgnoreCase("docx")) {
                ArrayList<String> al = new ArrayList<>();
                XWPFDocument document = null;
                try {
                    document = new XWPFDocument(
                            POIXMLDocument.openPackage(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 遍历段落
                Iterator<XWPFParagraph> itPara = document
                        .getParagraphsIterator();
                while (itPara.hasNext()) {
                    XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                    String paragraphString = paragraph.getText();
                    CharSequence cs = paragraphString.subSequence(0,
                            paragraphString.length());
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(cs);
                    int startPosition = 0;
                    while (matcher.find(startPosition)) {
                        if (!al.contains(matcher.group())) {
                            al.add(matcher.group());
                        }
                        startPosition = matcher.end();
                    }
                }
                // 遍历表
                Iterator<XWPFTable> itTable = document.getTablesIterator();
                while (itTable.hasNext()) {
                    XWPFTable table = (XWPFTable) itTable.next();
                    int rcount = table.getNumberOfRows();
                    for (int i = 0; i < rcount; i++) {
                        XWPFTableRow row = table.getRow(i);
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            String cellText = "";
                            cellText = cell.getText();
                            CharSequence cs = cellText.subSequence(0,
                                    cellText.length());
                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(cs);
                            int startPosition = 0;
                            while (matcher.find(startPosition)) {
                                if (!al.contains(matcher.group())) {
                                    al.add(matcher.group());
                                }
                                startPosition = matcher.end();
                            }
                        }
                    }
                }
                return al;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // 替换word中需要替换的特殊字符
    public static boolean replaceAndGenerateWord(String srcPath,
                                                 String destPath, Map<String, String> map) {
        String[] sp = srcPath.split("\\.");
        String[] dp = destPath.split("\\.");
        if ((sp.length > 0) && (dp.length > 0)) {// 判断文件有无扩展名
            // 比较文件扩展名
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
                try {
                    XWPFDocument document = new XWPFDocument(
                            POIXMLDocument.openPackage(srcPath));
                    // 替换段落中的指定文字
                    Iterator<XWPFParagraph> itPara = document
                            .getParagraphsIterator();
                    try {
                        while (itPara.hasNext()) {
                            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                            List<XWPFRun> runs = paragraph.getRuns();
                            for (int i = 0; i < runs.size(); i++) {
                                String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
                                for (Map.Entry<String, String> entry : map.entrySet()) {
                                    if (oneparaString != null)
                                        oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
                                }
                                runs.get(i).setText(oneparaString, 0);
                                //增加图表 向word中写入图片
                                if (StringUtil.isNotBlank(oneparaString) && oneparaString.contains("其他指标展示")) {
                                    byte[] data=Base64.getDecoder().decode("图表Base64编码");
                                    ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(data);
                                    runs.get(i).addPicture(byteArrayInputStream,XWPFDocument.PICTURE_TYPE_PNG,i+".png", Units.toEMU(400),Units.toEMU(200));
                                }
                                //扩展段落
                                if (StringUtil.isNotBlank(oneparaString) && oneparaString.contains("其他指标展示")) {
                                    //XWPFTable table = XWPFHelperTable.createTable(document, 4, 4, false, null);
                                    XWPFParagraph paragraphX = document.createParagraph();
                                    XWPFRun xwpfRun= paragraphX.createRun();
                                    xwpfRun.setText("1、坦克履带损坏情况");
                                    //XWPFParagraph 段落属性
                                    //paragraphX.addRun(runX0);//似乎并没有什么卵用
                                    //paragraphX.removeRun(1);//按数组下标删除run(文本)
                                     paragraphX.setAlignment(ParagraphAlignment.LEFT);//对齐方式
                                    //paragraphX.setBorderBetween(Borders.LIGHTNING_1);//边界 （但是我设置了好几个值都没有效果）
                                    //paragraphX.setFirstLineIndent(100);//首行缩进：-----效果不详
                                    //paragraphX.setFontAlignment(3);//字体对齐方式：1左对齐 2居中3右对齐
                                    //paragraphX.setIndentationFirstLine(2);//首行缩进：-----效果不详
                                    //paragraphX.setIndentationHanging(1);//指定缩进，从父段落的第一行删除，将第一行上的缩进移回到文本流方向的开头。-----效果不详
                                    //paragraphX.setIndentationLeft(2);//-----效果不详
                                    //paragraphX.setIndentationRight(2);//-----效果不详
                                    //paragraphX.setIndentFromLeft(2);//-----效果不详
                                    //paragraphX.setIndentFromRight(2);//-----效果不详
                                    //paragraphX.setNumID(new BigInteger("3"));//设置段落编号-----有效果看不懂（仅仅是整段缩进4个字）
                                    //paragraphX.setPageBreak(true);//段前分页
                                    //paragraphX.setSpacingAfter(1);//指定文档中此段最后一行以绝对单位添加的间距。-----效果不详
                                    //paragraphX.setSpacingBeforeLines(2);//指定在该行的第一行中添加行单位之前的间距-----效果不详
                                    //paragraphX.setStyle("标题 3");//段落样式：需要结合addCustomHeadingStyle(docxDocument, "标题 3", 3)配合使用
                                    paragraphX.setVerticalAlignment(TextAlignment.BOTTOM);//文本对齐方式(我猜在table里面会有比较明显得到效果)
                                    //paragraphX.setWordWrapped(true);//这个元素指定一个消费者是否应该突破拉丁语文本超过一行的文本范围，打破单词跨两行（打破字符水平）或移动到以下行字（打破字级）-----(我没看懂:填个false还报异常了)

                                    document.createParagraph().createRun().setText("1、坦克履带损坏情况");
                                    document.createTable().insertNewTableRow(6);

                                    document.createParagraph().createRun().setText("1、坦克履带损坏情况");
                                    document.createTable().insertNewTableRow(6);

                                    document.createParagraph().createRun().setText("1、坦克履带损坏情况");
                                    document.createTable().insertNewTableRow(6);

                                    document.createParagraph().createRun().setText("1、坦克履带损坏情况");
                                    document.createTable().insertNewTableRow(6);

                                    document.createParagraph().createRun().setText("1、坦克履带损坏情况");
                                    document.createTable().insertNewTableRow(6);

                                    document.createParagraph().createRun().setText("1、坦克履带损坏情况");
                                    document.createTable().insertNewTableRow(6);

                                    document.createParagraph().createRun().setText("1、坦克履带损坏情况");
                                    document.createTable().insertNewTableRow(6);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    try {
                        //遍历表格 替换表格中的指定文字
                        Iterator<XWPFTable> itTable = document.getTablesIterator();
                        while (itTable.hasNext()) {
                            XWPFTable table = (XWPFTable) itTable.next();
                            int rcount = table.getNumberOfRows();
                            for (int i = 0; i < rcount; i++) {
                                XWPFTableRow row = table.getRow(i);
                                List<XWPFTableCell> cells = row.getTableCells();
                                for (XWPFTableCell cell : cells) {
                                    String cellTextString = cell.getText();
                                    for (Map.Entry<String, String> e : map.entrySet()) {
                                        if (cellTextString.contains(e.getKey()))
                                            cellTextString = cellTextString
                                                    .replace(e.getKey(),
                                                            e.getValue());
                                    }
                                    cell.removeParagraph(0);
                                    cell.setText(cellTextString);
                                }
                            }
                            //增加一行
                            XWPFHelperTable.insertRow(table, 0, 1);
                            XWPFHelperTable.insertRow(table, 0, 2);
                            XWPFHelperTable.insertRow(table, 0, 3);

                        }
                    } catch (Exception ex) {
                        logger.error("表格创建失败：" + ex.getMessage());

                    }


                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();

                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            } else
                // doc只能生成doc，如果生成docx会出错
                if ((sp[sp.length - 1].equalsIgnoreCase("doc"))
                        && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                    HWPFDocument document = null;
                    try {
                        document = new HWPFDocument(new FileInputStream(srcPath));
                        Range range = document.getRange();
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            range.replaceText(entry.getKey(), entry.getValue());
                        }
                        FileOutputStream outStream = null;
                        outStream = new FileOutputStream(destPath);
                        document.write(outStream);
                        outStream.close();
                        return true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return false;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    return false;
                }
        } else {
            return false;
        }
    }

    /**
     * 导出word
     * @param request
     * @param response
     * @param wd
     */
    public   void exportReport(HttpServletRequest request,HttpServletResponse response,XWPFDocument wd){
        String fileName=UUID.randomUUID().toString()+"";
        OutputStream outputStream=null;
        try {
            setResponsetHeader(response,fileName);
            outputStream=new BufferedOutputStream(response.getOutputStream());
            wd.write(outputStream);
            outputStream.flush();
            outputStream.close();;

        }catch (Exception e)
        {
            logger.error("导出文件："+e.getMessage());
        }finally {
            if (outputStream!=null)
            {
                try {
                    outputStream.close();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public   void setResponsetHeader(HttpServletResponse response,String fileName)
    {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".docx");
    }

}
