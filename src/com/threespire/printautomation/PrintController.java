package com.threespire.printautomation;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;

import com.threespire.printautomation.db.DbConnectionManager;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class PrintController {
	
	private Connection conn;

	public JasperReportBuilder prepareOrder() throws DRException{
		
		JasperReportBuilder report = DynamicReports.report();
		
		//Styles
		StyleBuilder boldStyle = DynamicReports.stl.style().bold();
		StyleBuilder boldCentered = DynamicReports.stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder columnHeader = DynamicReports.stl.style(boldCentered).setBorder(DynamicReports.stl.pen()).setBackgroundColor(Color.LIGHT_GRAY);
		
		//Add title
		TextFieldBuilder<String> title =  DynamicReports.cmp.text("Nara Wien Order");
		title.setStyle(boldCentered);
		report.title(title);
		
		//Add columns
		TextColumnBuilder<Integer> orderIdColumn = Columns.column("Order ID", "order_id", DynamicReports.type.integerType());
		TextColumnBuilder<String> orderedItemColumn = Columns.column("Ordered Item", "order_Item", DynamicReports.type.stringType());
		TextColumnBuilder<Integer> quantityColumn = Columns.column("Quantity", "quantity", DynamicReports.type.integerType());
		TextColumnBuilder<Integer> priceColumn = Columns.column("Price", "price", DynamicReports.type.integerType());
		
		//Total Price column
		TextColumnBuilder<BigDecimal> totalPriceColumn = priceColumn.multiply(quantityColumn).setTitle("Total Price").setDataType(DynamicReports.type.bigDecimalType());
		
		//sum of prices
		report.subtotalsAtSummary(DynamicReports.sbt.sum(totalPriceColumn));
		
		//Row Number
		TextColumnBuilder<Integer> rowNumColumn = Columns.reportRowNumberColumn("No. ").setColumns(2).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		
		//column style
		report.highlightDetailEvenRows(); //Highlight rows
		report.setColumnTitleStyle(columnHeader);
		
		//add columns
		report.columns(rowNumColumn, orderIdColumn, orderedItemColumn, quantityColumn, priceColumn, totalPriceColumn);
		
		//report.setDataSource("SELECT * FROM print_order WHERE order_id=101", new DbConnectionManager().getConnection());		
		//report.show();
		return report;
		
	}
	
	public void printOrder(Boolean enablePrintDialog, String orderId) {
		
		JasperReportBuilder report;
		String queryString = "SELECT * FROM print_order WHERE order_id="+orderId;
		
		try {
			conn = new DbConnectionManager().getConnection();
			report =  prepareOrder().setDataSource(queryString, conn);
			report.show();
			//report.print(enablePrintDialog);
			conn.close();
		} catch (Exception e) {
			System.err.println("Failed to print :( " + e);
		}
		
	}
	
}