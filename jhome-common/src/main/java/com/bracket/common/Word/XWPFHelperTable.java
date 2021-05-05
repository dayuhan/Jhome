package com.bracket.common.Word;


import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.bracket.common.Word.XWPFHelper.copyParagraph;

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
 * @description: 操作word的表格基本工具类
 * @author: Daxv
 * @create: 2021-04-24 12:35
 **/
public class XWPFHelperTable {

    /**
     * 增加一个复制的样式的新行，并赋值
     * insertRow 在word表格中指定位置插入一行，并将某一行的样式复制到新增行
     * @param table
     * @param copyrowIndex 需要复制的行位置
     * @param newrowIndex 需要新增一行的位置
     * @param values 单元格赋值
     */
    public static void insertRow(XWPFTable table, int copyrowIndex, int newrowIndex,List<String> values) {
        // 在表格中指定的位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(newrowIndex);
        // 获取需要复制行对象
        XWPFTableRow copyRow = table.getRow(copyrowIndex);
        //复制行对象
        targetRow.getCtRow().setTrPr(copyRow.getCtRow().getTrPr());
        //或许需要复制的行的列
        List<XWPFTableCell> copyCells = copyRow.getTableCells();
        //复制列对象
        XWPFTableCell targetCell = null;
        for (int i = 0; i < copyCells.size(); i++) {
            XWPFTableCell copyCell = copyCells.get(i);
            targetCell = targetRow.addNewTableCell();
            targetCell.getCTTc().setTcPr(copyCell.getCTTc().getTcPr());
            targetCell.setText(values.get(i));
            if (copyCell.getParagraphs() != null && copyCell.getParagraphs().size() > 0) {
                targetCell.getParagraphs().get(0).getCTP().setPPr(copyCell.getParagraphs().get(0).getCTP().getPPr());
                if (copyCell.getParagraphs().get(0).getRuns() != null
                        && copyCell.getParagraphs().get(0).getRuns().size() > 0) {
                    XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                    cellR.setBold(copyCell.getParagraphs().get(0).getRuns().get(0).isBold());
                }
            }
        }

    }

    /**
     * 增加一个复制的样式的空行
     * insertRow 在word表格中指定位置插入一行，并将某一行的样式复制到新增行
     * @param table
     * @param copyrowIndex 需要复制的行位置
     * @param newrowIndex 需要新增一行的位置
     */

    public static void insertRow(XWPFTable table, int copyrowIndex, int newrowIndex) {
        // 在表格中指定的位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(newrowIndex);
        // 获取需要复制行对象
        XWPFTableRow copyRow = table.getRow(copyrowIndex);
        //复制行对象
        targetRow.getCtRow().setTrPr(copyRow.getCtRow().getTrPr());
        //或许需要复制的行的列
        List<XWPFTableCell> copyCells = copyRow.getTableCells();
        //复制列对象
        XWPFTableCell targetCell = null;
        for (int i = 0; i < copyCells.size(); i++) {
            XWPFTableCell copyCell = copyCells.get(i);
            targetCell = targetRow.addNewTableCell();
            targetCell.setText("曹尼玛~~~~~~");

            targetCell.getCTTc().setTcPr(copyCell.getCTTc().getTcPr());
            if (copyCell.getParagraphs() != null && copyCell.getParagraphs().size() > 0) {
                targetCell.getParagraphs().get(0).getCTP().setPPr(copyCell.getParagraphs().get(0).getCTP().getPPr());
                if (copyCell.getParagraphs().get(0).getRuns() != null
                        && copyCell.getParagraphs().get(0).getRuns().size() > 0) {
                    XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                    cellR.setBold(copyCell.getParagraphs().get(0).getRuns().get(0).isBold());
                }
            }
        }

    }
    /**
     * 删除指定位置的表格，被删除表格后的索引位置
     * @param document
     * @param pos
     * @Author daxv 2018年12月1日 下午10:32:43
     */
    public static void deleteTableByIndex(XWPFDocument document, int pos) {
        Iterator<IBodyElement> bodyElement = document.getBodyElementsIterator();
        int eIndex = 0, tableIndex = -1;
        while(bodyElement.hasNext()) {
            IBodyElement element = bodyElement.next();
            BodyElementType elementType = element.getElementType();
            if(elementType == BodyElementType.TABLE) {
                tableIndex++;
                if(tableIndex == pos) {
                    break;
                }
            }
            eIndex++;
        }
        document.removeBodyElement(eIndex);
    }
    /**
     * 获得指定位置的表格
     * @param document
     * @param index
     * @return
     * @Author daxv 2018年12月1日 下午10:34:14
     */
    public static XWPFTable getTableByIndex(XWPFDocument document, int index) {
        List<XWPFTable> tableList = document.getTables();
        if(tableList == null || index < 0 || index > tableList.size()) {
            return null;
        }
        return tableList.get(index);
    }
    /**
     * 得到表格的内容（第一次跨行单元格视为一个，第二次跳过跨行合并的单元格）
     * @param table
     * @return
     * @Author daxv 2018年12月1日 下午10:46:41
     */
    public  static List<List<String>> getTableRConten(XWPFTable table) {
        List<List<String>> tableContextList = new ArrayList<List<String>>();
        for(int rowIndex = 0, rowLen = table.getNumberOfRows(); rowIndex < rowLen; rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            List<String> cellContentList = new ArrayList<String>();
            for(int colIndex = 0, colLen = row.getTableCells().size(); colIndex < colLen; colIndex++ ) {
                XWPFTableCell cell = row.getCell(colIndex);
                CTTc ctTc = cell.getCTTc();
                if(ctTc.isSetTcPr()) {
                    CTTcPr tcPr = ctTc.getTcPr();
                    if(tcPr.isSetHMerge()) {
                        CTHMerge hMerge = tcPr.getHMerge();
                        if(STMerge.RESTART.equals(hMerge.getVal())) {
                            cellContentList.add(getTableCellContent(cell));
                        }
                    } else if(tcPr.isSetVMerge()) {
                        CTVMerge vMerge = tcPr.getVMerge();
                        if(STMerge.RESTART.equals(vMerge.getVal())) {
                            cellContentList.add(getTableCellContent(cell));
                        }
                    } else {
                        cellContentList.add(getTableCellContent(cell));
                    }
                }
            }
            tableContextList.add(cellContentList);
        }
        return tableContextList;
    }

    /**
     * 获得一个表格的单元格的内容
     * @param cell
     * @return
     * @Author daxv 2018年12月2日 下午7:39:23
     */
    public static String getTableCellContent(XWPFTableCell cell) {
        StringBuffer sb = new StringBuffer();
        List<XWPFParagraph> cellParagList = cell.getParagraphs();
        if(cellParagList != null && cellParagList.size() > 0) {
            for(XWPFParagraph xwpfPr: cellParagList) {
                List<XWPFRun> runs = xwpfPr.getRuns();
                if(runs != null && runs.size() > 0) {
                    for(XWPFRun xwpfRun : runs) {
                        sb.append(xwpfRun.getText(0));
                    }
                }
            }
        }
        return sb.toString();
    }
    /**
     * 得到表格内容，合并后的单元格视为一个单元格
     * @param table
     * @return
     * @Author daxv 2018年12月2日 下午7:47:19
     */
    public static List<List<String>> getTableContent(XWPFTable table) {
        List<List<String>> tableContentList = new ArrayList<List<String>>();
        for (int rowIndex = 0, rowLen = table.getNumberOfRows(); rowIndex < rowLen; rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            List<String> cellContentList = new ArrayList<String>();
            for (int colIndex = 0, colLen = row.getTableCells().size(); colIndex < colLen; colIndex++) {
                XWPFTableCell cell = row.getCell(colIndex);
                cellContentList.add(getTableCellContent(cell));
            }
            tableContentList.add(cellContentList);
        }
        return tableContentList;
    }

    /**
     * 跨列合并
     * @param table
     * @param row    所合并的行
     * @param fromCell    起始列
     * @param toCell    终止列
     * @Description
     * @Author daxv 2018年11月26日 下午9:23:22
     */
    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for(int cellIndex = fromCell; cellIndex <= toCell; cellIndex++ ) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if(cellIndex == fromCell) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    /**
     * 跨行合并
     * @param table
     * @param col    合并的列
     * @param fromRow    起始行
     * @param toRow    终止行
     * @Description
     * @Author daxv 2018年11月26日 下午9:09:19
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for(int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            //第一个合并单元格用重启合并值设置
            if(rowIndex == fromRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                //合并第一个单元格的单元被设置为“继续”
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * @Description: 创建表格,创建后表格至少有1行1列,设置列宽
     */
    public static XWPFTable createTable(XWPFDocument xdoc, int rowSize, int cellSize,
                                 boolean isSetColWidth, int[] colWidths) {
        XWPFTable table = xdoc.createTable(rowSize, cellSize);
        if (isSetColWidth) {
//            CTTbl ttbl = table.getCTTbl();
//            CTTblGrid tblGrid = ttbl.addNewTblGrid();
//            for (int j = 0, len = Math.min(cellSize, colWidths.length); j < len; j++) {
//                CTTblGridCol gridCol = tblGrid.addNewGridCol();
//                gridCol.setW(new BigInteger(String.valueOf(colWidths[j])));
//            }
        }
        return table;
    }

    /**
     * @Description: 设置表格总宽度与水平对齐方式
     */
    public static void setTableWidthAndHAlign(XWPFTable table, String width,
                                       STJc.Enum enumValue) {
        CTTblPr tblPr = getTableCTTblPr(table);
        // 表格宽度
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        if (enumValue != null) {
            CTJc cTJc = tblPr.addNewJc();
            cTJc.setVal(enumValue);
        }
        // 设置宽度
        tblWidth.setW(new BigInteger(width));
        tblWidth.setType(STTblWidth.DXA);
    }

    /**
     * @Description: 得到Table的CTTblPr,不存在则新建
     */
    public static CTTblPr getTableCTTblPr(XWPFTable table) {
        CTTbl ttbl = table.getCTTbl();
        // 表格属性
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        return tblPr;
    }

    /**
     * 设置表格行高
     * @param infoTable
     * @param heigth 高度
     * @param vertical 表格内容的显示方式：居中、靠右...
     * @Author daxv 2018年12月16日
     */
    public static void setTableHeight(XWPFTable infoTable, int heigth, STVerticalJc.Enum vertical) {
        List<XWPFTableRow> rows = infoTable.getRows();
        for(XWPFTableRow row : rows) {
            CTTrPr trPr = row.getCtRow().addNewTrPr();
            CTHeight ht = trPr.addNewTrHeight();
            ht.setVal(BigInteger.valueOf(heigth));
            List<XWPFTableCell> cells = row.getTableCells();
            for(XWPFTableCell tableCell : cells ) {
                CTTcPr cttcpr = tableCell.getCTTc().addNewTcPr();
                cttcpr.addNewVAlign().setVal(vertical);
            }
        }
    }

    /**
     * 复制表格
     * @param targetTable
     * @param sourceTable
     * @Author daxv 2018年12月1日 下午1:40:01
     */
    public static void copyTable(XWPFTable targetTable, XWPFTable sourceTable) {
        //复制表格属性
        targetTable.getCTTbl().setTblPr(sourceTable.getCTTbl().getTblPr());
        //复制行
        for(int i = 0; i < sourceTable.getRows().size(); i++) {
            XWPFTableRow targetRow = targetTable.getRow(i);
            XWPFTableRow sourceRow = sourceTable.getRow(i);
            if(targetRow == null) {
                targetTable.addRow(sourceRow);
            } else {
                copyTableRow(targetRow, sourceRow);
            }
        }
    }
    /**
     * 复制单元格
     * @param targetRow
     * @param sourceRow
     * @Author daxv 2018年12月1日 下午1:33:22
     */
    public static void copyTableRow(XWPFTableRow targetRow, XWPFTableRow sourceRow) {
        //复制样式
        if(sourceRow != null) {
            targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        }
        //复制单元格
        for(int i = 0; i < sourceRow.getTableCells().size(); i++) {
            XWPFTableCell tCell = targetRow.getCell(i);
            XWPFTableCell sCell = sourceRow.getCell(i);
            if(tCell == sCell) {
                tCell = targetRow.addNewTableCell();
            }
            copyTableCell(tCell, sCell);
        }
    }
    /**
     * 复制单元格（列） 从sourceCell到targetCell
     * @param targetCell
     * @param sourceCell
     * @Author daxv 2018年12月1日 下午1:27:38
     */
    public static void copyTableCell(XWPFTableCell targetCell, XWPFTableCell sourceCell) {
        //表格属性
        if(sourceCell.getCTTc() != null) {
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
        }
        //删除段落
        for(int pos = 0; pos < targetCell.getParagraphs().size(); pos++) {
            targetCell.removeParagraph(pos);
        }
        //添加段落
        for(XWPFParagraph sourceParag : sourceCell.getParagraphs()) {
            XWPFParagraph targetParag = targetCell.addParagraph();
            copyParagraph(targetParag, sourceParag);
        }
    }
}
