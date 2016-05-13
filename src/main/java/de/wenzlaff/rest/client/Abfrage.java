package de.wenzlaff.rest.client;

import java.io.IOException;
import java.io.StringReader;

import javax.ws.rs.client.ClientBuilder;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Beispiel REST Abfrage der Flugzeugdaten aus Hannover.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 */
public class Abfrage {

	// URL des Feeds aller Flugzeuge in Hannover von Thomas Wenzlaff. //
	private final static String FLUGZEUG_URL = "https://api.thingspeak.com/channels/44177/feeds/last.xml";

	public static void main(String[] args) throws JDOMException, IOException {

		String result = ClientBuilder.newClient().target(FLUGZEUG_URL).request().get(String.class);
		// result get sample:
		// <?xml version="1.0" encoding="UTF-8"?>
		// <feed>
		// <created-at type="dateTime">2016-05-13T12:20:01Z</created-at>
		// <entry-id type="integer">79166</entry-id>
		// <field1>18</field1>
		// <id type="integer" nil="true"/>
		// </feed>

		String field1 = new SAXBuilder().build(new StringReader(result)).getDocument().getRootElement().getChild("field1").getText();

		System.out.println("Aktuelle Anzahl Flugzeuge in Langenhagen: " + field1);
	}

}
