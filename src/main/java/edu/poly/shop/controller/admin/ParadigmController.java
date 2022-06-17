package edu.poly.shop.controller.admin;

import edu.poly.shop.beans.__ParadigmModel;
import edu.poly.shop.entities._Category;
import edu.poly.shop.entities._Figure;
import edu.poly.shop.entities._Material;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.service.ICategoryService;
import edu.poly.shop.service.IFigureService;
import edu.poly.shop.service.IMaterialService;
import edu.poly.shop.service.IParadigmService;
import edu.poly.shop.utility.CommonConst;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/paradigm")
public class ParadigmController {
    @Autowired
    IFigureService figureService;
    @Autowired
    IMaterialService materialService;
    @Autowired
    ServletContext app;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IParadigmService paradigmService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/list")
    public String index(Model model, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        Page<_Paradigm> paradigmsPage = paradigmService.getByPage(pageNumber, CommonConst.PAGE_SIZE);
        model.addAttribute("LIST_PARADIGM", paradigmsPage);
        modelView(model);
        model.addAttribute("hrefPage","/admin/paradigm/list?pageNumber=");
        return "layout-admin";
    }


    @PostMapping("/saveOrUpdate")
    public String save(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            Model model,
            @Valid @ModelAttribute("PARADIGMMODEL") __ParadigmModel dto,
            BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()){
            Page<_Paradigm> paradigmsPage = paradigmService.getByPage(pageNumber, CommonConst.PAGE_SIZE);
            model.addAttribute("LIST_PARADIGM", paradigmsPage);
            model.addAttribute("PARADIGMMODEL",dto);
            model.addAttribute("ACTION", "/admin/paradigm/saveOrUpdate");
            model.addAttribute("title", "Thêm mới");
            model.addAttribute("form", "admin/paradigm/create-update");
            model.addAttribute("table", "admin/paradigm/view-paradigm");
            return "layout-admin";
        }else {
//        attributes.addFlashAttribute("messageTC","Thêm mới mô hình thành công");
        paradigmService.insert(dto,attributes);
        }

        return "redirect:/admin/paradigm/list";
    }

    @RequestMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") Integer id, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        Optional<_Paradigm> optional_paradigm = paradigmService.findById(id);
        __ParadigmModel dto = null;
        if (optional_paradigm.isPresent()) {
            _Paradigm paradigm = optional_paradigm.get();
            File file = new File("uploads/" + paradigm.getImage());
            FileInputStream inputStream;
            try {
                inputStream = new FileInputStream(file);
                MultipartFile multipartFileIMG = new MockMultipartFile("file", file.getName(), "text/plain",
                        IOUtils.toByteArray(inputStream));
                dto = new __ParadigmModel(paradigm.getId(),
                        paradigm.getParadigmName(),
                       paradigm.getPrice(),
                        paradigm.getQuantity(),
                        paradigm.getCategory().getId(),
                        multipartFileIMG,
                        paradigm.getFigure().getId(),
                        paradigm.getCreateDate(),
                        paradigm.getStatus(),
                        paradigm.getDimension(),
                        paradigm.getMaterial().getId()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("PARADIGMMODEL", dto);
        } else {
            model.addAttribute("PARADIGMMODEL", new __ParadigmModel());
        }
        Page<_Paradigm> paradigmsPage = paradigmService.getByPage(pageNumber, CommonConst.PAGE_SIZE);
        model.addAttribute("LIST_PARADIGM", paradigmsPage);
        model.addAttribute("ACTION", "/admin/paradigm/saveOrUpdate");
        model.addAttribute("title", "Cập nhập");
        model.addAttribute("form", "admin/paradigm/create-update");
        model.addAttribute("table", "admin/paradigm/view-paradigm");
        return "layout-admin";
    }


    @RequestMapping("/find")
    public String findName(Model model,
                           @RequestParam (value = "name") String name,
                           @RequestParam(name = "pageNumber", defaultValue = "0" ,required = false) Optional<Integer> pageNumber
    ){
        List<_Paradigm> paradigmListByName = paradigmService.findName(name);
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), CommonConst.PAGE_SIZE);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), paradigmListByName.size());
        modelView(model);
        model.addAttribute("LIST_PARADIGM", new PageImpl<>(paradigmListByName.subList(start, end), pageable, paradigmListByName.size()));
        model.addAttribute("hrefPage","/admin/paradigm/find?name="+name+"&pageNumber=");
        return "layout-admin";
    }

    public void modelView(Model model){
        model.addAttribute("PARADIGMMODEL",  new __ParadigmModel());
        model.addAttribute("ACTION", "/admin/paradigm/saveOrUpdate");
        model.addAttribute("title", "Thêm mới");
        model.addAttribute("form", "admin/paradigm/create-update");
        model.addAttribute("table", "admin/paradigm/view-paradigm");
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        paradigmService.delete(id);
        return "redirect:/admin/paradigm/list";
    }

    @ModelAttribute("CATEGORIES")
    public List<_Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("MATERIALS")
    public List<_Material> materials() {
        return materialService.findAll();
    }

    @ModelAttribute("FIGURES")
    public List<_Figure> figures() {
        return figureService.findAll();
    }
}
