package plugin3.popup.actions;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.RectangleInsets;
import java.io.*;

public class TimeSeriesPlotter {

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	static LinkedHashMap<String, String> idMaprev = new LinkedHashMap<String,String>();
	private static JFreeChart createChart(XYDataset dataset,String des) {
		

		JFreeChart chart = ChartFactory.createTimeSeriesChart(des, // title
				"Date", // x-axis label
				idMaprev.get(des), // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
		);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

		return chart;

	}

	/**
	 * Creates a dataset, consisting of two series of monthly data.
	 *
	 * @return The dataset.
	 * @throws FileNotFoundException
	 */
	private XYDataset createDataset(String ofname,String metricId) throws FileNotFoundException {
		File ou = new File(ofname);
		Scanner in = new Scanner(ou);
		LinkedHashMap<String, String> idMap= new LinkedHashMap<String, String>();
		idMap.put("PAR", "Number of Parameters");
		idMap.put("NSF", "Number of Static Attributes");
		idMap.put("CE", "Efferent Coupling");
		idMap.put("SIX", "Specialization Index");
		idMap.put("NOC", "Number of Classes");
		idMap.put("NOF", "Number of Attributes");
		idMap.put("RMA", "Abstractness");
		idMap.put("RMD", "Normalized Distance");
		idMap.put("NSM", "Number of Static Methods");
		idMap.put("NOI", "Number of Interfaces");
		idMap.put("TLOC", "Total Lines of Code");
		idMap.put("WMC", "Weighted methods per Class");
		idMap.put("NOM", "Number of Methods");
		idMap.put("DIT", "Depth of Inheritance Tree");
		idMap.put("NOP", "Number of Packages");
		idMap.put("RMI", "Instability");
		idMap.put("VG", "McCabe Cyclomatic Complexity");
		idMap.put("NBD", "Nested Block Depth");
		idMap.put("LCOM", "Lack of Cohesion of Methods");
		idMap.put("MLOC", "Method Lines of Code");
		idMap.put("NORM", "Number of Overridden Methods");
		idMap.put("CA", "Afferent Coupling");
		idMap.put("NSC", "Number of Children");


		
		
		for(Map.Entry<String, String> entry : idMap.entrySet()){
		    idMaprev.put(entry.getValue(), entry.getKey());
		}
		
		TimeSeries s1 = new TimeSeries(metricId);
		ArrayList<String> mId= new ArrayList<String>(idMaprev.keySet());
	//	Iterator<String> it= idMaprev.keySet().iterator();
		

		while (in.hasNextLine()) {
			// System.out.println(in.nextLine());
			String inp[] = (in.nextLine()).split(" ");
			System.out.println("index "+mId.indexOf(metricId));
			System.out.println("test" + inp[mId.indexOf(metricId)+1]);
			Double loc =Double.parseDouble(inp[mId.indexOf(metricId)+1]);
			String tim = inp[0];
			Date noT = new Date(Long.parseLong(tim));
			s1.add(new Millisecond(noT), loc);

		}
		in.close();

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		// dataset.addSeries(s2);

		return dataset;
	}

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args
	 *            ignored.
	 * @throws FileNotFoundException
	 */
	public void init(String ofname,String metricId) throws FileNotFoundException {
		final JFreeChart chart = createChart(createDataset(ofname,metricId),metricId);
		final Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		shell.setSize(600, 300);
		shell.setLayout(new FillLayout());
		shell.setText("Software Metric Evolution");
		ChartComposite frame = new ChartComposite(shell, SWT.NONE, chart, true);
		frame.setDisplayToolTips(true);
		frame.setHorizontalAxisTrace(false);
		frame.setVerticalAxisTrace(false);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

}
