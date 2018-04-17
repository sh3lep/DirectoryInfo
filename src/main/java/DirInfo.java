import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;

public class DirInfo {

    public static void main(String[] args) throws IOException {

        CmdLineArgs values = new CmdLineArgs(args);

        boolean l = values.longFormat;
        boolean h = values.humanReadable;
        boolean r = values.reverse;
        boolean o = values.out != null;

        File dir = new File(values.dir);

        if (!dir.exists()) throw new IllegalArgumentException("No such file or directory found");

        boolean d = dir.isDirectory(); // Дополнительный флаг: директория или нет (файл)
        File outputName = new File(".//testOutput//" + values.out);

        ArrayList<String> res = getInfo(l, h, r, d, dir);

        toWrite(res, o, outputName);

    }

    private static String getRWX(File file, boolean flag) { // Проверка на чтение-запись-выполнение в 2х видах
        String res = "";
        if (flag) {

            if (file.canRead()) res += 1;
            else res += 0;
            if (file.canWrite()) res += 1;
            else res += 0;
            if (file.canExecute()) res += 1;
            else res += 0;

        } else {

            if (file.canRead()) res += "r";
            else res += "-";
            if (file.canWrite()) res += "w";
            else res += "-";
            if (file.canExecute()) res += "x";
            else res += "-";

        }

        return res;
    }

    private static String fromBytes(File file) { // Перевод из байтов. Добавить округление?
        String res;
        Long length = file.length();
        if (file.length() >= 1073741824) { // Больше гб или нет
            res = Double.toString((double) length / 1073741824.0) + " GB";
        } else {
            if (file.length() >= 1048576) {
                res = Double.toString((double) length / 1048576.0) + " MB"; // Больше мб или нет
            } else {
                res = Double.toString((double) length / 1024.0) + " KB"; // Т.к меньше МБ, то подсчет в КБ
            }
        }
        return res;
    }

    private static String getTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    private static String getData(boolean lh, File dir) {

        StringBuilder data = new StringBuilder();

        if (!lh) { // Если просто расширенный формат
            data.append(getRWX(dir, true)).append(" ");
            data.append(dir.lastModified()).append(" ");
            data.append(dir.length()).append(" Bytes");
        } else { // Если человеко-читаемый формат
            data.append(getRWX(dir, false)).append(" ");
            data.append(getTime(dir)).append(" ");
            data.append(fromBytes(dir));
        }
        return data.toString();
    }

    private static ArrayList<String> getInfo(boolean l, boolean h, boolean r, boolean d, File dir) {

        ArrayList<String> info = new ArrayList<>();

        if (!d || dir.listFiles() == null) { // Если не директория -> файл
            StringBuilder cur = new StringBuilder();
            cur.append(dir.getName()).append(" ");

            if (l) cur.append(getData(h, dir));

            info.add(cur.toString());

        } else { // Если директория
            File[] list = dir.listFiles();

            if (!l && !h && (list != null)) for (File file : list) info.add(file.getName());

            if (l && (list != null)) { // Если длинный формат
                for (File file : list) {
                    String cur = file.getName() + " " + getData(h, file);
                    info.add(cur);
                }
            }
        }
        if (r) Collections.reverse(info);
        return info;
    }

    private static void toWrite(ArrayList<String> res, boolean o, File outputName) {
        if (o) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName.toURI()))) {
                for (String info : res) {
                    writer.write(info);
                    writer.newLine();
                }
            } catch (IOException ex) {
                System.out.println(":(");
            }
        } else {
            for (String info : res) System.out.println(info);
        }
    }
}
