package edu.ahs.frc.spaceraiders.zodiac.input;

import java.util.ArrayList;
import java.util.List;

/**
 * Serial Communications
 * 
 * @author Andrew
 */
public class SerialCommunication {
	private final static SerialCommunication INSTANCE = new SerialCommunication();
	private List<Sensor> listeners = new ArrayList<Sensor>();

	private SerialCommunication() {
	}

	/**
	 * gets instance for serial
	 * 
	 * @return instance of Serial
	 */
	static SerialCommunication getInstance() {
		return INSTANCE;
	}

	/**
	 * handles events
	 */
	long timeAtStartOfEvents;
	long timeAtEndOfEvents;

	void handleEvents() {
		try {
			timeAtStartOfEvents = System.currentTimeMillis();
			String sentence = get_last_sentence();
			System.out.println((sentence != null)?sentence:"NULL");
			if (sentence != null) {
				SensorCommunicationEvent e = new SensorCommunicationEvent();
				boolean iterate = false;
				String tag = "";
				double val = 0;
				// parse sentence into array of Strings representing tags and
				// values
				ArrayList<String> parseArray = parse(sentence);
				if (parseArray.size() >=
						2) { // if at least two strings (one
												// tag/value pair)
					// parse array of strings into an event containing pairs of
					// tag and value
					for (String s : parseArray) {
						if (iterate == false) {
							tag = s;
							iterate = true;
						} else if (iterate == true) {
							try {
								val = Integer.parseInt(s);
								e.addPair(tag, val);
								iterate = false;
							} catch (Exception serialTrouble) {
								System.out
										.println("Trouble reading the serial!");
								serialTrouble.printStackTrace();
							}
						}
					}
					// Send parsed tag/value pairs to all listeners
					for (Sensor b : listeners) {
						b.receiveValue(e);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		timeAtEndOfEvents = System.currentTimeMillis();
		System.out.println("Time Spent In Handle Events:"
				+ (timeAtEndOfEvents - timeAtStartOfEvents) + "ms");
	}

	/**
	 * registers a listener
	 * 
	 * @param listener
	 *            a listener
	 */
	public void register(Sensor listener) {
		listeners.add(listener);
	}

	/**
	 * Breaks up message from serial into values and tags
	 * 
	 * @param STP
	 *            serial message
	 * @return values and tags
	 */
	public ArrayList<String> parse(String STP) {
		ArrayList<String> parts2 = new ArrayList<>();
		String[] parts = STP.split("!"); // Splits array of strings at the
											// exclamation point, a separator
											// character
		for (String s : parts) {
			String[] subparts = s.split(":"); // Splits the tags of a string
												// from the values
			for (String b : subparts) {
				parts2.add(b);
			}
		}
		// for (String s : parts2)
		// System.out.print(s);
		return parts2;
	}

	/**
	 * Reads all bytes from the serial buffer and parses and returns
	 * the last complete sensor sentence (throwing the rest away).
	 * Sensor data arrives fast enough that each time we are called,
	 * several sentences will be in the serial buffer.  Since each sentence
	 * completely describes the state of all sensors, we are only
	 * interested in the *last* sentence received and for efficiency,
	 * we can throw the older sentences away. 
	 * 
	 * @return a complete sensor sentence or null if none available
	 */
	public String get_last_sentence() {
		try {
			int numBytes = SerialSubsystem.serial.getBytesReceived();
			if (numBytes >= 5) { // minimum valid sentence
				byte readBytes[] = SerialSubsystem.serial.read(numBytes);
				String sentence= new String(readBytes);
				int end= sentence.lastIndexOf('\n');
				if (end >= 4) { // minimum valid sentence length
					// strip bytes received past end of sentence
					sentence = sentence.substring(0, end);
					// find end of preceding sentence
					int start = sentence.lastIndexOf('<');
					if ((start >= 0) && (end > start+1)) {
						return sentence.substring(start+1, end);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

	/*
	byte[] read = SerialSubsystem.serial.read(1);
	// converts to UTF-8
	currentChar = new String(read, "UTF-8");
	// Flushes the buffer if the message is too long for whatever reason
	if (currentMsg.length() > 100) {
		SerialSubsystem.serial.read(currentMsg.length());
	} // checks to see if the first character is the start of a packet
	if (currentChar.equals("<")) {
		currentChar = "";
		isRead = true;
	}
	// checks to see if it's the end of a packet
	else if ((currentChar.equals(">")) && (isRead == true)) {
		// resets counter so we don't get started in the middle of the
		// buffer
		isRead = false;

		String outputString = currentMsg.toString();
		// clears current message
		currentMsg = new StringBuilder();
		return outputString;
	} else if (isRead == true) {
		currentMsg.append(currentChar);
	}
  */