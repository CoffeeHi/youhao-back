package com.chenx.gateway.web.admin;

import com.chenx.gateway.web.admin.dto.Product;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/13 0013.
 */
@Log4j
@Controller
public class ProductController {

    @RequestMapping(value = "/product_input", method = RequestMethod.GET)
    public String inputProduct(Model model){
        model.addAttribute("product", new Product());
        return "ProductForm";
    }

    @RequestMapping(value = "/product_save")
    public String saveProduct(HttpServletRequest servletRequest,
                              @ModelAttribute Product product,
                              BindingResult bindingResult, Model model){
        List<MultipartFile> files = product.getImages();

        List<String> fileNames = new ArrayList<String>();

        if (null != files && files.size() > 0){
            for (MultipartFile multipartFile : files){
                String suffix = multipartFile.getOriginalFilename();
                String fileName = suffix;
//                String fileName = UUID.randomUUID().toString() + suffix.substring(suffix.lastIndexOf('.'));
                fileNames.add(fileName);
                File imageFile = new File(servletRequest.getServletContext().getRealPath("/image"), fileName);
                try{
                    multipartFile.transferTo(imageFile);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        //todo save product here
        model.addAttribute("product", product);
        return "ProductDetail";
    }
}
