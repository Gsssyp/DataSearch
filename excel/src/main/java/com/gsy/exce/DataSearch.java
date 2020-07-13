package com.gsy.exce;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 类说明：字符文件差异比较
 * 
 * <pre>
 * Modify Information:
 * Author        Date          Description
 * ============ =========== ============================
 * guoshuya     2020-07-10  Create this file
 * </pre>
 * 
 */

public class DataSearch {

    /**
	 * 获取某一列数据
	 * @param inputDataPath excel 文件路径
	 * @param startRow 开始行
	 * @param contentColumn 数据列
	 * @return
	 * @throws Exception
	 */
    public List<String> input(String inputDataPath, int startRow, int contentColumn) throws Exception {
        List<String> list = new ArrayList<String>();

        // 1.输入excel
        File inputDataFile = new File(inputDataPath);
        Workbook workbook = WorkbookFactory.create(new FileInputStream(inputDataFile));
        Sheet sheet = workbook.getSheetAt(0);

        // 2.遍历单元格获取数据
        int lastRowNum = sheet.getLastRowNum();
        for (int i = startRow; i <= lastRowNum; i++) {

            Row row = sheet.getRow(i);
            String searchContent = row.getCell(contentColumn).getStringCellValue();

            if (Objects.nonNull(searchContent) && !"".equals(searchContent)) {
                String val = searchContent.replace(" ", "");
                if ("".equals(val)) {
                    continue;
                }
                list.add(val);
            }

        }

        return list;
    }

    /**
     * 两个集合互相包含比较
     * @param collection1
     * @param collection2
     * @return
     */
    public List<String> procss(List<String> collection1, List<String> collection2) {
        List<String> result = new ArrayList<String>();
        for (String col1 : collection1) {
            for (String col2 : collection2) {
                if (col1.contains(col2)) {
                    result.add("1: " + col1 + "    " + "2: " + col2);
                }
            }
        }

        for (String col2 : collection2) {
            for (String col1 : collection1) {
                if (col2.contains(col1)) {
                    result.add("1: " + col1 + "    " + "2: " + col2);
                }
            }
        }
        System.out.println("差异条数:" + result.size());
        return result;
    }

    public void output(List<String> collection) {
        for (String val : collection) {
            System.out.println(val);
        }
        System.out.println("数据条数:" + collection.size());
    }

    public static void main(String[] args) throws Exception {
        DataSearch dataSearch = new DataSearch();
        // url
        List<String> searchContentForUrl = dataSearch.input("E:\\Desktop\\非法从事场外配资名单排查\\非法从事场外配资名单.xlsx", 2, 2);

        dataSearch.procss(searchContentForUrl, dataSearch.input("E:\\Desktop\\非法从事场外配资名单排查\\机构.xlsx", 1, 55));

        dataSearch.procss(searchContentForUrl, dataSearch.input("E:\\Desktop\\非法从事场外配资名单排查\\Encustomer_2020-07-10.xlsx", 2, 32));

        // name
        List<String> searchContentForName = dataSearch.input("E:\\Desktop\\非法从事场外配资名单排查\\非法从事场外配资名单.xlsx", 2, 3);
        // 平台简称
        List<String> plantShortNameList = dataSearch.input("E:\\Desktop\\非法从事场外配资名单排查\\Encustomer_2020-07-10.xlsx", 2, 3);

        // APP名称
        List<String> appNameList = dataSearch.input("E:\\Desktop\\非法从事场外配资名单排查\\Encustomer_2020-07-10.xlsx", 2, 36);

        dataSearch.procss(searchContentForName, plantShortNameList);

        dataSearch.procss(searchContentForName, appNameList);

    }
}
