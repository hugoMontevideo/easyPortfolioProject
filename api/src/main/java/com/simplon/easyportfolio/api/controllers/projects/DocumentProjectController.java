package com.simplon.easyportfolio.api.controllers.projects;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.projects.DocumentProjectServiceRequestModel;
import com.simplon.easyportfolio.api.services.projects.DocumentProjectServiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Validated
@CrossOrigin
@RestController
@RequestMapping("api/projects")
public class DocumentProjectController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

   @PostMapping("/{id}/documents")
    public DocumentProjectGetDTO addProjectPicture(@RequestParam ("title") String title,
                                  @RequestParam ("filename") Optional<String> filename,
                                  @RequestParam ("file")Optional<MultipartFile> file,
                                  @RequestParam ("projectId")Long projectId){
        // creating DTO with received parameters
        DocumentProjectDTO DTO = new DocumentProjectDTO(title, filename, file, projectId);
        DocumentProjectServiceRequestModel documentServiceRequestModel =
                mapper.documentProjectDtoToServiceRequestModelAdd(DTO);

        DocumentProjectServiceResponseModel addedDocProject =
                portfolioService.saveDocumentProject(documentServiceRequestModel);
        return mapper.documentProjectSvcToGetDTO( addedDocProject );
    }




}
