package chucknorris;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
         * stage 5
         */
        Scanner scanner = new Scanner(System.in);
        String strCase = null;
        String encodedStr;

        while (!Objects.equals(strCase, "exit")) {
            System.out.println("Please input operation (encode/decode/exit):");
            strCase = scanner.nextLine();
            switch (strCase) {
                case "encode":
                    System.out.println("Input string:");
                    encodedStr = scanner.nextLine();
                    System.out.println("Encoded string:");
                    System.out.println(chuckNorrisEncode(encodedStr));
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    encodedStr = scanner.nextLine();
                    if (isValidEncodeStr(encodedStr)) {
                        System.out.println("Decoded string:");
                        System.out.println(chuckNorrisDecode(encodedStr));
                    } else {
                        System.out.println("Encoded string is not valid.");
                    }
                    break;
                case "exit":
                    strCase = "exit";
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.printf("There is no '%s' operation%n", strCase);
                    break;
            }
        }
    }

    public static String chuckNorrisEncode(String inputStr) {
        String binaryString = strToBinaryString(inputStr);
        return chuckNorrisEncoderBinary(binaryString);
    }

    private static String strToBinaryString(String inputStr) {
        StringBuilder binaryRepresentation = new StringBuilder();
        for (char currentChar : inputStr.toCharArray()) {
            binaryRepresentation.append(String.format("%07d", Integer.parseInt(Integer.toBinaryString(currentChar))));
        }
        return binaryRepresentation.toString();
    }

    private static String chuckNorrisEncoderBinary(String str) {
        StringBuilder chuckRepresent = new StringBuilder();
        boolean isFirstBlock = true;

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (isFirstBlock) {
                if (chars[i] == '1') {
                    chuckRepresent.append("0 0");
                } else {
                    chuckRepresent.append("00 0");
                }
                isFirstBlock = false;
            } else {
                if (chars[i] == chars[i - 1]) {
                    chuckRepresent.append("0");
                } else {
                    chuckRepresent.append(" ");
                    isFirstBlock = true;
                    i--;
                }
            }
        }
        return chuckRepresent.toString();
    }

    public static String chuckNorrisDecode(String str) {
        String binaryRepresent = chuckNorrisDecodeToBin(str);
        char[] binaryArr = binaryRepresent.toCharArray();
        StringBuilder stringFromBinary = new StringBuilder();
        for (int i = 0; i < binaryArr.length; i = i + 7) {
            char chrFromBin = (char) Integer.parseInt(binaryRepresent.substring(i, i + 7), 2);
            stringFromBinary.append(chrFromBin);
        }
        return stringFromBinary.toString();
    }

    private static String chuckNorrisDecodeToBin(String inputStr) {
        String[] strInChuckFormat = inputStr.split(" ");
        StringBuilder binaryRepresent = new StringBuilder();
        String firstSymbol;
        for (int i = 0; i < strInChuckFormat.length; i += 2) {
            if (strInChuckFormat[i].equals("00")) {
                firstSymbol = "0";
            } else {
                firstSymbol = "1";
            }
            binaryRepresent.append(firstSymbol.repeat(strInChuckFormat[i + 1].toCharArray().length));
        }
        return binaryRepresent.toString();
    }

    public static boolean isValidEncodeStr(String inputStr) {
        String[] strInBlocks = inputStr.split(" ");

        if (strInBlocks.length % 2 != 0) {
            return false;
        }
        for (char tempChar : inputStr.toCharArray()
        ) {
            if (tempChar != '0' && tempChar != ' ') {
                return false;
            }
        }
        if (chuckNorrisDecodeToBin(inputStr).length() % 7 != 0) {
            return false;
        }
        for (int i = 0; i < strInBlocks.length; i += 2) {
            if (strInBlocks[i].length() > 2) {
                return false;
            }
        }
        return true;
    }
}
