package next;

import java.io.File;

import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServerLauncher {
    private static final Logger log = LoggerFactory.getLogger(WebServerLauncher.class);

    public static void main(String[] args) throws Exception {
        log.info("==== WebServerLauncher start ====");
        String webappDirLocation = "webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        log.info("configuring app with basedir: {}", new File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
