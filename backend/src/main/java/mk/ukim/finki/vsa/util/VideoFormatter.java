package mk.ukim.finki.vsa.util;

import java.io.IOException;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public class VideoFormatter {
    private static String UPLOADED_FOLDER = "/home/konstantin/Videos/";

    VideoFormatter(String path) throws IOException {
        String command = "MP4Box -dash 2000 -profile dashavc264:live -bs-switching multi -url-template " + UPLOADED_FOLDER + path + "#trackID=1:id=vid0:role=vid0 " + UPLOADED_FOLDER + path + "#trackID=2:id=aud0:role=aud0 -out " + UPLOADED_FOLDER + path + ".mpd";
        Process p = Runtime.getRuntime().exec(command);
    }

}
