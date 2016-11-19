package plugin3.popup.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLRead {

	String filename;

	public void parse(String fname, String ofname) throws IOException, ParserConfigurationException, SAXException {
		File inp = new File(fname);
		File xmlFiles[] = inp.listFiles();

		File ou = new File(ofname);
		if (!ou.exists()) {
			ou.createNewFile();
		}
		BufferedWriter br = new BufferedWriter(new FileWriter(ofname, true));

		for (File f : xmlFiles) {

			BasicFileAttributes attr = Files.readAttributes(Paths.get(f.getAbsolutePath()), BasicFileAttributes.class);

			System.out.println("Creation time " + attr.creationTime().toMillis());
			br.write(attr.creationTime().toMillis() + " ");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Metric");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nNode;
					System.out.print(el.getAttribute("description"));
					// System.out.print(" "+el.getAttribute("max"));
					if ((el.getAttribute("id").equals("TLOC")) || (el.getAttribute("id").equals("NOP"))) {
						System.out
								.println(((Element) (el.getElementsByTagName("Value")).item(0)).getAttribute("value"));
						br.write(((Element) (el.getElementsByTagName("Value")).item(0)).getAttribute("value") + " ");

					} else {
						System.out.println(
								"  " + ((Element) (el.getElementsByTagName("Values")).item(0)).getAttribute("avg"));
						br.write(((Element) (el.getElementsByTagName("Values")).item(0)).getAttribute("avg") + " ");
					}
				}
				System.out.println();

			}
			br.write("\n");

		}
		br.close();
	}

}
