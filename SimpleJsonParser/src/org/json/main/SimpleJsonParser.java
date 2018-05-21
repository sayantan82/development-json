/**
 * 
 */
package org.json.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Sayantan
 *
 */
public class SimpleJsonParser {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		if (args.length == 1) {
			try {
				final URL url = new URL(args[0]);
				final String json = readUrl(url);
				parseJson(json);
			} catch (final MalformedURLException malformedURLException) {
				System.out.println("Not a valid URL");
			} catch (final Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static String readUrl(final URL url) throws Exception {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			final StringBuffer buffer = new StringBuffer();
			int read;
			final char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	/**
	 * 
	 * @param json
	 */
	private static void parseJson(final String json) {
		final JsonParser jsonParser = new JsonParser();
		final JsonElement jsonElement = jsonParser.parse(json);
		final JsonObject jsonObject = jsonElement.getAsJsonObject();

		final Gson parser = new Gson();
		int totalSum = 0;

		for (final Map.Entry<String, JsonElement> entries : jsonObject.entrySet()) {
			final String key = entries.getKey();
			System.out.println("Key : " + key);
			if (key.equalsIgnoreCase("numbers")) {
				final int[] numbers = parser.fromJson(entries.getValue(), int[].class);
				int numberSum = 0;
				for (int number : numbers) {
					numberSum += number;
				}
				System.out.println("Numbers Sum = " + numberSum);
				totalSum += numberSum;
			}
		}
		System.out.println("Total Sum = " + totalSum);
	}

}
