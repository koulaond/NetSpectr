package rest;

import org.springframework.web.bind.annotation.RequestMapping;
import service.WebsiteService;

@RequestMapping(path = "/website")
public class WebsiteController extends RepositoryController<WebsiteService> {

}
