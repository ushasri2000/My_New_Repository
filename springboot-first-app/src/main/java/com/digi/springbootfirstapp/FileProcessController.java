package com.digi.springbootfirstapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//@Controller
//public class FileProcessController {
//    @GetMapping("/")
//    public String showUploadForm() {
//        return "upload-form";
//    }
//
//    @PostMapping("/processFile")
//    public ModelAndView processFiles(@RequestParam("file") MultipartFile file) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        if (!file.isEmpty()) {
//            try {
//                String originalFilename = file.getOriginalFilename().toLowerCase();
//
//                if (originalFilename.endsWith(".xml")) {
//                    XMLParsingExample xmlParser = new XMLParsingExample();
//                    String parsedAttributes = xmlParser.parseXML(file.getInputStream());
//                    modelAndView.addObject("attributes", parsedAttributes);
//                    // } else if (originalFilename.endsWith(".xlsx")) {
//                } else {
//                    XLSXReaderExample excelParser = new XLSXReaderExample();
//                    String parsedAttributes = XLSXReaderExample.parseXLSX(file.getInputStream());
//                    modelAndView.addObject("attributes", parsedAttributes);
//                }
//
//                modelAndView.setViewName("result-page"); // Display parsed attributes
//            } catch (Exception e) {
//                modelAndView.addObject("error", "Error processing file: " + e.getMessage());
//                modelAndView.setViewName("error");
//            }
//        } else {
//            modelAndView.addObject("error", "No file uploaded");
//            modelAndView.setViewName("error");
//        }
////        System.out.println("ERROR");
//
//        return modelAndView;
//    }
//}




// ... (import statements and annotations)

@Controller
public class FileProcessController {
    @GetMapping("/")
    public String showUploadForm() {
        return "upload-form";
    }

    @PostMapping("/processFile")
    public ModelAndView processFiles(
            @RequestParam("file") MultipartFile file,
            @RequestParam("searchValue") String searchValue // New parameter for search value
    ) {
        ModelAndView modelAndView = new ModelAndView();

        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename().toLowerCase();

                if (originalFilename.endsWith(".xml")) {
                    XMLParsingExample xmlParser = new XMLParsingExample();
                    String parsedAttributes = xmlParser.parseXML(file.getInputStream(), searchValue);
                    modelAndView.addObject("attributes", parsedAttributes);
                } else if (originalFilename.endsWith(".xlsx")) {
                    XLSXReaderExample excelParser = new XLSXReaderExample();
                    String parsedAttributes = excelParser.parseXLSX(file.getInputStream(), searchValue).toString(); // Pass search value
                    modelAndView.addObject("attributes", parsedAttributes);
                }

                modelAndView.setViewName("result-page"); // Display parsed attributes
            } catch (Exception e) {
                modelAndView.addObject("error", "Error processing file: " + e.getMessage());
                modelAndView.setViewName("error");
            }
        } else {
            modelAndView.addObject("error", "No file uploaded");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
}

