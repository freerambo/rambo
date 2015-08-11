
package com.rambo.erian;

import org.eclipse.jetty.server.Server;
import org.springside.modules.test.jetty.JettyFactory;
import org.springside.modules.test.spring.Profiles;


/**
 * use the embedded Jetty server to run and debug the Web application
 * input return in Console will reload the app.
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 
 * Create:  7 Nov, 2014 11:36:58 am
 */
public class QuickStartServer {

	public static final int PORT = 8888;
	public static final String CONTEXT = "/erian";
	public static final String[] TLD_JAR_NAMES = new String[] { "sitemesh", "spring-webmvc", "shiro-web",
			"springside-core" };

	/**
	 * 
	 * @function: main function
	 * @param args
	 * @throws Exception
	 * @author: zhuyuanbo    7 Nov, 2014 11:44:16 am
	 */
	public static void main(String[] args) throws Exception {
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

		// 启动Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

		try {
			server.start();

			System.out.println("[INFO] Server running at http://localhost:" + PORT + CONTEXT);
			System.out.println("[HINT] Hit Enter to reload the application quickly");

			// waitting for user Hit return, then reload the app.
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
