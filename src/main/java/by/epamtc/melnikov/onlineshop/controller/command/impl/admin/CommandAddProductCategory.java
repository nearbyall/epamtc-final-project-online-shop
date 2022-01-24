package by.epamtc.melnikov.onlineshop.controller.command.impl.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ProductService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

public class CommandAddProductCategory implements Command {

	private static final ProductService productService = ServiceProvider.getInstance().getProductService();
	
	private static final String UPLOAD_DIRECTORY = "productCategortImg";
	private static final int THRESHOLD_SIZE 	= 1024 * 1024 * 3; 	// 3MB
	private static final int MAX_FILE_SIZE 		= 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE 	= 1024 * 1024 * 50; // 50MB
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		List<FileItem> formItems;
		String filePath = StringUtils.EMPTY;
		String categoryName = StringUtils.EMPTY;
		String redirectCommand = StringUtils.EMPTY;
		String uploadPath = request.getServletContext().getRealPath("./") + UPLOAD_DIRECTORY;
		File uploadDirectory = new File(uploadPath);
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			//TODO request does not contain upload data
		}
		
		if (!uploadDirectory.exists()) {
			uploadDirectory.mkdir();
		}
		
		try {
			formItems = upload.parseRequest(request);
			for (FileItem fileItem : formItems) {
				if (fileItem.getFieldName().equals(JSPAttributeStorage.PRODUCT_CATEGORY_NAME)) {
					categoryName = fileItem.getString();
				}
				if (fileItem.getFieldName().equals(JSPAttributeStorage.REDIRECT_PAGE_COMMAND)) {
					redirectCommand = fileItem.getString();
				}
				if (fileItem.getFieldName().equals(JSPAttributeStorage.FILE)) {
					String fileName = new File(fileItem.getName()).getName();
					filePath = uploadPath + File.separator + fileName;
					File storeFile = new File(filePath);
					fileItem.write(storeFile);
				}
			}
		} catch (Exception e) {
			setErrorMessage(request, "service.commonError");
			result.setPage(PageStorage.ADD_PRODUCT_CATEGORY);
			result.setDirection(Direction.FORWARD);
		}
		
		ProductCategory category = new ProductCategory(categoryName, filePath);
		try {
			productService.addProductCategory(category);
            String redirectURL = getRedirectURL(request, redirectCommand);
            result.setPage(redirectURL);
            result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.ADD_PRODUCT_CATEGORY);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}

}
