package cs2110;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CsvJoin {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: cs2110.CsvJoin <left_table.csv> <right_table.csv>");
            System.exit(1);
        }

        try {
            Seq<Seq<String>> joined = join(csvToList(args[0]), csvToList(args[1]));
            System.out.print(listToCSV(joined));
        } catch (IOException e) {
            System.err.println("Error: Could not read input tables.");
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (AssertionError e) {
            System.err.println("Error: Input tables are not rectangular.");
            System.exit(1);
        }
    }

    /**
     * Return a string that represents the table in a Simplified CSV format
     */
    public static String listToCSV(Seq<Seq<String>> table) {
        StringBuilder output = new StringBuilder();
        for (Seq<String> row : table) {
            for (String token : row) {
                output.append(token);
                output.append(",");
            }
            output.deleteCharAt(output.length() - 1);
            output.append("\n");
        }
        output.deleteCharAt(output.length() - 1);
        return output.toString();
    }

    /**
     * Load a table from a Simplified CSV file and return a row-major list-of-lists representation.
     * The CSV file is assumed to be in the platform's default encoding. Throws an IOException if
     * there is a problem reading the file.
     */
    public static Seq<Seq<String>> csvToList(String file) throws IOException {
        Scanner input = new Scanner(new File(file));
        Seq<Seq<String>> rows = new LinkedSeq<>();
        while(input.hasNextLine()) {
            String in = input.nextLine();
            String[] tokens = in.split(",", -1);
            LinkedSeq<String> line = new LinkedSeq<>();
            for (String token : tokens) {
                line.append(token);
            }
            rows.append(line);
        }
        return rows;
    }

    /**
     * Return the left outer join of tables `left` and `right`, joined on their first column. Result
     * will represent a rectangular table, with empty strings filling in any columns from `right`
     * when there is no match. Requires that `left` and `right` represent rectangular tables with at
     * least 1 column.
     */
    public static Seq<Seq<String>> join(Seq<Seq<String>> left, Seq<Seq<String>> right) {
        assertRectangular(left);
        assertRectangular(right);

        Seq<Seq<String>> output = new LinkedSeq<>();
        int cols = left.get(0).size() + right.get(0).size() - 1;

        for (Seq<String> leftRow : left) {
            String key = leftRow.get(0);
            boolean foundInRightRow = false;

            for (Seq<String> rightRow : right) {
                if (rightRow.get(0).equals(key)) {
                    LinkedSeq<String> row = new LinkedSeq<>();
                    foundInRightRow = true;
                    //cloning the leftRow over
                    for (String token : leftRow) {
                        row.append(token);
                    }
                    //cloning the rightRow over
                    boolean skipFirst = true;
                    for (String token : rightRow) {
                        if (skipFirst) {
                            skipFirst = false;
                            continue;
                        }
                        row.append(token);
                    }
                    //add to output
                    output.append(row);
                }
            }

            if (!foundInRightRow) {
                LinkedSeq<String> row = new LinkedSeq<>();
                for (String token : leftRow) {
                    row.append(token);
                }
                while (row.size() < cols) {
                    row.append("");
                }
                output.append(row);
            }
        }
        assertRectangular(output);
        return output;
    }

    public static void assertRectangular(Seq<Seq<String>> table) {
        assert table.size() > 0;
        int cols = table.get(0).size();
        assert cols > 0;
        for (Seq<String> row : table) {
            assert cols == row.size();
        }
    }
}
