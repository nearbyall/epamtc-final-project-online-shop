package by.epamtc.melnikov.onlineshop.controller.command.impl.admin;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import by.epamtc.melnikov.onlineshop.bean.Product;
import by.epamtc.melnikov.onlineshop.bean.ProductCategory;
import by.epamtc.melnikov.onlineshop.bean.builder.ProductBuilder;
import by.epamtc.melnikov.onlineshop.controller.JSPAttributeStorage;
import by.epamtc.melnikov.onlineshop.controller.PageStorage;
import by.epamtc.melnikov.onlineshop.controller.command.Command;
import by.epamtc.melnikov.onlineshop.controller.command.CommandResult;
import by.epamtc.melnikov.onlineshop.controller.command.Direction;
import by.epamtc.melnikov.onlineshop.service.ProductService;
import by.epamtc.melnikov.onlineshop.service.ServiceProvider;
import by.epamtc.melnikov.onlineshop.service.exception.ServiceException;

/**
 * The implementation of the {@link Command} interface that is responsible
 * for adding the product {@link Product}.
 * 
 * @author nearbyall
 *
 */
public class CommandAddProduct implements Command {

	private static final ProductService productService = ServiceProvider.getInstance().getProductService();
	
	private static final String UPLOAD_DIRECTORY = "productImg";
	private static final int THRESHOLD_SIZE 	= 1024 * 1024 * 3; 	// 3MB
	private static final int MAX_FILE_SIZE 		= 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE 	= 1024 * 1024 * 50; // 50MB
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandResult result = new CommandResult();
		
		List<FileItem> formItems;
		String filePath = StringUtils.EMPTY;
		String productTitle = StringUtils.EMPTY;
		String productDescription = StringUtils.EMPTY;
		Integer categoryId = null;
		Integer productCount = null;
		Double productPrice = null;
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
			setErrorMessage(request, "uploading.uploadDataIsNotExist");
			result.setPage(PageStorage.ADD_PRODUCT);
			result.setDirection(Direction.FORWARD);
			return result;
		}
		
		if (!uploadDirectory.exists()) {
			uploadDirectory.mkdir();
		}
		
		try {
			formItems = upload.parseRequest(request);
			for (FileItem fileItem : formItems) {
				if (fileItem.getFieldName().equals(JSPAttributeStorage.PRODUCT_TITLE)) {
					productTitle = fileItem.getString();
				}
				if (fileItem.getFieldName().equals(JSPAttributeStorage.PRODUCT_DESCRIPTION)) {
					productDescription = fileItem.getString();
				}
				if (fileItem.getFieldName().equals(JSPAttributeStorage.PRODUCT_CATEGORY)) {
					categoryId = Integer.parseInt(fileItem.getString());
				}
				if (fileItem.getFieldName().equals(JSPAttributeStorage.PRODUCT_COUNT)) {
					productCount = Integer.parseInt(fileItem.getString());
				}
				if (fileItem.getFieldName().equals(JSPAttributeStorage.PRODUCT_PRICE)) {
					productPrice =Double.parseDouble(fileItem.getString());
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
			result.setPage(PageStorage.ADD_PRODUCT);
			result.setDirection(Direction.FORWARD);
			return result;
		}
		
		java.util.Date incomingValue = new java.util.Date(System.currentTimeMillis());
		Timestamp currentTimestamp = new Timestamp(incomingValue.getTime());
		
		Product product = new ProductBuilder()
				.withTitle(productTitle)
				.withDescription(productDescription)
				.withCount(productCount)
				.withPrice(productPrice)
				.withCreatedAt(currentTimestamp)
				.withUpdatedAt(currentTimestamp)
				.withCategory(new ProductCategory(categoryId))
				.withImgPath(filePath)
				.build();
		
		try {
			productService.addProduct(product);
			String redirectURL = getRedirectURL(request, redirectCommand);
			result.setPage(redirectURL);
			result.setDirection(Direction.REDIRECT);
		} catch (ServiceException e) {
			setErrorMessage(request, e.getMessage());
			result.setPage(PageStorage.ADD_PRODUCT);
			result.setDirection(Direction.FORWARD);
		}
		
		return result;
		
	}
	
}
