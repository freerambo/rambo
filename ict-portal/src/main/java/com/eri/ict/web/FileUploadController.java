package com.eri.ict.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLConnection;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.utils.Clock;

import com.eri.ict.entity.UploadFile;
import com.eri.ict.entity.User;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.eri.ict.service.uploadFile.IUploadFileService;
import com.rambo.common.params.FileConstants;

/**
 * 
 * @description Handles requests for the application file upload requests
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 20, 2015 8:07:10 PM
 */
@Controller
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private IUploadFileService uploadFileService;

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public /* @ResponseBody */ String uploadFileHandler(
			@RequestParam(value = "name", defaultValue = "placeholder") String name,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		String message = "";
		if (!file.isEmpty()) {
			name = file.getOriginalFilename();
			logger.info(name);
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file

				File dir = new File(FileConstants.UPLOAD_PATH);
				if (!dir.exists())
					dir.mkdirs();

				logger.info(dir.getAbsolutePath() + File.separator + name);
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				if (serverFile.exists()) {
					serverFile.delete();
				}
				serverFile.createNewFile();
				FileCopyUtils.copy(bytes, serverFile);

				// String format = "unKnown";
				// String fName = name;
				// int end = name.lastIndexOf(".");
				// if (end > 0) {
				// format = name.substring(end + 1);
				// fName = name.substring(0, end);
				// }

				String mimeType = URLConnection.guessContentTypeFromName(name);
				logger.info(mimeType + "\n" + "Server File Location=" + serverFile.getAbsolutePath());

				if (mimeType == null) {
					System.out.println("mimetype is not detectable, will take default");
					mimeType = "application/octet-stream";
				}

				UploadFile uploaded = new UploadFile();
				uploaded.setName(name);
				uploaded.setType(mimeType);
				uploaded.setStatus("active");
				// uploaded.setAuthor(new User(getCurrentUserId()));
				uploaded.setAuthor(new User(1L));
				uploaded.setUploadTime(Clock.DEFAULT.getCurrentDate());

				logger.info(uploaded.toString());

				uploadFileService.saveUploadFile(uploaded);

				message = "You successfully uploaded file=" + name;

			} catch (Exception e) {
				// e.printStackTrace();
				message = "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			message = "You failed to upload " + name + " because the file was empty.";
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/uploadFile/";
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name + "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}